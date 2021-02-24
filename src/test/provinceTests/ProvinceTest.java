package test.provinceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import unsw.gloriaromanus.province.Province;
import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.unit.artillery.Artillery;
import unsw.gloriaromanus.unit.cavalry.Chariot;
import unsw.gloriaromanus.unit.infantry.Berserker;
import unsw.gloriaromanus.unit.infantry.Druid;

import unsw.gloriaromanus.faction.Faction;

public class ProvinceTest {
    @Test
    /**
     * Test for creating a province and ensuring all values are intialiseed
     * appropriately
     */
    public void testInitialise() {
        Faction Rome = new Faction("Rome");
        Province A = new Province(Rome, "A", true);
        assertEquals(A.getFaction(), Rome);
        assertEquals(A.getName(), "A");
        assertEquals(A.getLandlocked(), true);
        assertEquals(A.getWealth(), 0);
        assertEquals(A.getTreasury(), 0);
        assertEquals(A.getTaxRate(), "low");
        assertEquals(A.getUnits(), new ArrayList<Unit>());
        assertEquals(A.getAdjacent(), new ArrayList<Province>());
    }

    @Test
    /**
     * Test for adding a unit will add it to the province list of units, change the
     * unit to be associated with the province and the faction it belongs to
     */
    public void testAddUnit() {
        Faction Rome = new Faction("Rome");
        Province A = new Province(Rome, "A", true);
        Unit unit = new Artillery(1);
        List<Unit> units = new ArrayList<Unit>();
        assertEquals(unit.getFaction(), null);
        assertEquals(unit.getProvince(), null);
        A.addUnit(unit);
        units.add(unit);
        assertEquals(unit.getFaction(), Rome);
        assertEquals(unit.getProvince(), A);
        assertEquals(A.getUnits(), units);
        assertEquals(1, A.getUnits().size());
    }

    @Test
    /**
     * Test for removng a unit will remove it from the province list of units,
     * change the unit's province and faction association to null
     */
    public void testRemoveUnit() {
        Faction Rome = new Faction("Rome");
        Province A = new Province(Rome, "A", true);
        Unit unit1 = new Artillery(1);
        Unit unit2 = new Artillery(2);
        List<Unit> units = new ArrayList<Unit>();
        A.addUnit(unit1);
        units.add(unit1);
        A.addUnit(unit2);
        units.add(unit2);
        A.removeUnit(unit1);
        units.remove(unit1);

        assertEquals(unit1.getFaction(), null);
        assertEquals(unit1.getProvince(), null);
        assertEquals(unit2.getFaction(), Rome);
        assertEquals(unit2.getProvince(), A);
        assertEquals(A.getUnits(), units);
    }

    @Test
    /**
     * Test for how low tax rate will impact the province wealth and treasury
     */
    public void testLowTaxRate() {
        Faction Rome = new Faction("Rome");
        Province A = new Province(Rome, "A", true);
        // Default is low tax rate
        A.setWealth(100);
        A.setTreasury(10);
        double wealth = A.getWealth();
        A.calculateWealth();
        assertEquals(A.getWealth(), wealth + 10);
        assertEquals(A.getTreasury(), wealth * 0.1);
    }

    @Test
    /**
     * Test for how normal tax rate will impact the province treasury
     */
    public void testNormalTaxRate() {
        Faction Rome = new Faction("Rome");
        Province A = new Province(Rome, "A", true);
        A.setTaxRate("normal");
        A.setWealth(100);
        A.setTreasury(10);
        double wealth = A.getWealth();
        A.calculateWealth();
        assertEquals(A.getWealth(), wealth);
        assertEquals(A.getTreasury(), wealth * 0.15);
    }

    @Test
    /**
     * Test for how high tax rate will impact the province wealth and treasury
     */
    public void testHighTaxRate() {
        Faction Rome = new Faction("Rome");
        Province A = new Province(Rome, "A", true);
        A.setTaxRate("high");
        A.setWealth(100);
        A.setTreasury(10);
        double wealth = A.getWealth();
        A.calculateWealth();
        assertEquals(A.getWealth(), wealth - 10);
        assertEquals(A.getTreasury(), wealth * 0.2);
    }

    @Test
    /**
     * Test for how very high tax rate will impact the province wealth, treasury and
     * individual unit morale
     */
    public void testVeryHighTaxRate() {
        Faction Rome = new Faction("Rome");
        Province A = new Province(Rome, "A", true);
        A.setTaxRate("very high");
        A.setWealth(100);
        A.setTreasury(10);
        double wealth = A.getWealth();
        Unit unit = new Chariot(-1);
        A.addUnit(unit);
        double morale = unit.getMorale();
        A.calculateWealth();
        assertEquals(A.getWealth(), wealth - 30);
        assertEquals(A.getTreasury(), wealth * 0.25);
        assertEquals(unit.getMorale(), morale - 1);
    }

