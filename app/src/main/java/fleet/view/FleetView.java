package fleet.view;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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
    //lab stuff
    private Point pos;
    private Point dest;
    private Boolean arrived;

    private Bitmap testimg;

    public FleetView(Context context, Bitmap img) {

        super(context);
        //lab stuff
        pos = new Point(100, 100);
        dest = new Point(100, 100);
        arrived = true;
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
        canvas.drawBitmap(testimg, 500, 500, null);
        canvas.drawCircle(pos.x, pos.y, radius, redPaint);
        if (!pos.equals(dest.x,dest.y)){
            arrived = false;
            int speed = 10;
            int sx = (dest.x - pos.x);
            int sy = (dest.y - pos.y);
            double s = Math.sqrt(sx * sx + sy * sy);
            double theta = Math.asin(sx / s);
            double deltax = speed * Math.sin(theta) ;
            double gamma = Math.acos(sy / s);
            double deltay = speed * Math.cos(gamma);
            pos.x = pos.x + (int)deltax;
            pos.y = pos.y + (int)deltay;

            if( Math.abs(dest.x - pos.x) <= speed && Math.abs(dest.y - pos.y) <= speed){
                pos.x = dest.x;
                pos.y = dest.y;

                arrived = true;

                AudioManager audioManager = (AudioManager) this.myContext.getSystemService(Context.AUDIO_SERVICE);
                float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                sounds.play(dropSound, volume, volume, 1, 0, 1);
            }
        }
        String text = "Select Fleet";
        canvas.drawText(text, 0 , text.length(), textX, textY, blackPaint);
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (arrived == true){
                    dest.set(x, y);
                }
                break;
        }

        invalidate();
        return true;
    }
}
