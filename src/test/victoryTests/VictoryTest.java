package test.victoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import unsw.gloriaromanus.victory.*;
import unsw.gloriaromanus.faction.Faction;

public class VictoryTest {
    @Test
    /**
     * test the conquest goal evaluates true when true is passed in
     */
    public void testConquestGoal() {
        Component c = new ConquestGoal(true);
        assertEquals(true, c.evaluate());
    }

    @Test
    /**
     * test the treasury goal evaluates false when below 100000
     */
    public void testTreasuryGoal() {
        Component t = new TreasuryGoal(1000);
        assertEquals(false, t.evaluate());
    }

    @Test
    /**
     * test the treasury goal evaluates true when >= 400000
     */
    public void testWealthGoal() {
        Component w = new WealthGoal(400000);
        assertEquals(true, w.evaluate());
    }

    @Test
    /**
     * test the logical or evaluates true when one condition is met
     */
    public void testLogicalOr1() {
        Component c = new ConquestGoal(true);
        Component t = new TreasuryGoal(1000);
        Component or = new LogicalOr(c, t);
        assertEquals(true, or.evaluate());
    }

    @Test
    /**
     * test the logical or evaluates false when neither condition is met
     */
    public void testLogicalOr2() {
        Component c = new ConquestGoal(false);
        Component t = new TreasuryGoal(1000);
        Component or = new LogicalOr(c, t);
        assertEquals(false, or.evaluate());
    }

    @Test
    /**
     * test the logical and evaluates true when both conditions are met
     */
    public void testLogicalAnd1() {
        Component c = new ConquestGoal(true);
        Component t = new TreasuryGoal(100000);
        Component and = new LogicalAnd(c, t);
        assertEquals(true, and.evaluate());
    }
    
    @Test
    /**
     * test the logical and evaluates false when one condition is not met
     */
    public void testLogicalAnd2() {
        Component c = new ConquestGoal(true);
        Component t = new TreasuryGoal(1000);
        Component and = new LogicalAnd(c, t);
        assertEquals(false, and.evaluate());
    }
    
    @Test
    /**
     * test that create campaign victory creates a combination of goals according to the spec
     */
    public void testCreateCampaignVictory() {
        CampaignVictory c = new CampaignVictory();
        List<String> list = c.createCampaignVictory();
        int size = list.size();
        assert (size == 3 || size == 5);
        assert (list.contains("WEALTH") || list.contains("TREASURY"));
        assert (list.contains("WEALTH") || list.contains("CONQUEST"));
        assert (list.contains("AND") || list.contains("OR"));
    }

    @Test
    /**
     * test that check campaign victory returns true when
     * there are two goals, a disjunction and one condition is fulfilled
     */
    public void testCheckCampaignVictory() {
        CampaignVictory c = new CampaignVictory();
        List<String> list = new ArrayList<String>(Arrays.asList("CONQUEST", "OR", "WEALTH"));
        boolean victory = c.checkCampaignVictory(list, true, 1, 1);
        assertEquals(true, victory);
    }

    @Test
    /**
     * test that check campaign victory returns false when 
     * there are two goals, a conjunction and only one condition is fulfilled
     */
    public void testCheckCampaignVictory2() {
        CampaignVictory c = new CampaignVictory();
        List<String> list = new ArrayList<String>(Arrays.asList("CONQUEST", "AND", "WEALTH"));
        boolean victory = c.checkCampaignVictory(list, true, 1, 1);
        assertEquals(false, victory);
    }

    @Test
    /**
     * test that check campaign victory returns false when 
     * there are tree goals, a conjunction and disjunction and only one condition is fulfilled
     */
    public void testCheckCampaignVictory3() {
        CampaignVictory c = new CampaignVictory();
        List<String> list = new ArrayList<String>(Arrays.asList("CONQUEST", "AND", "WEALTH", "OR", "TREASURY"));
        boolean victory = c.checkCampaignVictory(list, true, 1, 1);
        assertEquals(false, victory);
    }

    @Test
    /**
     * test that check campaign victory returns true when 
     * there are two goals, a conjunction and two conditions are fulfilled
     */
    public void testCheckCampaignVictory4() {
        CampaignVictory c = new CampaignVictory();
        List<String> list = new ArrayList<String>(Arrays.asList("CONQUEST", "AND", "WEALTH", "OR", "TREASURY"));
        boolean victory = c.checkCampaignVictory(list, true, 400000, 1);
        assertEquals(true, victory);
    }

    @Test
    /**
     * test that check campaign victory returns false when 
     * there are three goals, two conjunctions and only two conditions are fulfilled
     */
    public void testCheckCampaignVictory5() {
        CampaignVictory c = new CampaignVictory();
        List<String> list = new ArrayList<String>(Arrays.asList("CONQUEST", "AND", "WEALTH", "AND", "TREASURY"));
        boolean victory = c.checkCampaignVictory(list, true, 400000, 1);
        assertEquals(false, victory);
    }

    @Test
    /**
     * test that check campaign victory true when 
     * there are two goals, a conjunction and disjunction and one condition is fulfilled
     */
    public void testCheckCampaignVictory6() {
        CampaignVictory c = new CampaignVictory();
        List<String> list = new ArrayList<String>(Arrays.asList("CONQUEST", "OR", "WEALTH", "AND", "TREASURY"));
        boolean victory = c.checkCampaignVictory(list, true, 400000, 1);
        assertEquals(true, victory);
    }

