package fleet.activity;

import fleet.classes.gameLogic.Fleet;
import fleet.view.TitleView;

import fleet.R;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.io.IOException;
import java.io.InputStream;

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

    @Override
    public void onDestroy(){
        mp.stop();
        super.onDestroy();
    }

}
