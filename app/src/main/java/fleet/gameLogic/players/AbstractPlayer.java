package fleet.gameLogic.players;

import fleet.gameLogic.Fleet;
import fleet.gameLogic.PlayerGameBoard;

/**
 * Created by Radu on 9/30/2015.
 */
abstract class AbstractPlayer implements Player {
    protected Fleet playerFleet;
    protected PlayerGameBoard playerGameBoard;

    @Override
    public Fleet getFleet() {
        return playerFleet;
    }
}
