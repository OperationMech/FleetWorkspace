package fleet.view;

import fleet.R;
import fleet.activity.GameActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;


public class TitleView extends View {
    private Bitmap titleGraphic;
    private Bitmap playButtonUp;
    private Bitmap playButtonDown;
    private Bitmap optionsButtonUp;
    private Bitmap optionsButtonDown;
    private Bitmap muteButtonUp;
    private Bitmap muteButtonDown;
    private Bitmap titleBackground;
    private int screenW;
    private int screenH;
    private boolean playButtonPressed;
    private boolean optionsButtonPressed;
    private boolean muteButtonPressed;
    private boolean mute;
    private Context myContext;
    private MediaPlayer mp;

    public TitleView(Context context) {
        super(context);
        myContext = context;
        titleBackground = BitmapFactory.decodeResource(getResources(), R.drawable.title_background);
        titleGraphic = BitmapFactory.decodeResource(getResources(), fleet.R.drawable.title_graphic);
        titleGraphic = BitmapFactory.decodeResource(getResources(), R.drawable.title_graphic);
        playButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_up);
        playButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_down);
        optionsButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.options_button_up);
        optionsButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.options_button_down);
        muteButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.mute_button_up);
        muteButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.mute_button_down);
        mp = MediaPlayer.create(context, fleet.R.raw.title_bgm);
        mp.setLooping(true);
        mp.start();
        mute = false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && mute == false) {
            mp.start();
        } else {
            mp.pause();
        }
    }

    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(titleBackground, 0, 0, null);
        canvas.drawBitmap(titleGraphic, 0, 0, null);
        if (mute == true) {
            canvas.drawBitmap(muteButtonDown, (screenW - muteButtonUp.getWidth()), 0, null);
        } else {
            canvas.drawBitmap(muteButtonUp, (screenW - muteButtonUp.getWidth()), 0, null);
        }

        if (playButtonPressed) {
            canvas.drawBitmap(playButtonDown, (screenW - playButtonDown.getWidth()) / 2, (int) (screenH * 0.7), null);
        } else {
            canvas.drawBitmap(playButtonUp, (screenW - playButtonUp.getWidth()) / 2, (int) (screenH * 0.7), null);
        }

        if (optionsButtonPressed) {
            canvas.drawBitmap(optionsButtonDown, (screenW - optionsButtonDown.getWidth()) / 2, (int) (screenH * 0.5), null);
        } else {
            canvas.drawBitmap(optionsButtonUp, (screenW - optionsButtonUp.getWidth()) / 2, (int) (screenH * 0.5), null);
        }

        if (muteButtonPressed) {
            if (mute == true) {
                canvas.drawBitmap(muteButtonUp, (screenW - muteButtonUp.getWidth()), 0, null);
                mp.start();
                mute = false;
            } else {
                canvas.drawBitmap(muteButtonDown, (screenW - muteButtonDown.getWidth()), 0, null);
                mp.pause();
                mute = true;
            }

        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
        System.out.println("SCREEN W: " + screenW);
        System.out.println("SCREEN H: " + screenH);
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

                if (x > (screenW - optionsButtonUp.getWidth()) / 2 &&
                        x < ((screenW - optionsButtonUp.getWidth()) / 2) + optionsButtonUp.getWidth() &&
                        y > (int) (screenH * 0.5) &&
                        y < (int) (screenH * 0.5) + optionsButtonUp.getHeight()) {
                    optionsButtonPressed = true;
                    break;
                }

                if (x > (screenW - muteButtonUp.getWidth()) &&
                        x < ((screenW - muteButtonUp.getWidth())) + muteButtonUp.getWidth() &&
                        y > (int) 0 &&
                        y < (int) +muteButtonUp.getHeight()) {
                    muteButtonPressed = true;
                    break;
                }

            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                if (playButtonPressed) {
                    Intent gameIntent = new Intent(myContext, GameActivity.class);

                    myContext.startActivity(gameIntent);
                }
                muteButtonPressed = false;
                optionsButtonPressed = false;
                playButtonPressed = false;
                break;
        }

        invalidate();
        return true;
    }
}
