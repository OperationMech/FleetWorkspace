package fleet.gameLogic.players;
import fleet.gameLogic.Fleet;
import fleet.gameLogic.Ship;

/**
 * Player interface
 * Created by Radu on 9/27/2015.
 */
public interface Player {
    /**
     * Fleet selection
     */
    public void selectFleet();

    /**
     * Scout selection
     */
    public void scout();

    /**
     * Attack selection
     * @return both attacker and target ships
     */
    public Ship[] attack();

    /**
     * "Ready" function
     * @return if the player has their fleet arranged for a game
     */
    public boolean arrangeFleet();

    /**
     * Getter for player fleet
     * @return player's fleet
     */
    public Fleet getFleet();

}
