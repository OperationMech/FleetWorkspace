package fleet.activity;

import fleet.view.TitleView;

import fleet.R;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FleetActivity extends Activity {
    protected MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mp = MediaPlayer.create(this, fleet.R.raw.title_bgm);
        mp.setLooping(true);
        mp.start();
        TitleView titleView = new TitleView(this,mp);
        setContentView(titleView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fleet, menu);
        return true;
    }

    /**
     *  Stops the media player before onDestroy is called
     **/
    @Override
    public void onDestroy(){
        mp.release();
        super.onDestroy();
    }

}
