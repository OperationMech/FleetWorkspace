package fleet.gameLogic.players;

import java.util.ArrayList;
import java.util.Random;

import fleet.gameLogic.PlayerGameBoard;
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
     * @param players arrayList of game players
     */
    @Override
    public void scout(ArrayList<AbstractPlayer> players) {
        if(playerFleet.hasCarrier()) {
            int localRandom = new Random().nextInt();
            players.remove(this);
            int targetPlayer = localRandom % players.size();
            PlayerGameBoard targetBoard = players.get(targetPlayer).getGameBoard();
            boolean hasTarget = false;
            while(!hasTarget) {
                int targetShip = localRandom % 9;
                if (targetBoard.getShips()[targetShip].getFaceUpStatus()) {
                    targetBoard.revealShipAt(targetShip);
                    hasTarget = true;
                }
            }
        }
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