    @Test
    /**
     * Test for obtaining the correct number of Druit units within the province list
     * of units
     */
    public void testDruidInstances() {
        Faction Rome = new Faction("Rome");
        Province A = new Province(Rome, "A", true);
        Unit unit1 = new Druid(-1);
        unit1.setAvailable(true);
        Unit unit2 = new Druid(-1);
        unit2.setAvailable(true);
        Unit unit3 = new Chariot(-1);
        Unit unit4 = new Artillery(-1);
        A.addUnit(unit1);
        A.addUnit(unit2);
        A.addUnit(unit3);
        A.addUnit(unit4);
        assertEquals(2, A.getDruitUnits());
    }

    @Test
    /**
     * Test for if an Artillery unit can move to neighbouring province. Artillery
     * units can only move to immediate neighbours since it only has 4 movement
     * points
     */
    public void testMovementArtilleryA() {
        Faction Rome = new Faction("Rome");
        Faction Gaul = new Faction("Gaul");
        Province A = new Province(Rome, "A", false);
        Province B = new Province(Rome, "B", false);
        Province C = new Province(Gaul, "C", false);
        Province D = new Province(Gaul, "D", false);
        Province E = new Province(Gaul, "E", false);
        Province F = new Province(Rome, "F", false);
        A.addAdjacent(B);
        B.addAdjacent(A);
        B.addAdjacent(C);
        B.addAdjacent(D);
        C.addAdjacent(B);
        C.addAdjacent(D);
        D.addAdjacent(B);
        D.addAdjacent(C);
        D.addAdjacent(E);
        E.addAdjacent(D);
        Unit unit = new Artillery(1);
        unit.setAvailable(true);
        assertEquals(A.canMoveTroops(A, unit, true), true);
        assertEquals(A.canMoveTroops(B, unit, true), true);
        assertEquals(A.canMoveTroops(C, unit, true), false);
        assertEquals(A.canMoveTroops(D, unit, true), false);
        assertEquals(A.canMoveTroops(E, unit, true), false);
        assertEquals(A.canMoveTroops(F, unit, true), false);
    }

    @Test
    /**
     * Test for if an Infantry unit can move to neighbouring province. Infantry
     * units can move to immediate neighbours and their neighbours since it has 10
     * movement points
     */
    public void testMovementInfantryA() {
        Faction Rome = new Faction("Rome");
        Faction Gaul = new Faction("Gaul");
        Province A = new Province(Rome, "A", false);
        Province B = new Province(Rome, "B", false);
        Province C = new Province(Gaul, "C", false);
        Province D = new Province(Gaul, "D", false);
        Province E = new Province(Gaul, "E", false);
        Province F = new Province(Rome, "F", false);
        A.addAdjacent(B);
        B.addAdjacent(A);
        B.addAdjacent(C);
        B.addAdjacent(D);
        C.addAdjacent(B);
        C.addAdjacent(D);
        D.addAdjacent(B);
        D.addAdjacent(C);
        D.addAdjacent(E);
        E.addAdjacent(D);
        Unit unit = new Berserker(1);
        unit.setAvailable(true);
        assertEquals(A.canMoveTroops(A, unit, true), true);
        assertEquals(A.canMoveTroops(B, unit, true), true);
        assertEquals(A.canMoveTroops(C, unit, true), true);
        assertEquals(A.canMoveTroops(D, unit, true), true);
        assertEquals(A.canMoveTroops(E, unit, true), false);
        assertEquals(A.canMoveTroops(F, unit, true), false);
    }

    @Test
    /**
     * Test for if a Cavalry unit can move to neighbouring province. Cavalry units
     * can move to immediate neighbours and their neighbours and their neighbours
     * since it has 15 movement points
     */
    public void testMovementCavalryA() {
        Faction Rome = new Faction("Rome");
        Faction Gaul = new Faction("Gaul");
        Province A = new Province(Rome, "A", false);
        Province B = new Province(Rome, "B", false);
        Province C = new Province(Gaul, "C", false);
        Province D = new Province(Gaul, "D", false);
        Province E = new Province(Gaul, "E", false);
        Province F = new Province(Rome, "F", false);
        A.addAdjacent(B);
        B.addAdjacent(A);
        B.addAdjacent(C);
        B.addAdjacent(D);
        C.addAdjacent(B);
        C.addAdjacent(D);
        D.addAdjacent(B);
        D.addAdjacent(C);
        D.addAdjacent(E);
        E.addAdjacent(D);
        Unit unit = new Chariot(1);
        unit.setAvailable(true);
        assertEquals(A.canMoveTroops(A, unit, true), true);
        assertEquals(A.canMoveTroops(B, unit, true), true);
        assertEquals(A.canMoveTroops(C, unit, true), true);
        assertEquals(A.canMoveTroops(D, unit, true), true);
        assertEquals(A.canMoveTroops(E, unit, true), true);
        assertEquals(A.canMoveTroops(F, unit, true), false);
    }

