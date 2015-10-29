package fleet.gameLogic.players;

import fleet.gameLogic.Fleet;
import fleet.gameLogic.PlayerGameBoard;

/**
 * Player Abstract implementation
 * Created by Radu on 9/30/2015.
 */
abstract class AbstractPlayer implements Player {
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
}
