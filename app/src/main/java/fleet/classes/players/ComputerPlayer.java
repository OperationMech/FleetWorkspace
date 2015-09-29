package fleet.classes.players;

import fleet.classes.gameLogic.Ship;
import fleet.classes.gameLogic.Fleet;

/**
 * Created by Radu on 9/27/2015.
 */
public class ComputerPlayer implements Player {
    public ComputerPlayer() {
        super();
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
        return null;
    }
}
