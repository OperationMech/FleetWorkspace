package fleet.gameLogic;

import fleet.gameLogic.players.Player;
import java.util.ArrayList;

/**
 * Simple game class
 * Created by Radu on 9/27/2015.
 */
public class Game {
    protected ArrayList players;

    /**
     * Game constructor
     * @param players passed to the game
     */
    public void game(ArrayList<Player> players) {
        this.players = players;
        return;
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
