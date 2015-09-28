package fleet.classes.gameLogic;

import fleet.classes.players.Player;
import java.util.ArrayList;

/**
 * Created by Radu on 9/27/2015.
 */
public class Game {

    public void startGame(ArrayList<Player> players) {
        return;
    }

    protected boolean battle(Ship attacker, Ship defender) {
        attacker.isFaceUp = true;
        defender.isFaceUp = true;

        if (attacker.shipClass == defender.shipClass) {
            return false;
        }
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
