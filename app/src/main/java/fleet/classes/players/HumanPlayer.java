package fleet.classes.players;

import fleet.classes.gameLogic.Fleet;
import fleet.classes.gameLogic.PlayerGameBoard;
import fleet.classes.gameLogic.Ship;

/**
 * Created by Radu on 9/27/2015.
 */
public class HumanPlayer extends AbstractPlayer{

    public HumanPlayer() {

    }

    @Override
    public void selectFleet() {

    }

    @Override
    public void scout() {

    }

    @Override
    public Ship[] attack() {
        return null;
    }

    @Override
    public boolean arrangeFleet() {
        return false;
    }

    @Override
    public Fleet getFleet() {
        return this.playerFleet;
    }
}
