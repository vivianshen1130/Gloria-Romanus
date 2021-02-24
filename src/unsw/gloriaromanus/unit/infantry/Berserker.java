package unsw.gloriaromanus.unit.infantry;

import org.json.JSONObject;

import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public class Berserker extends Infantry {

    /**
     * construct the berserker unit class
     * 
     * @param recruitmentStart the round in which the unit is recruited
     */
    public Berserker(int recruitmentStart) {
        super(recruitmentStart);
        setArmour(0); // "Berserker" special ability: no armour
        setMorale(Double.POSITIVE_INFINITY); // "Berserker" special ability: infinite morale
        setMeleeAttackDamage(12); // "Berserker" special ability: 2x normal
        setShieldDefense(0); // "Berserker" special ability: no shield protection
    }

    /**
     * Store berseker details into a JSONObject
     * 
     * @return JSONObject of berseker details
     */
    @Override
    public JSONObject saveGame() {
        JSONObject unitDetails = new JSONObject();
        unitDetails.put("type", "unit");
        unitDetails.put("name", getClass().toString());
        unitDetails.put("province", super.getProvince().getName());
        unitDetails.put("cost", super.getCost());
        unitDetails.put("turnsToRecruit", super.getTurnsToRecruit());
        unitDetails.put("recruitmentStart", super.getRecruitmentStart());
        unitDetails.put("movementPoints", super.getMovementPoints());
        unitDetails.put("ranged", super.isRanged());
        unitDetails.put("available", super.isAvailable());
        unitDetails.put("numTroops", super.getNumTroops());
        unitDetails.put("armour", super.getArmour());
        // Exclude morale since JSON does not allow non-finite numbers
        unitDetails.put("speed", super.getSpeed());
        unitDetails.put("meleeAttackDamage", super.getMeleeAttackDamage());
        unitDetails.put("rangedAttackDamage", super.getRangedAttackDamage());
        unitDetails.put("defenseSkill", super.getDefenseSkill());
        unitDetails.put("shieldDefense", super.getShieldDefense());
        return unitDetails;
    }

    /**
     * Creates a clone of the given unit
     * 
     * @param unit unit to clone
     * @return cloned unit with the same details
     */
    @Override
    public Unit clone(Unit unit) {
        Faction faction = unit.getFaction();
        Province province = unit.getProvince();

        int cost = unit.getCost();
        int turnsToRecruit = unit.getTurnsToRecruit();
        int recruitmentStart = unit.getRecruitmentStart();
        int movementPoints = unit.getMovementPoints();
        boolean ranged = unit.isRanged();
        boolean available = unit.isAvailable();
        int numTroops = unit.getNumTroops();
        double armour = unit.getArmour();
        double speed = unit.getSpeed();
        double rangedAttackDamage = unit.getRangedAttackDamage();
        double defenseSkill = unit.getDefenseSkill();
        double shieldDefense = unit.getShieldDefense();
        double meleeAttackDamage = unit.getMeleeAttackDamage();

        Berserker copy = new Berserker(recruitmentStart);

        copy.setFaction(faction);
        copy.setProvince(province);
        copy.setCost(cost);
        copy.setTurnsToRecruit(turnsToRecruit);
        copy.setMovementPoints(movementPoints);
        copy.setRanged(ranged);
        copy.setAvailable(available);
        copy.setNumTroops(numTroops);
        copy.setArmour(armour);
        copy.setSpeed(speed);
        copy.setRangedAttackDamage(rangedAttackDamage);
        copy.setDefenseSkill(defenseSkill);
        copy.setShieldDefense(shieldDefense);
        copy.setMeleeAttackDamage(meleeAttackDamage);
        return copy;
    }
}
