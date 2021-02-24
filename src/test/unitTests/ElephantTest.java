package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.unit.cavalry.Elephant;
import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public class ElephantTest {
    @Test
    /**
     * test the turns to recruit is 2
     */
    public void testElephantTurnsToRecruit() {
        Elephant a = new Elephant(1);
        assertEquals(2, a.getTurnsToRecruit());
    }

    @Test
    /**
     * test the idempotency of the elephant class
     */
    public void testElephantIdempotent() {
        Elephant a = new Elephant(1);
        Elephant b = new Elephant(1);
        assertEquals(true, a.equals(b));
    }

    @Test
    /**
     * Test the clone method in Elephant
     */
    public void testElephantClone() {
        Faction Rome = new Faction("Rome");
        Province province = new Province(Rome, "province", true);
        Elephant a = new Elephant(1);
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
        a.setChargeStat(12);
        a.setMeleeAttackDamage(1);
        a.setRangedAttackDamage(2);
        a.setDefenseSkill(0);
        a.setShieldDefense(2);

        Unit cloneAfter = a.clone(a);

        assertEquals(false, a.equals(cloneBefore));
        assertEquals(true, a.equals(cloneAfter));
    }
}
