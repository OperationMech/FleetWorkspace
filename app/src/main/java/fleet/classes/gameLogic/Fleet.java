package fleet.classes.gameLogic;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.view.View;
import java.util.Map;


/**
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

    public Fleet(Parcel in) {
        this.fleetPath = in.readString();
        this.fleetName = in.readString();
        this.carrier = in.readParcelable(Ship.class.getClassLoader());
        this.facedown = in.readParcelable(Bitmap.class.getClassLoader());
        this.battleships = new Ship[4];
        in.readTypedArray(battleships, Ship.CREATOR);
        this.cruisers = new Ship[4];
        in.readTypedArray(cruisers, Ship.CREATOR);
        this.destroyers = new Ship[4];
        in.readTypedArray(destroyers, Ship.CREATOR);
    }


    public Fleet(Bitmap kingImg, AssetFileDescriptor fleetSoundFile, String fleetPath){
        battleships[0] = new Ship(kingImg);
        fleetName = fleetPath.split("/")[1];
        this.fleetPath = fleetPath;
        this.fleetSoundFile = fleetSoundFile;
    }

    public Bitmap getKing() {
        return battleships[0].faceUp;
    }

    public void setKing(Bitmap newKing ) {
        battleships[0].faceUp = newKing;
    }

    public String getFleetName() {
        return fleetName;
    }

    public String getFleetPath() {
        return fleetPath;
    }

    public void playFleetAttack() {
        SoundPool local = new SoundPool(1,1,1);
        local.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId,1,1,1,1,1);
            }
        });
        local.load(fleetSoundFile,1);
    }

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

    static final Parcelable.Creator<Fleet> CREATOR =
            new Parcelable.Creator<Fleet>() {

        public Fleet createFromParcel(Parcel in) {
            return new Fleet(in);
        }

        public Fleet[] newArray(int size) {
            return new Fleet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(fleetPath);
        dest.writeString(fleetName);
        dest.writeParcelable(carrier, flags);
        dest.writeParcelable(facedown, flags);
        dest.writeTypedArray(battleships, flags);
        dest.writeTypedArray(cruisers, flags);
        dest.writeTypedArray(destroyers, flags);
    }
}
