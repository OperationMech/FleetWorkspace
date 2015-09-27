package fleet.classes.gameLogic;

/**
 * Created by Radu on 9/27/2015.
 */
public enum ShipClass {
    CARRIER ("Ca", "CV"),
    BATTLESHIP("Battleship", "BB"),
    CRUISER ("Cruiser", "CA"),
    DESTROYER("Destroyer", "DD");

    private final String name;
    private final String abbr;

    private ShipClass(String name, String abbr){
        this.name = name;
        this.abbr =abbr;
    }

    public String getName() {
        return name;
    }

    public String getAbbr() {
        return abbr;
    }
}
