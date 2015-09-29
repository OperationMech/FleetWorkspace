package fleet.classes.players;

import fleet.classes.gameLogic.Fleet;
import fleet.classes.gameLogic.PlayerGameBoard;
import fleet.classes.gameLogic.Ship;

/**
 * Created by Radu on 9/27/2015.
 */
public class HumanPlayer implements Player {

    protected Fleet playerFleet;
    protected PlayerGameBoard playerGameBoard;

    public HumanPlayer() {
    }

    @Override
    public void startTurn() {
    }


    @Override
    public void selectFleet() {

    }

    @Override
    public void scout() {

    }

    @Override
    public boolean attack() {
        return false;
    }

    @Override
    public boolean arrangeFleet() {
        return false;
    }

    @Override
    public void endTurn() {
    }

    @Override
    public Fleet getFleet() {
        return this.playerFleet;
    }
}
