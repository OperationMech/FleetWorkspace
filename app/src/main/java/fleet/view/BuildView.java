package fleet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;
import fleet.gameLogic.Fleet;

import java.util.ArrayList;

import fleet.activity.SelectionActivity;
import fleet.gameLogic.players.Player;

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


    SelectionActivity myContext;
    Point[] slotsOrigin = new Point[12];

    public BuildView( Context myContext, Fleet playerFleet){
        super(myContext);
        this.playerFleet = playerFleet;
        this.myContext = (SelectionActivity)myContext;
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
        for (int column = 0; column < 3; column++){
            x = (int)((screenW * .025) + (column * (screenW * .33)));
            for (int row = 0; row < 4;row++){
                y = (int)((screenH * .025) + (row * (screenH * .25)));
                origin = new Point(x,y);
                slotsOrigin[pointNum] = origin;
                pointNum++;
            }

        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void organizeCards(){

    }

    @Override
    /** @param canvas the canvas we will be drawing on
     *
     */
    protected void onDraw(Canvas canvas){
        int i = 0;
        Point destroyerStack  = slotsOrigin[9];
        Point cruiserStack = slotsOrigin[10];
        Point battleShipStack = slotsOrigin[11];
        canvas.drawBitmap(playerFleet.getBattleships()[battleShipCount].faceUp,battleShipStack.x,battleShipStack.y,null);

        for (Point point : slotsOrigin) {

            System.out.println(point.x + " " + point.y);
          //  canvas.drawBitmap(fleetImgs.get(i), point.x, point.y, null);
            i++;
        }


    }
}