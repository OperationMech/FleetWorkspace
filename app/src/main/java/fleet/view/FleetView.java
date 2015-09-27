package fleet.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.View;

import fleet.R;

public class FleetView extends View {
    private Paint redPaint;
    private Paint blackPaint;
    private int circleX;
    private int circleY;
    private int screenW = getWidth();
    private int textX;
    private int textY;
    private float radius;
    private Context myContext;
    private static SoundPool sounds;
    private int dropSound;
    private MediaPlayer mp;

    public FleetView(Context context) {

        super(context);
        redPaint = new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setColor(Color.RED);
        blackPaint = new Paint();
        blackPaint.setAntiAlias(true);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setTextSize(-100);
        textX = screenW/2;
        textY = 50;
        circleX = 100;
        circleY = 100;
        radius = 30;
        myContext = context;
        mp = MediaPlayer.create(context, R.raw.fleet_bgm);
        mp.setLooping(true);
        mp.start();

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

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(circleX, circleY, radius, redPaint);
        canvas.drawText("Select Fleet", textX , textY, blackPaint);
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
