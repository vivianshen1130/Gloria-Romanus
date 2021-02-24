package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.gloriaromanus.unit.infantry.*;
import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public class HopliteTest {
    @Test
    /**
     * test the speed is set to 5
     */
    public void testHopliteSpeed() {
        Hoplite a = new Hoplite(1);
        assertEquals(5, a.getSpeed());
    }

    @Test
    /**
     * test the defense skill is set to 20
     */
    public void testHopliteDefenseSkill() {
        Hoplite a = new Hoplite(1);
        assertEquals(20, a.getDefenseSkill());
    }

    @Test
    /**
     * test the idempotency of the hoplite class
     */
    public void testHopliteIdempotent() {
        Hoplite a = new Hoplite(1);
        Hoplite b = new Hoplite(1);
        assertEquals(true, a.equals(b));
    }

    @Test
    /**
     * Test the clone method in Druid
     */
    public void testDruidClone() {
        Faction Rome = new Faction("Rome");
        Province province = new Province(Rome, "province", true);
        Hoplite a = new Hoplite(1);
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
