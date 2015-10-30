package fleet.gameLogic.players;

import fleet.gameLogic.Ship;
import fleet.gameLogic.Fleet;

/**
 * Computer player
 * Created by Radu on 9/27/2015.
 */
public class ComputerPlayer extends AbstractPlayer {

    /**
     * Computer player constructor
     */
    public ComputerPlayer() {
        super();
    }

    /**
     * Fleet selection
     */
    @Override
    public void selectFleet() {

    }

    /**
     * Scout selection
     */
    @Override
    public void scout() {

    }

    /**
     * Attack selection
     * @return both attacker and target ships
     */
    @Override
    public Ship[] attack() {
        return null;
    }

    /**
     * "Ready" function
     * @return for basic ai purposes always true
     */
    @Override
    public boolean arrangeFleet() {
        return true;
    }

    /**
     * Getter for player fleet
     * @return player's fleet
     */
    @Override
    public Fleet getFleet() {
        return null;
    }
}
