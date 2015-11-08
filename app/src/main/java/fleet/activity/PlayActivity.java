package fleet.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import fleet.R;
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
 *
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
    private int currentPlayer = 0;
    private boolean isWon = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        musicMuted = bundle.getBoolean("musicMuted");
        /*
        assetManager = getAssets();
        try {
            shipList = (assetManager.list(Fleets.get(playerSelected).getFleetPath()));
            for (String ship : shipList) {
                InputStream shipStream = assetManager.open(Fleets.get(playerSelected).getFleetPath() +"/"+ ship);
                Fleets.get(playerSelected).populateFleet(ship, BitmapFactory.decodeStream(shipStream));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        populatePlayers();
        startGame();
    }


    private void populatePlayers() {
        HumanPlayer humanPlayer = new HumanPlayer(TransferBuffer.board, nextPlayerID);
        players.add(humanPlayer);
        nextPlayerID++;
        PlayView humanPlayView = new PlayView(this, humanPlayer);
        activePlayers.add(humanPlayView);
        //TODO:make a different board for computer player
        ComputerPlayer computerPlayer = new ComputerPlayer(TransferBuffer.board, nextPlayerID);
        players.add(computerPlayer);
        nextPlayerID++;
        PlayView computerPlayView = new PlayView(this, computerPlayer);
        activePlayers.add(computerPlayView);
    }

    public void nextTurn() {
        if (currentPlayer < activePlayers.size()) {
            currentPlayer++;
            activePlayers.get(currentPlayer).caller = players.get(currentPlayer);
        } else {
            currentPlayer = 0;
            activePlayers.get(currentPlayer).caller = players.get(currentPlayer);
        }
        setContentView(activePlayers.get(currentPlayer));
    }

    /**
     * Game start function
     */
    public void startGame() {
        game();
    }

    /**
     * Game function
     * @return won status of the current game
     */
    public void game() {
        AbstractPlayer player = players.get(currentPlayer);
        activePlayers.get(currentPlayer).caller = player;
        setContentView(activePlayers.get(currentPlayer));
    }

    public void runTurn(AbstractPlayer player) {
        Ship[] shipAndTarget = new Ship[2];
        shipAndTarget = player.attack(players);
        if (shipAndTarget[1] == null) {
            if (player.getClass().equals(HumanPlayer.class)) {
                Toast.makeText(this, "You Lose", Toast.LENGTH_LONG).show();
            }
            players.remove(player);
        }
        if (shipAndTarget[0] != null && !shipAndTarget[0].shipClass.equals(ShipClass.CARRIER)) {
            shipAndTarget[1].sinkShip(battle(shipAndTarget[0], shipAndTarget[1]));
            if(shipAndTarget[1].getStatus()) {
                Toast.makeText(this,shipAndTarget[1].getShipNum() + " " + shipAndTarget[1].shipClass.getName() +": Remains afloat", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,shipAndTarget[1].getShipNum() + " " + shipAndTarget[1].shipClass.getName() + ": Is no more", Toast.LENGTH_SHORT).show();
            }
            //player.scout(players);
        } else {
            if (player.getClass().equals(HumanPlayer.class)) {
                Toast.makeText(this, "Carrier can't attack", Toast.LENGTH_LONG).show();
            }
        }
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
        return defender.getStatus();
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void showCurrentPlayerView() {
        setContentView(activePlayers.get(currentPlayer));
    }

    public void getNextPlayerView() {
        if (currentPlayer < activePlayers.size()) {
            activePlayers.get(currentPlayer + 1).caller = players.get(currentPlayer);
            setContentView(activePlayers.get(currentPlayer + 1));
        } else {
            activePlayers.get(0).caller = players.get(currentPlayer);
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
