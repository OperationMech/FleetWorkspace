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
import android.media.MediaPlayer;
import android.os.Build;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

import fleet.R;
import fleet.activity.GameActivity;
import fleet.activity.PlayActivity;
import fleet.classes.gameLogic.Fleet;

public class FleetView extends View {
    private Paint blackPaint;
    private int screenW;
    private int screenH;
    private Context myContext;
    private MediaPlayer mp;
    private ArrayList<Fleet> fleets;
    private ArrayList<Bitmap> kings = new ArrayList<Bitmap>();
    private Integer fleetNum = 0;
    private Bitmap leftArrow = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
    private int leftArrowX;
    private int leftArrowY;
    private Bitmap rightArrow = BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow);
    private int rightArrowX;
    private int rightArrowY;
    private Bitmap selectFleet;
    private Bitmap fleetKing;
    private int fleetKingX;
    private int fleetKingY;
    private int selectFleetX;
    private int selectFleetY;
    private BitmapFactory.Options options = new BitmapFactory.Options();
    private Bitmap selectFleetDown;
    private boolean selectFleetPressed = false;
    protected Intent playIntent;

    public FleetView(Context context, ArrayList<Fleet> fleets) {

        super(context);
        myContext = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
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
        myContext = context;
        mp = MediaPlayer.create(context, R.raw.fleet_bgm);
        mp.setLooping(true);
        mp.start();
        this.fleets = fleets;

        options.inMutable = true;

        fleetKing = fleets.get(fleetNum).getKing();
        selectFleet = BitmapFactory.decodeResource(getResources(), R.drawable.select_fleet, options);
        selectFleet = Bitmap.createScaledBitmap(selectFleet, fleetKing.getWidth(), selectFleet.getHeight(), false);
        selectFleetDown = BitmapFactory.decodeResource(getResources(), R.drawable.select_fleet_down, options);
        selectFleetDown = Bitmap.createScaledBitmap(selectFleetDown,fleetKing.getWidth(),selectFleetDown.getHeight(),false);

        Bitmap king;
        //scale Images
        for (int i = 0; i < fleets.size(); i++) {
            king = Bitmap.createScaledBitmap(fleets.get(i).getKing(), screenH / 4, screenH / 3, false);
            kings.add(king);
        }

        fleetKing = kings.get(fleetNum);
        selectFleet = Bitmap.createScaledBitmap(selectFleet, fleetKing.getWidth(), selectFleet.getHeight(), false);
        selectFleetDown = Bitmap.createScaledBitmap(selectFleetDown,fleetKing.getWidth(),selectFleetDown.getHeight(),false);
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
        if (hasWindowFocus && !mp.isPlaying()) {
            mp.start();
        } else {
            mp.pause();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void onDraw(Canvas canvas) {
        leftArrowX = (int) (screenW * 0.15) - leftArrow.getWidth() / 2;
        leftArrowY = (int) (screenH * 0.50) - leftArrow.getHeight() / 2;
        canvas.drawBitmap(leftArrow, leftArrowX, leftArrowY, null);
        rightArrowX = (int) (screenW * 0.85) - rightArrow.getWidth() / 2;
        rightArrowY = screenH / 2 - rightArrow.getHeight() / 2;
        canvas.drawBitmap(rightArrow, rightArrowX, rightArrowY, null);
        fleetKing = kings.get(fleetNum);
        fleetKingX = screenW / 2 - fleetKing.getWidth() / 2;
        fleetKingY = screenH / 2 - fleetKing.getHeight() / 2;
        canvas.drawBitmap(fleetKing, fleetKingX, fleetKingY, null);
        selectFleetX = fleetKingX;
        selectFleetY = (int) (fleetKingY + fleetKing.getHeight() + screenH * 0.1);
        if (selectFleetPressed){
            canvas.drawBitmap(selectFleetDown,selectFleetX,selectFleetY,null);
        }else {
            canvas.drawBitmap(selectFleet, selectFleetX, selectFleetY, null);
        }

        String text = fleets.get(fleetNum).getFleetName();
        canvas.drawText(text, 0, text.length(), screenW / 2, fleetKingY - (int)(screenH * 0.05) , blackPaint);
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
                    if (fleetNum + 1 < fleets.size()) {
                        fleetNum++;
                    } else {
                        fleetNum = 0;
                    }
                    break;
                }
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
                if (x > selectFleetX &&
                        x < selectFleetX + selectFleet.getWidth() &&
                        y > selectFleetY &&
                        y < selectFleetY + selectFleet.getHeight()) {
                    selectFleetPressed = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(selectFleetPressed){
                    playIntent = new Intent(myContext, PlayActivity.class);
                    myContext.startActivity(playIntent);
                }
                selectFleetPressed = false;
                break;
        }

        invalidate();
        return true;
    }
}
