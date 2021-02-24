package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import org.json.JSONObject;

import unsw.gloriaromanus.unit.cavalry.*;
import unsw.gloriaromanus.unit.artillery.Artillery;
import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public class CavalryTest {
    @Test
    /**
     * test the faction the cavalry is initialised to is null
     */
    public void testCavalryFaction() {
        Cavalry a = new Cavalry(1);
        assertEquals(null, a.getFaction());
    }

    @Test
    /**
     * test the province the cavalry is initialised to is null
     */
    public void testCavalryProvince() {
        Cavalry a = new Cavalry(1);
        assertEquals(null, a.getProvince());
    }

    @Test
    /**
     * test the turns to recruit is initialised to 1
     */
    public void testCavalryTurnsToRecruit() {
        Cavalry a = new Cavalry(1);
        assertEquals(1, a.getTurnsToRecruit());
    }

    @Test
    /**
     * test the recruitment start time is passed in
     */
    public void testCavalryRecruitmentStart() {
        Cavalry a = new Cavalry(1);
        assertEquals(a.getRecruitmentStart(), 1);
        a.setRecruitmentStart(2);
        assertEquals(a.getRecruitmentStart(), 2);
    }

    @Test
    /**
     * test the movement points is initialised to 15 and can be set to 25
     */
    public void testCavalryMovementPoints() {
        Cavalry a = new Cavalry(1);
        assertEquals(15, a.getMovementPoints());
        a.setMovementPoints(25);
        assertEquals(25, a.getMovementPoints());
    }

    @Test
    /**
     * test the range is initialised to false
     */
    public void testCavalryRange() {
        Cavalry a = new Cavalry(1);
        assertEquals(false, a.isRanged());
    }

    @Test
    /**
     * test the number of troops is initialised to 50 and can be set to 100
     */
    public void testCavalryNumTroops() {
        Cavalry a = new Cavalry(1);
        assertEquals(50, a.getNumTroops());
        a.setNumTroops(100);
        assertEquals(100, a.getNumTroops());
    }

    @Test
    /**
     * test the armour is initialised to 5 and can be set to 10
     */
    public void testCavalryArmour() {
        Cavalry a = new Cavalry(1);
        assertEquals(5, a.getArmour());
        a.setArmour(10);
        assertEquals(10, a.getArmour());
    }

    @Test
    /**
     * test the morale is initialised to 10
     */
    public void testCavalryMorale() {
        Cavalry a = new Cavalry(1);
        assertEquals(10, a.getMorale());
    }

    @Test
    /**
     * test the speed is initialised to 10 and can be set to 20
     */
    public void testCavalrySpeed() {
        Cavalry a = new Cavalry(1);
        assertEquals(10, a.getSpeed());
        a.setSpeed(20);
        assertEquals(20, a.getSpeed());
    }

    @Test
    /**
     * test the melee attack damage returns 16 (melee attack damage + charge stat)
     * and can be set to 10 to return 20
     */
    public void testCavalryMeleeAttackDamage() {
        Cavalry a = new Cavalry(1);
        assertEquals(16, a.getMeleeAttackDamage());
        a.setMeleeAttackDamage(10);
        assertEquals(20, a.getMeleeAttackDamage());
    }

    @Test
    /**
     * test the ranged attack damage is initialised to 0
     */
    public void testCavalryRangedAttackDamage() {
        Cavalry a = new Cavalry(1);
        assertEquals(0, a.getRangedAttackDamage());
    }

    @Test
    /**
     * test the defense skill is initialised to 10 and can be set to 20
     */
    public void testCavalryDefenseSkill() {
        Cavalry a = new Cavalry(1);
        assertEquals(10, a.getDefenseSkill());
        a.setDefenseSkill(20);
        assertEquals(20, a.getDefenseSkill());
    }

    @Test
    /**
     * test the shield defense is initialised to 3 and can be set to 5
     */
    public void testCavalryShieldDefense() {
        Cavalry a = new Cavalry(1);
        assertEquals(3, a.getShieldDefense());
        a.setShieldDefense(5);
        assertEquals(5, a.getShieldDefense());
    }

    @Test
    /**
     * test the idempotency of the cavalry unit
     */
    public void testCavalryIdempotent() {
        Cavalry a = new Cavalry(1);
        Cavalry b = new Cavalry(1);
        assertEquals(true, a.equals(b));
    }

    @Test
    /**
     * test the availability is initialised to false and can be set to true
     */
    public void testCavalryAvailable() {
        Cavalry a = new Cavalry(1);
        assertEquals(false, a.isAvailable());
        a.setAvailable(true);
        assertEquals(true, a.isAvailable());
    }

    @Test
    /**
     * test the cavalry cost is initialised to 10 and can be set to 50
     */
    public void testCavalryGetCost() {
        Cavalry a = new Cavalry(1);
        assertEquals(10, a.getCost());
        a.setCost(50);
        assertEquals(50, a.getCost());
    }

    @Test
    /**
     * test the equality override
     */
    public void testCavalryIsEquals() {
        Cavalry a = new Cavalry(1);
        Cavalry b = new Cavalry(1);
        Cavalry c = new Cavalry(2);
        Object d = new Artillery(2);
        Cavalry e = new Chariot(2);
        assertEquals(true, a.equals(b));
        assertEquals(false, a.equals(c));
        assertEquals(true, b.equals(a));
        assertEquals(false, b.equals(c));
        assertEquals(false, c.equals(a));
        assertEquals(false, c.equals(b));

        assertEquals(false, a.equals(e));

        assertEquals(false, a.equals(null));
        assertEquals(false, a.equals(d));
    }

    @Test
    /**
     * test the toString overrride
     */
    public void testCavalryToString() {
        Cavalry a = new Cavalry(1);
        Cavalry b = new Cavalry(1);
        Cavalry c = new Cavalry(2);
        assertEquals(a.toString(), b.toString());
        assertNotEquals(a.toString(), c.toString());
        assertEquals(b.toString(), a.toString());
        assertNotEquals(b.toString(), c.toString());
        assertNotEquals(c.toString(), a.toString());
        assertNotEquals(c.toString(), b.toString());
    }

    @Test
    /**
     * Test the equals override in faction
     */
    public void testProvinceSave() {
        Faction Rome = new Faction("Rome");
        Province province = new Province(Rome, "province", true);
        Cavalry a = new Cavalry(1);
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
        a.setChargeStat(12);
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
        unitDetails.put("morale", 1);
        unitDetails.put("speed", 2);
        unitDetails.put("chargeStat", 12);
        unitDetails.put("meleeAttackDamage", 1);
        unitDetails.put("rangedAttackDamage", 2);
        unitDetails.put("defenseSkill", 0);
        unitDetails.put("shieldDefense", 2);

        JSONObject Saved = a.saveGame();
        assertEquals(Saved.toString(), unitDetails.toString());
    }
}
