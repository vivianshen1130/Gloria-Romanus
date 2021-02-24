package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.security.AllPermission;
import java.util.ArrayList;
import java.util.List;

import unsw.gloriaromanus.*;
import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.province.Province;
import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.unit.artillery.Artillery;
import unsw.gloriaromanus.unit.cavalry.Chariot;
import unsw.gloriaromanus.unit.cavalry.Elephant;
import unsw.gloriaromanus.unit.cavalry.HorseArcher;
import unsw.gloriaromanus.unit.cavalry.Lancer;
import unsw.gloriaromanus.unit.cavalry.MeleeCavalry;
import unsw.gloriaromanus.unit.infantry.Berserker;
import unsw.gloriaromanus.unit.infantry.Druid;
import unsw.gloriaromanus.unit.infantry.Hoplite;
import unsw.gloriaromanus.unit.infantry.JavelinSkirmisher;
import unsw.gloriaromanus.unit.infantry.MeleeInfantry;
import unsw.gloriaromanus.unit.infantry.MissileInfantry;
import unsw.gloriaromanus.unit.infantry.Pikeman;
import unsw.gloriaromanus.unit.infantry.Spearman;

public class GloriaRomanusControllerTest {
    @Test
    /**
     * Test only add unique factions to the master list
     */
    public void testAddFaction() {
        GloriaRomanusController controller = new GloriaRomanusController();
        Faction Rome = new Faction("Rome");
        Faction Gaul = new Faction("Gaul");
        List<Faction> allFactions = new ArrayList<Faction>();

        controller.addFaction(Rome);
        allFactions.add(Rome);
        controller.addFaction(Gaul);
        allFactions.add(Gaul);
        controller.addFaction(Rome);
        allFactions.add(Rome);

        assertNotEquals(controller.getAllFactions(), allFactions);

        allFactions = new ArrayList<Faction>();
        allFactions.add(Rome);
        allFactions.add(Gaul);
        assertEquals(controller.getAllFactions(), allFactions);
    }

    @Test
    /**
     * Test getting all unique factions created
     */
    public void testGetAllFactions() {
        GloriaRomanusController controller = new GloriaRomanusController();
        Faction Rome = new Faction("Rome");
        Faction Gaul = new Faction("Gaul");
        List<Faction> allFactions = new ArrayList<Faction>();

        controller.addFaction(Rome);
        allFactions.add(Rome);
        controller.addFaction(Gaul);
        allFactions.add(Gaul);
        controller.addFaction(Rome);

        assertEquals(controller.getAllFactions(), allFactions);
    }

    @Test
    /**
     * Test only add unique provinces to the master list
     */
    public void testAddProvince() {
        GloriaRomanusController controller = new GloriaRomanusController();
        Faction Rome = new Faction("Rome");
        Province A = new Province(Rome, "A", false);
        Province B = new Province(Rome, "B", true);
        List<Province> allProvinces = new ArrayList<Province>();

        controller.addProvince(A);
        allProvinces.add(A);
        controller.addProvince(B);
        allProvinces.add(B);
        controller.addProvince(A);
        allProvinces.add(A);

        assertNotEquals(controller.getAllProvinces(), allProvinces);

        allProvinces = new ArrayList<Province>();
        allProvinces.add(A);
        allProvinces.add(B);
        assertEquals(controller.getAllProvinces(), allProvinces);
    }

    @Test
    /**
     * Test getting all unique provinces created
     */
    public void testGetAllProvinces() {
        GloriaRomanusController controller = new GloriaRomanusController();
        Faction Rome = new Faction("Rome");
        Faction Gaul = new Faction("Gaul");
        Province A = new Province(Rome, "A", false);
        Province B = new Province(Rome, "B", true);
        Province C = new Province(Gaul, "C", false);
        List<Province> allProvinces = new ArrayList<Province>();

        controller.addProvince(A);
        allProvinces.add(A);
        controller.addProvince(B);
        allProvinces.add(B);
        controller.addProvince(C);
        allProvinces.add(C);

        assertEquals(controller.getAllProvinces(), allProvinces);
    }

