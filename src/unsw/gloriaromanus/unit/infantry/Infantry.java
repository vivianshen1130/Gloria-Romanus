package unsw.gloriaromanus.unit.infantry;

import org.json.JSONObject;

import unsw.gloriaromanus.unit.*;
import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public class Infantry implements Unit {
    private Faction faction;
    private Province province;

    private int cost;
    private int upkeepCost;
    private int turnsToRecruit; // the number of turns it takes to recruit this kind of unit
    private int recruitmentStart; // the turn in which the recruitment of this unit started
    private int movementPoints; // the number of movement points this unit has
    private int currentMovementPoints; //the number of movement points the unit has remaining in the turn
    private boolean ranged; // whether or not this unit is a ranged unit
    private boolean available;

    private int numTroops; // the number of troops in this unit (should reduce based on depletion)
    private double armour; // armour defense
    private double morale; // resistance to fleeing
    private double speed; // ability to disengage from disadvantageous battle
    private double meleeAttackDamage; // amount of damage the unit can inflict in melee
    private double rangedAttackDamage; // amount of damage the unit can inflict in ranged attack - 0 if not ranged unit
    private double defenseSkill; // skill to defend in battle. Does not protect from arrows!
    private double shieldDefense; // a shield

    /**
     * construct the infantry class
     * 
     * @param recruitmentStart the round in which the unit is recruited
     */
    public Infantry(int recruitmentStart) {
        this.cost = 10;
        this.upkeepCost = 5;
        this.turnsToRecruit = 1;
        this.recruitmentStart = recruitmentStart;
        this.movementPoints = 10;
        this.currentMovementPoints = 10;
        this.ranged = false;
        this.available = false;

        this.numTroops = 50;
        this.armour = 5;
        this.morale = 10;
        this.speed = 10;
        this.meleeAttackDamage = 6;
        this.rangedAttackDamage = 0;
        this.defenseSkill = 10;
        this.shieldDefense = 3;
    }

    /**
     * getter for the faction the unit belongs to
     * 
     * @return the faction the unit belongs to
     */
    public Faction getFaction() {
        return faction;
    }

    /**
     * setter for the faction the unit belongs to
     * 
     * @param faction the faction the unit belongs to
     */
    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    /**
     * getter for the province the unit is in
     * 
     * @return the province the unit is in
     */
    public Province getProvince() {
        return province;
    }

    /**
     * setter for the province the unit is in
     * 
     * @param province the province the unit is in
     */
    public void setProvince(Province province) {
        this.province = province;
    }

    /**
     * getter for the cost of recruiting the unit
     * 
     * @return the cost of recruiting the unit
     */
    public int getCost() {
        return cost;
    }

    /**
     * setter for the cost of recruiting the unit
     * 
     * @param cost the cost of recruiting the unit
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * getter for the upkeep cost of recruiting the unit
     * 
     * @return the upkeep cost of recruiting the unit
     */
    public int getUpkeepCost() {
        return upkeepCost;
    }

    /**
     * getter for the number of turns it takes to recruit this unit
     * 
     * @return the number of turns to recruit
     */
    public int getTurnsToRecruit() {
        return turnsToRecruit;
    }

    /**
     * setter for the number of turns it takes to recruit this unit
     * 
     * @param turnsToRecruit the number of turns to recruit
     */
    public void setTurnsToRecruit(int turnsToRecruit) {
        this.turnsToRecruit = turnsToRecruit;
    }

    /**
     * getter for the round this unit was recruited in
     * 
     * @return the round the unit was recruited in
     */
    public int getRecruitmentStart() {
        return recruitmentStart;
    }

    /**
     * setter for the round this unit was recruited in
     * 
     * @param recruitmentStart the round the unit was recruited in
     */
    public void setRecruitmentStart(int recruitmentStart) {
        this.recruitmentStart = recruitmentStart;
    }

    /**
     * getter for the movement points this unit has
     * 
     * @return the movement points of the unit
     */
    public int getMovementPoints() {
        return movementPoints;
    }

    /**
     * setter for the movement points this unit has
     * 
     * @param movementPoints the movement points of the unit
     */
    public void setMovementPoints(int movementPoints) {
        this.movementPoints = movementPoints;
    }

    /**
     * getter for the movement points the unit has left for the turn
     * 
     * @return the remaning movement points of the unit
     */
    public int getCurrentMovementPoints() {
        return currentMovementPoints;
    }

    /**
     * setter for the movement points the unit has left for the turn
     * 
     * @param currentMovementPoints the remaining movement points of the unit
     */
    public void setCurrentMovementPoints(int currentMovementPoints) {
        this.currentMovementPoints = currentMovementPoints;
    }

    /**
     * getter for whether the unit is ranged
     * 
     * @return true if ranged
     */
    public boolean isRanged() {
        return ranged;
    }

    /**
     * setter for whether the unit is ranged
     * 
     * @param ranged true if ranged
     */
    public void setRanged(boolean ranged) {
        this.ranged = ranged;
    }

    /**
     * getter for whether the unit is available
     * 
     * @return true if available
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * setter for whether the unit is available
     * 
     * @param available true if available
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * getter for the number of troops the unit has
     * 
     * @return the number of troops
     */
    public int getNumTroops() {
        return numTroops;
    }

    /**
     * setter for the number of troops the unit has
     * 
     * @param numTroops the number of troops
     */
    public void setNumTroops(int numTroops) {
        this.numTroops = numTroops;
    }

    /**
     * getter for the armour the unit has
     * 
     * @return the armour the unit has
     */
    public double getArmour() {
        return armour;
    }

    /**
     * setter for the armour the unit has
     * 
     * @param armour the armour the unit has
     */
    public void setArmour(double armour) {
        this.armour = armour;
    }

    /**
     * getter for the morale of the unit
     * 
     * @return the morale of the unit
     */
    public double getMorale() {
        return morale;
    }

    /**
     * setter for the morale of the unit
     * 
     * @param morale the morale of the unit
     */
    public void setMorale(double morale) {
        this.morale = morale;
    }

    /**
     * getter for the speed of the unit
     * 
     * @return the speed of the unit
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * setter for the speed of the unit
     * 
     * @param speed the speed of the unit
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * getter for the melee attack damage of the unit
     * 
     * @return the melee attack damage of the unit
     */
    public double getMeleeAttackDamage() {
        return meleeAttackDamage;
    }

    /**
     * setter for the melee attack damage of the unit
     * 
     * @param meleeAttackDamage the melee attack damage of the unit
     */
    public void setMeleeAttackDamage(double meleeAttackDamage) {
        this.meleeAttackDamage = meleeAttackDamage;
    }

    /**
     * getter for charge statistic of the unit
     * 
     * @return charge statistic of the unit
     */
    public double getChargeStat() {
        return 0;
    }

    /**
     * setter for charge statistic of the unit
     * 
     * @param chargeStat charge statisic of the unit
     */
    public void setChargeStat(double chargeStat) {
    }

    /**
     * getter for the ranged attack damage of the unit
     * 
     * @return the ranged attack damage of the unit
     */
    public double getRangedAttackDamage() {
        return rangedAttackDamage;
    }

    /**
     * setter for the ranged attack damage of the unit
     * 
     * @param rangedAttackDamage the ranged attack damage of the unit
     */
    public void setRangedAttackDamage(double rangedAttackDamage) {
        this.rangedAttackDamage = rangedAttackDamage;
    }

    /**
     * getter for the defense skill of the unit
     * 
     * @return the defese skill of the unit
     */
    public double getDefenseSkill() {
        return defenseSkill;
    }

    /**
     * setter for the defense skill of the unit
     * 
     * @param defenseSkill the defense skill of the unit
     */
    public void setDefenseSkill(double defenseSkill) {
        this.defenseSkill = defenseSkill;
    }

    /**
     * getter for the shield defense of the unit
     * 
     * @return the shield defense of the unit
     */
    public double getShieldDefense() {
        return shieldDefense;
    }

    /**
     * setter for the shield defense of the unit
     * 
     * @param shieldDefense the shield defense of the unit
     */
    public void setShieldDefense(double shieldDefense) {
        this.shieldDefense = shieldDefense;
    }

    /**
     * Creates a clone of the given unit. Function gets overridden in subclasses
     * 
     * @param unit unit to clone
     * @return cloned unit with the same details
     */
    @Override
    public Unit clone(Unit unit) {
        return unit;
    }

    /**
     * Store infantry details into a JSONObject
     * 
     * @return JSONObject of infantry details
     */
    public JSONObject saveGame() {
        JSONObject unitDetails = new JSONObject();
        unitDetails.put("type", "unit");
        unitDetails.put("name", getClass().toString());
        unitDetails.put("province", province.getName());
        unitDetails.put("cost", cost);
        unitDetails.put("turnsToRecruit", turnsToRecruit);
        unitDetails.put("recruitmentStart", recruitmentStart);
        unitDetails.put("movementPoints", movementPoints);
        unitDetails.put("ranged", ranged);
        unitDetails.put("available", available);
        unitDetails.put("numTroops", numTroops);
        unitDetails.put("armour", armour);
        unitDetails.put("morale", morale);
        unitDetails.put("speed", speed);
        unitDetails.put("meleeAttackDamage", meleeAttackDamage);
        unitDetails.put("rangedAttackDamage", rangedAttackDamage);
        unitDetails.put("defenseSkill", defenseSkill);
        unitDetails.put("shieldDefense", shieldDefense);
        return unitDetails;
    }

    @Override
    public String toString() {
        return getClass().toString() + ", turns to recruit: " + turnsToRecruit + ", recruitment start: "
                + recruitmentStart + ", movement points: " + movementPoints + ", ranged: " + ranged
                + ", number of troops: " + numTroops + ", armour: " + armour + ", morale: " + morale + ", speed: "
                + speed + ", melee attack damage: " + meleeAttackDamage + ", range attack damage: " + rangedAttackDamage
                + ", defense skill: " + defenseSkill + ", shield defense: " + shieldDefense;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Infantry t = (Infantry) obj;
        return (turnsToRecruit == t.turnsToRecruit && recruitmentStart == t.recruitmentStart
                && movementPoints == t.movementPoints && ranged == t.ranged && numTroops == t.numTroops
                && armour == t.armour && morale == t.morale && speed == t.speed
                && meleeAttackDamage == t.meleeAttackDamage && rangedAttackDamage == t.rangedAttackDamage
                && defenseSkill == t.defenseSkill && shieldDefense == t.shieldDefense);
    }
}
