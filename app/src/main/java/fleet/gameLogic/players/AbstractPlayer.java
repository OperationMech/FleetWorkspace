package fleet.gameLogic.players;

import java.util.ArrayList;

import fleet.gameLogic.Fleet;
import fleet.gameLogic.Game;
import fleet.gameLogic.PlayerGameBoard;
import fleet.gameLogic.Ship;

/**
 * Abstract Player subclass
 * Created by Radu on 9/30/2015.
 */
public abstract class AbstractPlayer implements Player {
    protected Ship attackTarget = null;
    protected Ship attackSelected = null;
    protected Boolean attacked = false;
    protected Ship scoutTarget = null;
    protected Fleet playerFleet;
    protected PlayerGameBoard playerGameBoard;
    protected Game game;
    private int playerID;

    /**
     * Basic  player Constructor
     * @param  board The board the the player will be using
     * @param playerID The ID of this player
     */
    public AbstractPlayer (PlayerGameBoard board,int playerID){
        this.playerGameBoard = board;
        this.playerID = playerID;
        playerGameBoard.reset();
    }

    /**
     * Getter for player fleet
     * @return the player's fleet
     */
    @Override
    public Fleet getFleet() {
        return playerFleet;
    }

    /**
     * Getter for attacked boolean
     * @return true if the player has attached this turn, false otherwise
     */
    public Boolean getAttacked() { return attacked; }

    /**
     * Getter for which ship the player wants to scout
     * @return A ship that a player wants revealed
     */
    public void setAttacked(Boolean attacked) { this.attacked = attacked; }

    /**
     * Getter for which ship the player wants to scout
     * @return A ship that a player wants revealed
     */
    public Ship getScoutTarget() { return scoutTarget; }

    /**
     * Getter for player PlayerGameBoard
     * @return the player's PlayerGameBoard
     */
    public PlayerGameBoard getGameBoard() {
        return playerGameBoard;
    }

    /**
     * Getter for player playerID
     * @return the player's playerID
     */
    public int getPlayerID(){
        return playerID;
    }

    /**
     * Ready function
     * @return ready status
     */
    @Override
    public boolean ready() {
        return true;
    }

    /**
     * Sets what ship will be receiving the player's attack.
     */
    public void setDefender(Ship ship ) {
        attackTarget = ship;
    }

    /**
     * Sets what ship is attacking.
     */
    public void setAttacker(Ship ship) {
        attackSelected = ship;
    }

    /**
     * Sets what ship will be scouted by the player.
     */
    public void setScoutTarget(Ship ship) {
        scoutTarget = ship;
    }


}
