package fleet.classes.players;

/**
 * Created by Radu on 9/27/2015.
 */
interface Player {
    public void startTurn();
    public boolean scout();
    public boolean attack();
    public boolean arrangeFleet();
    public void endTurn();

}
