package unsw.gloriaromanus.faction;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import unsw.gloriaromanus.victory.Subject;
import unsw.gloriaromanus.victory.Observer;
import unsw.gloriaromanus.province.Province;
import unsw.gloriaromanus.unit.Unit;

public class Faction implements Subject {
    private String name = null;
    private List<Observer> listObservers = new ArrayList<Observer>();

    private double wealth;
    private double treasury;
    private boolean allConquered = false;
    private boolean bankrupt = false;

    private List<Province> provincesOwned = new ArrayList<Province>();

    /**
     * Constructs a Faction for our game
     * 
     * @param name faction name
     */
    public Faction(String name) {
        this.name = name;
        this.wealth = 5000;
        this.treasury = 1000;
    }

    /**
     * Getter method for faction name
     * 
     * @return faction name
     */
    public String getName() {
        return name;
    }

    /**
     * Adding an observer to our faction
     */
    @Override
    public void attach(Observer o) {
        if (!listObservers.contains(o)) {
            listObservers.add(o);
        }
    }

    /**
     * Removing an observer from our faction
     */
    @Override
    public void detach(Observer o) {
        listObservers.remove(o);
    }

    /**
     * Updates observer on changes within our faction
     */
    @Override
    public void notifyObservers() {
        for (Observer obs : listObservers) {
            obs.update(this);
        }
    }

    /**
     * Getter method for faction wealth
     * 
     * @return faction wealth
     */
    public double getWealth() {
        return wealth;
    }

    /**
     * Setter method for faction wealth
     * 
     * @param wealth faction wealth
     */
    public void setWealth(double wealth) {
        this.wealth = wealth;
        if (wealth < 0) {
            bankrupt = true;
        } else {
            bankrupt = false;
        }
        notifyObservers();
    }

    /**
     * Getter method for faction treasury
     * 
     * @return faction treasury
     */
    public double getTreasury() {
        return treasury;
    }

    /**
     * Setter method for faction treasury
     * 
     * @param treasury faction treasury
     */
    public void setTreasury(double treasury) {
        this.treasury = treasury;
        notifyObservers();
    }

    /**
     * Getter method for faction conquest goal
     * 
     * @return obtained conquest goal
     */
    public boolean isAllConquered() {
        return allConquered;
    }

    /**
     * Setter method for faction conquest goal
     * 
     * @param allConquered list of all provinces that belongs to the faction
     */
    public void setAllConquered(boolean allConquered) {
        this.allConquered = allConquered;
        notifyObservers();
    }

    /**
     * Getter method for faction list of all provinces owned
     * 
     * @return provincesOwned
     */
    public List<Province> getProvincesOwned() {
        return provincesOwned;
    }

    /**
     * Setter method for faction list of all provinces owned
     * 
     * @param provinces list of provinces that the faction will own
     */
    public void setProvincesOwned(List<Province> provinces) {
        provincesOwned = provinces;
    }

    /**
     * Add province to faction list of provincesOwned. Changes province association
     * to this faction
     * 
     * @param province province to add
     */
    public void addProvince(Province province) {
        if (!provincesOwned.contains(province)) {
            province.getFaction().removeProvince(province);
            province.setFaction(this);
            provincesOwned.add(province);
        }
        notifyObservers();
    }

    /**
     * Removes province from faction list of provincesOwned
     * 
     * @param province province to remove
     */
    public void removeProvince(Province province) {
        provincesOwned.remove(province);
        notifyObservers();
    }

    /**
     * Calculates and sets faction wealth (used for every turn)
     */
    public void wealthCalculation() {
        for (Province province : provincesOwned) {
            wealth += province.getWealth();
        }
        setWealth(wealth);
    }

    /**
     * Calculates and set faction treasury (used for every turn)
     */
    public void treasuryCalculation() {
        for (Province province : provincesOwned) {
            province.calculateWealth();
            treasury += province.getTreasury();
        }
        setTreasury(treasury);
    }

    /**
     * Calculates and set faction allConquered boolean (used for every turn)
     * 
     * @param allProvinces list of all exisiting provinces
     */
    public void conquestCalculation(List<Province> allProvinces) {
        if (allProvinces.equals(provincesOwned)) {
            setAllConquered(true);
        }
    }

    /**
     * Getter method for faction list of observers
     * 
     * @return faction listObservers
     */
    public List<Observer> getListObservers() {
        return listObservers;
    }

    /**
     * Store faction details into a JSONObject
     * 
     * @return JSONObject of faction details
     */
    public JSONObject saveGame() {
        JSONObject factionDetails = new JSONObject();
        factionDetails.put("type", "faction");
        factionDetails.put("name", getName());
        factionDetails.put("wealth", getWealth());
        factionDetails.put("treasury", getTreasury());
        factionDetails.put("allConquered", allConquered);
        return factionDetails;
    }

    /**
     * Getter method for all available units the faction owns
     * 
     * @return List of all units the faction owns (not including units in training)
     */
    public List<Unit> getAllAvailableUnits() {
        List<Unit> units = new ArrayList<Unit>();
        for (Province province : provincesOwned) {
            units.addAll(province.getAvailableUnits());
        }
        return units;
    }

    /**
     * Apply the bankruptcy penalty of reducing unit morale and disbanding units for
     * each round the faction is bankrupt
     */
    public void applyBankruptcy() {
        // Penalty won't apply if the player is not bankrupt
        if (!bankrupt || wealth > 0) {
            return;
        }

        List<Unit> units = getAllAvailableUnits();
        // Reduce morale of each unit by 1
        for (Unit unit : units) {
            unit.setMorale(unit.getMorale() - 1);
        }

        // Disband up to 10% of player's units
        int toRemove = (int) (units.size() * 0.1);
        int i = 0;
        while (i != toRemove) {
            for (Unit unit : units) {
                Province province = unit.getProvince();
                province.removeUnit(unit);
                i++;
            }
        }
    }

    /**
     * Apply upkeep costs for every available unit (unit not in training)
     * 
     * @return total upkeep costs for the faction this round
     */
    public int applyUpKeepCosts() {
        List<Unit> units = getAllAvailableUnits();
        int totalCosts = 0;
        for (Unit unit : units) {
            totalCosts += unit.getUpkeepCost();
        }

        setWealth(wealth - totalCosts);
        return totalCosts;
    }

    /**
     * Encapsulates faction details into a string
     */
    @Override
    public String toString() {
        List<String> ProvinceId = new ArrayList<String>();
        for (Province province : provincesOwned) {
            ProvinceId.add(province.getName());
        }

        return getClass().toString() + ", name = " + name + ", provinces = " + ProvinceId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Faction t = (Faction) obj;
        return (name.equals(t.name) && wealth == t.wealth && treasury == t.treasury && allConquered == t.allConquered
                && listObservers.equals(t.listObservers) && provincesOwned.equals(t.provincesOwned));
    }
}
