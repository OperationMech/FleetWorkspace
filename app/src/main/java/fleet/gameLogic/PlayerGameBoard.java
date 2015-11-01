package fleet.gameLogic;

import java.util.ArrayList;

/**
 * Per player gameboard class
 * Created by Radu on 9/27/2015.
 */
public class PlayerGameBoard {
    protected ArrayList<Ship> fleetPositions = new ArrayList<Ship>();
    private String fleetpath;

    /**
     * PlayerGameBoard constructor
     * @param selectedShips list of ships
     * @param fleetpath the player's fleet path
     */
    public PlayerGameBoard(ArrayList<Ship> selectedShips,String fleetpath) {
        this.fleetpath = fleetpath;
        this.fleetPositions = selectedShips;
    }

    public int size() {
        return fleetPositions.size();
    }

}
