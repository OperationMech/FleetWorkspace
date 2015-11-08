package fleet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import java.lang.annotation.Target;

import fleet.R;
import fleet.activity.PlayActivity;
import fleet.gameLogic.PlayerGameBoard;
import fleet.gameLogic.Ship;
import fleet.gameLogic.players.Player;

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
    public Player player;
    Point targetingButtonOrigin;
    Bitmap findTarget = BitmapFactory.decodeResource(getResources(), R.drawable.find_target);
    Bitmap confirmTarget = BitmapFactory.decodeResource(getResources(), R.drawable.confirm_target);
    Point myFleetOrigin;
    Bitmap myFleet = BitmapFactory.decodeResource(getResources(), R.drawable.my_fleet);
    Point selectedTextOrigin;

    /**
     * PlayView constructor
     * @param myContext the context instance
     * @param player the player instance
     */
    public PlayView(Context myContext, Player player) {
        super(myContext);
        this.player = player;
        this.board = player.getGameBoard();
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
        //Scaling images
        int shipXScale = screenW / 4;
        int shipYScale = screenH / 5;
        for (Ship ship : board.fleetPositions) {
            scaledImgs[ship.getShipNum()] = Bitmap.createScaledBitmap(ship.faceUp, shipXScale, shipYScale, false);
        }
        confirmTarget = Bitmap.createScaledBitmap(confirmTarget,(int)(scaledImgs[1].getWidth() * 1.5), confirmTarget.getHeight(),false);
        findTarget = Bitmap.createScaledBitmap(findTarget,(int)(scaledImgs[1].getWidth() * 1.5), confirmTarget.getHeight(),false);
        myFleet = Bitmap.createScaledBitmap(myFleet,(int)(scaledImgs[1].getWidth() * 1.5), confirmTarget.getHeight(),false);

        //Finding other UI origin points
        targetingButtonOrigin = new Point((int) (screenW * .60), (int) (screenH * 0.75));
        myFleetOrigin = new Point((int)(screenW * .70), (int) (screenH * 0.85));
        selectedTextOrigin = new Point((int) (screenW * .25), (int) (screenH * 0.95));
    }

    @Override
    /** @param canvas the canvas we will be drawing on
     *
     */
    protected void onDraw(Canvas canvas) {
        //Checking if the current player is the player that owns this board
        if (player.getPlayerID() == myContext.getCurrentPlayer()) {
            //Drawing positions
            for (int i = 0; i < 9; i++) {
                if (board.fleetPositions[i] != null) {
                    Ship ship = board.fleetPositions[i];
                    Bitmap scaledImg = scaledImgs[ship.getShipNum()];
                    canvas.drawBitmap(scaledImg, slotsOrigin[i].x, slotsOrigin[i].y, null);
                }
            }
            if (selectedShip >= 0) {
                String text = "Selected: " + board.fleetPositions[selectedShip].shipClass.toString();
                canvas.drawText(text, 0, text.length(), selectedTextOrigin.x, selectedTextOrigin.y, blackPaint);
                System.out.println(text);
                canvas.drawBitmap(findTarget,targetingButtonOrigin.x,targetingButtonOrigin.y,null);
            }
        }else{
            //TODO: Draw things facedown
            canvas.drawBitmap(myFleet,myFleetOrigin.x,myFleetOrigin.y,null);
            canvas.drawBitmap(confirmTarget,targetingButtonOrigin.x,targetingButtonOrigin.y,null);
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
                for (int i = 0; i < 9; i++) {
                    Point slot = slotsOrigin[i];
                    if (x > slot.x
                            && x < slot.x + slotScaleX
                            && y > slot.y
                            && y < slot.y + slotScaleY) {
                        selectedShip = i;
                        break;
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}