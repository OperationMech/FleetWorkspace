package fleet.classes.players;

/**
 * Created by Radu on 9/27/2015.
 */
public class ComputerPlayer implements Player {
    @Override
    public void startTurn() {

    }

    @Override
    public boolean scout() {
        return false;
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
}
