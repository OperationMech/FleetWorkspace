package fleet.classes.players;
import fleet.classes.gameLogic.Fleet;
import fleet.classes.gameLogic.Ship;

/**
 * Created by Radu on 9/27/2015.
 */
public interface Player {
    public void selectFleet();
    public void scout();
    public Ship[] attack();
    public boolean arrangeFleet();
    public Fleet getFleet();

}
