package fleet.classes.gameLogic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.lang.reflect.Array;
import java.util.EnumSet;

import fleet.R;

/**
 * Created by Radu on 9/27/2015.
 */
public class Fleet {
    protected String fleetPath;
    protected String FleetName;
    protected Ship carrier;
    protected Bitmap facedown;
    protected Ship[] battleships = new Ship[4];
    protected Ship[] cruisers = new Ship[4];
    protected Ship[] destroyers = new Ship[4];

    public Fleet(Bitmap kingImg){
        battleships[0] = new Ship(kingImg);
    }

    public Bitmap getKing() {
        return battleships[0].faceUp;
    }

    public void setKing(Bitmap newKing ) {   battleships[0].faceUp = newKing;   }

    public void populateFleet(){
        return;
    }
}
