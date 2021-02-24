package test.factionTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;

public class FactionTest {
    @Test
    /**
     * Test for creating a faction and ensuring all values are intialiseed
     * appropriately
     */
    public void testInitialisation() {
        Faction Rome = new Faction("Rome");
        assertEquals(Rome.getName(), "Rome");
        assertEquals(Rome.getWealth(), 5000);
        assertEquals(Rome.getTreasury(), 1000);
        assertEquals(Rome.isAllConquered(), false);
        assertEquals(Rome.getProvincesOwned(), new ArrayList<Province>());
    }

    @Test
    /**
     * Test that conquest calculation will set the isAllConquered boolean to true
     * when the faction has conquered/owned all existing provinces
     */
    public void testConquest() {
        Faction Rome = new Faction("Rome");
        Faction Gaul = new Faction("Gaul");
        Province A = new Province(Rome, "A", true);
        Province B = new Province(Rome, "B", false);
        Province C = new Province(Gaul, "C", false);
        List<Province> allProvinces = new ArrayList<Province>();
        List<Province> RomeOwned = new ArrayList<Province>();
        List<Province> GaulOwned = new ArrayList<Province>();
        Rome.addProvince(A);
        RomeOwned.add(A);
        allProvinces.add(A);
        Rome.addProvince(B);
        RomeOwned.add(B);
        allProvinces.add(B);
        Gaul.addProvince(C);
        GaulOwned.add(C);
        allProvinces.add(C);

        Rome.conquestCalculation(allProvinces);
        Gaul.conquestCalculation(allProvinces);
        assertEquals(Rome.isAllConquered(), false);
        assertEquals(Gaul.isAllConquered(), false);

        Rome.addProvince(C);
        RomeOwned.add(C);
        GaulOwned.remove(C);
        Rome.conquestCalculation(allProvinces);
        Gaul.conquestCalculation(allProvinces);
        assertEquals(Rome.isAllConquered(), true);
        assertEquals(Gaul.isAllConquered(), false);
    }

    @Test
    /**
     * Test faction wealth is the summation of all the province wealth it owns
     */
    public void testWealth() {
        Faction Rome = new Faction("Rome");
        Faction Gaul = new Faction("Gaul");
        Province A = new Province(Rome, "A", true);
        Province B = new Province(Rome, "B", false);
        Province C = new Province(Gaul, "C", false);
        double startRomeWealth = Rome.getWealth();
        double startGaulWealth = Gaul.getWealth();
        A.setWealth(100);
        B.setWealth(200);
        C.setWealth(300);
        Rome.wealthCalculation();
        assertEquals(Rome.getWealth(), startRomeWealth + 100 + 200);
        Gaul.wealthCalculation();
        assertEquals(Gaul.getWealth(), startGaulWealth + 300);
    }

    @Test
    /**
     * Test faction treasury balance is the summation of all the province treasury
     * balance it owns
     */
    public void testTreasury() {
        Faction Rome = new Faction("Rome");
        Faction Gaul = new Faction("Gaul");
        Province A = new Province(Rome, "A", true);
        Province B = new Province(Rome, "B", false);
        Province C = new Province(Gaul, "C", false);
        double startRomeTreasury = Rome.getTreasury();
        double startGaulTreasury = Gaul.getTreasury();
        A.setTreasury(100);
        B.setTreasury(200);
        C.setTreasury(300);
        Rome.treasuryCalculation();
        assertEquals(Rome.getTreasury(), startRomeTreasury + 100 + 200);
        Gaul.treasuryCalculation();
        assertEquals(Gaul.getTreasury(), startGaulTreasury + 300);
    }

    @Test
    /**
     * Test for only add unique provinces to the faction list of provinces
     */
    public void testAddProvince() {
        Faction Rome = new Faction("Rome");
        Faction Gaul = new Faction("Gaul");
        Province A = new Province(Rome, "A", true);
        Province B = new Province(Rome, "B", false);
        Province C = new Province(Gaul, "C", false);
        List<Province> provincesA = new ArrayList<Province>();
        List<Province> provincesC = new ArrayList<Province>();
        Gaul.addProvince(C);
        provincesC.add(C);
        assertEquals(Gaul.getProvincesOwned(), provincesC);
        assertEquals(C.getFaction(), Gaul);

        Rome.addProvince(A);
        provincesA.add(A);
        Rome.addProvince(B);
        provincesA.add(B);
        Rome.addProvince(C);
        provincesA.add(C);

        assertEquals(Rome.getProvincesOwned(), provincesA);
        assertEquals(Gaul.getProvincesOwned(), new ArrayList<Province>());
        assertEquals(A.getFaction(), Rome);
        assertEquals(B.getFaction(), Rome);
        assertEquals(C.getFaction(), Rome);
    }

    @Test
    /**
     * Test the equals override in faction
     */
    public void testFactionIsEquals() {
        Faction a = new Faction("Rome");
        Faction b = new Faction("Rome");
        Faction c = new Faction("Celtic");
        Object d = new Object();
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
     * Test the equals override in faction
     */
    public void testFactionSave() {
        Faction a = new Faction("Rome");
        JSONObject factionDetails = new JSONObject();
        a.setWealth(100);
        a.setTreasury(200);
        a.setAllConquered(true);
        factionDetails.put("type", "faction");
        factionDetails.put("name", "Rome");
        factionDetails.put("wealth", 100);
        factionDetails.put("treasury", 200);
        factionDetails.put("allConquered", true);

        JSONObject Saved = a.saveGame();
        assertEquals(Saved.toString(), factionDetails.toString());
    }
}
