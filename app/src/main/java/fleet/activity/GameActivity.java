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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GameActivity extends Activity {
    private final String FLEET_DIR = "fleets";
    private String[] fleetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AssetManager assetManager = getAssets();
        Bitmap img = null;
        ArrayList<Fleet> fleets = new ArrayList<Fleet>();

        try {
            fleetList = (assetManager.list(FLEET_DIR));
            System.out.println(fleetList);
            for (String fleet : fleetList) {
                System.out.println(fleet);
                InputStream kingStream = assetManager.open(FLEET_DIR + "/" +
                        fleet + "/King.jpg");
                Bitmap kingImg = BitmapFactory.decodeStream(kingStream);
                Fleet newFleet = new Fleet(kingImg);
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
