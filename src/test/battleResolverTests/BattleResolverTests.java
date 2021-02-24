package test.battleResolverTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Random;

import unsw.gloriaromanus.battleResolver.*;
import unsw.gloriaromanus.province.*;
import unsw.gloriaromanus.faction.*;
import unsw.gloriaromanus.unit.*;
import unsw.gloriaromanus.unit.infantry.*;

public class BattleResolverTests {

    @Test
    /**
     * test the equals override for battle resolver
     */
    public void testIdempotency() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
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
        BattleResolver d = new BattleResolver(a, 2, b, 2, random);
        BattleResolver e = null;
        assertEquals(c, d);
        assertEquals(c.toString(), d.toString());
        assertEquals(false, c.equals(b));
        assertEquals(false, c.equals(e));
    }

    @Test
    /**
     * test that an engagement with two ranged units is a ranged engagement
     */
    public void testBattleResolverRanged() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
        a.addUnit(unit1);
        Unit unit2 = new MissileInfantry(1);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        b.addUnit(unit3);
        Unit unit4 = new MissileInfantry(1);
        b.addUnit(unit4);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 2, b, 2, random);
        assertEquals(true, c.engagementType(unit1, unit3));
    }

    @Test
    /**
     * test that an engagement with two melee units is a melee engagement
     */
    public void testBattleResolverMelee() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MeleeInfantry(1);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MeleeInfantry(1);
        b.addUnit(unit3);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 1, b, 1, random);
        assertEquals(false, c.engagementType(unit1, unit3));
    }

    @Test
    /**
     * ranged engagement when one unit is ranged and one unit is melee
     */
    public void testBattleResolverMixed1() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MeleeInfantry(1);
        unit1.setSpeed(0);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        b.addUnit(unit3);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 1, b, 1, random);
        assertEquals(true, c.engagementType(unit1, unit3));
    }

    @Test
    /**
     * melee engagement when one unit is ranged and one unit is melee
     */
    public void testBattleResolverMixed2() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MeleeInfantry(1);
        unit1.setSpeed(20);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        b.addUnit(unit3);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 1, b, 1, random);
        assertEquals(false, c.engagementType(unit3, unit1));
    }

    @Test
    /**
     * check what happens when no units are available on either side
     */
    public void testBattleResolverNoneAvailable() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
        a.addUnit(unit1);
        Unit unit2 = new MissileInfantry(1);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        b.addUnit(unit3);
        Unit unit4 = new MissileInfantry(1);
        b.addUnit(unit4);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 2, b, 2, random);
        assertEquals(0, c.getDefendingArmy().size());
        assertEquals(0, c.getDefendingArmy().size());
        assertEquals(false, c.manageBattle());
        assert (b.getFaction() != a.getFaction());
    }

    @Test
    /**
     * check that when units are available they are added to the lists of armies in
     * a battle
     */
    public void testBattleResolverUnitsAvailable() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
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
        assertEquals(2, c.getDefendingArmy().size());
        assertEquals(2, c.getDefendingArmy().size());
    }

    @Test
    /**
     * check a simple battle resolver where only the invader has available units
     */
    public void testBattleResolverSimpleBattle() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);
        Unit unit2 = new MissileInfantry(1);
        unit2.setAvailable(true);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        b.addUnit(unit3);
        Unit unit4 = new MissileInfantry(1);
        b.addUnit(unit4);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 2, b, 2, random);
        assertEquals(true, c.manageBattle());
        assertEquals(b.getFaction(), a.getFaction());
        assertEquals(c.getInvadingArmy(), b.getUnits());
    }

    @Test
    /**
     * test battle resolver returns false when neither side has available units
     */
    public void testBattleResolverSimpleBattle2() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
        a.addUnit(unit1);
        Unit unit2 = new MissileInfantry(1);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        b.addUnit(unit3);
        Unit unit4 = new MissileInfantry(1);
        b.addUnit(unit4);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 2, b, 2, random);
        assertEquals(false, c.manageBattle());
        assert (b.getFaction() != a.getFaction());
    }

    @Test
    /**
     * test battle resolver returns false when they reach 200 engagements
     */
    public void testBattleResolver() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MeleeInfantry(1);
        unit1.setMeleeAttackDamage(0);
        a.addUnit(unit1);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MeleeInfantry(1);
        unit3.setMeleeAttackDamage(0);
        b.addUnit(unit3);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 1, b, 1, random);
        assertEquals(false, c.manageBattle());
        assert (b.getFaction() != a.getFaction());
    }

    @Test
    /**
     * battle resolver when units are available to both armies
     */
    public void testBattleResolver1() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
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
        b.addUnit(unit4);

        Random random = new Random(1234);
        BattleResolver c = new BattleResolver(a, 2, b, 2, random);
        assertEquals(false, c.manageBattle());
    }

    @Test
    /**
     * battle resolver when units are available to both armies
     */
    public void testBattleResolver2() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
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
        assertEquals(3, c.getDefendingArmy().size());
        assertEquals(1, c.getInvadingArmy().size());
        assertEquals(false, c.manageBattle());
        assert (b.getFaction() != a.getFaction());
    }

    @Test
    /**
     * test casualties for ranged engagement
     */
    public void testEngagment1() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);
        Unit unit4 = new MeleeInfantry(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);

        Random random = new Random(1234);
        Engagement e = new RangedEngagement(unit1, unit3, random);
        assert (e.getCasualty1() >= 0 && e.getCasualty1() <= unit1.getNumTroops());
        assert (e.getCasualty2() >= 0 && e.getCasualty2() <= unit3.getNumTroops());
        assertEquals(2, e.getCasualty1());
        assertEquals(0, e.getCasualty2());
    }

    @Test
    /**
     * test casualties for melee engagement
     */
    public void testEngagment2() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);
        Unit unit4 = new MeleeInfantry(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);

        Random random = new Random(34);
        Engagement e = new MeleeEngagement(unit2, unit4, random);
        assert (e.getCasualty1() >= 0 && e.getCasualty1() <= unit2.getNumTroops());
        assert (e.getCasualty2() >= 0 && e.getCasualty2() <= unit4.getNumTroops());
        assertEquals(3, e.getCasualty1());
        assertEquals(2, e.getCasualty2());
    }

    @Test
    /**
     * test casualties for ranged engagement with high attack damage and low armour
     */
    public void testEngagment2a() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        unit2.setArmour(0);
        unit2.setMeleeAttackDamage(1000);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit4 = new MeleeInfantry(1);
        unit4.setAvailable(true);
        unit4.setArmour(0);
        unit4.setMeleeAttackDamage(1000);
        b.addUnit(unit4);

        Random random = new Random(1234);
        Engagement e = new MeleeEngagement(unit2, unit4, random);
        // assert(e.getCasualty1() >= 0 && e.getCasualty1() <= unit2.getNumTroops());
        // assert(e.getCasualty2() >= 0 && e.getCasualty2() <= unit4.getNumTroops());
        // assertEquals(50, e.getCasualty1());
        assertEquals(50, e.getCasualty2());
    }

    @Test
    /**
     * check whether units breaks with zero morale after ranged engagement
     */
    public void testEngagment3() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        unit2.setMorale(0);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        unit3.setAvailable(true);
        unit3.setMorale(0);
        b.addUnit(unit3);
        Unit unit4 = new MeleeInfantry(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);

        Random random = new Random(1234);
        Engagement e = new RangedEngagement(unit2, unit3, random);
        assertEquals(true, e.breaking1(50, 50));
        assertEquals(true, e.breaking2(50, 50));
    }

    @Test
    /**
     * check whether units breaks with zero morale after melee engagement
     */
    public void testEngagment3a() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        unit2.setMorale(0);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        unit3.setAvailable(true);
        unit3.setMorale(0);
        b.addUnit(unit3);
        Unit unit4 = new MeleeInfantry(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);

        Random random = new Random(1234);
        Engagement e = new MeleeEngagement(unit2, unit3, random);
        assertEquals(true, e.breaking1(50, 50));
        assertEquals(true, e.breaking2(50, 50));
    }

    @Test
    /**
     * check whether units breaks after melee engagement
     */
    public void testEngagment4() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);
        Unit unit4 = new MeleeInfantry(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);

        Random random = new Random(34);
        Engagement e = new MeleeEngagement(unit2, unit3, random);
        assertEquals(false, e.breaking1(50, 50));
        assertEquals(true, e.breaking2(50, 50));
    }

    @Test
    /**
     * check the number of engagements involved in a skirmish
     */
    public void testManageSkirmish1() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);
        Unit unit4 = new MeleeInfantry(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);

        Random random = new Random(34);
        BattleResolver c = new BattleResolver(a, 1, b, 3, random);
        int num = c.manageSkirmish(c.getInvadingArmy(), c.getDefendingArmy(), 0, random);
        assertEquals(16, num);
    }

    @Test
    /**
     * test whether a unit successfully flees
     */
    public void testFlee1() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);
        Unit unit4 = new MeleeInfantry(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);

        Random random = new Random(34);
        BattleResolver c = new BattleResolver(a, 1, b, 3, random);
        unit2.setSpeed(15);
        assertEquals(true, c.flee(unit2, unit4));
        assertEquals(false, c.flee(unit4, unit2));
    }

    @Test
    /**
     * test whether a unit successfully flees after an engagement
     */
    public void testFlee2() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);
        Unit unit4 = new MeleeInfantry(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);

        Random random = new Random(34);
        BattleResolver c = new BattleResolver(a, 1, b, 3, random);
        unit2.setSpeed(20);
        assertEquals(true, c.flee(unit2, unit4));
        assertEquals(false, c.flee(unit4, unit2));
    }

    @Test
    /**
     * test clone unit list creates a copy of the list of units with all the correct
     * corresponding values
     */
    public void testCloneUnitList() {
        Faction Gaul = new Faction("Gaul");
        Faction Rome = new Faction("Rome");

        Province a = new Province(Gaul, "a", false);
        Unit unit1 = new MissileInfantry(1);
        unit1.setAvailable(true);
        a.addUnit(unit1);
        Unit unit2 = new MeleeInfantry(1);
        unit2.setAvailable(true);
        a.addUnit(unit2);

        Province b = new Province(Rome, "b", false);
        Unit unit3 = new MissileInfantry(1);
        unit3.setAvailable(true);
        b.addUnit(unit3);
        Unit unit4 = new MeleeInfantry(1);
        unit4.setAvailable(true);
        b.addUnit(unit4);

        Random random = new Random(34);
        new BattleResolver(a, 1, b, 3, random);
        unit1.setCost(100);
        unit1.setTurnsToRecruit(13);
        unit1.setRecruitmentStart(10);
        unit1.setMovementPoints(50);
        unit1.setRanged(true);
        unit1.setAvailable(true);
        unit1.setNumTroops(1000);
        unit1.setArmour(1);
        unit1.setMorale(2);
        unit1.setSpeed(20);
        unit1.setRangedAttackDamage(500);
        unit1.setDefenseSkill(1);
        unit1.setShieldDefense(2);
    }

}
