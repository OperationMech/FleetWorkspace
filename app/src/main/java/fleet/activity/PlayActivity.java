package fleet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import fleet.R;
import fleet.view.PlayView;
import fleet.view.TitleView;

/**
 * Created by Radu on 10/18/2015.
 */
public class PlayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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
