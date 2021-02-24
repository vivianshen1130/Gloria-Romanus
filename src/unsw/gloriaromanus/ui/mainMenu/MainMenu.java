package unsw.gloriaromanus.ui.mainMenu;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Math;

import org.json.JSONObject;

public class MainMenu {
    private int numPlayers = 4;
    private List<String> playingFactions = new ArrayList<String>();
    private String allFactions;
    private List<String> allFactionsList;
    private String allProvinces;
    private List<String> allProvincesList;

    public MainMenu() {
        initialiseAllFactionsList();
        initialiseAllProvincesList();
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public List<String> getPlayingFactions() {
        return playingFactions;
    }

    public List<String> getAllFactionsList() {
        return allFactionsList;
    }

    public List<String> getAllProvincesList() {
        return allProvincesList;
    }

    public void initialiseAllProvincesList() {
        allProvinces = "Achaia, Aegyptus, Africa Proconsularis, Alpes Cottiae, Alpes Graiae et Poeninae, "
                + "Alpes Maritimae, Aquitania, Arabia, Armenia Mesopotamia, Asia, Baetica, Belgica, "
                + "Bithynia et Pontus, Britannia, Cilicia, Creta et Cyrene, Cyprus, Dacia, Dalmatia, "
                + "Galatia et Cappadocia, Germania Inferior, Germania Superior, I, II, III, IV, IX, "
                + "Iudaea, Lugdunensis, Lusitania, Lycia et Pamphylia, Macedonia, Mauretania Caesariensis, "
                + "Mauretania Tingitana, Moesia Inferior, Moesia Superior, Narbonensis, Noricum, Numidia, "
                + "Pannonia Inferior, Pannonia Superior, Raetia, Sardinia et Corsica, Sicilia, Syria, "
                + "Tarraconensis, Thracia, V, VI, VII, VIII, X, XI, ";
        allProvincesList = new ArrayList<String>(Arrays.asList(allProvinces.split(", ")));
        // System.out.println(allProvincesList);
    }

    public void initialiseAllFactionsList() {
        allFactions = "Rome, Gaul, Celtic Briton, Germania, Carthaginian, Spain, Numidian, Egyptians, "
                + "The Seleucid Empire, People of Pontus, Amenia, Parthian, Greek City States, "
                + "Macedonian, Thracian, Dacians, ";
        allFactionsList = new ArrayList<String>(Arrays.asList(allFactions.split(", ")));
        // System.out.println(allFactionsList);

        playingFactions.add("Rome");
        playingFactions.add("Gaul");
        playingFactions.add("Celtic Briton");
        playingFactions.add("D");
        playingFactions.add("E");
        playingFactions.add("F");
    }

    public JSONObject distributeProvinces(int num, boolean test, List<String> allProvincesList) {
        // initialiseAllProvincesList();
        JSONObject distribution = new JSONObject();

        // Create a random start number so that each game the players will be assigned
        // new provinces
        // Random rand = new Random();
        // int start = rand.nextInt(allProvincesList.size());
        int start = 50;
        int loop = (int) Math.ceil((double) allProvincesList.size() / (double) numPlayers);

        for (int i = 0; i < numPlayers; i++) {
            List<String> newList = new ArrayList<String>();
            // List<String> subList = new ArrayList<String>();
            newList.addAll(allProvincesList);
            List<String> provinces;

            int lower = (start + loop * (i)) % allProvincesList.size();
            int upper = lower + loop;
            // int upper = start + loop * (i + 1);
            boolean end = false;
            if (upper > allProvincesList.size()) {
                upper = upper % allProvincesList.size();
                end = true;
            }
            if (i == 0) {
                if (end) {
                    System.out.println("1st if");
                    provinces = newList.subList(lower, allProvincesList.size());
                    provinces.addAll(newList.subList(0, upper));
                } else {
                    System.out.println("1st else");
                    provinces = allProvincesList.subList(start, upper);
                }
                // System.out.println("size: " + allProvincesList.size() + ", loop: " + loop +
                // ", i: " + i + ", lower: "
                // + lower + ", upper: " + upper);
            } else if (i == numPlayers - 1) {
                // provinces = newList.subList(lower, allProvincesList.size());
                // subList = newList.subList(0, start);
                // provinces.addAll(subList);
                // provinces.addAll(newList.subList(0, start));
                if (end) {
                    System.out.println("last if");
                    provinces = newList.subList(lower, allProvincesList.size());
                    List<String> sub = newList.subList(0, upper);
                    System.out.println(sub.size());
                    provinces.addAll(sub);
                } else {
                    System.out.println("last else");
                    provinces = allProvincesList.subList(lower, upper - 1);
                }
                // System.out.println("size: " + allProvincesList.size() + ", loop: " + loop +
                // ", i: " + i + ", lower: "
                // + lower + ", upper: " + upper + ", start: " + start);
            } else {
                if (end) {
                    System.out.println("og if");
                    provinces = newList.subList(lower, allProvincesList.size());
                    List<String> sub = newList.subList(0, upper);
                    System.out.println(sub.size());
                    provinces.addAll(sub);
                } else {
                    System.out.println("og else");
                    provinces = allProvincesList.subList(lower, upper);
                }
                // System.out.println("size: " + allProvincesList.size() + ", loop: " + loop +
                // ", i: " + i + ", lower: "
                // + lower + ", upper: " + upper);
            }

            // System.out.println(playingFactions.get(i) + provinces.size());
            distribution.put(playingFactions.get(i), provinces);
            System.out.println("Faction: " + playingFactions.get(i) + ", Assigned: " + provinces.size() + ", Total: "
                    + allProvincesList.size() + ", LIST: " + provinces + "\n");
        }

        String filename = "province_ownership.json";
        File file = new File("src/unsw/gloriaromanus/" + filename);
        if (test) {
            // Different pathing for test
            file = new File("bin/unsw/gloriaromanus/" + filename);
        }

        try {
            file.createNewFile();
            FileWriter fr = new FileWriter(file);
            fr.write(distribution.toString() + "\n");
            fr.flush();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return distribution;
    }

    public static void main(String[] args) {
        MainMenu main = new MainMenu();
        main.initialiseAllFactionsList();
        // main.initialiseAllProvincesList();
        main.distributeProvinces(4, false, main.getAllProvincesList());
    }
}
