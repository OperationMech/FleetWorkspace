package fleet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

/**
 * Created by Radu on 10/20/2015.
 */
public class BuildView extends View {
    int screenW;
    int screenH;
    Point[] slotsOrigin = new Point[12];
    Bitmap[] shipImages = new Bitmap[12];

    public BuildView( Context myContext){
        super(myContext);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        screenW = w;
        screenH = h;
        Point origin;
        int x;
        int y;
        int pointNum = 0;
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
    protected void onDraw(Canvas canvas){
        for (Point point : slotsOrigin) {
            System.out.println(point.x + " " + point.y);
            canvas.drawCircle(point.x, point.y,5,new Paint());
        }


    }
}