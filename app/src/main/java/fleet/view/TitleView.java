package fleet.view;

import fleet.R;
import fleet.activity.MenuData;
import fleet.activity.SelectionActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;


public class TitleView extends View {
    private Bitmap titleGraphic;
    private Bitmap playButtonUp;
    private Bitmap playButtonDown;
    private Bitmap muteButtonUp;
    private Bitmap muteButtonDown;
    private Bitmap titleBackground;
    private int screenW;
    private int screenH;
    private boolean playButtonPressed;
    private Context myContext;
    private MediaPlayer mp;

    public TitleView(Context context, MediaPlayer mp) {
        super(context);
        myContext = context;
        this.mp = mp;
        titleBackground = BitmapFactory.decodeResource(getResources(), R.drawable.title_background);
        titleGraphic = BitmapFactory.decodeResource(getResources(), R.drawable.title_graphic);
        playButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_up);
        playButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_down);
        muteButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.mute_button_up);
        muteButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.mute_button_down);
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
            canvas.drawBitmap(playButtonDown, (screenW - playButtonDown.getWidth()) / 2, (int) (screenH * 0.7), null);
        } else {
            canvas.drawBitmap(playButtonUp, (screenW - playButtonUp.getWidth()) / 2, (int) (screenH * 0.7), null);
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
        titleBackground = Bitmap.createScaledBitmap(titleBackground, screenW, screenH, false);
        titleGraphic = Bitmap.createScaledBitmap(titleGraphic,(int)(screenW * 0.25), (int)( screenH * 0.25),false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (x > (screenW - playButtonUp.getWidth()) / 2 &&
                        x < ((screenW - playButtonUp.getWidth()) / 2) + playButtonUp.getWidth() &&
                        y > (int) (screenH * 0.7) &&
                        y < (int) (screenH * 0.7) + playButtonUp.getHeight()) {
                    playButtonPressed = true;
                    break;
                }
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (playButtonPressed) {
                    Intent gameIntent = new Intent(myContext, SelectionActivity.class);
                    myContext.startActivity(gameIntent);
                }
                playButtonPressed = false;
                break;
        }
        invalidate();
        return true;
    }
}
