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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AssetManager assetManager = getAssets();
		Bitmap img = null;
		ArrayList<Fleet> fleets = new ArrayList<Fleet>();

		try {
			String[] fleetList = (assetManager.list("fleets"));
			for (int i = 0; i<=fleetList.length; i++){
				String kingPath = "fleets/" + fleetList[i] + "/King.png";
				InputStream kingStream = assetManager.open(kingPath);
				Bitmap kingImg = BitmapFactory.decodeStream(kingStream);
				Fleet newFleet = new Fleet(kingImg);
				fleets.add(newFleet);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		FleetView myView = new FleetView(this,fleets);
		setContentView(myView);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fleet, menu);
		return true;
	}

}
