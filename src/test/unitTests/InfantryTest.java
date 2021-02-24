package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import org.json.JSONObject;

import unsw.gloriaromanus.unit.infantry.Infantry;
import unsw.gloriaromanus.unit.artillery.Artillery;
import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public class InfantryTest {
    @Test
    /**
     * check that the unit is created with a null faction
     */
    public void testInfantryFaction() {
        Infantry a = new Infantry(1);
        assertEquals(null, a.getFaction());
    }

    @Test
    /**
     * check that the unit is created with a null province
     */
    public void testInfantryProvince() {
        Infantry a = new Infantry(1);
        assertEquals(null, a.getProvince());
    }

    @Test
    /**
     * test the turns to recruit can be changed
     */
    public void testInfantryTurnsToRecruit() {
        Infantry a = new Infantry(1);
        assertEquals(1, a.getTurnsToRecruit());
        a.setTurnsToRecruit(2);
        assertEquals(2, a.getTurnsToRecruit());
    }

    @Test
    /**
     * test the recruitment start time is initialised with the value passed in and
     * can be changed
     */
    public void testInfantryRecruitmentStart() {
        Infantry a = new Infantry(1);
        assertEquals(1, a.getRecruitmentStart());
        a.setRecruitmentStart(2);
        assertEquals(2, a.getRecruitmentStart());
    }

    @Test
    /**
     * test the movement points start at 10 and can be set to another value
     */
    public void testInfantryMovementPoints() {
        Infantry a = new Infantry(1);
        assertEquals(10, a.getMovementPoints());
        a.setMovementPoints(50);
        assertEquals(50, a.getMovementPoints());
    }

    @Test
    /**
     * test ranged is initialised at false
     */
    public void testInfantryRange() {
        Infantry a = new Infantry(1);
        assertEquals(false, a.isRanged());
    }

    @Test
    /**
     * test number of troops is initialised to 50 and can be set to 100
     */
    public void testInfantryNumTroops() {
        Infantry a = new Infantry(1);
        assertEquals(50, a.getNumTroops());
        a.setNumTroops(100);
        assertEquals(100, a.getNumTroops());
    }

    @Test
    /**
     * test armour is initialised to 5
     */
    public void testInfantryArmour() {
        Infantry a = new Infantry(1);
        assertEquals(5, a.getArmour());
    }

    @Test
    /**
     * test morale is initialised to 10
     */
    public void testInfantryMorale() {
        Infantry a = new Infantry(1);
        assertEquals(10, a.getMorale());
    }

    @Test
    /**
     * test speed is initialised to 10
     */
    public void testInfantrySpeed() {
        Infantry a = new Infantry(1);
        assertEquals(10, a.getSpeed());
    }

    @Test
    /**
     * test melee attack damage is initialised to 6
     */
    public void testInfantryMeleeAttackDamage() {
        Infantry a = new Infantry(1);
        assertEquals(6, a.getMeleeAttackDamage());
        a.setMeleeAttackDamage(25);
        assertEquals(25, a.getMeleeAttackDamage());
    }

    @Test
    /**
     * test ranged attack damage is initialised to 0
     */
    public void testInfantryRangedAttackDamage() {
        Infantry a = new Infantry(1);
        assertEquals(0, a.getRangedAttackDamage());
    }

    @Test
    /**
     * test defense skill is initialised to 10
     */
    public void testInfantryDefenseSkill() {
        Infantry a = new Infantry(1);
        assertEquals(10, a.getDefenseSkill());
    }

    @Test
    /**
     * test shield defense is initialised to 3
     */
    public void testInfantryShieldDefense() {
        Infantry a = new Infantry(1);
        assertEquals(3, a.getShieldDefense());
    }

    @Test
    /**
     * test idempotency of the unit
     */
    public void testInfantryIdempotent() {
        Infantry a = new Infantry(1);
        Infantry b = new Infantry(1);
        assertEquals(true, a.equals(b));
    }

    @Test
    /**
     * test availability is initialised to false
     */
    public void testInfantryAvailable() {
        Infantry a = new Infantry(1);
        assertEquals(false, a.isAvailable());
    }

    @Test
    /**
     * test cost is initialised to 10 and can be changed
     */
    public void testInfantryGetCost() {
        Infantry a = new Infantry(1);
        assertEquals(10, a.getCost());
        a.setCost(50);
        assertEquals(50, a.getCost());
    }

    @Test
    /**
     * test equals override
     */
    public void testInfantryIsEquals() {
        Infantry a = new Infantry(1);
        Infantry b = new Infantry(1);
        Infantry c = new Infantry(2);
        Object d = new Artillery(2);
        assertEquals(true, a.equals(b));
        assertEquals(false, a.equals(c));
        assertEquals(true, b.equals(a));
        assertEquals(false, b.equals(c));
        assertEquals(false, c.equals(a));
        assertEquals(false, c.equals(b));

        assertEquals(false, a.equals(null));
        assertEquals(false, a.equals(d));
    }

    @Test
    /**
     * test toString override
     */
    public void testInfantryToString() {
        Infantry a = new Infantry(1);
        Infantry b = new Infantry(1);
        Infantry c = new Infantry(2);
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
        Infantry a = new Infantry(1);
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
        unitDetails.put("morale", 1);
        unitDetails.put("speed", 2);
        unitDetails.put("meleeAttackDamage", 1);
        unitDetails.put("rangedAttackDamage", 2);
        unitDetails.put("defenseSkill", 0);
        unitDetails.put("shieldDefense", 2);

        JSONObject Saved = a.saveGame();
        assertEquals(Saved.toString(), unitDetails.toString());
    }
}
