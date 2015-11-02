package fleet.gameLogic;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Ship class
 * Created by Radu on 9/27/2015.
 */
public class Ship implements Parcelable {
    public Bitmap faceUp;
    protected Boolean isSunk;
    protected Boolean isFaceUp;
    public ShipClass shipClass;
    protected int shipNum;

    /**
     * Ship constructor from parcel
     * @param in parcelized form
     */
    public Ship (Parcel in) {
        faceUp = in.readParcelable(Bitmap.class.getClassLoader());
        isSunk = (Boolean) in.readValue(Boolean.class.getClassLoader());
        isFaceUp = (Boolean) in.readValue(Boolean.class.getClassLoader());
        shipClass = (ShipClass) in.readValue(ShipClass.class.getClassLoader());
        shipNum = in.readInt();

    }


    /**
     * Parcelable creator
     */
    static final Parcelable.Creator<Ship> CREATOR = new Parcelable.Creator<Ship>() {
        public Ship createFromParcel(Parcel in) {
            return new Ship(in);
        }

        public Ship[] newArray(int size) {
            return new Ship[size];
        }
    };

    /**
     * Constructor for Ship
     * @param faceUp card face image
     */
    public Ship(Bitmap faceUp,int shipNum) {
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

    /**
     * Parcelized flag options function
     * @return flag options
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcelize with flags
     * @param dest target parcel
     * @param flags options
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(faceUp, 0);
        dest.writeValue(isSunk);
        dest.writeValue(isFaceUp);
        dest.writeValue(shipClass);
        dest.writeInt(shipNum);
    }
}
