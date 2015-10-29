package fleet.gameLogic;

/**
 * Ship capability backend class
 * Created by Radu on 9/27/2015.
 */
public enum ShipClass {
    CARRIER ("Carrier", "CV"),
    BATTLESHIP("Battleship", "BB"),
    CRUISER ("Cruiser", "CA"),
    DESTROYER("Destroyer", "DD");

    private final String name;
    private final String abbr;

    /**
     * ShipClass constructor
     * @param name ship class name
     * @param abbr ship class designation
     */
    private ShipClass(String name, String abbr){
        this.name = name;
        this.abbr = abbr;
    }

    /**
     * Getter for name of the designation
     * @return the name of the designation
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the simplified designation
     * @return the simplified designation
     */
    public String getAbbr() {
        return abbr;
    }
}
