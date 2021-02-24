package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.unit.infantry.*;
import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public class MeleeInfantryTest {

    @Test
    /**
     * test melee attack damage is initialised to 6 and can be changed to 25
     */
    public void testMeleeInfantryMeleeAttackDamage() {
        Unit a = new MeleeInfantry(1);
        assertEquals(9, a.getMeleeAttackDamage());
        assertEquals(6, a.getMeleeAttackDamage());
        a.setMeleeAttackDamage(25);
        assertEquals(25, a.getMeleeAttackDamage());
        assertEquals(25, a.getMeleeAttackDamage());
        assertEquals(28, a.getMeleeAttackDamage());

    }

    @Test
    /**
     * test shield defense is initialised to 3 and can be changed to 5
     */
    public void testInfantryShieldDefense() {
        Infantry a = new Infantry(1);
        assertEquals(3, a.getShieldDefense());
        a.setShieldDefense(5);
        assertEquals(5, a.getShieldDefense());
    }

    @Test
    /**
     * test the idempotency of the MeleeInfantry class
     */
    public void testMeleeInfantryIdempotent() {
        MeleeInfantry a = new MeleeInfantry(1);
        MeleeInfantry b = new MeleeInfantry(1);
        assertEquals(true, a.equals(b));
    }

    @Test
    /**
     * Test the clone method in MeleeInfantry
     */
    public void testMeleeInfantryClone() {
        Faction Rome = new Faction("Rome");
        Province province = new Province(Rome, "province", true);
        MeleeInfantry a = new MeleeInfantry(1);
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
