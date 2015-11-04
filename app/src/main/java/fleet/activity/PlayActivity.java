package fleet.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import fleet.R;
import fleet.gameLogic.PlayerGameBoard;
import fleet.gameLogic.TransferBuffer;
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
    PlayerGameBoard board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.board = TransferBuffer.board;
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
        PlayView playView = new PlayView(this,board);
        setContentView(playView);
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
