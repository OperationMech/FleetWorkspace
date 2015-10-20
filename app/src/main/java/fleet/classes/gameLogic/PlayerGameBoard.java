package fleet.classes.gameLogic;

import fleet.classes.players.Player;

/**
 * Created by Radu on 9/27/2015.
 */
public class PlayerGameBoard {
    protected Ship[] fleetPositions = new Ship[9];
    private String fleetpath;

    public PlayerGameBoard(String[] positions,String fleetpath) {
        this.fleetpath = fleetpath;
        this.fleetPositions = buildBoard(positions);
    }

    protected Ship[] buildBoard(String[] positions){
        for (String shipName : positions){

        }
        return null;
    }

}
