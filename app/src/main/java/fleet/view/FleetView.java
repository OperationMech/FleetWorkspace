package fleet.view;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.View;

import fleet.R;
import fleet.classes.gameLogic.Fleet;

public class FleetView extends View {
    private Paint redPaint;
    private Paint blackPaint;
    private int circleX;
    private int circleY;
    private float textX;
    private float textY = 20;
    private float radius;
    private Context myContext;
    private static SoundPool sounds;
    private int dropSound;
    private MediaPlayer mp;
    private final String[] list = null;
    private Fleet burningLove = null;

    private Bitmap testimg;

    public FleetView(Context context, Bitmap img) {

        super(context);
        redPaint = new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setColor(Color.RED);
        blackPaint = new Paint();
        blackPaint.setAntiAlias(true);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setTextScaleX((float) 2.0);
        blackPaint.setTextAlign(Paint.Align.CENTER);
        textX = (float)(800/2.0);
        circleX = 100;
        circleY = 100;
        radius = 30;
        myContext = context;
        mp = MediaPlayer.create(context, R.raw.fleet_bgm);
        mp.setLooping(true);
        mp.start();

        testimg = img;

        sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        dropSound = sounds.load(myContext, fleet.R.raw.blip2, 1);

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            mp.start();
        } else {
            mp.pause();
        }
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(circleX, circleY, radius, redPaint);
        String text ="Select Fleet";
        canvas.drawText(text, 0 , text.length(), textX, textY, blackPaint);
        canvas.drawBitmap(testimg,0,0,null);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                circleX = x;
                circleY = y;
                break;
            case MotionEvent.ACTION_UP:
                circleX = x;
                circleY = y;
                AudioManager audioManager = (AudioManager) this.myContext.getSystemService(Context.AUDIO_SERVICE);
                float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                sounds.play(dropSound, volume, volume, 1, 0, 1);
                break;
        }

        invalidate();
        return true;
    }
}