    @Test
    /**
     * test that check campaign victory returns false when 
     * there are three goals, a disjunction and a conjunction and only one condition is fulfilled
     */
    public void testCheckCampaignVictory7() {
        CampaignVictory c = new CampaignVictory();
        List<String> list = new ArrayList<String>(Arrays.asList("CONQUEST", "OR", "WEALTH", "AND", "TREASURY"));
        boolean victory = c.checkCampaignVictory(list, false, 400000, 1);
        assertEquals(false, victory);
    }

    @Test
    /**
     * test that check campaign victory returns true when 
     * there are three goals, a conjunction and a disjunction and two conditions are fulfilled
     */
    public void testCheckCampaignVictory8() {
        CampaignVictory c = new CampaignVictory();
        List<String> list = new ArrayList<String>(Arrays.asList("CONQUEST", "OR", "WEALTH", "AND", "TREASURY"));
        boolean victory = c.checkCampaignVictory(list, false, 400000, 100000);
        assertEquals(true, victory);
    }

    @Test
     /**
     * test that check campaign victory returns true when 
     * there are three goals, two conjunctions and all three conditions are fulfilled
     */
    public void testCheckCampaignVictory9() {
        CampaignVictory c = new CampaignVictory();
        List<String> list = new ArrayList<String>(Arrays.asList("CONQUEST", "AND", "WEALTH", "AND", "TREASURY"));
        boolean victory = c.checkCampaignVictory(list, true, 400000, 100000);
        assertEquals(true, victory);
    }

    @Test
    /**
     * Test for conquest goal observer evaluates to true once allConquered boolean
     * has been updated to true
     */
    public void testConquestGoalObserver() {
        Faction rome = new Faction("Rome");
        ConquestGoal conquest = new ConquestGoal(rome.isAllConquered());

        rome.attach(conquest);
        assertEquals(conquest.evaluate(), false);
        rome.setAllConquered(true);
        assertEquals(conquest.evaluate(), true);
        rome.setAllConquered(false);
        assertEquals(conquest.evaluate(), false);
    }

    @Test
    /**
     * Test for treasury goal observer evaluates to true once treasury value has
     * been updated to be 100000 or over
     */
    public void testTreasuryGoalObserver() {
        Faction rome = new Faction("Rome");
        TreasuryGoal treasury = new TreasuryGoal(rome.getTreasury());

        rome.attach(treasury);
        assertEquals(treasury.evaluate(), false);
        rome.setTreasury(100000);
        assertEquals(treasury.evaluate(), true);
        rome.setTreasury(100001);
        assertEquals(treasury.evaluate(), true);
        rome.setTreasury(100);
        assertEquals(treasury.evaluate(), false);
    }

    @Test
    /**
     * Test for wealth goal observer evaluates to true once wealth value has been
     * updated to be 4000000 or over
     */
    public void testWealthGoalObserver() {
        Faction rome = new Faction("Rome");
        WealthGoal wealth = new WealthGoal(rome.getWealth());

        rome.attach(wealth);
        assertEquals(wealth.evaluate(), false);
        rome.setWealth(4000000);
        assertEquals(wealth.evaluate(), true);
        rome.setWealth(4000001);
        assertEquals(wealth.evaluate(), true);
        rome.setWealth(100);
        assertEquals(wealth.evaluate(), false);
    }

    @Test
    /**
     * Test for only adding unique observers to the list of observers
     */
    public void testAttachDupObserver() {
        Faction rome = new Faction("Rome");
        WealthGoal wealth = new WealthGoal(rome.getWealth());
        List<Observer> observers = new ArrayList<Observer>();

        rome.attach(wealth);
        observers.add(wealth);
        assertEquals(rome.getListObservers(), observers);
        rome.attach(wealth);
        assertEquals(rome.getListObservers(), observers);
    }

    @Test
    /**
     * Test for removing a conquest observer means they will no longer be updated on
     * the conquest goal
     */
    public void testRemoveConquestObserver() {
        Faction rome = new Faction("Rome");
        ConquestGoal conquest = new ConquestGoal(rome.isAllConquered());

        rome.attach(conquest);
        rome.detach(conquest);
        rome.setAllConquered(true);
        assertEquals(conquest.evaluate(), false);
    }

    @Test
    /**
     * Test for removing a treasury observer means they will no longer be updated on
     * the treasury goal
     */
    public void testRemoveTeasuryObserver() {
        Faction rome = new Faction("Rome");
        TreasuryGoal treasury = new TreasuryGoal(rome.getTreasury());

        rome.attach(treasury);
        rome.detach(treasury);
        rome.setTreasury(1000000000);
        assertEquals(treasury.evaluate(), false);
    }

    @Test
    /**
     * Test for removing a wealth observer means they will no longer be updated on
     * the wealth goal
     */
    public void testRemoveWealthObserver() {
        Faction rome = new Faction("Rome");
        WealthGoal wealth = new WealthGoal(rome.getWealth());

        rome.attach(wealth);
        rome.detach(wealth);
        rome.setWealth(1000000);
        assertEquals(wealth.evaluate(), false);
    }
}
