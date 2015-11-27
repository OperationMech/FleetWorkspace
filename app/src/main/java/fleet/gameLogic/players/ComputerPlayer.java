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
        int localRandom = Math.abs(new Random().nextInt());
        int targetShip = localRandom % 9;
        int scoutAttempts = 0;
        //Finding a face down card to scout
        while (!hasTarget && scoutAttempts <= 9) {
            if (!targetBoard.fleetPositions[targetShip].getFaceUpStatus()) {
                scoutTarget = targetBoard.fleetPositions[targetShip];
                hasTarget = true;
            } else {
                scoutAttempts++;
                if (targetShip == 8) {
                    targetShip = 0;
                } else {
                    targetShip++;
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
        for(Ship ship : targetShips) {
            if(!ship.getStatus()) {
                targetShips.remove(ship);
            }
        }
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
                        break;
                    case CRUISER:
                        for (Ship attackingShip : myShips) {
                            if (attackingShip.shipClass == ShipClass.BATTLESHIP) {
                                combatants[0] = attackingShip;
                                combatants[1] = defendingShip;
                            }
                        }
                        break;
                    case BATTLESHIP:
                        for (Ship attackingShip : myShips) {
                            if (attackingShip.shipClass == ShipClass.DESTROYER) {
                                combatants[0] = attackingShip;
                                combatants[1] = defendingShip;
                            }
                        }
                        break;
                    case CARRIER:
                        combatants[0] = myShips.get(0);
                        combatants[1] = defendingShip;
                        break;
                    default:
                        combatants[0] = myShips.get(0);
                        combatants[1] = null;

                }
            }
        } else {
            //Enemy has no face up ships
            int randomDefender = Math.abs(new Random().nextInt());
            int randomAttacker = Math.abs(new Random().nextInt());
            combatants[0] = playerGameBoard.getShips().get(randomAttacker % myShips.size());
            combatants[1] = targetBoard.getShips().get(randomDefender % targetBoard.getShips().size());
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
    protected PlayerGameBoard selectPlayer(ArrayList<AbstractPlayer> players) {
        int localRandom = Math.abs(new Random().nextInt());
        ArrayList<AbstractPlayer> localPlayers = (ArrayList<AbstractPlayer>)players.clone();
        localPlayers.remove(this);
        int targetPlayer = localRandom % localPlayers.size();
        return localPlayers.get(targetPlayer).getGameBoard();
    }

}
