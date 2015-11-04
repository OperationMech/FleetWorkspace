package fleet.gameLogic;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Fleet class with parcelization
 * Created by Radu on 9/27/2015.
 */
public class Fleet  {
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
        battleships[0] = new Ship(kingImg,13);
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
     * Getter for battleships
     * @return battleships array
     */
    public Ship[] getBattleships(){ return battleships;}

    /**
     * Getter for battleships
     * @return battleships array
     */
    public Ship[] getCruisers(){ return cruisers;}

    /**
     * Getter for battleships
     * @return battleships array
     */
    public Ship[] getDestroyers(){ return destroyers;}

    /**
     * Getter for the Carrier
     * @return the carrier
     */
    public Ship getCarrier(){ return carrier;}

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
     * @param name TransferBuffer ship's filename
     * @param ship TransferBuffer ship's image
     */
    public void populateFleet(String name, Bitmap ship) {
        if(name.startsWith("Two.")) {
            destroyers[3] = new Ship(ship,2);
        } else if (name.startsWith("Three.")) {
            destroyers[2] = new Ship(ship,3);
        } else if (name.startsWith("Four.")) {
            destroyers[1] = new Ship(ship,4);
        } else if (name.startsWith("Five.")) {
            destroyers[0] = new Ship(ship,5);
        } else if (name.startsWith("Six.")) {
            cruisers[3] = new Ship(ship,6);
        } else if (name.startsWith("Seven.")) {
            cruisers[2] = new Ship(ship,7);
        } else if (name.startsWith("Eight.")) {
            cruisers[1] = new Ship(ship,8);
        } else if (name.startsWith("Nine.")) {
            cruisers[0] = new Ship(ship,9);
        } else if (name.startsWith("Ten.")) {
            battleships[3] = new Ship(ship,10);
        } else if (name.startsWith("Jack.")) {
            battleships[2] = new Ship(ship,11);
        } else if (name.startsWith("Queen.")) {
            battleships[1] = new Ship(ship,12);
        }else if (name.startsWith("King.")) {
            battleships[0] = new Ship(ship,13);
        } else if (name.startsWith("Ace.")) {
            carrier = new Ship(ship,1);
        } else if (name.startsWith("FaceDown.")) {
            facedown = ship;
        }
    }

    /**
     * Parcelable creator
     */
  //  public static final Parcelable.Creator<Fleet> CREATOR =
    //        new Parcelable.Creator<Fleet>() {

   //             public Fleet createFromParcel(Parcel in) {
    //                return new Fleet(in);
     //           }

    //            public Fleet[] newArray(int size) {
     //               return new Fleet[size];
     //           }
    //        };

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
 //   @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcelize with flags
     * @param dest the target parcel
     * @param flags options
     */
  //  @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(fleetPath);
        dest.writeString(fleetName);
      //  dest.writeParcelable(carrier, flags);
        dest.writeParcelable(facedown, flags);
    }
}
