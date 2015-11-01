package fleet.gameLogic;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Fleet class with parcelization
 * Created by Radu on 9/27/2015.
 */
public class Fleet implements Parcelable {
    protected String fleetPath;
    protected String fleetName;
    protected Ship carrier;
    protected AssetFileDescriptor fleetSoundFile;
    protected Bitmap facedown;
    protected Ship[] battleships = new Ship[4];
    protected Ship[] cruisers = new Ship[4];
    protected Ship[] destroyers = new Ship[4];


    /**
     * Fleet constructor
     * @param kingImg
     * @param fleetSoundFile
     * @param fleetPath
     */
    public Fleet(Bitmap kingImg, AssetFileDescriptor fleetSoundFile, String fleetPath){
        battleships[0] = new Ship(kingImg);
        fleetName = fleetPath.split("/")[1];
        this.fleetPath = fleetPath;
        this.fleetSoundFile = fleetSoundFile;
    }

    /**
     * Carrier in-play check
     * @return does the fleet have an operational carrier
     */
    public boolean hasCarrier() {
        return !carrier.isSunk;
    }

    /**
     * Getter for the king card image
     * @return King image
     */
    public Bitmap getKing() {
        return battleships[0].faceUp;
    }

    /**
     * Setter for the king card image
     * @param newKing image of the king card
     */
    public void setKing(Bitmap newKing ) {
        battleships[0].faceUp = newKing;
    }

    /**
     * Getter for fleet name
     * @return fleet's name
     */
    public String getFleetName() {
        return fleetName;
    }

    /**
     * Getter for fleet assets directory path
     * @return fleet's assets path
     */
    public String getFleetPath() {
        return fleetPath;
    }

    /**
     * Getter for the main attack file descriptor
     * @return fleet's main attack file descriptor
     */
    public AssetFileDescriptor getFleetAttack() {
        return fleetSoundFile;
    }

    /**
     * Adds ships to a fleet
     * @param name A ship's filename
     * @param ship A ship's image
     */
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
        }else if (name.equals("King.png")) {
            battleships[0] = new Ship(ship);
        } else if (name.equals("Ace.png")) {
            carrier = new Ship(ship);
        } else if (name.equals("FaceDown.png")) {
            facedown = ship;
        }
    }

    /**
     * Parcelable creator
     */
    public static final Parcelable.Creator<Fleet> CREATOR =
            new Parcelable.Creator<Fleet>() {

                public Fleet createFromParcel(Parcel in) {
                    return new Fleet(in);
                }

                public Fleet[] newArray(int size) {
                    return new Fleet[size];
                }
            };

    /**
     * De-parcel function
     * @param in Parcelized fleet
     */
    public Fleet(Parcel in) {
        this.fleetPath = in.readString();
        this.fleetName = in.readString();
        this.carrier = in.readParcelable(Ship.class.getClassLoader());
        this.facedown = in.readParcelable(Bitmap.class.getClassLoader());
    }

    /**
     * Parcel options flag function
     * @return flag options
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcelize with flags
     * @param dest the target parcel
     * @param flags options
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(fleetPath);
        dest.writeString(fleetName);
        dest.writeParcelable(carrier, flags);
        dest.writeParcelable(facedown, flags);
    }
}
