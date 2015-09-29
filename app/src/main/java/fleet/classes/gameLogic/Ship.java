package fleet.classes.gameLogic;

import android.graphics.Bitmap;

import java.util.EnumSet;

/**
 * Created by Radu on 9/27/2015.
 */
public class Ship {
    protected Bitmap faceUp;
    protected boolean isSunk;
    protected boolean isFaceUp;
    protected ShipClass shipClass;
    protected int cardNum;

    public void reveal(){
        this.isFaceUp = true;
    }
}
