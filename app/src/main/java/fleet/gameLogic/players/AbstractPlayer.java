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
    protected Ship scoutTarget = null;
    protected Fleet playerFleet;
    protected PlayerGameBoard playerGameBoard;
    protected Game game;
    private int playerID;

    public AbstractPlayer (PlayerGameBoard board,int playerID){
        this.playerGameBoard = board;
        this.playerID = playerID;
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

    public void setDefender(Ship ship ) {
        attackTarget = ship;
    }

    public void setAttacker(Ship ship) {
        attackSelected = ship;
    }

    public void setScoutTarget(Ship ship) {
        scoutTarget = ship;
    }


}
