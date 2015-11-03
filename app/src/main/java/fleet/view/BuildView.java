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
import fleet.gameLogic.ShipClass;

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
    Bitmap carrierImg;
    PlayerGameBoard board = new PlayerGameBoard();
    int movingX;
    int movingY;
    int movingShip = -1;


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
            cruiserImgs[i] = Bitmap.createScaledBitmap(cruisers[i].faceUp, shipXScale, shipYScale, false);
            destroyerImgs[i] = Bitmap.createScaledBitmap(destroyers[i].faceUp, shipXScale, shipYScale, false);
        }
        carrierImg = Bitmap.createScaledBitmap(playerFleet.getCarrier().faceUp, shipXScale, shipYScale, false);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void organizeCards() {

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
                System.out.println(i +" ?");
                Ship ship = board.fleetPositions[i];
                Bitmap scaledImg = null;
                System.out.println("aaa"  + ship.shipClass);
                switch (ship.shipClass) {
                    case BATTLESHIP:
                        scaledImg = battleshipImgs[13 - ship.getShipNum()];
                        break;
                    case CRUISER:
                        scaledImg = cruiserImgs[9 - ship.getShipNum()];
                        break;
                    case DESTROYER:
                        scaledImg = destroyerImgs[5 - ship.getShipNum()];
                        break;
                    case CARRIER:
                        scaledImg = carrierImg;
                        break;
                }
                canvas.drawBitmap(scaledImg, slotsOrigin[i].x, slotsOrigin[i].y, null);

            }
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
                for (movingShip = 0; movingShip < 12; movingShip++) {
                    Point slot = slotsOrigin[movingShip];
                    if (x > slot.x
                            && x < slot.x + slotScaleX
                            && y > slot.y
                            && y < slot.y + slotScaleY) {
                        break;
                    }
                }
                System.out.println("hey"+movingShip);
                if (movingShip  == 9){
                    destroyerCount ++;
                    invalidate();
                }
                if (movingShip  == 10){
                    cruiserCount ++;
                    invalidate();
                }
                if (movingShip  == 11){
                    battleShipCount ++;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                movingX = x;
                movingY = y;
                break;
            case MotionEvent.ACTION_UP:
                if (movingShip >= 0) {
                    int endSlot;
                    Ship temp = null;
                    for (int i = 0; i < 12; i++) {
                        Point slot = slotsOrigin[i];
                        if (x > slot.x
                                && x < slot.x + slotScaleX
                                && y > slot.y
                                && y < slot.y + slotScaleY) {
                            //We are moving a ship already on the grid to another location
                            if (i < 9 && movingShip < 9) {
                                if (board.fleetPositions[i] != null) {
                                    temp = board.fleetPositions[i];
                                }
                                board.fleetPositions[i] = board.fleetPositions[movingShip];
                                board.fleetPositions[movingShip] = temp;
                            }
                            //We are moving a destroyer off the stack onto the board
                            else if (movingShip == 9 && destroyerCount < 5) {
                                board.fleetPositions[i] = playerFleet.getDestroyers()[destroyerCount-1];
                            }
                            //We are moving a cruiser off the stack onto the board
                            else if (movingShip == 10 && cruiserCount < 5) {
                                board.fleetPositions[i] = playerFleet.getCruisers()[cruiserCount-1];
                                System.out.println("CruiserHIT");
                            }
                            //We are moving a battleship off the stack onto the board
                            else if (movingShip == 11 && battleShipCount < 5) {
                                board.fleetPositions[i] = playerFleet.getBattleships()[battleShipCount-1];
                            }
                        }
                    }
                    invalidate();
                    break;
                }

                invalidate();
                return true;
        }
        return true;
    }
}

