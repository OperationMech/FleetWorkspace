package fleet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
        for (int i = 0; i < 12; i++){
            origin  =  new Point(screenW,screenH);
            slotsOrigin[i] = origin;
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas){


    }
}