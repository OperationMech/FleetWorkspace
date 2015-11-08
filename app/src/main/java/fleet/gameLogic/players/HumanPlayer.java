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

    @Override
    public void setDefender(Ship ship ) {
        attackTarget = ship;
    }

    @Override
    public void setAttacker(Ship ship) {
        attackSelected = ship;
    }

    @Override
    public void setScoutTarget(Ship ship) {
        scoutTarget = ship;
    }

    /**
     * Scout selection
     * @param players arrayList of game players
     */
    @Override
    public void scout(ArrayList<AbstractPlayer> players) {
        scoutTarget.reveal();
    }

    /**
     * Attack selection
     * @return both attacker and target ships
     */
    @Override
    public Ship[] attack() {
        Ship[] ships = {attackSelected, attackTarget};
        return ships;
    }

    /**
     * "Ready" function
     * @return if the player has their fleet arranged for a game
     */
    @Override
    public boolean ready() {
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
