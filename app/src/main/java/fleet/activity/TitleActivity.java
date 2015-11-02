package fleet.activity;

import fleet.view.TitleView;

import fleet.R;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class TitleActivity extends Activity {
    protected MediaPlayer mp;
    protected boolean musicMuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mp = MediaPlayer.create(this, fleet.R.raw.title_bgm);
        mp.setLooping(true);
        mp.start();
        TitleView titleView = new TitleView(this,mp,musicMuted);
        setContentView(titleView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fleet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.global_mute:
                setMusic();
                item.setChecked(!item.isChecked());
                return true;
            default:
                return false;
        }
    }

    public void setMusic() {
        if(musicMuted) {
            musicMuted = false;
        } else {
            musicMuted = true;
        }
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
