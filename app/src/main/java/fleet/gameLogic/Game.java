package fleet.gameLogic;


import fleet.gameLogic.players.AbstractPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Simple game class
 * Created by Radu on 9/27/2015.
 */
public class Game {
    protected ArrayList<AbstractPlayer> players;
    private HashMap<AbstractPlayer, Integer> loss;
    private boolean isWon = false;
    private int runs = 1;

    /**
     * Game constructor
     *
     * @param players passed to the game
     */
    public Game(ArrayList<AbstractPlayer> players, int runs) {
        this.players = players;
        loss = new HashMap<AbstractPlayer, Integer>();
        for (AbstractPlayer player : players) {
            loss.put(player, 0);
        }
        this.runs = runs;
    }

    /**
     * @return Players currently in the game
     */
    public ArrayList<AbstractPlayer> getPlayers() {
        return this.players;
    }

    /**
     * Game loop start function
     */
    public String startGame() {
        String output = "";
        for (int games = 0; games < runs; games++) {
            Collections.shuffle(players);
            for (AbstractPlayer player : players) {
                player.getGameBoard().reset();
            }
            isWon = false;
            while (!isWon) {
                isWon = gameLoop();
            }
        }

        for (AbstractPlayer player : players) {
            output = output + " " + player.getClass().getName() + " won: " + (runs - loss.get(player)) + " times.\n:>";
        }

        double zScore = ((runs - loss.get(players.get(0))) - (runs * 0.5)) / Math.sqrt(runs * 0.25);

        if (zScore <= 1.95 && zScore >= -1.95) {
            output = output + " The Z score is statistically insignificant.\n:>";
        } else {
            output = output + " The Z score is statistically significant.\n:>";
        }

        for (AbstractPlayer player : players) {
            player.getGameBoard().reset();
        }

        return output + " Z score: " + zScore + "\n\n:>";
    }

    /**
     * Game loop function
     *
     * @return won status of the current game
     */
    public boolean gameLoop() {
        for (AbstractPlayer player : players) {
            boolean isTurn = true;
            while (isTurn) {
                Ship[] shipAndTarget;
                shipAndTarget = player.attack(players);
                if (shipAndTarget[1] == null) {
                    loss.put(player, loss.get(player) + 1);
                    return true;
                }
                if (shipAndTarget[0] != null && !shipAndTarget[0].shipClass.equals(ShipClass.CARRIER)) {
                    battle(shipAndTarget[0], shipAndTarget[1]);
                    player.scout(players);
                    isTurn = false;
                }
            }
        }
        return false;
    }

    /**
     * Battle resolve function
     *
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