    @Test
    /**
     * Test get province by name
     */
    public void testGetProvinceByName() {
        GloriaRomanusController controller = new GloriaRomanusController();
        Faction Rome = new Faction("Rome");
        Faction Gaul = new Faction("Gaul");
        Province A = new Province(Rome, "A", false);
        Province B = new Province(Rome, "B", true);
        Province C = new Province(Gaul, "C", false);
        List<Province> allProvinces = new ArrayList<Province>();

        controller.addProvince(A);
        allProvinces.add(A);
        controller.addProvince(B);
        allProvinces.add(B);
        controller.addProvince(C);
        allProvinces.add(C);

        assertEquals(controller.getProvinceByName("A"), A);
        assertEquals(controller.getProvinceByName("B"), B);
        assertEquals(controller.getProvinceByName("C"), C);
    }

    @Test
    /**
     * Test for not able to recruit soldiers unless the faction's treasury can
     * afford it
     */
    public void testRecruitSoldiersCost() {
        GloriaRomanusController controller = new GloriaRomanusController();
        Faction Rome = new Faction("Rome");
        Province A = new Province(Rome, "A", true);
        Artillery unit = new Artillery(1);
        controller.addFaction(Rome);
        controller.getFactionByName("Rome").setTreasury(0);
        assertEquals(controller.canRecruit(Rome, A, unit), false);
        controller.getFactionByName("Rome").setTreasury(100);
        assertEquals(controller.canRecruit(Rome, A, unit), true);
    }

    @Test
    /**
     * Test for not able to recruit soldiers if the faction is already training 2
     * units
     */
    public void testRecruitSoldiersTrainingMaxed() {
        GloriaRomanusController controller = new GloriaRomanusController();
        Faction Rome = new Faction("Rome");
        Province A = new Province(Rome, "A", true);
        Artillery unit1 = new Artillery(1);
        Artillery unit2 = new Artillery(2);
        Artillery unit3 = new Artillery(3);
        controller.addFaction(Rome);
        controller.addProvince(A);
        controller.getFactionByName("Rome").setTreasury(100);
        controller.getProvinceByName("A").addTrainingUnit(unit1);
        assertEquals(controller.canRecruit(Rome, A, unit2), true);
        controller.getProvinceByName("A").addTrainingUnit(unit1);
        assertEquals(controller.canRecruit(Rome, A, unit3), false);
        controller.getProvinceByName("A").removeTrainingUnit(unit1);
        assertEquals(controller.canRecruit(Rome, A, unit3), true);
    }

    @Test
    /**
     * Test for not able to recruit berserkers if the faction is not either Gaul,
     * Celtic Briton or Germania
     */
    public void testRecruitSoldiersBerserker() {
        GloriaRomanusController controller = new GloriaRomanusController();
        Faction Rome = new Faction("Rome");
        Faction CelticBriton = new Faction("Celtic Briton");
        Faction Gaul = new Faction("Gaul");
        Faction Germania = new Faction("Germania");
        Province A = new Province(Rome, "A", true);
        Province B = new Province(CelticBriton, "B", true);
        Province C = new Province(Gaul, "C", true);
        Province D = new Province(Germania, "D", true);
        Berserker unit1 = new Berserker(1);
        Berserker unit2 = new Berserker(2);
        Berserker unit3 = new Berserker(3);
        controller.addFaction(Rome);
        controller.addFaction(CelticBriton);
        controller.addFaction(Gaul);
        controller.addFaction(Germania);
        controller.addProvince(A);
        controller.addProvince(B);
        controller.addProvince(C);
        controller.addProvince(D);
        controller.getFactionByName("Rome").setTreasury(100);
        controller.getFactionByName("Celtic Briton").setTreasury(100);
        controller.getFactionByName("Gaul").setTreasury(100);
        controller.getFactionByName("Germania").setTreasury(100);

        assertEquals(controller.canRecruit(Rome, A, unit1), false);
        assertEquals(controller.canRecruit(CelticBriton, B, unit1), true);
        assertEquals(controller.canRecruit(Gaul, B, unit2), true);
        assertEquals(controller.canRecruit(Germania, B, unit3), true);
    }

    @Test
    /**
     * Test for changing the unit's availabilities based on the turn to account for
     * their training time
     */
    public void testChangeTurns() {
        GloriaRomanusController controller = new GloriaRomanusController();

        controller.setTurnNumber(1);
        assertEquals(controller.getTurnNumber(), 1);
        controller.setTurnNumber(2);
        assertEquals(controller.getTurnNumber(), 2);
    }

