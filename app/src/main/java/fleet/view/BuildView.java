package fleet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;

import fleet.activity.GameActivity;

/**
 * Created by Radu on 10/20/2015.
 */
public class BuildView extends View {
    int screenW;
    int screenH;
    ArrayList<Bitmap> fleetImgs;
    GameActivity myContext;
    Point[] slotsOrigin = new Point[12];
    Bitmap[] shipImages = new Bitmap[12];

    public BuildView( Context myContext, ArrayList<Bitmap> fleetImgs){
        super(myContext);
        this.fleetImgs = fleetImgs;
        this.myContext = (GameActivity)myContext;
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

    @Override
    /** @param canvas the canvas we will be drawing on
     *
     */
    protected void onDraw(Canvas canvas){
        int i = 0;
        for (Point point : slotsOrigin) {
            System.out.println(point.x + " " + point.y);
            canvas.drawBitmap(fleetImgs.get(i), point.x, point.y, null);
            canvas.drawCircle(point.x, point.y, 5, new Paint());
            System.out.println(i);
            i++;
        }


    }
}