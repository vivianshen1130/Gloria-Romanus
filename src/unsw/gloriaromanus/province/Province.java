package unsw.gloriaromanus.province;

import java.util.ArrayList;
import java.util.List;

import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.unit.infantry.Druid;

import org.json.JSONObject;

public class Province {
    Faction faction = null;
    String name = null;
    boolean landlocked = false;
    int numTraining = 0;

    double wealth = 0;
    String taxRate = "low";
    double treasury = 0;

    List<Unit> units = new ArrayList<Unit>();
    List<Unit> training = new ArrayList<Unit>();
    List<Province> adjacent = new ArrayList<Province>();
    List<Province> adjacentFriendly = new ArrayList<Province>();

    /**
     * Constructs a Province for our game
     * 
     * @param faction    faction object
     * @param name       province name
     * @param landlocked landlocked value
     */
    public Province(Faction faction, String name, boolean landlocked) {
        this.faction = faction;
        this.name = name;
        this.landlocked = landlocked;
        faction.addProvince(this);
    }

    /**
     * Getter method for the faction the province belongs to
     * 
     * @return faction
     */
    public Faction getFaction() {
        return faction;
    }

    /**
     * Setter method for the faction the province belongs to
     * 
     * @param faction faction object
     */
    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    /**
     * Getter method for province name
     * 
     * @return province name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for whether or not the province is landlocked
     * 
     * @return landlocked
     */
    public boolean getLandlocked() {
        return landlocked;
    }

    /**
     * Getter method for province wealth
     * 
     * @return wealth
     */
    public double getWealth() {
        return wealth;
    }

    /**
     * Setter method for province wealth
     */
    public void setWealth(double wealth) {
        this.wealth = wealth;
    }

    /**
     * Getter method for province tax rate
     * 
     * @return taxRate
     */
    public String getTaxRate() {
        return taxRate;
    }

    /**
     * Setter method for province tax rate
     * 
     * @param taxRate province tax rate
     */
    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * Getter method for province treasury balance
     */
    public double getTreasury() {
        return treasury;
    }

    /**
     * Setter method for province treasury balance
     * 
     * @param treasury province treasury balance
     */
    public void setTreasury(double treasury) {
        this.treasury = treasury;
    }

    /**
     * Getter method for province list of units
     * 
     * @return province list of units
     */
    public List<Unit> getUnits() {
        return units;
    }

    /**
     * Setter method for province list of units
     * 
     * @param units list of units
     */
    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    /**
     * Add unit to province list of units. Assign the unit to belong to this
     * province and it's associated faction
     * 
     * @param unit unit to add
     */
    public void addUnit(Unit unit) {
        units.add(unit);
        unit.setFaction(faction);
        unit.setProvince(this);

    }

    /**
     * Remove unit from the province list of units. Remove the unit's association to
     * the province and the faction it belongs to
     * 
     * @param unit unit to remove
     */
    public void removeUnit(Unit unit) {
        units.remove(unit);
        unit.setFaction(null);
        unit.setProvince(null);
    }

    /**
     * Getter method for province list of training units
     * 
     * @return province list of training units
     */
    public List<Unit> getTrainingUnits() {
        return training;
    }

    /**
     * Add unit to province list of training units
     * 
     * @param unit unit to add
     */
    public void addTrainingUnit(Unit unit) {
        training.add(unit);
    }

    /**
     * Remove unit from the province list of training units
     * 
     * @param unit unit to remove
     */
    public void removeTrainingUnit(Unit unit) {
        training.remove(unit);
    }

    /**
     * Getter method for province list of adjacent provinces
     * 
     * @return list of adjacent provinces
     */
    public List<Province> getAdjacent() {
        return adjacent;
    }

    /**
     * Add province to the province list of adjacent provinces if it hasn't already
     * been added
     * 
     * @param province province to add
     */
    public void addAdjacent(Province province) {
        if (!adjacent.contains(province)) {
            adjacent.add(province);
            addAdjacentFriendly(province);
        }
    }

    /**
     * Getter method for province list of adjacent provinces
     * 
     * @return list of adjacent provinces
     */
    public List<Province> getAdjacentFriendly() {
        return adjacentFriendly;
    }

    /**
     * Add province to the province list of adjacent provinces if it hasn't already
     * been added
     * 
     * @param province province to add
     */
    public void addAdjacentFriendly(Province province) {
        if (province.getFaction() == faction && !adjacentFriendly.contains(province)) {
            adjacentFriendly.add(province);
        }
    }

    /**
     * Apply the tax rate and calculate the province wealth and treasury (every
     * round)
     */
    public void calculateWealth() {
        double wealth = getWealth();
        switch (taxRate) {
            case "low":
                setWealth(getWealth() + 10);
                setTreasury(wealth * 0.1);
                break;
            case "normal":
                setTreasury(wealth * 0.15);
                break;
            case "high":
                setWealth(getWealth() - 10);
                setTreasury(wealth * 0.2);
                break;
            case "very high":
                setWealth(getWealth() - 30);
                setTreasury(wealth * 0.25);
                for (Unit unit : units) {
                    unit.setMorale(unit.getMorale() - 1);
                }
                break;
        }
    }