    @Test
    /**
     * Test for changing the unit's availabilities based on the turn to account for
     * their training time
     */
    public void testChangeAvailableUnit() {
        GloriaRomanusController controller = new GloriaRomanusController();
        Faction Rome = new Faction("Rome");
        Province province = new Province(Rome, "A", false);
        controller.addProvince(province);
        Artillery unit = new Artillery(1);
        province.addUnit(unit);

        controller.setTurnNumber(1);
        controller.makeUnitsAvailable();
        assertEquals(unit.isAvailable(), false);
        controller.setTurnNumber(2);
        controller.makeUnitsAvailable();
        assertEquals(unit.isAvailable(), true);
    }

    @Test
    /**
     * Test for resetting the state of the game
     */
    public void testResetState() {
        GloriaRomanusController controller = new GloriaRomanusController();
        controller.setTurnNumber(20);

        Faction Rome = new Faction("Rome");
        List<Faction> allFactions = new ArrayList<Faction>();
        controller.addFaction(Rome);
        allFactions.add(Rome);

        Province province = new Province(Rome, "A", false);
        List<Province> allProvinces = new ArrayList<Province>();
        Rome.addProvince(province);
        controller.addProvince(province);
        allProvinces.add(province);

        Artillery unit = new Artillery(1);
        List<Unit> allUnits = new ArrayList<Unit>();
        province.addUnit(unit);
        controller.addUnit(unit);
        allUnits.add(unit);

        assertEquals(controller.getTurnNumber(), 20);
        assertEquals(controller.getAllFactions(), allFactions);
        assertEquals(controller.getAllProvinces(), allProvinces);
        assertEquals(controller.getAllUnits(), allUnits);

        controller.resetState();

        assertEquals(controller.getTurnNumber(), 0);
        assertEquals(controller.getAllFactions(), new ArrayList<Faction>());
        assertEquals(controller.getAllProvinces(), new ArrayList<Province>());
        assertEquals(controller.getAllUnits(), new ArrayList<Unit>());
    }

    // TODO: ask tutor why it works locally but not with gradle?
    // @Test
    // /**
    // * Test for saving and loading file for turn
    // */
    // public void testTurnNumberSaveLoad() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // controller.setTurnNumber(20);
    // controller.saveGame("controllerTests.json", true);

