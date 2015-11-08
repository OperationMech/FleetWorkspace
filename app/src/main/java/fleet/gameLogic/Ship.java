package fleet.gameLogic;

import android.graphics.Bitmap;

/**
 * Ship class
 * Created by Radu on 9/27/2015.
 */
public class Ship {
    public Bitmap faceUp;
    protected Boolean isSunk;
    protected Boolean isFaceUp;
    public ShipClass shipClass;
    protected int shipNum;

    /**
     * Constructor for Ship
     * @param faceUp card face image
     */
    public Ship(Bitmap faceUp, int shipNum ) {
        this.isFaceUp = false;
        this.faceUp = faceUp;
        this.shipNum = shipNum;
        if (shipNum == 1){
            this.shipClass = ShipClass.CARRIER;
        }else if (shipNum > 1 && shipNum <= 5){
            this.shipClass = ShipClass.DESTROYER;
        } else if(shipNum > 5 && shipNum <= 9){
            this.shipClass = ShipClass.CRUISER;
        } else {
            this.shipClass = ShipClass.BATTLESHIP;
        }

    }

    public int getShipNum(){
        return shipNum;
    }

    public boolean getStatus() {
        return !isSunk;
    }

    public void sinkShip(boolean status) {
        isSunk = status;
    }

    /**
     * Card reveal function
     */
    public void reveal(){
        isFaceUp = true;
    }

    /**
     * Getter for isFaceUp
     * @return isFaceUp boolean status
     */
    public boolean getFaceUpStatus() {
        return isFaceUp;
    }
}
