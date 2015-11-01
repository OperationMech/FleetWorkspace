package fleet.gameLogic;

import fleet.gameLogic.players.AbstractPlayer;
import java.util.ArrayList;

/**
 * Simple game class
 * Created by Radu on 9/27/2015.
 */
public class Game {
    protected ArrayList<AbstractPlayer> players;
    private boolean isWon = false;

    /**
     * Game constructor
     * @param players passed to the game
     */
    public void game(ArrayList<AbstractPlayer> players) {
        this.players = players;
    }

    /**
     *
     * @return Players currently in the game
     */
    public ArrayList<AbstractPlayer> getPlayers(){
        return this.players;
    }

    /**
     * Game loop start function
     */
    public void startGame() {
        while(!isWon) {
           isWon = gameLoop();
        }
    }

    /**
     * Game loop function
     * @return won status of the current game
     */
    public boolean gameLoop() {
        for (AbstractPlayer player : players) {
            boolean isTurn = true;
            while (isTurn) {
                Ship[] shipAndTarget = new Ship[2];
                shipAndTarget = player.attack();
                if (player.getGameBoard().allShipsSunk()) {
                    players.remove(player);
                }
                if (players.size() < 2) {
                    return true;
                }
                if (shipAndTarget[0] != null) {
                    battle(shipAndTarget[0], shipAndTarget[1]);
                    player.scout(players);
                    isTurn = false;
                } else {
                    isTurn = true;
                }
            }
        }
        return false;
    }

    /**
     * Battle resolve function
     * @param attacker ship selected by the attacker
     * @param defender target ship
     * @return boolean value for target
     */
    protected boolean battle(Ship attacker, Ship defender) {
        attacker.isFaceUp = true;
        defender.isFaceUp = true;

        // When the classes match draw
        if (attacker.shipClass == defender.shipClass) {
            return false;
        }
        // Rock - paper - scissor logic
        if (defender.shipClass == ShipClass.CARRIER) {
            defender.isSunk = true;
        } else {
            switch (attacker.shipClass) {
                case DESTROYER:
                    if (defender.shipClass == ShipClass.BATTLESHIP) {
                        defender.isSunk = true;
                    }

                case CRUISER:
                    if (defender.shipClass == ShipClass.DESTROYER) {
                        defender.isSunk = true;
                    }

                case BATTLESHIP:
                    if (defender.shipClass == ShipClass.CRUISER) {
                        defender.isSunk = true;
                    }
            }
        }
        return defender.isSunk;
    }
}
