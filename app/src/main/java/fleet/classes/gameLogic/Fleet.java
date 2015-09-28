package fleet.classes.gameLogic;

import android.graphics.Bitmap;

import java.lang.reflect.Array;
import java.util.EnumSet;

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

    public Fleet(String fleetPath){


    }

}
