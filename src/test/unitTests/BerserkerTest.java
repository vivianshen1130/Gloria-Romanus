package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.json.JSONObject;

import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.unit.infantry.Berserker;
import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public class BerserkerTest {
    @Test
    /**
     * test berserker armour is initialised to 0
     */
    public void testBerserkerArmour() {
        Berserker a = new Berserker(1);
        assertEquals(0, a.getArmour());
    }

    @Test
    /**
     * test berserker morale is initialised to positive infinity
     */
    public void testBerserkerMorale() {
        Berserker a = new Berserker(1);
        assertEquals(Double.POSITIVE_INFINITY, a.getMorale());
    }

    @Test
    /**
     * test berserker melee attack damage is initialised to 12
     */
    public void testBerserkerMeleeAttackDamage() {
        Berserker a = new Berserker(1);
        assertEquals(12, a.getMeleeAttackDamage());
    }

    @Test
    /**
     * test berserker shield defense is initialised to 0
     */
    public void testBerserkerShieldDefense() {
        Berserker a = new Berserker(1);
        assertEquals(0, a.getShieldDefense());
    }

    @Test
    /**
     * test berserker idempotency
     */
    public void testBerserkerIdempotent() {
        Berserker a = new Berserker(1);
        Berserker b = new Berserker(1);
        assertEquals(true, a.equals(b));
    }

    @Test
    /**
     * Test the equals override in faction
     */
    public void testProvinceSave() {
        Faction Rome = new Faction("Rome");
        Province province = new Province(Rome, "province", true);
        Berserker a = new Berserker(1);
        province.addUnit(a);

        a.setCost(10);
        a.setTurnsToRecruit(1);
        a.setRecruitmentStart(2);
        a.setMovementPoints(4);
        a.setRanged(false);
        a.setAvailable(true);
        a.setNumTroops(10000);
        a.setArmour(20);
        a.setMorale(1);
        a.setSpeed(2);
        a.setMeleeAttackDamage(1);
        a.setRangedAttackDamage(2);
        a.setDefenseSkill(0);
        a.setShieldDefense(2);

        JSONObject unitDetails = new JSONObject();
        unitDetails.put("type", "unit");
        unitDetails.put("name", a.getClass().toString());
        unitDetails.put("province", "province");
        unitDetails.put("cost", 10);
        unitDetails.put("turnsToRecruit", 1);
        unitDetails.put("recruitmentStart", 2);
        unitDetails.put("movementPoints", 4);
        unitDetails.put("ranged", false);
        unitDetails.put("available", true);
        unitDetails.put("numTroops", 10000);
        unitDetails.put("armour", 20);
        unitDetails.put("speed", 2);
        unitDetails.put("meleeAttackDamage", 1);
        unitDetails.put("rangedAttackDamage", 2);
        unitDetails.put("defenseSkill", 0);
        unitDetails.put("shieldDefense", 2);

        JSONObject Saved = a.saveGame();
        assertEquals(Saved.toString(), unitDetails.toString());
    }

    @Test
    /**
     * Test the clone method in MeleeCavalry
     */
    public void testMeleeCavalryClone() {
        Faction Rome = new Faction("Rome");
        Province province = new Province(Rome, "province", true);
        Berserker a = new Berserker(1);
        province.addUnit(a);

        Unit cloneBefore = a.clone(a);

        a.setCost(10);
        a.setTurnsToRecruit(1);
        a.setRecruitmentStart(2);
        a.setMovementPoints(4);
        a.setRanged(false);
        a.setAvailable(true);
        a.setNumTroops(10000);
        a.setArmour(20);
        a.setSpeed(2);
        a.setMeleeAttackDamage(1);
        a.setRangedAttackDamage(2);
        a.setDefenseSkill(0);
        a.setShieldDefense(2);

        Unit cloneAfter = a.clone(a);

        assertEquals(false, a.equals(cloneBefore));
        assertEquals(true, a.equals(cloneAfter));
    }
}
