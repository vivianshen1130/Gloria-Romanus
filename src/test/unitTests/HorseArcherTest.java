package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import unsw.gloriaromanus.unit.cavalry.*;
import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public class HorseArcherTest {
    @Test
    /**
     * test that range is initialised to true
     */
    public void testHorseArcherRange() {
        HorseArcher a = new HorseArcher(1);
        assertEquals(true, a.isRanged());
    }

    @Test
    /**
     * test the ranged attack damage is initialised to 6
     */
    public void testHorseArcherRangeAttackDamage() {
        HorseArcher a = new HorseArcher(1);
        assertEquals(6, a.getRangedAttackDamage());
    }

    @Test
    /**
     * test the idempotency of the horse archer class
     */
    public void testHorseArcherIdempotent() {
        HorseArcher a = new HorseArcher(1);
        HorseArcher b = new HorseArcher(1);
        assertEquals(true, a.equals(b));
    }

    @Test
    /**
     * Test the clone method in HorseArcher
     */
    public void testHorseArcherClone() {
        Faction Rome = new Faction("Rome");
        Province province = new Province(Rome, "province", true);
        HorseArcher a = new HorseArcher(1);
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
