package fleet.activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import fleet.R;
import fleet.view.PlayView;
import fleet.classes.gameLogic.Fleet;

/**
 * Created by Radu on 10/18/2015.
 */
public class PlayActivity extends Activity {

    private AssetManager assetManager;
    private String[] shipList;
    private ArrayList<Fleet> Fleets = new ArrayList<Fleet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Fleets  = (ArrayList<Fleet>) savedInstanceState.get("fleets");
        Integer playerSelected = savedInstanceState.getInt("playerFleet");
        assetManager = getAssets();
        try {
            shipList = (assetManager.list(Fleets.get(playerSelected).getFleetPath()));
            for (String ship : shipList) {
                InputStream shipStream = assetManager.open(Fleets.get(playerSelected).getFleetPath() + ship);
                Fleets.get(playerSelected).populateFleet(ship, BitmapFactory.decodeStream(shipStream));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        PlayView playView = new PlayView(this);
        setContentView(playView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fleet, menu);
        return true;
    }

}
