package fleet.activity;

import fleet.R;
import fleet.gameLogic.Fleet;
import fleet.gameLogic.TransferBuffer;
import fleet.view.BuildView;
import fleet.view.FleetView;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SelectionActivity extends Activity {
    private final String FLEET_DIR = "fleets";
    private AssetManager assetManager;
    private static ArrayList<Fleet> fleets = new ArrayList<Fleet>();
    private String[] fleetList;
    private ArrayList<String> unusedFleetPaths;
  //  private static int runOnce = 0;
    protected String playerFleetPath;
    protected MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetManager = getAssets();
        unusedFleetPaths = new ArrayList<String>();
     //   if(runOnce == 0) {
            try {
                //Scans the /assets/fleet/ directory for fleets
                fleetList = (assetManager.list(FLEET_DIR));
                for (String fleet : fleetList) {
                    String fleetPath = (FLEET_DIR + "/" + fleet);
                    unusedFleetPaths.add(fleetPath);
                    InputStream kingStream = assetManager.open(fleetPath + "/King.png");
                    AssetFileDescriptor fleetSoundFile = assetManager.openFd(fleetPath + "/MainAttack.ogg");
                    Bitmap kingImg = BitmapFactory.decodeStream(kingStream);
                    Fleet newFleet = new Fleet(kingImg, fleetSoundFile, fleetPath);
                    fleets.add(newFleet);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
      //      runOnce = 1;
      //  }
        mp = MediaPlayer.create(this, R.raw.fleet_bgm);
        mp.setLooping(true);
        mp.start();
        FleetView myView = new FleetView(this, fleets);

        setContentView(myView);
    }

    /**
     * Sets the current view to a build view, where the player can organize their fleet before
     * the game starts.
     *
     * @param fleet The players selected fleet
     **/
    public void buildFleet(Fleet fleet){
        this.playerFleetPath = fleet.getFleetPath();
        unusedFleetPaths.remove(fleet.getFleetPath());
        try {
            String[] fleetFiles = (assetManager.list(playerFleetPath));
            for (String cardPath : fleetFiles) {
                //getting all fleet bitmaps
                if (!cardPath.startsWith("King.") && !cardPath.startsWith("MainAttack.")) {
                    InputStream cardStream = assetManager.open(playerFleetPath + "/" + cardPath);
                    Bitmap cardImg = BitmapFactory.decodeStream(cardStream);
                    fleet.populateFleet(cardPath,cardImg);
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        BuildView buildView = new BuildView(this,fleet);
        TransferBuffer.unusedFleetPaths = unusedFleetPaths;
        setContentView(buildView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fleet, menu);
        menu.findItem(R.id.music_mute).setChecked(MenuData.musicMuted);
        menu.findItem(R.id.effects_enabled).setChecked(MenuData.soundEffectsEnabled);
        menu.findItem(R.id.human_mode).setChecked(MenuData.isAiOnly);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.music_mute:
                MenuData.musicMuted = !MenuData.musicMuted;
                item.setChecked(!item.isChecked());
                return true;
            case R.id.effects_enabled:
                MenuData.soundEffectsEnabled = !MenuData.soundEffectsEnabled;
                item.setChecked(!item.isChecked());
                return true;
            case R.id.human_mode:
                MenuData.isAiOnly = !MenuData.isAiOnly;
                item.setChecked(!item.isChecked());
                return true;
            default:
                return false;
        }
    }

    @Override
    /**
     *  Stops the media player before onDestroy is called
     **/
    public void onDestroy(){
        mp.release();
        super.onDestroy();
    }

}
