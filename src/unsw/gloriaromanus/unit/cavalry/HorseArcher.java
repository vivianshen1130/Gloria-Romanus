package unsw.gloriaromanus.unit.cavalry;

import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public class HorseArcher extends Cavalry {

    /**
     * construct the haorse archer unit class
     * 
     * @param recruitmentStart the round in which the unit is recruited
     */
    public HorseArcher(int recruitmentStart) {
        super(recruitmentStart);
        setRanged(true); // horse archer is a ranged cavalry unit
        setRangedAttackDamage(6); // has ranged attack damage of 6
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
        double morale = unit.getMorale();
        double chargeStat = unit.getChargeStat();
        double meleeAttackDamage = unit.getMeleeAttackDamage() - chargeStat;

        HorseArcher copy = new HorseArcher(recruitmentStart);

        copy.setMorale(morale);
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
        copy.setChargeStat(chargeStat);
        copy.setMeleeAttackDamage(meleeAttackDamage);
        return copy;
    }
}
