package unsw.gloriaromanus.unit.infantry;

import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public class MeleeInfantry extends Infantry {
    private int counter = 0;
    private double meleeAttackDamage = 6;
    private double shieldDefense = 3;

    /**
     * construct a melee infantry class
     * 
     * @param recruitmentStart the rounf in which the unit was recruited
     */
    public MeleeInfantry(int recruitmentStart) {
        super(recruitmentStart);
    }

    /**
     * setter for the melee attack damage of the unit
     * 
     * @param meleeAttackDamage the melee attack damage of the unit
     */
    public void setMeleeAttackDamage(double meleeAttackDamage) {
        this.meleeAttackDamage = meleeAttackDamage;
    }

    @Override
    public double getMeleeAttackDamage() {
        if (counter % 4 == 0) {
            counter++;
            return meleeAttackDamage + shieldDefense;
        }
        counter++;
        return meleeAttackDamage;
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
        double meleeAttackDamage = unit.getMeleeAttackDamage();

        MeleeInfantry copy = new MeleeInfantry(recruitmentStart);

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
        copy.setMeleeAttackDamage(meleeAttackDamage);
        return copy;
    }
}
