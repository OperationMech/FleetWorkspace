package fleet.activity;

import fleet.R;
import fleet.classes.gameLogic.Fleet;
import fleet.view.FleetView;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
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
        try {
            fleetList = (assetManager.list(FLEET_DIR));
            for (String fleet : fleetList) {
                InputStream kingStream = assetManager.open(FLEET_DIR + "/" +
                        fleet + "/" + "King.png");
                Bitmap kingImg = BitmapFactory.decodeStream(kingStream);
                Fleet newFleet = new Fleet(kingImg);
                newFleet.setFleetPath(FLEET_DIR + "/" + fleet);
                fleets.add(newFleet);
                    /* populate fleets code
                    try {
                        shipList = (assetManager.list(FLEET_DIR + "/" + fleet));
                        for (String ship : shipList) {
                            InputStream shipStream = assetManager.open(FLEET_DIR + "/" +
                                    fleet + "/" + ship);
                            newFleet.populateFleet(ship, BitmapFactory.decodeStream(shipStream));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
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
