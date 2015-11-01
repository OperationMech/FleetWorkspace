package fleet.gameLogic.players;

import java.util.ArrayList;

import fleet.gameLogic.Fleet;
import fleet.gameLogic.PlayerGameBoard;

/**
 * Abstract Player subclass
 * Created by Radu on 9/30/2015.
 */
public abstract class AbstractPlayer implements Player {
    protected Fleet playerFleet;
    protected PlayerGameBoard playerGameBoard;

    /**
     * Getter for player fleet
     * @return the player's fleet
     */
    @Override
    public Fleet getFleet() {
        return playerFleet;
    }


    public PlayerGameBoard getGameBoard() {
        return playerGameBoard;
    }

}
