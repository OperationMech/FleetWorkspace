package fleet.classes.gameLogic;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.EnumSet;

/**
 * Created by Radu on 9/27/2015.
 */
public class Ship implements Parcelable {
    protected Bitmap faceUp;
    protected Boolean isSunk;
    protected Boolean isFaceUp;
    protected ShipClass shipClass;
    protected int cardNum;

    public Ship (Parcel in) {
        faceUp = in.readParcelable(Bitmap.class.getClassLoader());
        isSunk = (Boolean) in.readValue(Boolean.class.getClassLoader());
        isFaceUp = (Boolean) in.readValue(Boolean.class.getClassLoader());
        shipClass = (ShipClass) in.readValue(ShipClass.class.getClassLoader());
        cardNum = in.readInt();

    }

    static final Parcelable.Creator<Ship> CREATOR = new Parcelable.Creator<Ship>() {
        public Ship createFromParcel(Parcel in) {
            return new Ship(in);
        }

        public Ship[] newArray(int size) {
            return new Ship[size];
        }
    };

    public Ship(Bitmap faceUp){
        this.faceUp = faceUp;
    }
    public void reveal(){
        this.isFaceUp = true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(faceUp, 0);
        dest.writeValue(isSunk);
        dest.writeValue(isFaceUp);
        dest.writeValue(shipClass);
        dest.writeInt(cardNum);
    }
}
