package fleet.activity;

import fleet.view.TitleView;

import fleet.R;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class TitleActivity extends Activity {
    protected MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        menu.findItem(R.id.music_mute).setChecked(MenuData.musicMuted);
        menu.findItem(R.id.effects_enabled).setChecked(MenuData.soundEffectsEnabled);
        menu.findItem(R.id.human_mode).setChecked(MenuData.isAiOnly);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.music_mute:
                MenuData.musicMuted = !MenuData.musicMuted;
                item.setChecked(!item.isChecked());
                return true;
            case R.id.effects_enabled:
                MenuData.soundEffectsEnabled = !MenuData.soundEffectsEnabled;
                item.setChecked(!item.isChecked());
                return true;
            case R.id.human_mode:
                MenuData.isAiOnly = !MenuData.isAiOnly;
                item.setChecked(!item.isChecked());
                return true;
            default:
                return false;
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
