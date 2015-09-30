package fleet.activity;

import fleet.view.TitleView;

import fleet.R;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.io.IOException;

public class FleetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		AssetManager assetManager = getAssets();

		try {
			System.out.println(assetManager.list("fleets")[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}

		super.onCreate(savedInstanceState);
		TitleView titleView = new TitleView(this);
		setContentView(titleView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fleet, menu);
		return true;
	}

}
