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

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AssetManager assetManager = getAssets();
		Bitmap img = null;

		try {
			Fleet burningLove;
			String love = (assetManager.list("fleets")[0]);
			String kingpath = "fleets/" + love + "/King.png";
			System.out.println(kingpath);
			InputStream burning = assetManager.open(kingpath);
			img = BitmapFactory.decodeStream(burning);

		} catch (IOException e) {
			e.printStackTrace();
		}
		FleetView myView = new FleetView(this,img);
		setContentView(myView);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fleet, menu);
		return true;
	}

}