    // controller.resetState();

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }

    // assertEquals(controller.getTurnNumber(), 20);
    // }

    // @Test
    // /**
    // * Test for saving and loading file for faction list
    // */
    // public void testFactionListSaveLoad() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Faction> allFactions = new ArrayList<Faction>();
    // Faction Rome = new Faction("Rome");
    // controller.addFaction(Rome);
    // allFactions.add(Rome);
    // controller.saveGame("controllerTests.json", true);

    // controller.resetState();
    // assertEquals(controller.getAllFactions(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllFactions(), allFactions);
    // }

    // @Test
    // public void testFactionListSaveLoadDetails() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Faction> allFactions = new ArrayList<Faction>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Province C = new Province(Rome, "C", false);
    // controller.addFaction(Rome);
    // allFactions.add(Rome);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addProvince(C);

    // controller.getFactionByName("Rome").setWealth(100);
    // controller.getFactionByName("Rome").setTreasury(20000);
    // controller.getFactionByName("Rome").setAllConquered(true);
    // Rome.setWealth(100);
    // Rome.setTreasury(20000);
    // Rome.setAllConquered(true);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllFactions(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllFactions(), allFactions);
    // }

    // @Test
    // public void testFactionNotEqual() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Faction> allFactions = new ArrayList<Faction>();
    // Faction Rome = new Faction("Rome");
    // Faction Gaul = new Faction("Gaul");
    // controller.addFaction(Rome);
    // allFactions.add(Gaul);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllFactions(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertNotEquals(controller.getAllFactions(), allFactions);
    // }

    // @Test
    // public void testProvinceListSaveLoad() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Province> allProvinces = new ArrayList<Province>();
    // Faction Rome = new Faction("Rome");
    // Province province = new Province(Rome, "A", true);
    // controller.addFaction(Rome);
    // controller.addProvince(province);
    // allProvinces.add(province);
    // controller.saveGame("controllerTests.json", true);

    // controller.resetState();
    // assertEquals(controller.getAllProvinces(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllProvinces(), allProvinces);
    // }

    // @Test
    // public void testProvinceListSaveLoadDetails() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Province> allProvinces = new ArrayList<Province>();
    // Faction Rome = new Faction("Rome");
    // Faction Gaul = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Province C = new Province(Rome, "C", false);
    // Unit unit = new Chariot(1);
    // controller.addFaction(Rome);
    // allProvinces.add(A);
    // allProvinces.add(B);
    // allProvinces.add(C);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addProvince(C);
    // controller.addUnit(unit);
    // C.addUnit(unit);

    // controller.getProvinceByName("A").setFaction(Gaul);
    // A.setFaction(Gaul);
    // controller.getProvinceByName("B").setWealth(100);
    // controller.getProvinceByName("B").setTreasury(20000);
    // B.setWealth(100);
    // B.setTreasury(20000);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllProvinces(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllProvinces(), allProvinces);
    // }

    // @Test
    // public void testProvinceNotEqual() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Province> allProvinces = new ArrayList<Province>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // controller.addFaction(Rome);
    // controller.addProvince(A);
    // allProvinces.add(B);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllProvinces(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertNotEquals(controller.getAllProvinces(), allProvinces);
    // }

    // @Test
    // public void testUnitListSaveLoad() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province province = new Province(Rome, "A", true);
    // controller.addFaction(Rome);
    // controller.addProvince(province);
    // Unit unit = new Chariot(1);
    // controller.addUnit(unit);
    // allUnits.add(unit);
    // province.addUnit(unit);
    // controller.saveGame("controllerTests.json", true);

    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testUnitListSaveLoadDetails() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new Chariot(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // Unit controllerUnit = controller.getAllUnits().get(0);
    // controllerUnit.setCost(100);
    // controllerUnit.setTurnsToRecruit(13);
    // controllerUnit.setRecruitmentStart(10);
    // controllerUnit.setMovementPoints(50);
    // controllerUnit.setRanged(true);
    // controllerUnit.setAvailable(true);
    // controllerUnit.setNumTroops(1000);
    // controllerUnit.setArmour(1);
    // controllerUnit.setMorale(2);
    // controllerUnit.setSpeed(20);
    // controllerUnit.setMeleeAttackDamage(200);
    // controllerUnit.setRangedAttackDamage(500);
    // controllerUnit.setDefenseSkill(1);
    // controllerUnit.setShieldDefense(2);

    // unit.setCost(100);
    // unit.setTurnsToRecruit(13);
    // unit.setRecruitmentStart(10);
    // unit.setMovementPoints(50);
    // unit.setRanged(true);
    // unit.setAvailable(true);
    // unit.setNumTroops(1000);
    // unit.setArmour(1);
    // unit.setMorale(2);
    // unit.setSpeed(20);
    // unit.setMeleeAttackDamage(200);
    // unit.setRangedAttackDamage(500);
    // unit.setDefenseSkill(1);
    // unit.setShieldDefense(2);
    // B.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testProcessArtillery() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new Artillery(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testProcessChariot() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new Chariot(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testProcessElephant() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new Elephant(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testProcessHorseArcher() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new HorseArcher(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testProcessLancer() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new Lancer(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testProcessMeleeCavalry() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new MeleeCavalry(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testProcessBerserker() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new Berserker(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testProcessDruid() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new Druid(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testProcessHoplite() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new Hoplite(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testProcessJavelinSkirmisher() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new JavelinSkirmisher(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testProcessMeleeInfantry() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new MeleeInfantry(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Unit>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testProcessMissileInfantry() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new MissileInfantry(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testProcessPikeman() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new Pikeman(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testProcessSpearman() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit = new Spearman(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit);
    // A.addUnit(unit);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertEquals(controller.getAllUnits(), allUnits);
    // }

    // @Test
    // public void testUnitNotEqual() {
    // GloriaRomanusController controller = new GloriaRomanusController();
    // List<Unit> allUnits = new ArrayList<Unit>();
    // Faction Rome = new Faction("Rome");
    // Province A = new Province(Rome, "A", true);
    // Province B = new Province(Rome, "B", false);
    // Unit unit1 = new MissileInfantry(1);
    // Unit unit2 = new MeleeInfantry(1);
    // controller.addFaction(Rome);
    // allUnits.add(unit2);
    // controller.addProvince(A);
    // controller.addProvince(B);
    // controller.addUnit(unit1);
    // A.addUnit(unit1);

    // controller.saveGame("controllerTests.json", true);
    // controller.resetState();
    // assertEquals(controller.getAllUnits(), new ArrayList<Faction>());

    // try {
    // controller.loadGame("controllerTests.json", true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // assertNotEquals(controller.getAllUnits(), allUnits);
    // }
}
