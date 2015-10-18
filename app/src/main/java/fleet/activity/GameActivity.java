package fleet.activity;

import fleet.R;
import fleet.classes.gameLogic.Fleet;
import fleet.view.FleetView;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.SoundEffectConstants;
import android.widget.ViewSwitcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GameActivity extends Activity {
    private final String FLEET_DIR = "fleets";
    private AssetManager assetManager;
    private static ArrayList<Fleet> fleets = new ArrayList<Fleet>();
    private String[] fleetList;
    private static int runOnce = 0;
    protected int playerFleet;
    private ViewSwitcher switcher;
    private static final int REFRESH_SCREEN = 1;
    protected MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetManager = getAssets();
        if(runOnce == 0) {
            try {
                fleetList = (assetManager.list(FLEET_DIR));
                for (String fleet : fleetList) {
                    String fleetPath = (FLEET_DIR + "/" + fleet);
                    InputStream kingStream = assetManager.open(fleetPath + "/King.png");
                    AssetFileDescriptor fleetSoundFile = assetManager.openFd(fleetPath + "/MainAttack.ogg");
                    Bitmap kingImg = BitmapFactory.decodeStream(kingStream);
                    Fleet newFleet = new Fleet(kingImg, fleetSoundFile, fleetPath);
                    fleets.add(newFleet);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnce = 1;
        }
        mp = MediaPlayer.create(this, R.raw.fleet_bgm);
        mp.setLooping(true);
        mp.start();
        FleetView myView = new FleetView(this, fleets);

        setContentView(myView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fleet, menu);
        return true;
    }
    @Override
    public void onDestroy(){
        mp.release();
        super.onDestroy();
    }

}
