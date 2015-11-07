package fleet.gameLogic.players;

import java.util.ArrayList;

import fleet.gameLogic.Fleet;
import fleet.gameLogic.Game;
import fleet.gameLogic.PlayerGameBoard;

/**
 * Abstract Player subclass
 * Created by Radu on 9/30/2015.
 */
public abstract class AbstractPlayer implements Player {
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

}
