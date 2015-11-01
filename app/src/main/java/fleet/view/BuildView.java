package fleet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import fleet.activity.SelectionActivity;
import fleet.gameLogic.Fleet;
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
    Bitmap carrierImg;


    SelectionActivity myContext;
    Point[] slotsOrigin = new Point[12];

    public BuildView(Context myContext, Fleet playerFleet) {
        super(myContext);
        this.playerFleet = playerFleet;
        this.myContext = (SelectionActivity) myContext;
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
        for (int column = 0; column < 3; column++) {
            x = (int) ((screenW * .025) + (column * (screenW * .33)));
            for (int row = 0; row < 4; row++) {
                y = (int) ((screenH * .025) + (row * (screenH * .25)));
                origin = new Point(x, y);
                slotsOrigin[pointNum] = origin;
                pointNum++;
            }
        }

        //scaling Images
        Ship[] battleships = playerFleet.getBattleships();
        Ship[] cruisers = playerFleet.getCruisers();
        Ship[] destroyers = playerFleet.getDestroyers();
        int shipXScale = screenW/4;
        int shipYScale = screenH/5;
        for (int i = 0; i < 4; i++) {
            battleshipImgs[i] = Bitmap.createScaledBitmap(battleships[i].faceUp,shipXScale,shipYScale,false);
            cruiserImgs[i] = Bitmap.createScaledBitmap(cruisers[i].faceUp,shipXScale,shipYScale,false);
            destroyerImgs[i] = Bitmap.createScaledBitmap(destroyers[i].faceUp,shipXScale,shipYScale,false);
        }
        carrierImg = Bitmap.createScaledBitmap(playerFleet.getCarrier().faceUp,shipXScale,shipYScale,false);
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
        Point destroyerStack = slotsOrigin[3];
        Bitmap topDestroyer = playerFleet.getDestroyers()[destroyerCount].faceUp;
        canvas.drawBitmap(destroyerImgs[destroyerCount], destroyerStack.x, destroyerStack.y, null);
        Point cruiserStack = slotsOrigin[7];
        Bitmap topCruiser = cruiserImgs[cruiserCount];
        canvas.drawBitmap(topCruiser, cruiserStack.x, cruiserStack.y, null);
        Point battleShipStack = slotsOrigin[11];
        Bitmap topBattleShip = battleshipImgs[battleShipCount];
        canvas.drawBitmap(topBattleShip, battleShipStack.x, battleShipStack.y, null);

        //Drawing positions
        canvas.drawBitmap(carrierImg,slotsOrigin[5].x,slotsOrigin[5].y,null);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        invalidate();
        return true;
    }
}

