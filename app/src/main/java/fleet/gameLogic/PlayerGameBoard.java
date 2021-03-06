package fleet.gameLogic;

import android.graphics.Bitmap;

import java.util.ArrayList;

import fleet.gameLogic.players.Player;

/**
 * Per player gameboard class
 * Created by Radu on 9/27/2015.
 */
public class PlayerGameBoard {
    public Ship[] fleetPositions = new Ship[9];
    public Bitmap faceDown;
    public Player player;

    /**
     *
     * Enpty constructor
     */
    public PlayerGameBoard(){

    }

    /**
     * Getter for Ship array
     *
     * @return Ship array
     */
    public ArrayList<Ship> getShips() {
        ArrayList<Ship> aliveShips = new ArrayList<Ship>();
        for (Ship ship : fleetPositions) {
            if (!ship.isSunk) {
                aliveShips.add(ship);
            }
        }
        return aliveShips;
    }
    /**
     * Checks if a board has a carrier
     * @return True if the board has a carrier, false otherwise.
     */
    public boolean hasCarrier(){
        boolean carrierStatus = true;
        for (Ship ship : fleetPositions) {
            if (ship.shipClass == ShipClass.CARRIER){
                carrierStatus = ship.getStatus();
                break;
            }
        }
        return  carrierStatus;
    }

    /**
     * @return  ArrayList of ships that still face up on the board
     */
    public ArrayList<Ship> getFaceUpShips() {
        ArrayList<Ship> faceUpShips = new ArrayList<Ship>();
        for (Ship ship : getShips()) {
            if (ship.isFaceUp) {
                faceUpShips.add(ship);
            }
        }
        return faceUpShips;
    }

    /**
     * Setter for the faceDown bitmap
     *
     * @param faceDown the index to reveal at
     */
    public void setFaceDown(Bitmap faceDown){
        this.faceDown = faceDown;
    }

    /**
     * Resets the board to its initial state.
     */
    public void reset() {
        for(Ship ship : fleetPositions) {
            ship.isFaceUp = false;
            ship.isSunk = false;
        }
    }

    /**
     * Checks if a board is fully populated with ships.
     */
    public boolean isFull() {
        for (Ship ship: fleetPositions){
            if (ship == null) {
                return false;
            }
        }
        return true;
    }

}
