package fleet.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import fleet.R;
import fleet.gameLogic.TransferBuffer;
import fleet.gameLogic.players.ComputerPlayer;
import fleet.gameLogic.players.HumanPlayer;
import fleet.view.PlayView;
import fleet.gameLogic.Fleet;

/**
 * Created by Radu on 10/18/2015.
 *
 *      //                         \\
 *     ||   !!UNDER CONSTRUCTION!!  ||
 *      \\                         //
 *
 */
public class PlayActivity extends Activity {

    private AssetManager assetManager;
    private String[] shipList;
    private ArrayList<Fleet> Fleets = new ArrayList<Fleet>();
    protected Boolean musicMuted;
    protected ArrayList<PlayView> activePlayers = new ArrayList<PlayView>();
    private int nextPlayerID = 0;
    private int currentPlayer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        musicMuted = bundle.getBoolean("musicMuted");
       // PlayerGameBoard playerBoard = (PlayerGameBoard)bundle.getSerializable("playerBoard");
        /*
        System.out.println(bundle.getInt("playerFleet"));
        int playerSelected = bundle.getInt("playerFleet");
        Fleets = bundle.getParcelableArrayList("fleets");
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
        setContentView(activePlayers.get(currentPlayer));
    }


    private void populatePlayers (){
        HumanPlayer humanPlayer = new HumanPlayer(TransferBuffer.board, nextPlayerID);
        nextPlayerID++;
        PlayView humanPlayView = new PlayView(this,humanPlayer);
        activePlayers.add(humanPlayView);
        //TODO:make a different board for computer player
        ComputerPlayer computerPlayer = new ComputerPlayer(TransferBuffer.board, nextPlayerID);
        nextPlayerID++;
        PlayView computerPlayView = new PlayView(this,computerPlayer);
        activePlayers.add(computerPlayView);
    }
    
    public void nextPlayerTurn(){
        if (currentPlayer < activePlayers.size()){
            currentPlayer++;
        }else {
            currentPlayer = 0;
        }
        String playerType = activePlayers.get(currentPlayer).player.getClass().getSimpleName();

        if ( playerType.equals("HumanPlayer")){
            setContentView(activePlayers.get(currentPlayer));
        }else{

        }

    }

    public int getCurrentPlayer(){
        return currentPlayer;
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
        switch(item.getItemId()) {
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
        if(musicMuted) {
            musicMuted = false;
        } else {
            musicMuted = true;
        }
    }

}
