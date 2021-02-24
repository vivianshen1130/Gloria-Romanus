package unsw.gloriaromanus.unit;

import org.json.JSONObject;

import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public interface Unit {
    public Faction getFaction();

    public void setFaction(Faction faction);

    public Province getProvince();

    public void setProvince(Province province);

    public int getTurnsToRecruit();

    public void setTurnsToRecruit(int turnsToRecruit);

    public int getRecruitmentStart();

    public void setRecruitmentStart(int recruitmentStart);

    public int getMovementPoints();

    public void setMovementPoints(int movementPoints);

    public int getCurrentMovementPoints();

    public void setCurrentMovementPoints(int currentMovementPoints);

    public boolean isRanged();

    public void setRanged(boolean ranged);

    public int getNumTroops();

    public void setNumTroops(int numTroops);

    public double getArmour();

    public void setArmour(double armour);

    public double getMorale();

    public void setMorale(double morale);

    public double getSpeed();

    public void setSpeed(double speed);

    public double getMeleeAttackDamage();

    public void setMeleeAttackDamage(double meleeAttackDamage);

    public double getChargeStat();

    public void setChargeStat(double chargeStat);

    public double getRangedAttackDamage();

    public void setRangedAttackDamage(double rangedAttackDamage);

    public double getDefenseSkill();

    public void setDefenseSkill(double defenseSkill);

    public double getShieldDefense();

    public void setShieldDefense(double shieldDefense);

    // public double getMoraleBonus();

    // public void setMoraleBonus(double moraleBonus);

    // public double getMoraleDecrease();

    // public void setMoraleDecrease(double moraleDecrease);

    public boolean isAvailable();

    public void setAvailable(boolean available);

    public int getCost();

    public void setCost(int cost);

    public int getUpkeepCost();

    public JSONObject saveGame();

    public Unit clone(Unit unit);
}
