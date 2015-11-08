package fleet.gameLogic.players;
import java.util.ArrayList;

import fleet.gameLogic.Fleet;
import fleet.gameLogic.PlayerGameBoard;
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
    public void scout(ArrayList<AbstractPlayer> players);

    /**
     * Attack selection
     * @return both attacker and target ships
     */
    public Ship[] attack();

    /**
     * "Ready" function
     * @return if the player has their fleet arranged for a game
     */
    public boolean ready();

    /**
     * Getter for player fleet
     * @return player's fleet
     */
    public Fleet getFleet();

    /**
     * Getter for player PlayerGameBoard
     * @return the player's PlayerGameBoard
     */
    public PlayerGameBoard getGameBoard();

    /**
     * Getter for player ID
     * @return the player's ID
     */
    public int getPlayerID();


}
