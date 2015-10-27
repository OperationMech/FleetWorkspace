package fleet.gameLogic.players;
import fleet.gameLogic.Fleet;
import fleet.gameLogic.Ship;

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
