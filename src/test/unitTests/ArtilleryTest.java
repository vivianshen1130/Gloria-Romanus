package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import org.json.JSONObject;

import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.unit.artillery.*;
import unsw.gloriaromanus.unit.infantry.Berserker;
import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public class ArtilleryTest {
    @Test
    /**
     * check that the unit is created with a null faction
     */
    public void testArtilleryFaction() {
        Artillery a = new Artillery(1);
        assertEquals(null, a.getFaction());
    }

    @Test
    /**
     * check that the unit is created with a null province
     */
    public void testArtilleryProvince() {
        Artillery a = new Artillery(1);
        assertEquals(null, a.getProvince());
    }

    @Test
    /**
     * test the turns to recruit can be changed
     */
    public void testArtilleryTurnsToRecruit() {
        Artillery a = new Artillery(1);
        assertEquals(1, a.getTurnsToRecruit());
        a.setTurnsToRecruit(2);
        assertEquals(2, a.getTurnsToRecruit());
    }

    @Test
    /**
     * test the recruitment start time is initialised with the value passed in and
     * can be changed
     */
    public void testArtilleryRecruitmentStart() {
        Artillery a = new Artillery(1);
        assertEquals(1, a.getRecruitmentStart());
        a.setRecruitmentStart(2);
        assertEquals(2, a.getRecruitmentStart());
    }

    @Test
    /**
     * test the movement points start at 4 and can be set to another value
     */
    public void testArtilleryMovementPoints() {
        Artillery a = new Artillery(1);
        assertEquals(4, a.getMovementPoints());
        a.setMovementPoints(5);
        assertEquals(5, a.getMovementPoints());
    }

    @Test
    /**
     * test ranged is initialised at true and can be set to false
     */
    public void testArtilleryRange() {
        Artillery a = new Artillery(1);
        assertEquals(true, a.isRanged());
        a.setRanged(false);
        assertEquals(false, a.isRanged());
    }

    @Test
    /**
     * test number of troops is initialised to 50 and can be set to 100
     */
    public void testArtilleryNumTroops() {
        Artillery a = new Artillery(1);
        assertEquals(50, a.getNumTroops());
        a.setNumTroops(100);
        assertEquals(100, a.getNumTroops());
    }

    @Test
    /**
     * test armour is initialised to 5 and can be set to 10
     */
    public void testArtilleryArmour() {
        Artillery a = new Artillery(1);
        assertEquals(5, a.getArmour());
        a.setArmour(10);
        assertEquals(10, a.getArmour());
    }

    @Test
    /**
     * test morale is initialised to 10 and can be set to 20
     */
    public void testArtilleryMorale() {
        Artillery a = new Artillery(1);
        assertEquals(10, a.getMorale());
        a.setMorale(20);
        assertEquals(20, a.getMorale());
    }

    @Test
    /**
     * test speed is initialised to 10 and can be set to 20
     */
    public void testArtillerySpeed() {
        Artillery a = new Artillery(1);
        assertEquals(10, a.getSpeed());
        a.setSpeed(20);
        assertEquals(20, a.getSpeed());
    }

    @Test
    /**
     * test melee attack damage is initialised to 6 and can be set to 10
     */
    public void testArtilleryMeleeAttackDamage() {
        Artillery a = new Artillery(1);
        assertEquals(6, a.getMeleeAttackDamage());
        a.setMeleeAttackDamage(10);
        assertEquals(10, a.getMeleeAttackDamage());
    }

    @Test
    /**
     * test ranged attack damage is initialised to 6 and can be set to 10
     */
    public void testArtilleryRangedAttackDamage() {
        Artillery a = new Artillery(1);
        assertEquals(6, a.getRangedAttackDamage());
        a.setRangedAttackDamage(10);
        assertEquals(10, a.getRangedAttackDamage());
    }

    @Test
    /**
     * test defense skill is initialised to 10 and can be set to 20
     */
    public void testArtilleryDefenseSkill() {
        Artillery a = new Artillery(1);
        assertEquals(10, a.getDefenseSkill());
        a.setDefenseSkill(20);
        assertEquals(20, a.getDefenseSkill());
    }

    @Test
    /**
     * test shield defense is initialised to 3 and can be set to 5
     */
    public void testArtilleryShieldDefense() {
        Artillery a = new Artillery(1);
        assertEquals(3, a.getShieldDefense());
        a.setShieldDefense(5);
        assertEquals(5, a.getShieldDefense());
    }

    @Test
    /**
     * test artillery is idempotent
     */
    public void testArtilleryIdempotent() {
        Artillery a = new Artillery(1);
        Artillery b = new Artillery(1);
        assertEquals(true, a.equals(b));
    }

    @Test
    /**
     * test available is initialised to false and can be set to true
     */
    public void testArtilleryAvailable() {
        Artillery a = new Artillery(1);
        assertEquals(false, a.isAvailable());
    }

    @Test
    /**
     * test cost is initialised to 10 and can be set to 50
     */
    public void testArtilleryGetCose() {
        Artillery a = new Artillery(1);
        assertEquals(a.getCost(), 10);
        a.setCost(50);
        assertEquals(50, a.getCost());
    }

    @Test
    /**
     * test the equals override in artillery
     */
    public void testArtilleryIsEquals() {
        Artillery a = new Artillery(1);
        Artillery b = new Artillery(1);
        Artillery c = new Artillery(2);
        Object d = new Berserker(1);
        assertEquals(true, a.equals(b));
        assertEquals(false, a.equals(c));
        assertEquals(true, b.equals(a));
        assertEquals(false, b.equals(c));
        assertEquals(false, c.equals(a));
        assertEquals(false, c.equals(b));

        assertEquals(a.equals(null), false);
        assertEquals(a.equals(d), false);
    }

    @Test
    /**
     * test the to string override in artillery
     */
    public void testArtilleryToString() {
        Artillery a = new Artillery(1);
        Artillery b = new Artillery(1);
        Artillery c = new Artillery(2);
        assertEquals(a.toString(), b.toString());
        assertNotEquals(a.toString(), c.toString());
        assertEquals(b.toString(), a.toString());
        assertNotEquals(b.toString(), c.toString());
        assertNotEquals(c.toString(), a.toString());
        assertNotEquals(c.toString(), b.toString());

        Artillery d = new Artillery(2);
        assertEquals(c.toString(), d.toString());
        d.setShieldDefense(50);
        assertNotEquals(c.toString(), d.toString());
    }

    @Test
    /**
     * Test the equals override in faction
     */
    public void testProvinceSave() {
        Faction Rome = new Faction("Rome");
        Province province = new Province(Rome, "province", true);
        Artillery a = new Artillery(1);
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

    @Test
    /**
     * Test the clone method in artillery
     */
    public void testArtilleryClone() {
        Faction Rome = new Faction("Rome");
        Province province = new Province(Rome, "province", true);
        Artillery a = new Artillery(1);
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
        a.setMorale(1);
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