    @Test
    /**
     * Test for not being able to move a unit to another province if it is currently
     * unavailable
     */
    public void testMovementUnavailableUnit() {
        Faction Rome = new Faction("Rome");
        Faction Gaul = new Faction("Gaul");
        Province A = new Province(Rome, "A", false);
        Province B = new Province(Rome, "B", false);
        Province C = new Province(Gaul, "C", false);
        Province D = new Province(Gaul, "D", false);
        Province E = new Province(Gaul, "E", false);
        Province F = new Province(Rome, "F", false);
        A.addAdjacent(B);
        B.addAdjacent(A);
        B.addAdjacent(C);
        B.addAdjacent(D);
        C.addAdjacent(B);
        C.addAdjacent(D);
        D.addAdjacent(B);
        D.addAdjacent(C);
        D.addAdjacent(E);
        E.addAdjacent(D);
        Unit unit = new Chariot(1);
        assertEquals(unit.isAvailable(), false);
        assertEquals(A.canMoveTroops(A, unit, true), false);
        assertEquals(A.canMoveTroops(B, unit, true), false);
        assertEquals(A.canMoveTroops(C, unit, true), false);
        assertEquals(A.canMoveTroops(D, unit, true), false);
        assertEquals(A.canMoveTroops(E, unit, true), false);
        assertEquals(A.canMoveTroops(F, unit, true), false);
    }

    @Test
    /**
     * Test that it will only add unique provinces to the province adjacent list
     */
    public void testAddAdjacentDuplicate() {
        Faction Rome = new Faction("Rome");
        Faction Gaul = new Faction("Gaul");
        Province A = new Province(Rome, "A", false);
        Province B = new Province(Rome, "B", false);
        Province C = new Province(Gaul, "C", false);
        A.addAdjacent(B);
        A.addAdjacent(C);
        List<Province> beforeDup = A.getAdjacent();
        A.addAdjacent(B);
        assertEquals(A.getAdjacent(), beforeDup);
    }

    @Test
    /**
     * Test that getAvailable units will only count available units
     */
    public void testAvailableUnits() {
        Faction Rome = new Faction("Rome");
        Province A = new Province(Rome, "A", false);
        Unit unit1 = new Artillery(1);
        unit1.setAvailable(true);
        A.addUnit(unit1);
        Unit unit2 = new Artillery(1);
        unit2.setAvailable(true);
        A.addUnit(unit2);
        Unit unit3 = new Artillery(1);
        A.addUnit(unit3);
        assertEquals(2, A.getAvailableUnits().size());
    }

    @Test
    /**
     * Test that getNumTroops units will only count troops from available units
     */
    public void testGetNumTroops() {
        Faction Rome = new Faction("Rome");
        Province A = new Province(Rome, "A", false);
        Unit unit1 = new Artillery(1);
        unit1.setAvailable(true);
        A.addUnit(unit1);
        Unit unit2 = new Artillery(1);
        unit2.setAvailable(true);
        A.addUnit(unit2);
        Unit unit3 = new Artillery(1);
        A.addUnit(unit3);
        assertEquals(100, A.getNumTroops());
    }

    @Test
    /**
     * Test that the province toString method will be the same if the province
     * details are identical
     */
    public void testProvinceToString() {
        Faction Rome = new Faction("Rome");
        Province A = new Province(Rome, "A", false);
        Province B = new Province(Rome, "A", false);
        Province C = new Province(Rome, "C", false);
        assertEquals(A.toString(), B.toString());
        assertNotEquals(A.toString(), C.toString());
        assertEquals(B.toString(), A.toString());
        assertNotEquals(B.toString(), C.toString());
        assertNotEquals(C.toString(), A.toString());
        assertNotEquals(C.toString(), A.toString());
    }

    @Test
    /**
     * Test the equals override in province
     */
    public void testProvinceIsEquals() {
        Faction Rome = new Faction("Rome");
        Faction Gaul = new Faction("Gaul");
        Province a = new Province(Rome, "A", true);
        Province b = new Province(Rome, "A", true);
        Province c = new Province(Gaul, "C", true);
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
    public void testProvinceSave() {
        Faction Rome = new Faction("Rome");
        Province a = new Province(Rome, "A", true);
        JSONObject provinceDetails = new JSONObject();
        a.setWealth(100);
        a.setTreasury(200);
        a.setTaxRate("very high");
        provinceDetails.put("type", "province");
        provinceDetails.put("name", "A");
        provinceDetails.put("faction", "Rome");
        provinceDetails.put("landlocked", true);
        provinceDetails.put("wealth", 100);
        provinceDetails.put("taxRate", "very high");
        provinceDetails.put("treasury", 200);

        JSONObject Saved = a.saveGame();
        assertEquals(Saved.toString(), provinceDetails.toString());
    }
}
