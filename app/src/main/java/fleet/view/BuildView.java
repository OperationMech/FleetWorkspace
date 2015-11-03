package fleet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import fleet.activity.SelectionActivity;
import fleet.gameLogic.Fleet;
import fleet.gameLogic.PlayerGameBoard;
import fleet.gameLogic.Ship;

/**
 * Created by Radu on 10/20/2015.
 */
public class BuildView extends View {
    int screenW;
    int screenH;
    Fleet playerFleet;
    int destroyerCount = 0;
    int cruiserCount = 0;
    int battleShipCount = 0;
    Bitmap[] battleshipImgs = new Bitmap[4];
    Bitmap[] cruiserImgs = new Bitmap[4];
    Bitmap[] destroyerImgs = new Bitmap[4];
    Bitmap[] scaledImgs = new Bitmap[14];
    Bitmap carrierImg;
    PlayerGameBoard board = new PlayerGameBoard();
    int movingX;
    int movingY;
    int movingShipSlot = -1;
    Bitmap movingShipImg = null;


    SelectionActivity myContext;
    Point[] slotsOrigin = new Point[12];

    public BuildView(Context myContext, Fleet playerFleet) {
        super(myContext);
        this.playerFleet = playerFleet;
        this.myContext = (SelectionActivity) myContext;
        board.fleetPositions[4] = playerFleet.getCarrier();
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
        Point origin;
        int x;
        int y;
        int pointNum = 0;
        //Creating a 3x4 grid for card placement
        for (int row = 0; row < 4; row++) {
            y = (int) ((screenH * .025) + (row * (screenH * .25)));
            for (int column = 0; column < 3; column++) {
                x = (int) ((screenW * .025) + (column * (screenW * .33)));
                origin = new Point(x, y);
                slotsOrigin[pointNum] = origin;
                pointNum++;
            }
        }

        //scaling Images
        Ship[] battleships = playerFleet.getBattleships();
        Ship[] cruisers = playerFleet.getCruisers();
        Ship[] destroyers = playerFleet.getDestroyers();
        int shipXScale = screenW / 4;
        int shipYScale = screenH / 5;
        for (int i = 0; i < 4; i++) {
            battleshipImgs[i] = Bitmap.createScaledBitmap(battleships[i].faceUp, shipXScale, shipYScale, false);
            scaledImgs[battleships[i].getShipNum()] = battleshipImgs[i];
            cruiserImgs[i] = Bitmap.createScaledBitmap(cruisers[i].faceUp, shipXScale, shipYScale, false);
            scaledImgs[cruisers[i].getShipNum()] = cruiserImgs[i];
            destroyerImgs[i] = Bitmap.createScaledBitmap(destroyers[i].faceUp, shipXScale, shipYScale, false);
            scaledImgs[destroyers[i].getShipNum()] = destroyerImgs[i];
        }
        carrierImg = Bitmap.createScaledBitmap(playerFleet.getCarrier().faceUp, shipXScale, shipYScale, false);
        scaledImgs[1] = carrierImg;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    /** @param canvas the canvas we will be drawing on
     *
     */
    protected void onDraw(Canvas canvas) {
        // Drawing stacks
        if (destroyerCount < 4) {
            Point destroyerStack = slotsOrigin[9];
            canvas.drawBitmap(destroyerImgs[destroyerCount], destroyerStack.x, destroyerStack.y, null);
        }
        if (cruiserCount < 4) {
            Point cruiserStack = slotsOrigin[10];
            canvas.drawBitmap(cruiserImgs[cruiserCount], cruiserStack.x, cruiserStack.y, null);
        }
        if (battleShipCount <4) {
            Point battleShipStack = slotsOrigin[11];
            canvas.drawBitmap(battleshipImgs[battleShipCount], battleShipStack.x, battleShipStack.y, null);
        }
        //Drawing positions
        for (int i = 0; i < 9; i++) {
            if (board.fleetPositions[i] != null) {
                Ship ship = board.fleetPositions[i];
                Bitmap scaledImg = scaledImgs[ship.getShipNum()];
                canvas.drawBitmap(scaledImg, slotsOrigin[i].x, slotsOrigin[i].y, null);
            }
        }
        //Drawing moving Image
        if (movingShipImg != null){
            canvas.drawBitmap(movingShipImg,movingX,movingY,null);
        }
        //canvas.drawBitmap(carrierImg, slotsOrigin[4].x, slotsOrigin[4].y, null);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        int slotScaleX = screenW / 4;
        int slotScaleY = screenH / 5;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                for (movingShipSlot = 0; movingShipSlot < 12; movingShipSlot++) {
                    Point slot = slotsOrigin[movingShipSlot];
                    if (x > slot.x
                            && x < slot.x + slotScaleX
                            && y > slot.y
                            && y < slot.y + slotScaleY) {
                        movingX = x;
                        movingY = y;
                        break;
                    }
                }
                if (movingShipSlot > 0 && movingShipSlot < 9){
                    movingShipImg = scaledImgs[board.fleetPositions[movingShipSlot].getShipNum()];
                    invalidate();
                }
                if (movingShipSlot == 9){
                    movingShipImg = destroyerImgs[destroyerCount];
                    destroyerCount ++;
                    invalidate();
                }
                if (movingShipSlot == 10){
                    movingShipImg = cruiserImgs[cruiserCount];
                    cruiserCount ++;
                    invalidate();
                }
                if (movingShipSlot == 11){
                    movingShipImg = battleshipImgs[battleShipCount];
                    battleShipCount ++;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                movingX = x;
                movingY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (movingShipSlot >= 0) {
                    Ship temp = null;
                    for (int i = 0; i < 12; i++) {
                        Point slot = slotsOrigin[i];
                        if (x > slot.x
                                && x < slot.x + slotScaleX
                                && y > slot.y
                                && y < slot.y + slotScaleY) {
                            //We are moving a ship already on the grid to another location
                            if (i < 9 && movingShipSlot < 9) {
                                if (board.fleetPositions[i] != null) {
                                    temp = board.fleetPositions[i];
                                }
                                board.fleetPositions[i] = board.fleetPositions[movingShipSlot];
                                board.fleetPositions[movingShipSlot] = temp;
                            }
                            //We are moving a destroyer off the stack onto the board
                            else if (movingShipSlot == 9) {
                                board.fleetPositions[i] = playerFleet.getDestroyers()[destroyerCount-1];
                            }
                            //We are moving a cruiser off the stack onto the board
                            else if (movingShipSlot == 10) {
                                board.fleetPositions[i] = playerFleet.getCruisers()[cruiserCount-1];
                            }
                            //We are moving a battleship off the stack onto the board
                            else if (movingShipSlot == 11) {
                                board.fleetPositions[i] = playerFleet.getBattleships()[battleShipCount-1];
                            }
                        }
                    }
                    movingShipImg = null;
                    invalidate();
                    break;
                }

                invalidate();
                return true;
        }
        return true;
    }
}

