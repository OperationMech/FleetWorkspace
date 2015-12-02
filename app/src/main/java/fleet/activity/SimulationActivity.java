package fleet.activity;

import fleet.R;
import fleet.gameLogic.Fleet;
import fleet.gameLogic.players.AbstractPlayer;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * SimulationActivity class
 *
 * Authors: Conner and Anthony Cali
 */
public class SimulationActivity extends Activity {
    private final String FLEET_DIR = "fleets";
    private AssetManager assetManager;
    private static ArrayList<Fleet> fleets = new ArrayList<Fleet>();
    private String[] fleetList;
    private AbstractPlayer[] players = new AbstractPlayer[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation_activity);
    }

    public void runSimulations() {
        loadFleets();
    }

    public void loadFleets() {
        assetManager = getAssets();
        // Get all the fleets
        try {
            fleetList = (assetManager.list(FLEET_DIR));
            for(String fleet : fleetList) {
                String fleetPath = (FLEET_DIR + "/" + fleet);
                InputStream kingImgStream = assetManager.open(fleetPath + "/King.png");
                AssetFileDescriptor fleetSoundFile = assetManager.openFd(fleetPath + "MainAttack.ogg");
                Bitmap kingImg = BitmapFactory.decodeStream(kingImgStream);
                Fleet newFleet = new Fleet(kingImg, fleetSoundFile, fleetPath);
                for(String ship : assetManager.list(fleetPath)) {
                    if(ship.equals("King.png") || ship.equals("MainAttack.ogg")){
                        // do nothing
                    } else {
                        InputStream shipImgStream = assetManager.open(fleetPath+ship);
                        Bitmap shipImg = BitmapFactory.decodeStream(shipImgStream);
                        newFleet.populateFleet(ship, shipImg );
                    }
                }
                fleets.add(newFleet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
