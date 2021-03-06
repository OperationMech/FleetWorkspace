package fleet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import fleet.R;
import fleet.gameLogic.PlayerGameBoard;
import fleet.gameLogic.Ship;
import fleet.gameLogic.ShipClass;
import fleet.gameLogic.TransferBuffer;
import fleet.gameLogic.players.AbstractPlayer;
import fleet.gameLogic.players.AdvancedCPU;
import fleet.gameLogic.players.ComputerPlayer;
import fleet.gameLogic.players.HumanPlayer;
import fleet.view.PlayView;
import fleet.gameLogic.Fleet;

/**
 * PlayActivity class
 *
 * Authors: Anthony Cali and Conner Ferguson
 */
public class PlayActivity extends Activity {
    protected ArrayList<PlayView> activePlayers;
    private ArrayList<AbstractPlayer> players;
    private Bundle selfBundle = new Bundle();
    private int nextPlayerID;
    private int currentPlayerID;
    private PlayActivity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selfBundle = savedInstanceState;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        self = this;
        activePlayers = new ArrayList<PlayView>();
        players = new ArrayList<AbstractPlayer>();
        nextPlayerID = 0;
        currentPlayerID = 0;
        populatePlayers();
        nextTurn();
    }

    /**
     * @param fleetPath   File directory for the fleet to be used
     * @param staticBoard Boolean, if true makes a statically defined board, else randomly generates a legal board
     * @return a populated game board.
     */
    private PlayerGameBoard createBoard(String fleetPath, boolean staticBoard) {
        Fleet aiFleet = new Fleet(fleetPath);
        PlayerGameBoard aiBoard = new PlayerGameBoard();
        AssetManager assetManager = getAssets();
        try {
            String[] fleetFiles = (assetManager.list(fleetPath));
            for (String cardPath : fleetFiles) {
                if (!cardPath.startsWith("MainAttack.")) {
                    InputStream cardStream = assetManager.open(fleetPath + "/" + cardPath);
                    Bitmap cardImg = BitmapFactory.decodeStream(cardStream);
                    aiFleet.populateFleet(cardPath, cardImg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        aiBoard.setFaceDown(aiFleet.getFacedown());
        if (staticBoard) {
            //Static board for testing, or for an easier opponent
            aiBoard.fleetPositions[0] = aiFleet.getCarrier();
            aiBoard.fleetPositions[1] = aiFleet.getBattleships()[0];
            aiBoard.fleetPositions[2] = aiFleet.getBattleships()[1];
            aiBoard.fleetPositions[3] = aiFleet.getBattleships()[2];
            aiBoard.fleetPositions[4] = aiFleet.getCruisers()[0];
            aiBoard.fleetPositions[5] = aiFleet.getCruisers()[1];
            aiBoard.fleetPositions[6] = aiFleet.getDestroyers()[0];
            aiBoard.fleetPositions[7] = aiFleet.getDestroyers()[1];
            aiBoard.fleetPositions[8] = aiFleet.getDestroyers()[2];
        } else {
            //Makes a random board
            int battleShips = 1;
            int destroyers = 1;
            int cruisers = 1;
            int localRandom;
            boolean shipAdded;
            ArrayList<Integer> positions = new ArrayList<Integer>();
            for (int i = 0; i < 9; i++)
                positions.add(i);
            Collections.shuffle(positions);
            //Forcing the board to have at least one of each class
            int nextShipPos = positions.remove(0);
            aiBoard.fleetPositions[nextShipPos] = aiFleet.getCarrier();
            nextShipPos = positions.remove(0);
            aiBoard.fleetPositions[nextShipPos] = aiFleet.getCruisers()[0];
            nextShipPos = positions.remove(0);
            aiBoard.fleetPositions[nextShipPos] = aiFleet.getDestroyers()[0];
            nextShipPos = positions.remove(0);
            aiBoard.fleetPositions[nextShipPos] = aiFleet.getBattleships()[0];
            while (positions.size() != 0) {
                shipAdded = false;
                localRandom = Math.abs(new Random().nextInt());
                Collections.shuffle(positions);
                while (!shipAdded) {
                    if (localRandom % 3 == 1) {
                        if (cruisers < 4) {
                            nextShipPos = positions.remove(0);
                            aiBoard.fleetPositions[nextShipPos] = aiFleet.getCruisers()[cruisers];
                            cruisers++;
                            shipAdded = true;
                        } else {
                            localRandom++;
                        }
                    } else if (localRandom % 3 == 2) {
                        if (battleShips < 4) {
                            nextShipPos = positions.remove(0);
                            aiBoard.fleetPositions[nextShipPos] = aiFleet.getBattleships()[battleShips];
                            battleShips++;
                            shipAdded = true;
                        } else {
                            localRandom++;
                        }
                    } else {
                        if (destroyers < 4) {
                            nextShipPos = positions.remove(0);
                            aiBoard.fleetPositions[nextShipPos] = aiFleet.getDestroyers()[destroyers];
                            destroyers++;
                            shipAdded = true;
                        } else {
                            localRandom++;
                        }
                    }
                }
            }
        }
        return aiBoard;
    }

    private void populatePlayers() {
        HumanPlayer humanPlayer = new HumanPlayer(TransferBuffer.board, nextPlayerID);
        players.add(nextPlayerID, humanPlayer);
        nextPlayerID++;
        PlayView humanPlayView = new PlayView(this, humanPlayer);
        activePlayers.add(humanPlayView);
        int fleetPathNum = Math.abs(new Random().nextInt()) % TransferBuffer.unusedFleetPaths.size();
        PlayerGameBoard computerBoard = createBoard(TransferBuffer.unusedFleetPaths.get(fleetPathNum), MenuData.staticAiBoard);
        AbstractPlayer computerPlayer;
        if(MenuData.isHardMode) {
            computerPlayer = new AdvancedCPU(computerBoard, nextPlayerID);
        } else {
            computerPlayer = new ComputerPlayer(computerBoard,nextPlayerID);
        }
        players.add(nextPlayerID, computerPlayer);
        nextPlayerID++;
        PlayView computerPlayView = new PlayView(this, computerPlayer);
        activePlayers.add(computerPlayView);
    }

    public void nextTurn() {
        if (currentPlayerID < players.size() - 1) {
            currentPlayerID++;
        } else {
            currentPlayerID = 0;
        }
        if (players.size() < 2) {
            endGame();
            return;
        }
        activePlayers.get(currentPlayerID).viewer = players.get(currentPlayerID);
        AbstractPlayer currentPlayer = players.get(currentPlayerID);
        currentPlayer.setAttacked(false);
        if (currentPlayer.getClass() == HumanPlayer.class) {
            activePlayers.get(currentPlayerID).viewer = currentPlayer;
            setContentView(activePlayers.get(currentPlayerID));
        } else if (currentPlayer.getClass() != HumanPlayer.class) {
            attackAction(currentPlayer);
            if (currentPlayer.getGameBoard().hasCarrier()) {
                currentPlayer.scout(players);
                scoutAction(currentPlayer);
            }
            if (players.size() < 2) {
                endGame();
            } else {
                nextTurn();
            }
        }
    }

    public void endGame() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        System.exit(0);
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        self.onCreate(selfBundle);
                        break;
                }
            }
        };
        // The actual dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Game finished. Play again?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    public void surrender() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        players.get(currentPlayerID).setAttacker(null);
                        self.attackAction(players.get(currentPlayerID));
                        break;
                }
            }
        };
        // The actual dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you wish to surrender?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    public void attackAction(AbstractPlayer player) {
        Ship[] shipAndTarget;
        shipAndTarget = player.attack(players);
        if (shipAndTarget[0] == null) {
            if (player.getClass().equals(HumanPlayer.class)) {
                Toast.makeText(this, "You Lose", Toast.LENGTH_LONG).show();
                activePlayers.get(player.getPlayerID()).isDefeated = true;
            }
            players.remove(player);
            nextTurn();
        }
        if (shipAndTarget[0] != null && !shipAndTarget[0].shipClass.equals(ShipClass.CARRIER)) {
            shipAndTarget[1].sinkShip(battle(shipAndTarget[0], shipAndTarget[1]));
            if (shipAndTarget[1].getStatus()) {
                Toast.makeText(this, shipAndTarget[1].getShipNum() + " " + shipAndTarget[1].shipClass.getName() + ": Remains afloat", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, shipAndTarget[1].getShipNum() + " " + shipAndTarget[1].shipClass.getName() + ": Is no more", Toast.LENGTH_SHORT).show();
            }
            player.setAttacked(true);
        } else {
            if (player.getClass().equals(HumanPlayer.class) && player.getGameBoard().hasCarrier()) {
                Toast.makeText(this, "Carrier can't attack", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void scoutAction(AbstractPlayer player) {
        player.getScoutTarget().reveal();
    }

    /**
     * Battle resolve function
     *
     * @param attacker ship selected by the attacker
     * @param defender target ship
     * @return boolean value for target
     */
    protected boolean battle(Ship attacker, Ship defender) {
        attacker.reveal();
        defender.reveal();

        // When the classes match draw
        if (attacker.shipClass == defender.shipClass) {
            return false;
        }
        // Rock - paper - scissor logic
        if (defender.shipClass == ShipClass.CARRIER) {
            return true;
        } else {
            switch (attacker.shipClass) {
                case DESTROYER:
                    if (defender.shipClass == ShipClass.BATTLESHIP) {
                        return true;
                    }
                    break;

                case CRUISER:
                    if (defender.shipClass == ShipClass.DESTROYER) {
                        return true;
                    }
                    break;

                case BATTLESHIP:
                    if (defender.shipClass == ShipClass.CRUISER) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    public void showCurrentPlayerView() {
        setContentView(activePlayers.get(currentPlayerID));
    }

    public void getNextPlayerView() {
        if (currentPlayerID < activePlayers.size() - 1) {
            activePlayers.get(currentPlayerID + 1).viewer = players.get(currentPlayerID);
            setContentView(activePlayers.get(currentPlayerID + 1));
        } else {
            activePlayers.get(0).viewer = players.get(currentPlayerID);
            setContentView(activePlayers.get(0));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fleetplay, menu);
        menu.findItem(R.id.music_mute).setChecked(MenuData.musicMuted);
        menu.findItem(R.id.effects_enabled).setChecked(MenuData.soundEffectsEnabled);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.music_mute:
                MenuData.musicMuted = !MenuData.musicMuted;
                item.setChecked(!item.isChecked());
                return true;
            case R.id.effects_enabled:
                MenuData.soundEffectsEnabled = !MenuData.soundEffectsEnabled;
                item.setChecked(!item.isChecked());
                return true;
            default:
                return false;
        }
    }

}
