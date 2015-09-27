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
	private  Bitmap optionsButtonDown;
	private Bitmap titleBackground;
	private int screenW;
	private int screenH;
	private boolean playButtonPressed;
	private boolean optionsButtonPressed;
	private Context myContext;
	private MediaPlayer mp;

	public TitleView(Context context) {
		super(context);
	myContext = context;
		titleBackground = BitmapFactory.decodeResource(getResources(),R.drawable.title_background);
	titleGraphic = BitmapFactory.decodeResource(getResources(), fleet.R.drawable.title_graphic);
	titleGraphic = BitmapFactory.decodeResource(getResources(), R.drawable.title_graphic);
			playButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_up);
		playButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_down);
		optionsButtonUp = BitmapFactory.decodeResource(getResources(),R.drawable.options_button_up);
		optionsButtonDown = BitmapFactory.decodeResource(getResources(),R.drawable.options_button_down);
		mp = MediaPlayer.create(context, R.raw.title_bgm);
		mp.setLooping(true);
        mp.start();
	}

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            mp.start();
        }
    }

    public void onDraw(Canvas canvas) {
		canvas.drawBitmap(titleBackground,0,0,null);
		canvas.drawBitmap(titleGraphic,0,0,null);
		if (playButtonPressed) {
			canvas.drawBitmap(playButtonDown, (screenW-playButtonDown.getWidth())/2, (int)(screenH*0.7), null);
		} else {
			canvas.drawBitmap(playButtonUp, (screenW-playButtonUp.getWidth())/2, (int)(screenH*0.7), null);			
		}
		if (optionsButtonPressed) {
			canvas.drawBitmap(optionsButtonDown, (screenW-optionsButtonDown.getWidth())/2, (int)(screenH*0.5), null);
		} else {
			canvas.drawBitmap(optionsButtonUp, (screenW-optionsButtonUp.getWidth())/2, (int)(screenH*0.5), null);
		}
	}
	
    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh){
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
		
		switch(action) {
			case MotionEvent.ACTION_DOWN:
	        	if (x > (screenW-playButtonUp.getWidth())/2 &&
		        		x < ((screenW-playButtonUp.getWidth())/2) + playButtonUp.getWidth() &&
		        		y > (int)(screenH*0.7) &&
		        		y < (int)(screenH*0.7) + playButtonUp.getHeight()) {
		        		playButtonPressed = true;
                        mp.pause();
					 	break;
		        	}

			if (x > (screenW-optionsButtonUp.getWidth())/2 &&
					x < ((screenW-optionsButtonUp.getWidth())/2) + optionsButtonUp.getWidth() &&
					y > (int)(screenH*0.5) &&
					y < (int)(screenH*0.5) + optionsButtonUp.getHeight()) {
					optionsButtonPressed = true;
					break;
				}

			case MotionEvent.ACTION_MOVE:
				
				break;
			case MotionEvent.ACTION_UP:
	        	if (playButtonPressed) {
		        	Intent gameIntent = new Intent(myContext, GameActivity.class);
		        	
		        	myContext.startActivity(gameIntent);	        		
	        	}
				optionsButtonPressed = false;
	        	playButtonPressed = false;
				break;
		}
		
		invalidate();
		return true;
	}
}
