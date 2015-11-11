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
    public boolean hasCarrier = true;

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

    public ArrayList<Ship> getFaceUpShips() {
        ArrayList<Ship> faceUpShips = new ArrayList<Ship>();
        for (Ship ship : fleetPositions) {
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
     * Setter for revealing the ship at an index
     *
     * @param index the index to reveal at
     */
    public void revealShipAt(int index) {
        fleetPositions[index].reveal();
    }

    /**
     * Status check on if all ships are sunk
     *
     * @return boolean status of all ships
     */
    public boolean allShipsSunk() {
        if (getShips().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void sinkShip(Ship ship){
        ship.sinkShip(true);
        if (ship.shipClass == ShipClass.CARRIER){
            hasCarrier = false;
        }
    }

    public boolean isFull() {
        for (Ship ship: fleetPositions){
            if (ship == null) {
                return false;
            }
        }
        return true;
    }

}
