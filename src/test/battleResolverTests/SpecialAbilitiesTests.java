package test.battleResolverTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import org.junit.jupiter.api.Test;

import java.util.Random;

import unsw.gloriaromanus.battleResolver.*;
import unsw.gloriaromanus.province.*;
import unsw.gloriaromanus.faction.*;
import unsw.gloriaromanus.unit.*;
import unsw.gloriaromanus.unit.cavalry.*;
import unsw.gloriaromanus.unit.infantry.*;

public class SpecialAbilitiesTests {

    @Test
    /**
     * test that the "Legionary Eagle" special ability is applied to unit3
     */
    public void testLegionaryEagle() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MeleeCavalry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);
        Unit unit2 = new MissileInfantry(1);
        unit2.setAvailable(true);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);
        Unit unit4 = new MissileInfantry(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 2, b, 2, random);
        assertEquals(false, c.engagementType(unit1, unit3));
        assertFalse(c.applySpecialAbilities(unit1, unit3, false).get(1).equals(unit3));
        assertEquals(11, c.applySpecialAbilities(unit1, unit3, false).get(1).getMorale());
    }

    @Test
    /**
     * test that the "Legionary Eagle" special ability is applied to unit3
     */
    public void testLegionaryEagle1() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MeleeCavalry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);
        Unit unit2 = new MissileInfantry(1);
        unit2.setAvailable(true);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);
        Unit unit4 = new MissileInfantry(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(b, 2, a, 2, random);
        assertFalse(c.applySpecialAbilities(unit1, unit3, false).get(1).equals(unit3));
        assertEquals(11, c.applySpecialAbilities(unit3, unit1, false).get(0).getMorale());
    }

    @Test
    /**
     * test that the "Heroic Charge" special ability is applied to unit1
     */
    public void testHeroicCharge1() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MeleeCavalry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit2 = new MissileInfantry(1);
        unit2.setAvailable(true);
        b.addUnit(unit2);
        Unit unit3 = new MissileInfantry(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);
        Unit unit4 = new MissileInfantry(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 1, b, 3, random);
        assertEquals(false, c.engagementType(unit1, unit3));
        assertFalse(c.manageBattle());
        assertFalse(c.applySpecialAbilities(unit1, unit2, false).get(0).equals(unit1));
        assertEquals(15, c.applySpecialAbilities(unit1, unit2, false).get(0).getMorale());
        assertEquals(20, c.applySpecialAbilities(unit1, unit2, false).get(0).getChargeStat());
    }

    @Test
    /**
     * test that the "Heroic Charge" special ability is not applied if
     * the army does not have fewer than half the number of units as the enemy
     */
    public void testHeroicCharge2() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MeleeCavalry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit2 = new MissileInfantry(1);
        unit2.setAvailable(true);
        b.addUnit(unit2);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 1, b, 3, random);
        assertEquals(false, c.engagementType(unit1, unit2));
        assertTrue(c.applySpecialAbilities(unit1, unit2, false).get(0).equals(unit1));
        assertEquals(10, c.applySpecialAbilities(unit1, unit2, false).get(0).getMorale());
        assertEquals(10, c.applySpecialAbilities(unit1, unit2, false).get(0).getChargeStat());
    }

    @Test
    /**
     * test that the "Skirmisher anti-armour" special ability is applied to unit2
     */
    public void testSkirmisher1() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new JavelinSkirmisher(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit2 = new MissileInfantry(1);
        unit2.setAvailable(true);
        b.addUnit(unit2);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 1, b, 1, random);
        assertEquals(true, c.engagementType(unit1, unit2));
        assertFalse(c.applySpecialAbilities(unit1, unit2, false).get(1).equals(unit2));
        assertEquals(5, c.applySpecialAbilities(unit1, unit2, false).get(1).getArmour());
    }

    @Test
    /**
     * test that the "Skirmisher anti-armour" special ability is applied to unit2 when defending
     */
    public void testSkirmisher2() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new JavelinSkirmisher(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit2 = new MissileInfantry(1);
        unit2.setAvailable(true);
        b.addUnit(unit2);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(b, 1, a, 1, random);
        assertEquals(true, c.engagementType(unit2, unit1));
        assertFalse(c.applySpecialAbilities(unit2, unit1, false).get(0).equals(unit2));
        assertEquals(5, c.applySpecialAbilities(unit2, unit1, false).get(0).getArmour());
    }

    @Test
    /**
     * test that the "Cantabrian circle" special ability is applied to unit2
     */
    public void testCantabrianCircle1() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new HorseArcher(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit2 = new MissileInfantry(1);
        unit2.setAvailable(true);
        b.addUnit(unit2);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 1, b, 1, random);
        assertEquals(true, c.engagementType(unit1, unit2));
        assertFalse(c.applySpecialAbilities(unit1, unit2, false).get(1).equals(unit2));
        assertEquals(5, c.applySpecialAbilities(unit1, unit2, false).get(1).getRangedAttackDamage());
    }

    @Test
    /**
     * test that the "Cantabrian circle" special ability is applied to unit2
     */
    public void testCantabrianCircle2() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new HorseArcher(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit2 = new MissileInfantry(1);
        unit2.setAvailable(true);
        b.addUnit(unit2);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(b, 1, a, 1, random);
        assertEquals(true, c.engagementType(unit1, unit2));
        assertFalse(c.applySpecialAbilities(unit2, unit1, false).get(0).equals(unit2));
        assertEquals(5, c.applySpecialAbilities(unit2, unit1, false).get(0).getRangedAttackDamage());
    }

    @Test
    /**
     * test that the "Cantabrian circle" special ability is not applied if not ranged engagement
     */
    public void testCantabrianCircle3() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new HorseArcher(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        b.addUnit(unit2);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(b, 1, a, 1, random);
        assertEquals(false, c.engagementType(unit1, unit2));
        assertEquals(0, c.applySpecialAbilities(unit2, unit1, false).get(0).getRangedAttackDamage());
    }

    @Test
    /**
     * test that the "Druidic Fervour" special ability is applied to unit2
     */
    public void testDruidicFervour1() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Celtic");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MeleeInfantry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        b.addUnit(unit2);
        Unit unit3 = new Druid(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 1, b, 2, random);
        assertEquals(false, c.engagementType(unit1, unit2));
        assertEquals(9.5, c.applySpecialAbilities(unit1, unit2, false).get(0).getMorale());
        assertEquals(11, c.applySpecialAbilities(unit1, unit2, false).get(1).getMorale());
    }

    @Test
    /**
     * test that the "Druidic Fervour" special ability is applied to all units
     */
    public void testDruidicFervour2() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Celtic");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MeleeInfantry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        b.addUnit(unit2);
        Unit unit3 = new Druid(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);
        Unit unit4 = new Druid(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 1, b, 3, random);
        assertEquals(false, c.engagementType(unit1, unit2));
        assertEquals(9, c.applySpecialAbilities(unit1, unit2, false).get(0).getMorale());
        assertEquals(12, c.applySpecialAbilities(unit1, unit2, false).get(1).getMorale());
    }

    @Test
    /**
     * test that the "Druidic Fervour" special ability is limited to 5 in either direction
     */
    public void testDruidicFervour3() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Celtic");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MeleeInfantry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        b.addUnit(unit2);
        Unit unit3 = new Druid(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);
        Unit unit4 = new Druid(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);
        Unit unit5 = new Druid(1);
        unit5.setAvailable(true);
        b.addUnit(unit4);
        Unit unit6 = new Druid(1);
        unit6.setAvailable(true);
        b.addUnit(unit6);
        Unit unit7 = new Druid(1);
        unit7.setAvailable(true);
        b.addUnit(unit7);
        Unit unit8 = new Druid(1);
        unit8.setAvailable(true);
        b.addUnit(unit8);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 1, b, 7, random);
        assertEquals(false, c.engagementType(unit1, unit2));
        assertEquals(7.5, c.applySpecialAbilities(unit1, unit2, false).get(0).getMorale());
        assertEquals(15, c.applySpecialAbilities(unit1, unit2, false).get(1).getMorale());
    }

    @Test
    /**
     * test that special abilities are applied in the correct order
     */
    public void testDruidicFervour4() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MeleeCavalry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        b.addUnit(unit2);
        Unit unit3 = new Druid(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);
        Unit unit4 = new Druid(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);
        Unit unit5 = new Druid(1);
        unit5.setAvailable(true);
        b.addUnit(unit5);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 1, b, 4, random);
        assertEquals(12.75, c.applySpecialAbilities(unit1, unit2, false).get(0).getMorale());
        assertEquals(14.3, c.applySpecialAbilities(unit1, unit2, false).get(1).getMorale());
    }

    @Test
    /**
     * test that the "Elephants run amok" special ability is 
     */
    public void testElephantsRunAmok1() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new Elephant(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MeleeInfantry(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);

        Random random = new Random(5);
        BattleResolver c = new BattleResolver(a, 2, b, 1, random);
        // c.manageBattle();
        // assertEquals(false, c.engagementType(unit1, unit2));
        c.manageSkirmish(a.getUnits(), b.getUnits(), 0, random);
        assertEquals(44, unit2.getNumTroops());
        assertEquals(48, unit1.getNumTroops());
    }
}