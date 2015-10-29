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

}
