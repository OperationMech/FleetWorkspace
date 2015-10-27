package fleet.gameLogic.players;

import fleet.gameLogic.Ship;
import fleet.gameLogic.Fleet;

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
