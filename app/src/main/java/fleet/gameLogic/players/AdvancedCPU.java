package fleet.gameLogic.players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import fleet.gameLogic.PlayerGameBoard;
import fleet.gameLogic.Ship;
import fleet.gameLogic.ShipClass;

/**
 * Created by Radu on 11/28/2015.
 */
public class AdvancedCPU extends ComputerPlayer {

    public AdvancedCPU(PlayerGameBoard board, int playerID) {
        super(board, playerID);
    }

    @Override
    /**
     * Attack selection
     * @param players in the game.
     * @return both attacker and target ships. Position 0 is the attacking ship, position 1 is the defending ship.
     * If no ships can be defeated, and there are no face down cards on the enemies board, both positions will be null.
     * The improvements to this method make the AI prioritize using its face up ships as well as be more likely to attack ships that counter its weakest class.
     * This method is a bit long, I've tried to include helpful inline comments, since breaking it down into smaller methods would be more effort, and require too many parameters to be constantly passed around.
     */
    public Ship[] attack(ArrayList<AbstractPlayer> players) {
        PlayerGameBoard targetBoard = super.selectPlayer(players);
        Ship[] combatants = new Ship[2];
        ArrayList<Ship> myShips = playerGameBoard.getShips();
        ArrayList<Ship> myPreferredShips = new ArrayList<Ship>();
        int battleshipCount = 0;
        int cruiserCount = 0;
        int destroyerCount = 0;
        int faceDownShips = 0;
        //Advanced AI wants to use ships it has face up instead of revealing more cards.
        //It also wants to know how many of each class it has remaining
        for (Ship ship : myShips) {
            if (ship.getFaceUpStatus()) {
                myPreferredShips.add(ship);
            } else {
                faceDownShips++;
            }
            switch (ship.shipClass) {
                case DESTROYER:
                    destroyerCount++;
                    break;
                case CRUISER:
                    cruiserCount++;
                    break;
                case BATTLESHIP:
                    battleshipCount++;
                    break;
            }
        }
        //If I have no face up cards and all my counts are equal we can save some time and just skip to regular logic
        if (myPreferredShips.isEmpty()
                && battleshipCount == cruiserCount
                && battleshipCount == destroyerCount
                && destroyerCount == cruiserCount) {
            return super.attack(players);
        } else {
            int minCount;
            ArrayList<Ship> targetShips = targetBoard.getFaceUpShips();
            for (Ship ship : targetShips) {
                if (!ship.getStatus()) {
                    targetShips.remove(ship);
                }
            }
            if (destroyerCount < cruiserCount) {
                //Min count is our most vulnerable class, so we will use this to focus its counters
                minCount = destroyerCount;
            } else {
                minCount = cruiserCount;
            }
            if (battleshipCount < minCount) {
                minCount = battleshipCount;
            }
            //Shuffling all ship lists so that actions can't be predicted based on grid positions
            Collections.shuffle(targetShips);
            Collections.shuffle(myShips);
            Collections.shuffle(myPreferredShips);
            if (faceDownShips >= 3) {
                //If I still have 3 face down ships and I see a face up carrier, we will sink that ship first, using a face up card if possible.
                for (Ship defendingShip : targetShips) {
                    if (defendingShip.shipClass == ShipClass.CARRIER) {
                        combatants[1] = defendingShip;
                        if (!myPreferredShips.isEmpty()) {
                            combatants[0] = myPreferredShips.get(0);
                        } else {
                            //We have no face up ships, so we want to use reveal one of our stronger suits to attack with
                            for (Ship attackingShip : myShips) {
                                if (minCount != battleshipCount && attackingShip.shipClass == ShipClass.BATTLESHIP) {
                                    combatants[0] = attackingShip;
                                    break;
                                } else if (minCount != cruiserCount && attackingShip.shipClass == ShipClass.CRUISER) {
                                    combatants[0] = attackingShip;
                                    break;
                                } else if (minCount != destroyerCount && attackingShip.shipClass == ShipClass.DESTROYER) {
                                    combatants[0] = attackingShip;
                                    break;
                                }
                            }
                        }
                        return combatants;
                    }
                }
                if (destroyerCount == minCount) {
                    //If our weak class is destroyers, the best move would be to use one of our face up battleships to sink an enemy cruiser.
                    for (Ship defendingShip : targetShips) {
                        if (defendingShip.shipClass == ShipClass.CRUISER) {
                            for (Ship attackingShip : myPreferredShips) {
                                switch (attackingShip.shipClass) {
                                    case BATTLESHIP:
                                        combatants[0] = attackingShip;
                                        combatants[1] = defendingShip;
                                        return combatants;
                                    default :
                                        break;
                                }
                            }
                        }
                    }
                }
                if (cruiserCount == minCount) {
                    //If our weak class is cruisers, the best move would be to use one of our face up destroyers to sink an enemy battleship.
                    for (Ship defendingShip : targetShips) {
                        if (defendingShip.shipClass == ShipClass.BATTLESHIP) {
                            for (Ship attackingShip : myPreferredShips) {
                                switch (attackingShip.shipClass) {
                                    case DESTROYER:
                                        combatants[0] = attackingShip;
                                        combatants[1] = defendingShip;
                                        return combatants;
                                    default :
                                        break;
                                }
                            }
                        }
                    }
                }
                if (battleshipCount == minCount) {
                    //If our weak class is battleships, the best move would be to use one of our face up cruisers to sink an enemy destroyer.
                    for (Ship defendingShip : targetShips) {
                        if (defendingShip.shipClass == ShipClass.DESTROYER) {
                            for (Ship attackingShip : myPreferredShips) {
                                switch (attackingShip.shipClass) {
                                    case CRUISER:
                                        combatants[0] = attackingShip;
                                        combatants[1] = defendingShip;
                                        return combatants;
                                    default :
                                        break;
                                }
                            }
                        }
                    }
                }
                if (!myPreferredShips.isEmpty()) {
                    //Next, if we haven't found an ideal move yet we will prioritize using face up cards, instead of revealing more.
                    if (!targetShips.isEmpty()) {
                        for (Ship defendingShip : targetShips) {
                            switch (defendingShip.shipClass) {
                                case DESTROYER:
                                    for (Ship attackingShip : myPreferredShips) {
                                        if (attackingShip.shipClass == ShipClass.CRUISER) {
                                            combatants[0] = attackingShip;
                                            combatants[1] = defendingShip;
                                            return combatants;
                                        }
                                    }
                                    break;
                                case CRUISER:
                                    for (Ship attackingShip : myPreferredShips) {
                                        if (attackingShip.shipClass == ShipClass.BATTLESHIP) {
                                            combatants[0] = attackingShip;
                                            combatants[1] = defendingShip;
                                            return combatants;
                                        }
                                    }
                                    break;
                                case BATTLESHIP:
                                    for (Ship attackingShip : myPreferredShips) {
                                        if (attackingShip.shipClass == ShipClass.DESTROYER) {
                                            combatants[0] = attackingShip;
                                            combatants[1] = defendingShip;
                                            return combatants;
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        }
        //If somehow we still haven't returned anything yet, defaulting to the basic method.
        return super.attack(players);
    }
}