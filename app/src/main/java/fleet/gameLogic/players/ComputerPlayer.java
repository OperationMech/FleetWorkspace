package fleet.gameLogic.players;

import java.util.ArrayList;
import java.util.Random;

import fleet.gameLogic.Game;
import fleet.gameLogic.PlayerGameBoard;
import fleet.gameLogic.Ship;
import fleet.gameLogic.Fleet;
import fleet.gameLogic.ShipClass;

/**
 * Computer player
 * Created by Radu on 9/27/2015.
 */
public class ComputerPlayer extends AbstractPlayer {

    /**
     * Computer player constructor
     */
    public ComputerPlayer(PlayerGameBoard board, int playerID) {
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
     *
     * @param players arrayList of game players
     */
    @Override
    public void scout(ArrayList<AbstractPlayer> players) {
        PlayerGameBoard targetBoard = selectPlayer(players);
        boolean hasTarget = false;
        int localRandom = new Random().nextInt();
        int targetShip = localRandom % 9;
        int scoutAttempts = 0;
        //Finding a face down card to scout
        while (!hasTarget && scoutAttempts <= 9) {
            if (targetBoard.getShips().get(targetShip).getFaceUpStatus()) {
                targetBoard.revealShipAt(targetShip);
                hasTarget = true;
            } else {
                scoutAttempts++;
                if (localRandom == 8) {
                    localRandom = 0;
                } else {
                    localRandom++;
                }
            }
        }
    }

    /**
     * Attack selection
     * @param players in the game.
     * @return both attacker and target ships. Position 0 is the attacking ship, position 1 is the defending ship.
     * If no ships can be defeated, and there are no face down cards on the enemies board, both positions will be null.
     */
    @Override
    public Ship[] attack(ArrayList<AbstractPlayer> players) {
        PlayerGameBoard targetBoard = selectPlayer(players);
        Ship[] combatants = new Ship[2];
        ArrayList<Ship> targetShips = targetBoard.getFaceUpShips();
        ArrayList<Ship> myShips = playerGameBoard.getShips();
        if (!targetShips.isEmpty()) {
            //Looking for first situation where we can beat a face up ship
            for (Ship defendingShip : targetShips) {
                switch (defendingShip.shipClass) {
                    case DESTROYER:
                        for (Ship attackingShip : myShips) {
                            if (attackingShip.shipClass == ShipClass.CRUISER) {
                                combatants[0] = attackingShip;
                                combatants[1] = defendingShip;
                            }
                        }
                    case CRUISER:
                        for (Ship attackingShip : myShips) {
                            if (attackingShip.shipClass == ShipClass.BATTLESHIP) {
                                combatants[0] = attackingShip;
                                combatants[1] = defendingShip;
                            }
                        }
                    case BATTLESHIP:
                        for (Ship attackingShip : myShips) {
                            if (attackingShip.shipClass == ShipClass.DESTROYER) {
                                combatants[0] = attackingShip;
                                combatants[1] = defendingShip;
                            }
                        }
                    case CARRIER:
                        combatants[0] = myShips.get(0);
                        combatants[1] = defendingShip;
                }
            }
        } else {
            //Enemy has no face up ships
            int randomDefender = new Random().nextInt();
            int randomAttacker = new Random().nextInt();
            combatants[0] = playerGameBoard.getShips().get(randomAttacker % myShips.size());
            combatants[1] = playerGameBoard.getShips().get(randomDefender % targetBoard.getShips().size());
        }
        return combatants;
    }

    /**
     * "Ready" function
     *
     * @return for basic ai purposes always true
     */
    @Override
    public boolean ready() {
        return true;
    }

    /**
     * Selects a player to target
     * @param players in the game.
     */
    private PlayerGameBoard selectPlayer(ArrayList<AbstractPlayer> players) {
        int localRandom = new Random().nextInt();
        players.remove(this);
        int targetPlayer = localRandom % players.size();
        return players.get(targetPlayer).getGameBoard();
    }

}
