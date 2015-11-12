package fleet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Selection;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import fleet.R;
import fleet.gameLogic.PlayerGameBoard;
import fleet.gameLogic.Ship;
import fleet.gameLogic.ShipClass;
import fleet.gameLogic.TransferBuffer;
import fleet.gameLogic.players.AbstractPlayer;
import fleet.gameLogic.players.ComputerPlayer;
import fleet.gameLogic.players.HumanPlayer;
import fleet.view.PlayView;
import fleet.gameLogic.Fleet;

/**
 * Created by Radu on 10/18/2015.
 * <p/>
 * //                         \\
 * ||   !!UNDER CONSTRUCTION!!  ||
 * \\                         //
 */
public class PlayActivity extends Activity {

    private AssetManager assetManager;
    private ArrayList<Fleet> Fleets = new ArrayList<Fleet>();
    protected Boolean musicMuted;
    protected ArrayList<PlayView> activePlayers = new ArrayList<PlayView>();
    private ArrayList<AbstractPlayer> players = new ArrayList<AbstractPlayer>();
    private int nextPlayerID = 0;
    private int currentPlayerID = 0;
    private boolean isWon = false;
    private Context localActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        musicMuted = bundle.getBoolean("musicMuted");

        populatePlayers();
        nextTurn();
    }

    private PlayerGameBoard createBoard(String fleetPath) {
        Fleet aiFleet = new Fleet(fleetPath);
        PlayerGameBoard aiBoard = new PlayerGameBoard();
        assetManager = getAssets();
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
        aiBoard.fleetPositions[0] = aiFleet.getCarrier();
        aiBoard.fleetPositions[1] = aiFleet.getBattleships()[0];
        aiBoard.fleetPositions[2] = aiFleet.getBattleships()[1];
        aiBoard.fleetPositions[3] = aiFleet.getBattleships()[2];
        aiBoard.fleetPositions[4] = aiFleet.getCruisers()[0];
        aiBoard.fleetPositions[5] = aiFleet.getCruisers()[1];
        aiBoard.fleetPositions[6] = aiFleet.getDestroyers()[0];
        aiBoard.fleetPositions[7] = aiFleet.getDestroyers()[1];
        aiBoard.fleetPositions[8] = aiFleet.getDestroyers()[2];
        aiBoard.setFaceDown(aiFleet.getFacedown());
        return aiBoard;
    }

    private void populatePlayers() {
        HumanPlayer humanPlayer = new HumanPlayer(TransferBuffer.board, nextPlayerID);
        players.add(nextPlayerID, humanPlayer);
        nextPlayerID++;
        PlayView humanPlayView = new PlayView(this, humanPlayer);
        activePlayers.add(humanPlayView);
        //TODO:make a different board for computer player
        PlayerGameBoard computerBoard = createBoard(TransferBuffer.unusedFleetPaths.get(0));
        ComputerPlayer computerPlayer = new ComputerPlayer(computerBoard, nextPlayerID);
        players.add(nextPlayerID, computerPlayer);
        nextPlayerID++;
        PlayView computerPlayView = new PlayView(this, computerPlayer);
        activePlayers.add(computerPlayView);
    }

    public void nextTurn() {
        if (currentPlayerID < activePlayers.size() - 1) {
            currentPlayerID++;
        } else {
            currentPlayerID = 0;
        }
        activePlayers.get(currentPlayerID).viewer = players.get(currentPlayerID);
        AbstractPlayer currentPlayer = players.get(currentPlayerID);
        currentPlayer.setAttacked(false);
        if (currentPlayer.getClass() == HumanPlayer.class) {
            activePlayers.get(currentPlayerID).viewer = currentPlayer;
            setContentView(activePlayers.get(currentPlayerID));
        } else if (currentPlayer.getClass() == ComputerPlayer.class) {
            attackAction(currentPlayer);
            if (currentPlayer.getGameBoard().hasCarrier()) {
                currentPlayer.scout(players);
                scoutAction(currentPlayer);
            }
            if(players.size() < 2 ) {
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
                switch(which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        System.exit(0);
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        System.exit(0);
                        break;
                }
            }
        };
        // The actual dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Game finished. Play again?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No",dialogClickListener).show();

    }

    public void attackAction(AbstractPlayer player) {
        Ship[] shipAndTarget = new Ship[2];
        shipAndTarget = player.attack(players);
        player.setAttacked(true);
        if (shipAndTarget[1] == null) {
            if (player.getClass().equals(HumanPlayer.class)) {
                Toast.makeText(this, "You Lose", Toast.LENGTH_LONG).show();
                activePlayers.get(player.getPlayerID()).isDefeated = true;
            }
            players.remove(player);
        }
        if (shipAndTarget[0] != null && !shipAndTarget[0].shipClass.equals(ShipClass.CARRIER)) {
            shipAndTarget[1].sinkShip(battle(shipAndTarget[0], shipAndTarget[1]));
            if (shipAndTarget[1].getStatus()) {
                Toast.makeText(this, shipAndTarget[1].getShipNum() + " " + shipAndTarget[1].shipClass.getName() + ": Remains afloat", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, shipAndTarget[1].getShipNum() + " " + shipAndTarget[1].shipClass.getName() + ": Is no more", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (player.getClass().equals(HumanPlayer.class)) {
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

                case CRUISER:
                    if (defender.shipClass == ShipClass.DESTROYER) {
                        return true;
                    }

                case BATTLESHIP:
                    if (defender.shipClass == ShipClass.CRUISER) {
                        return true;
                    }
            }
        }
        return false;
    }

    public int getCurrentPlayer() {
        return currentPlayerID;
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
        getMenuInflater().inflate(R.menu.fleet, menu);
        menu.findItem(R.id.global_mute).setChecked(musicMuted);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.global_mute:
                setMusic();
                item.setChecked(!item.isChecked());
                return true;
            default:
                return false;
        }
    }

    public void setMusic() {
        if (musicMuted) {
            musicMuted = false;
        } else {
            musicMuted = true;
        }
    }

}
