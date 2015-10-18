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
    //protected String fleetPath;
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

    public void populateFleet(String name, Bitmap ship) {
        if(name.equals("Two.png")) {
            destroyers[3] = new Ship(ship);
        } else if (name.equals("Three.png")) {
            destroyers[2] = new Ship(ship);
        } else if (name.equals("Four.png")) {
            destroyers[1] = new Ship(ship);
        } else if (name.equals("Five.png")) {
            destroyers[0] = new Ship(ship);
        } else if (name.equals("Six.png")) {
            cruisers[3] = new Ship(ship);
        } else if (name.equals("Seven.png")) {
            cruisers[2] = new Ship(ship);
        } else if (name.equals("Eight.png")) {
            cruisers[1] = new Ship(ship);
        } else if (name.equals("Nine.png")) {
            cruisers[0] = new Ship(ship);
        } else if (name.equals("Ten.png")) {
            battleships[3] = new Ship(ship);
        } else if (name.equals("Jack.png")) {
            battleships[2] = new Ship(ship);
        } else if (name.equals("Queen.png")) {
            battleships[1] = new Ship(ship);
        } else if (name.equals("Ace.png")) {
            carrier = new Ship(ship);
        } else if (name.equals("FaceDown.png")) {
            facedown = ship;
        }
    }
}
