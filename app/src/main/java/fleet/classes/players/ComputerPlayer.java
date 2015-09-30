package fleet.classes.players;

import fleet.classes.gameLogic.Ship;
import fleet.classes.gameLogic.Fleet;

/**
 * Created by Radu on 9/27/2015.
 */
public class ComputerPlayer extends AbstractPlayer {
    public ComputerPlayer() {
        super();
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
        return null;
    }
}
