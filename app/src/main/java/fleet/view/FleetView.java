package fleet.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

import fleet.R;
import fleet.activity.SelectionActivity;
import fleet.gameLogic.Fleet;

public class FleetView extends View {
    private Paint blackPaint;
    private int screenW;
    private int screenH;
    private SelectionActivity myContext;
    private ArrayList<Fleet> fleets;
    private ArrayList<Bitmap> kings = new ArrayList<Bitmap>();
    private Integer fleetNum = 0;
    private Bitmap background;
    private Bitmap leftArrow = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
    private int leftArrowX;
    private int leftArrowY;
    private Bitmap rightArrow = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);
    private int rightArrowX;
    private int rightArrowY;
    private Bitmap fleetKing;
    private int fleetKingX;
    private int fleetKingY;
    private int selectFleetY;
    private Bitmap selectFleet;
    private Bitmap selectFleetDown;
    private boolean selectFleetPressed = false;
    private Bitmap resumeGame;
    private int resumeGameY;
    private Bitmap resumeGameDown;
    private boolean resumeGamePressed =false;
    protected Intent playIntent;
    private AudioManager audioManager;
    private boolean isMuted;
    SoundPool selectionSound;

    /**
     * Constructor
     * @param context The activity that has built this view
     * @param fleets An array list of fleets that where found in assets/fleets by an assetmanager
     **/
    public FleetView(Context context, ArrayList<Fleet> fleets, boolean musicMuted) {
        //TODO: Make this less of a mess
        super(context);
        myContext = (SelectionActivity)context;
        isMuted = musicMuted;
        audioManager = (AudioManager)
                this.myContext.getSystemService(Context.AUDIO_SERVICE);
        selectionSound = new SoundPool(1,1,1);
        selectionSound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                float volume = (float)
                        audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                soundPool.play(sampleId, volume, volume, 1, 0, 1);
            }
        });
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.title_background);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenH = size.y;
        screenW = size.x;
        blackPaint = new Paint();
        blackPaint.setAntiAlias(true);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setTextSize((float) 24.0);
        blackPaint.setTextAlign(Paint.Align.CENTER);
        this.fleets = fleets;

        fleetKing = fleets.get(fleetNum).getKing();
        selectFleet = BitmapFactory.decodeResource(getResources(), R.drawable.select_fleet);
        selectFleetDown = BitmapFactory.decodeResource(getResources(), R.drawable.select_fleet_down);
        resumeGame = BitmapFactory.decodeResource(getResources(), R.drawable.resume_game);
        resumeGameDown = BitmapFactory.decodeResource(getResources(), R.drawable.resume_game_down);

        Bitmap king;
        //scaling Images
        for (int i = 0; i < fleets.size(); i++) {
            king = Bitmap.createScaledBitmap(fleets.get(i).getKing(), screenH / 4, screenH / 3, false);
            kings.add(king);
        }

        fleetKing = kings.get(fleetNum);
        selectFleet = Bitmap.createScaledBitmap(selectFleet, fleetKing.getWidth(), selectFleet.getHeight(), false);
        selectFleetDown = Bitmap.createScaledBitmap(selectFleetDown,fleetKing.getWidth(),selectFleetDown.getHeight(),false);
        resumeGame = Bitmap.createScaledBitmap(resumeGame, fleetKing.getWidth(), resumeGame.getHeight(), false);
        resumeGameDown = Bitmap.createScaledBitmap(resumeGameDown, fleetKing.getWidth(),resumeGameDown.getHeight(),false);
    }

    @Override
    /**
     * Called when the screen orientation is changed
     *
     * @param w Width of the screen
     * @param h Hight of the screen
     * @param oldw Previous width of the screen
     * @param oldh Previous height of the screen
     **/
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        screenW = w;
        screenH = h;
        leftArrowX = (int) (screenW * 0.15) - leftArrow.getWidth() / 2;
        leftArrowY = (int) (screenH * 0.50) - leftArrow.getHeight() / 2;
        rightArrowX = (int) (screenW * 0.85) - rightArrow.getWidth() / 2;
        fleetKingX = screenW / 2 - fleetKing.getWidth() / 2;
        fleetKingY = screenH / 2 - fleetKing.getHeight() / 2;
        rightArrowY = screenH / 2 - rightArrow.getHeight() / 2;
        resumeGameY = (int) (fleetKingY - screenH * 0.2);
        background = Bitmap.createScaledBitmap(background,screenW,screenH,false);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /** @param canvas the canvas we will be drawing on
     *
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(leftArrow, leftArrowX, leftArrowY, null);
        canvas.drawBitmap(rightArrow, rightArrowX, rightArrowY, null);
        fleetKing = kings.get(fleetNum);
        canvas.drawBitmap(fleetKing, fleetKingX, fleetKingY, null);
        selectFleetY = (int) (fleetKingY + fleetKing.getHeight() + screenH * 0.1);
        //Change button image if the button has been pressed.
        if (selectFleetPressed){
            canvas.drawBitmap(selectFleetDown,fleetKingX, selectFleetY,null);
        }else {
            canvas.drawBitmap(selectFleet, fleetKingX, selectFleetY, null);
        }
        String text = fleets.get(fleetNum).getFleetName();
        canvas.drawText(text, 0, text.length(), screenW / 2, fleetKingY - (int)(screenH * 0.05) , blackPaint);
        invalidate();
        //Check if there is a game currently going on, drawing a button to get back to it if there is one.
        if( playIntent != null){
            if(resumeGamePressed){
                canvas.drawBitmap(resumeGameDown, fleetKingX, resumeGameY, null);
            }else {
                canvas.drawBitmap(resumeGame, fleetKingX, resumeGameY, null);
            }
        }
    }

    /** @param event The user's action
     * @return  True when event has been processed
     */
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //Detection on right arrow
                if (x > rightArrowX &&
                        x < rightArrowX + rightArrow.getWidth() &&
                        y > rightArrowY &&
                        y < rightArrowY + rightArrow.getHeight()) {
                    if (fleetNum + 1 < fleets.size()) {
                        fleetNum++;
                    } else {
                        fleetNum = 0;
                    }
                    break;
                }
                //Detection on left arrow
                if (x > leftArrowX &&
                        x < leftArrowX + leftArrow.getWidth() &&
                        y > leftArrowY &&
                        y < leftArrowY + leftArrow.getHeight()) {
                    if (fleetNum - 1 >= 0) {
                        fleetNum--;
                    } else {
                        fleetNum = fleets.size() - 1;
                    }
                    break;
                }
                //Detection on select Fleet button
                if (x > fleetKingX &&
                        x < fleetKingX + selectFleet.getWidth() &&
                        y > selectFleetY &&
                        y < selectFleetY + selectFleet.getHeight()) {
                    selectFleetPressed = true;
                    break;
                }
                //Detection on Resume Game button
                if (x > fleetKingX &&
                        x < fleetKingX + resumeGame.getWidth() &&
                        y > resumeGameY &&
                        y < resumeGameY + resumeGame.getHeight()) {
                    resumeGamePressed = true;
                    break;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(resumeGamePressed){
                    myContext.startActivity(playIntent);
                }
                if(selectFleetPressed){
                    // Moving to the buildFleet view
                    Fleet playerFleet = fleets.get(fleetNum);
                    selectionSound.load(playerFleet.getFleetAttack(), 1);
                    myContext.buildFleet(playerFleet);
                  //  playIntent = new Intent(myContext, PlayActivity.class);
                  //  playIntent.putExtra("playerFleet", fleetNum.intValue());
                  //  playIntent.putParcelableArrayListExtra("fleets", fleets);
                    //   myContext.startActivity(playIntent);
                }
                selectFleetPressed = false;
                resumeGamePressed = false;
                break;
        }

        invalidate();
        return true;
    }
}
