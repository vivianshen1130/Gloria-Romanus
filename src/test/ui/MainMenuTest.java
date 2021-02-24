package test.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import unsw.gloriaromanus.ui.mainMenu.*;

public class MainMenuTest {
    @Test
    /**
     * Test that the constructor extracts all the factions and stores them into a
     * list of strings
     */
    public void testInitialiseFactions() {
        MainMenu menu = new MainMenu();
        List<String> allFactions = new ArrayList<String>();
        assertNotEquals(allFactions, menu.getAllFactionsList());
        allFactions.add("Rome");
        allFactions.add("Gaul");
        allFactions.add("Celtic Briton");
        allFactions.add("Germania");
        allFactions.add("Carthaginian");
        allFactions.add("Spain");
        allFactions.add("Numidian");
        allFactions.add("Egyptians");
        allFactions.add("The Seleucid Empire");
        allFactions.add("People of Pontus");
        allFactions.add("Amenia");
        allFactions.add("Parthian");
        allFactions.add("Greek City States");
        allFactions.add("Macedonian");
        allFactions.add("Thracian");
        allFactions.add("Dacians");

        assertEquals(allFactions, menu.getAllFactionsList());
    }

    @Test
    /**
     * Test that the constructor extracts all the provinces and stores them into a
     * list of strings
     */
    public void testInitialiseProvince() {
        MainMenu menu = new MainMenu();
        List<String> allProvinces = new ArrayList<String>();
        assertNotEquals(allProvinces, menu.getAllProvincesList());

        allProvinces.add("Achaia");
        allProvinces.add("Aegyptus");
        allProvinces.add("Africa Proconsularis");
        allProvinces.add("Alpes Cottiae");
        allProvinces.add("Alpes Graiae et Poeninae");
        allProvinces.add("Alpes Maritimae");
        allProvinces.add("Aquitania");
        allProvinces.add("Arabia");
        allProvinces.add("Armenia Mesopotamia");
        allProvinces.add("Asia");
        allProvinces.add("Baetica");
        allProvinces.add("Belgica");
        allProvinces.add("Bithynia et Pontus");
        allProvinces.add("Britannia");
        allProvinces.add("Cilicia");
        allProvinces.add("Creta et Cyrene");
        allProvinces.add("Cyprus");
        allProvinces.add("Dacia");
        allProvinces.add("Dalmatia");
        allProvinces.add("Galatia et Cappadocia");
        allProvinces.add("Germania Inferior");
        allProvinces.add("Germania Superior");
        allProvinces.add("I");
        allProvinces.add("II");
        allProvinces.add("III");
        allProvinces.add("IV");
        allProvinces.add("IX");
        allProvinces.add("Iudaea");
        allProvinces.add("Lugdunensis");
        allProvinces.add("Lusitania");
        allProvinces.add("Lycia et Pamphylia");
        allProvinces.add("Macedonia");
        allProvinces.add("Mauretania Caesariensis");
        allProvinces.add("Mauretania Tingitana");
        allProvinces.add("Moesia Inferior");
        allProvinces.add("Moesia Superior");
        allProvinces.add("Narbonensis");
        allProvinces.add("Noricum");
        allProvinces.add("Numidia");
        allProvinces.add("Pannonia Inferior");
        allProvinces.add("Pannonia Superior");
        allProvinces.add("Raetia");
        allProvinces.add("Sardinia et Corsica");
        allProvinces.add("Sicilia");
        allProvinces.add("Syria");
        allProvinces.add("Tarraconensis");
        allProvinces.add("Thracia");
        allProvinces.add("V");
        allProvinces.add("VI");
        allProvinces.add("VII");
        allProvinces.add("VIII");
        allProvinces.add("X");
        allProvinces.add("XI");

        assertEquals(allProvinces, menu.getAllProvincesList());
    }
}
