package fleet.activity;

import fleet.view.TitleView;

import fleet.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FleetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
