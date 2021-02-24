package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.unit.infantry.JavelinSkirmisher;
import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public class JavelinSkirmisherTest {
    @Test
    /**
     * test that range is initialised to true
     */
    public void testJavelinSkirmisherRange() {
        JavelinSkirmisher a = new JavelinSkirmisher(1);
        assertEquals(true, a.isRanged());
    }

    @Test
    /**
     * test the ranged attack damage is initialised to 10
     */
    public void testJavelinSkirmisherRangeAttackDamage() {
        JavelinSkirmisher a = new JavelinSkirmisher(1);
        assertEquals(10, a.getRangedAttackDamage());
    }

    @Test
    /**
     * test the idempotency of the missile infantry class
     */
    public void testJavelinSkirmisherIdempotent() {
        JavelinSkirmisher a = new JavelinSkirmisher(1);
        JavelinSkirmisher b = new JavelinSkirmisher(1);
        assertEquals(true, a.equals(b));
    }
    
    @Test
    /**
     * Test the clone method in JavelinSkirmisher
     */
    public void testJavelinSkirmisherClone() {
        Faction Rome = new Faction("Rome");
        Province province = new Province(Rome, "province", true);
        JavelinSkirmisher a = new JavelinSkirmisher(1);
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
