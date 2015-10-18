package fleet.activity;

import fleet.R;
import fleet.classes.gameLogic.Fleet;
import fleet.view.FleetView;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    protected int playerFleet;
    private ViewSwitcher switcher;
    private static final int REFRESH_SCREEN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetManager = getAssets();
        SoundPool fleetSounds = new SoundPool(1,1,1);
        try {
            fleetList = (assetManager.list(FLEET_DIR));
            for (String fleet : fleetList) {
                String fleetPath = (FLEET_DIR + "/" + fleet);
                InputStream kingStream = assetManager.open(fleetPath + "/" + "King.png");
                AssetFileDescriptor mainAttack = assetManager.openFd(fleetPath + "/" + "mainattack.ogg");
                Bitmap kingImg = BitmapFactory.decodeStream(kingStream);
                fleetSounds.load(mainAttack,0);
                Fleet newFleet = new Fleet(kingImg, fleetSounds,fleetPath);
                fleets.add(newFleet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FleetView myView = new FleetView(this, fleets);
        setContentView(myView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fleet, menu);
        return true;
    }

}
