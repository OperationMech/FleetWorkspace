package fleet.gameLogic.players;

import java.util.ArrayList;

import fleet.gameLogic.Fleet;
import fleet.gameLogic.Game;
import fleet.gameLogic.PlayerGameBoard;
import fleet.gameLogic.Ship;

/**
 * Human player
 * Created by Radu on 9/27/2015.
 */
public class HumanPlayer extends AbstractPlayer{

    /**
     * HumanPlayer constructor
     */
    public HumanPlayer(PlayerGameBoard board, int playerID) {
        super(board, playerID);
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
            players.remove(this);
            int targetPlayer = 0;
            PlayerGameBoard targetBoard = players.get(targetPlayer).getGameBoard();
            int targetShip = 0;
            targetBoard.revealShipAt(targetShip);
        }
    }

    /**
     * Attack selection
     * @return both attacker and target ships
     */
    @Override
    public Ship[] attack() {
        Ship[] ships = {null, null};
        return ships;
    }

    /**
     * "Ready" function
     * @return if the player has their fleet arranged for a game
     */
    @Override
    public boolean arrangeFleet() {
        return false;
    }

    /**
     * Getter for player fleet
     * @return player's fleet
     */
    @Override
    public Fleet getFleet() {
        return this.playerFleet;
    }
}
