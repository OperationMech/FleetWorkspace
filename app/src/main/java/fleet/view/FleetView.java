package fleet.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.View;

/**
 * This class is the prototype of Crazy Eights.
 * The concept is from James (2013), although I made some small
 * changes mostly in style plus added sounds.
 *
 * @author Ron Coleman
 */
public class FleetView extends View {
    private Paint redPaint;
    private int circleX;
    private int circleY;
    private float radius;
    private Context myContext;
    private static SoundPool sounds;
    private int dropSound;

    public FleetView(Context context) {
        super(context);
        redPaint = new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setColor(Color.RED);
        circleX = 100;
        circleY = 100;
        radius = 30;
        myContext = context;

        sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        dropSound = sounds.load(myContext, fleet.R.raw.blip2, 1);

    }

    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(circleX, circleY, radius, redPaint);
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
