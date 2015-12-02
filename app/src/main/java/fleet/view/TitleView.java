package fleet.view;

import fleet.R;
import fleet.activity.MenuData;
import fleet.activity.SelectionActivity;
import fleet.activity.SimulationActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;


public class TitleView extends View {
    private Bitmap titleGraphic =BitmapFactory.decodeResource(getResources(), R.drawable.title_graphic);
    private Bitmap playButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.play_up);
    private Bitmap playButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.play_down);
    private Point playButtonOrigin;
    private Bitmap muteButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.mute_button_up);
    private Bitmap muteButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.mute_button_down);
    private Bitmap titleBackground  = BitmapFactory.decodeResource(getResources(), R.drawable.title_background);
    private Bitmap simButtonUp = BitmapFactory.decodeResource(getResources(),R.drawable.simmode_up);
    private Bitmap simButtonDown  = BitmapFactory.decodeResource(getResources(),R.drawable.simmode_down);
    private Point simButtonOrigin;
    private int screenW;
    private int screenH;
    private boolean playButtonPressed;
    private boolean simButtonPressed;
    private Context myContext;
    private MediaPlayer mp;

    public TitleView(Context context, MediaPlayer mp) {
        super(context);
        myContext = context;
        this.mp = mp;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && !MenuData.musicMuted) {
            mp.start();
        } else {
            mp.pause();
        }
        invalidate();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
        playButtonOrigin = new Point (screenW/2 - playButtonDown.getWidth()/2 ,(int)(screenH*.7));
        simButtonOrigin = new Point(screenW/2 - simButtonDown.getWidth()/2 ,(int)(playButtonOrigin.y + playButtonDown.getHeight() * 1.5));
        titleBackground = Bitmap.createScaledBitmap(titleBackground, screenW, screenH, false);
        titleGraphic = Bitmap.createScaledBitmap(titleGraphic,(int)(screenW * 0.25), (int)( screenW * 0.25),false);

    }

    /**
     * @param canvas the canvas we will be drawing on
     */
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(titleBackground, 0, 0, null);
        canvas.drawBitmap(titleGraphic, 0, 0, null);
        if (MenuData.musicMuted) {
            canvas.drawBitmap(muteButtonDown, (screenW - muteButtonUp.getWidth()), 0, null);
        } else {
            canvas.drawBitmap(muteButtonUp, (screenW - muteButtonUp.getWidth()), 0, null);
        }
        if (playButtonPressed) {
            canvas.drawBitmap(playButtonDown, playButtonOrigin.x,playButtonOrigin.y, null);
        } else {
            canvas.drawBitmap(playButtonUp, (screenW - playButtonUp.getWidth()) / 2, (int) (screenH * 0.7), null);
        }
        if (simButtonPressed) {
            canvas.drawBitmap(simButtonDown, simButtonOrigin.x,simButtonOrigin.y,null);
        } else {
            canvas.drawBitmap(simButtonUp, simButtonOrigin.x,simButtonOrigin.y,null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (x > playButtonOrigin.x
                        && x < playButtonOrigin.x + playButtonDown.getWidth()
                        && y > playButtonOrigin.y
                        && y < playButtonOrigin.y + playButtonDown.getHeight()) {
                    playButtonPressed = true;
                    break;
                }
                if (x > simButtonOrigin.x
                        && x < simButtonOrigin.x + simButtonDown.getWidth()
                        && y > simButtonOrigin.y
                        && y < simButtonOrigin.y + simButtonDown.getHeight()) {
                    simButtonPressed = true;
                    break;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (playButtonPressed) {
                    Intent gameIntent = new Intent(myContext, SelectionActivity.class);
                    myContext.startActivity(gameIntent);
                }
                if (simButtonPressed) {
                    Intent nonInteractive = new Intent (myContext, SimulationActivity.class);
                    myContext.startActivity(nonInteractive);
                }
                playButtonPressed = false;
                simButtonPressed = false;
                break;
        }
        invalidate();
        return true;
    }
}