    /**
     * Checks if the chosen unit can move to the chosen province
     * 
     * @param province province to move to
     * @param unit     unit to move with
     * @return -1 if the province cannot be reached, otherwise the number of
     *         provinces it had to move through to reach it
     */
    public int canMoveTroops(Province province, Unit unit, boolean invade) {

        // If unit is not available then we cannot move the unit
        if (!unit.isAvailable()) {
            return -1;
        }

        List<Province> traverse = new ArrayList<Province>();

        // Use full adjacency list if we are invading. Else if we are moving, we only
        // use the faction specific subset of the adjacency list
        if (invade) {
            traverse.addAll(adjacent);
        } else {
            traverse.addAll(adjacentFriendly);
        }

        List<Province> canTravelTo = new ArrayList<Province>();
        List<Province> search = new ArrayList<Province>();
        List<Province> nextSearch = new ArrayList<Province>();

        // Travelling to each province takes up 4 movement points
        int movementPoints = unit.getCurrentMovementPoints() / 4;

        // BFS traversal
        for (int i = 0; movementPoints != 0; movementPoints--) {
            if (canTravelTo.isEmpty()) {
                canTravelTo.addAll(traverse);
                canTravelTo.add(this);
                nextSearch.addAll(traverse);
                // Checks if the province is in the canTravelTo list
                if (canTravelTo.contains(province)) {
                    System.out.println(
                            "immediate adjacent " + province.getName() + ", " + unit.getMovementPoints() + ", " + i);
                    return (i + 1);
                }
                continue;
            }

            search = nextSearch;
            nextSearch = new ArrayList<Province>();
            for (Province provinces : search) {
                if (invade) {
                    canTravelTo.addAll(provinces.getAdjacent());
                    nextSearch.addAll(provinces.getAdjacent());
                } else {
                    canTravelTo.addAll(provinces.getAdjacentFriendly());
                    nextSearch.addAll(provinces.getAdjacentFriendly());
                }
            }

            // Checks if the province is in the canTravelTo list
            if (canTravelTo.contains(province)) {
                System.out.println(province.getName() + ", " + unit.getMovementPoints() + ", " + (i + 1));
                return (i + 1);
            }

            i++;
        }

        // // Checks if the province is in the canTravelTo list
        // if (canTravelTo.contains(province)) {
        // return true;
        // }

        return -1;
    }

    public Unit getUnitFromIndex(int index) {
        return this.getAvailableUnits().get(index);
    }

    /**
     * Getter method to return the number of Druit units in the province list of
     * units (used in battle resolver)
     * 
     * @return number of Druit units in the province list of units
     */
    public int getDruitUnits() {
        int count = 0;
        for (Unit unit : units) {
            if (unit instanceof Druid && unit.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Getter for the total number of troops in the province
     * 
     * @return the number of troops in a province
     */
    public int getNumTroops() {
        int total = 0;
        for (Unit unit : units) {
            if (unit.isAvailable()) {
                total = total + unit.getNumTroops();
            }
        }
        return total;
    }

    /**
     * Getter for the total number of troops in the province
     * 
     * @return the number of available units in a province
     */
    public List<Unit> getAvailableUnits() {
        List<Unit> Aunits = new ArrayList<Unit>();
        for (Unit unit : units) {
            if (unit.isAvailable()) {
                Aunits.add(unit);
            }
        }
        return Aunits;
    }

    /**
     * Store province details into a JSONObject
     * 
     * @return JSONObject of province details
     */
    public JSONObject saveGame() {
        JSONObject provinceDetails = new JSONObject();
        provinceDetails.put("type", "province");
        provinceDetails.put("name", name);
        provinceDetails.put("faction", faction.getName());
        provinceDetails.put("landlocked", landlocked);
        provinceDetails.put("wealth", wealth);
        provinceDetails.put("taxRate", taxRate);
        provinceDetails.put("treasury", treasury);
        return provinceDetails;
    }

    /**
     * Encapsulates province details into a string
     */
    // @Override
    // public String toString() {
    // return getClass().toString() + ", name = " + name + ", faction = " + faction
    // + ", landlocked = " + landlocked
    // + ", wealth = " + wealth + ", taxRate = " + taxRate + ", treasury = " +
    // treasury + ", units = " + units;
    // }
    @Override
    public String toString() {
        return name + ", faction = " + faction.getName() + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Province t = (Province) obj;
        return (name.equals(t.name) && wealth == t.wealth && treasury == t.treasury && taxRate.equals(t.taxRate)
                && units.equals(t.units) && adjacent.equals(t.adjacent));
    }

    public int getNumTraining() {
        return numTraining;
    }

    public void setNumTraining(int numTraining) {
        this.numTraining = numTraining;
    }
}
