package fleet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import fleet.R;
import fleet.classes.gameLogic.Fleet;

public class FleetView extends View {
    private Paint redPaint;
    private Paint blackPaint;
    private float textX;
    private float textY = 20;
    private int screenW;
    private int screenH;
    private float radius;
    private Context myContext;
    private static SoundPool sounds;
    private int dropSound;
    private MediaPlayer mp;
    private final String[] list = null;
    private ArrayList<Fleet> fleets;
    private Integer fleetnum = 0;
    private Bitmap leftArrow = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
    private int leftArrowX;
    private int leftArrowY;
    private Bitmap rightArrow = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);
    private int rightArrowX;
    private int rightArrowY;
    //lab stuff
    private Point pos;
    private Point dest;
    private Boolean arrived;

    private Bitmap testimg;

    public FleetView(Context context, ArrayList<Fleet> fleets) {

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
        textX = (float) (800 / 2.0);
        radius = 30;
        myContext = context;
        mp = MediaPlayer.create(context, R.raw.fleet_bgm);
        mp.setLooping(true);
        mp.start();
        this.fleets = fleets;

        sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        dropSound = sounds.load(myContext, fleet.R.raw.blip2, 1);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        screenW = w;
        screenH = h;
        super.onSizeChanged(w, h, oldw, oldh);
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
        //canvas.drawBitmap(testimg, 500, 500, null);
        canvas.drawCircle(pos.x, pos.y, radius, redPaint);
        leftArrowX = (int) (screenW * 0.20) - leftArrow.getWidth() / 2;
        leftArrowY = (int) (screenH * 0.50) - leftArrow.getHeight() / 2;
        canvas.drawBitmap(leftArrow, leftArrowX, leftArrowY, null);
        rightArrowX = (int) (screenW * 0.80) - rightArrow.getWidth() / 2;
        rightArrowY = screenH / 2 - rightArrow.getHeight() / 2;
        canvas.drawBitmap(rightArrow, rightArrowX, rightArrowY, null);
        Bitmap fleetKing = fleets.get(fleetnum).getKing();

        canvas.drawBitmap(fleetKing, (screenW / 2 - fleetKing.getWidth() / 2), (screenH / 2 - fleetKing.getHeight() / 2), null);
        if (!pos.equals(dest.x, dest.y)) {
            arrived = false;
            int speed = 10;
            int sx = (dest.x - pos.x);
            int sy = (dest.y - pos.y);
            double s = Math.sqrt(sx * sx + sy * sy);
            double deltax = speed * (sx / s);
            double deltay = speed * (sy / s);
            pos.x = pos.x + (int) deltax;
            pos.y = pos.y + (int) deltay;

            if (Math.abs(dest.x - pos.x) <= speed && Math.abs(dest.y - pos.y) <= speed) {
                pos.x = dest.x;
                pos.y = dest.y;
                arrived = true;

                AudioManager audioManager = (AudioManager) this.myContext.getSystemService(Context.AUDIO_SERVICE);
                float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                sounds.play(dropSound, volume, volume, 1, 0, 1);
            }
        }
        String text = "Select Fleet";
        canvas.drawText(text, 0, text.length(), textX, textY, blackPaint);
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (x > rightArrowX &&
                        x < rightArrowX + rightArrow.getWidth() &&
                        y > rightArrowY &&
                        y < rightArrowY + rightArrow.getHeight()) {
                    if (fleetnum + 1 < fleets.size()) {
                        fleetnum++;
                    } else {
                        fleetnum = 0;
                    }
                    break;
                }
                System.out.println("X = " + x + "Y:" + y);
                System.out.println(leftArrowX);
                System.out.println(leftArrowX + leftArrow.getWidth() / 2);
                if (x > leftArrowX &&
                        x < leftArrowX + leftArrow.getWidth() &&
                        y > leftArrowY &&
                        y < leftArrowY + leftArrow.getHeight()) {
                    if (fleetnum - 1 > 0) {
                        fleetnum--;
                    } else {
                        fleetnum = fleets.size() - 1;
                    }
                    break;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (arrived == true) {
                    dest.set(x, y);
                }
                break;
        }

        invalidate();
        return true;
    }
}
