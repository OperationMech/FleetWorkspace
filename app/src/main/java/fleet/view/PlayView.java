package fleet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import fleet.activity.PlayActivity;
import fleet.gameLogic.PlayerGameBoard;
import fleet.gameLogic.Ship;

/**
 * Created by Radu on 10/18/2015.
 */
public class PlayView extends View {
    PlayerGameBoard board;
    PlayActivity myContext;
    int screenW;
    int screenH;
    Point[] slotsOrigin = new Point[9];
    Bitmap[] scaledImgs = new Bitmap[14];
    int selectedShip = -1;
    private Paint blackPaint;

    public PlayView(Context myContext, PlayerGameBoard board) {
        super(myContext);
        this.board = board;
        this.myContext = (PlayActivity) myContext;
        blackPaint = new Paint();
        blackPaint.setAntiAlias(true);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setTextSize((float) 24.0);
        blackPaint.setTextAlign(Paint.Align.CENTER);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        screenW = w;
        screenH = h;
        Point origin;
        int x;
        int y;
        int pointNum = 0;
        //Creating a 3x3 grid for card placement
        for (int row = 0; row < 3; row++) {
            y = (int) ((screenH * .025) + (row * (screenH * .25)));
            for (int column = 0; column < 3; column++) {
                x = (int) ((screenW * .025) + (column * (screenW * .33)));
                origin = new Point(x, y);
                slotsOrigin[pointNum] = origin;
                pointNum++;
            }
        }
        int shipXScale = screenW / 4;
        int shipYScale = screenH / 5;

        for (Ship ship : board.fleetPositions) {
            scaledImgs[ship.getShipNum()] = Bitmap.createScaledBitmap(ship.faceUp, shipXScale, shipYScale, false);
        }
    }

    @Override
    /** @param canvas the canvas we will be drawing on
     *
     */
    protected void onDraw(Canvas canvas) {
        //Drawing positions
        for (int i = 0; i < 9; i++) {
            if (board.fleetPositions[i] != null) {
                Ship ship = board.fleetPositions[i];
                Bitmap scaledImg = scaledImgs[ship.getShipNum()];
                canvas.drawBitmap(scaledImg, slotsOrigin[i].x, slotsOrigin[i].y, null);
            }
        }
        if(selectedShip >= 0 ) {
            String text = "Selected: " + board.fleetPositions[selectedShip].shipClass.toString();
            canvas.drawText(text, 0, text.length(), (int) (screenW * .75), (int) (screenH * 0.95), blackPaint);
            System.out.println(text);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        int slotScaleX = screenW / 4;
        int slotScaleY = screenH / 5;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                for (selectedShip = 0; selectedShip < 9; selectedShip++) {
                    Point slot = slotsOrigin[selectedShip];
                    if (x > slot.x
                            && x < slot.x + slotScaleX
                            && y > slot.y
                            && y < slot.y + slotScaleY) {
                        System.out.println("AAAAAAAAAA " + selectedShip);
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}