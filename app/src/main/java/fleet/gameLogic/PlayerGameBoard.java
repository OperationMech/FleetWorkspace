package fleet.gameLogic;


/**
 * Per player gameboard class
 * Created by Radu on 9/27/2015.
 */
public class PlayerGameBoard {
    protected Ship[] fleetPositions = new Ship[9];
    private String fleetpath;

    /**
     * PlayerGameBoard constructor
     * @param selectedShips list of ships
     * @param fleetpath the player's fleet path
     */
    public PlayerGameBoard(Ship[] selectedShips,String fleetpath) {
        this.fleetpath = fleetpath;
        this.fleetPositions = selectedShips;
    }

    /**
     * Getter for Ship array
     * @return Ship array
     */
    public Ship[] getShips() {
        return fleetPositions;
    }

    /**
     * Setter for revealing the ship at an index
     * @param index the index to reveal at
     */
    public void revealShipAt(int index) {
        fleetPositions[index].reveal();
    }

    /**
     * Status check on if all ships are sunk
     * @return boolean status of all ships
     */
    public boolean allShipsSunk() {
        return  fleetPositions[0].isSunk && fleetPositions[1].isSunk &&
                fleetPositions[2].isSunk && fleetPositions[3].isSunk &&
                fleetPositions[4].isSunk && fleetPositions[5].isSunk &&
                fleetPositions[6].isSunk && fleetPositions[7].isSunk &&
                fleetPositions[8].isSunk;
    }

}
