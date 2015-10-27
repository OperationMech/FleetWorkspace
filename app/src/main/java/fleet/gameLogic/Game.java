package fleet.gameLogic;

import fleet.gameLogic.players.Player;
import java.util.ArrayList;

/**
 * Created by Radu on 9/27/2015.
 */
public class Game {
    protected ArrayList players;

    public void game(ArrayList<Player> players) {
        this.players = players;
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
