package fleet.classes.players;

import fleet.classes.gameLogic.Fleet;
import fleet.classes.gameLogic.PlayerGameBoard;

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
