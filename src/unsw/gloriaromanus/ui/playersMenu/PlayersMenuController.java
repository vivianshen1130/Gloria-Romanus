package unsw.gloriaromanus.ui.playersMenu;

import java.io.File;
import java.io.FileWriter;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import java.lang.Math;

import org.json.JSONObject;

import unsw.gloriaromanus.GloriaRomanusApplication;
import unsw.gloriaromanus.music.MusicApplication;
import unsw.gloriaromanus.ui.mainMenu.MainMenuApplication;

public class PlayersMenuController {
    @FXML
    private Label text;
    @FXML
    private VBox container;
    @FXML
    private VBox players;
    @FXML
    private HBox player;
    @FXML
    private Label player_number;
    @FXML
    private ComboBox<String> faction;
    @FXML
    private VBox victory;
    @FXML
    private HBox buttons;
    @FXML
    private Button backbtn;
    @FXML
    private Button nextbtn;

    private int numPlayers;
    private int victoryType = -1;
    private List<ComboBox<String>> factionDetails = new ArrayList<ComboBox<String>>();
    private List<String> playingFactions = new ArrayList<String>();
    private List<ComboBox<String>> victoryDetails = new ArrayList<ComboBox<String>>();
    private List<String> playingVictories = new ArrayList<String>();
    private String allFactions;
    private List<String> allFactionsList;
    private String allProvinces;
    private List<String> allProvincesList;
    private List<String> allocatedProvinces = new ArrayList<String>();

    private MusicApplication menuMusic;

    /**
     * Initialises the Players Menu Scene
     */
    @FXML
    public void initialize() {
        // Dynamically add the comboboxes for the players faction selections and
        // cutomiseable victory options
        addPlayerFactionSelections();
        addVictorySelections();

        container.setSpacing(20);
        // int width = (int) container.getWidth();
        // System.out.println("WIDTH: " + width);
        players.setPadding(new Insets(0, 0, 0, 250));

        initialiseAllFactionsList();
        initialiseAllProvincesList();
    }

    /**
     * Dynamically add the back and next buttons to the front-end
     */
    public void addButtons() {
        HBox buttons = new HBox();
        buttons.setId("buttons");

        List<Button> buttonsList = new ArrayList<Button>();

        Button backbtn = createBackButton();
        Button nextbtn = createNextButton();

        buttonsList.add(backbtn);
        buttonsList.add(nextbtn);

        buttons.getChildren().addAll(buttonsList);
        buttons.setSpacing(40);
        buttons.setPadding(new Insets(0, 0, 0, 310));
        container.getChildren().addAll(buttons);
    }

    /**
     * Create back button
     * 
     * @return created back button
     */
    public Button createBackButton() {
        Button backbtn = new Button();
        backbtn.setId("backbtn");
        backbtn.setText("Back");
        backbtn.setPrefWidth(70);

        // When clicked, transitions to the Main Menu Scene and closes the current
        // Players Menu Scene
        backbtn.setOnAction(e -> {
            Platform.runLater(new Runnable() {
                public void run() {
                    try {
                        new MainMenuApplication().start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            Stage stage = (Stage) backbtn.getScene().getWindow();
            stage.close();
        });
        return backbtn;
    }

    /**
     * Creates next button
     * 
     * @return created next button
     */
    public Button createNextButton() {
        Button nextbtn = new Button();
        nextbtn.setId("nextbtn");
        nextbtn.setText("Next");
        nextbtn.setPrefWidth(70);
        nextbtn.setOnAction(e -> {
            playingFactions = new ArrayList<String>();
            playingVictories = new ArrayList<String>();

            // Checks to ensure that all player factions and customiseable victory details
            // have been selected. Display alert if otherwise
            if (factionDetailsAlert() || victoriesDetailsAlert()) {
                return;
            }

            List<String> victories = new ArrayList<String>();
            for (ComboBox<String> box : victoryDetails) {
                String victory = box.getSelectionModel().getSelectedItem();
                victories.add(victory.toUpperCase());
            }

            distributeProvinces(numPlayers, allProvincesList);

            // Transition to Gloria Romanus Game Scene and close this current Players Menu
            // Scene
            Platform.runLater(new Runnable() {
                public void run() {
                    try {
                        GloriaRomanusApplication gloriaApp = new GloriaRomanusApplication();
                        gloriaApp.setVictoryType(victoryType);
                        gloriaApp.setCustomise(victories);
                        gloriaApp.start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            Stage stage = (Stage) nextbtn.getScene().getWindow();
            stage.close();
            menuMusic.stopMenuMusic();
        });
        return nextbtn;
    }

    /**
     * Checks if every player has chosen a faction. Display alert if not all players
     * have chosen a faction or if there is a duplicated player faction chosen
     * 
     * @return true if the alert has been displayed
     */
    public Boolean factionDetailsAlert() {
        for (ComboBox<String> box : factionDetails) {
            String faction = box.getSelectionModel().getSelectedItem();
            if (factionAlert(faction) || factionDupAlert(faction)) {
                return true;
            }
            playingFactions.add(faction);
        }
        return false;
    }

    /**
     * Checks if the players have customised their victory goal. Display alert if
     * not all customiseable victory options have been chosen
     * 
     * @return true if the alert has been displayed
     */
    public Boolean victoriesDetailsAlert() {
        for (ComboBox<String> box : victoryDetails) {
            String victory = box.getSelectionModel().getSelectedItem();
            if (victoryAlert(victory) || victoryDupAlert(victory)) {
                return true;
            }
            playingVictories.add(victory);
        }
        return false;
    }

    /**
     * Dynamically add customisable victory selection comboboxes
     */
    public void addVictorySelections() {
        if (victoryType == -1) {
            return;
        }

        VBox victory = new VBox();
        victory.setId("victory");
        List<String> types = Arrays.asList("Conquest", "Treasury", "Wealth");
        List<String> conjunctions = Arrays.asList("And", "Or");
        HBox hbox = new HBox();
        // Dynamically adding victory comboboxes
        for (int i = 0; i < victoryType; i++) {
            if (i == 1 || i == 2) {
                // Conjunction comboboxes only appear when there is more than 2 victories
                // selected
                ComboBox<String> comboBox = new ComboBox();
                comboBox.getItems().addAll(conjunctions);
                comboBox.setPromptText("Pick conjunction");
                comboBox.setId("conjunction " + (i + 1));

                victoryDetails.add(comboBox);
                hbox.getChildren().addAll(comboBox);
            }

            if (i == 1) {
                // Opening bracket will only appear when there is more than 1 victory selected
                Label victoryLabel = new Label();
                victoryLabel.setText("(");
                victoryLabel.setPadding(new Insets(5, 20, 20, 20));
                hbox.getChildren().addAll(victoryLabel);
            }

            // Normal victory combobox creations
            ComboBox<String> comboBox = new ComboBox();
            comboBox.getItems().addAll(types);
            comboBox.setPromptText("Pick victory type");
            comboBox.setId("victory " + (i + 1));

            victoryDetails.add(comboBox);
            hbox.getChildren().addAll(comboBox);

            if (i != 0 && i == victoryType - 1) {
                Label victoryLabel = new Label();
                victoryLabel.setText(")");
                victoryLabel.setPadding(new Insets(5, 20, 20, 20));
                hbox.getChildren().addAll(victoryLabel);
            }
        }

        victory.getChildren().addAll(hbox);
        // victory.setAlignment(Pos.CENTER);
        // int maxWidth = (int) victory.getMaxWidth();
        // int prefWidth = (int) victory.getPrefWidth();
        // int width = (int) victory.getWidth();
        // System.out.println("VICTORY WIDTH: " + width + ", max: " + maxWidth + ",
        // pref: " + prefWidth);
        victory.setPadding(new Insets(0, 0, 0, 30));
        container.getChildren().addAll(victory);
        addButtons();
    }

    /**
     * Getter method for victory type
     * 
     * @return integer corresponding to the victory type
     */
    public int getVictoryType() {
        return victoryType;
    }

    /**
     * Setter method for victory type
     * 
     * @param victoryType integer corresponding to the victory type
     */
    public void setVictoryType(int victoryType) {
        this.victoryType = victoryType;
    }

    /**
     * Dynamically add player faction comboboxes
     */
    public void addPlayerFactionSelections() {
        List<String> list = Arrays.asList("Rome", "Gaul", "Celtic Briton", "Germania", "Carthaginian", "Spain",
                "Numidian", "Egyptians", "The Seleucid Empire", "People of Pontus", "Amenia", "Parthian",
                "Greek City States", "Macedonian", "Thracian", "Dacians");

        List<HBox> hboxes = new ArrayList<HBox>();

        players.getChildren().clear();
        for (int i = 0; i < numPlayers; i++) {
            // Creating player number label
            Label playerLabel = new Label();
            playerLabel.setText("Player " + (i + 1));
            playerLabel.setPadding(new Insets(5, 20, 20, 20));

            // Creating combobox of all possible factions to play
            ComboBox<String> comboBox = new ComboBox();
            comboBox.getItems().addAll(list);
            comboBox.setPromptText("Pick Faction");
            comboBox.setId("player " + (i + 1));
            factionDetails.add(comboBox);

            // Creating hbox to encapsulate all details
            HBox hbox = new HBox();
            hbox.getChildren().addAll(playerLabel);
            hbox.getChildren().addAll(comboBox);

            // Adding the hbox to the list of hboxes
            hboxes.add(hbox);
        }

        // Adding all hboxes to the vbox that encapsulates it all
        players.getChildren().addAll(hboxes);
    }

    /**
     * Setter method for the number of players
     * 
     * @param numPlayers integer representing the selected number of players
     */
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
        try {
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Display faction alert if the player has not selected a faction
     * 
     * @param faction the specific combobox faction details
     * @return true if the alert has been displayed
     */
    public boolean factionAlert(String faction) {
        if (faction == null) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("You must pick a faction for the player");
            errorAlert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * Dsplay faction alert if at least 2 players have selected the same faction
     * 
     * @param faction the specific combobox faction details
     * @return true if the alert has been displayed
     */
    public boolean factionDupAlert(String faction) {
        if (playingFactions != null && playingFactions.contains(faction)) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Duplicate faction");
            errorAlert.setContentText("Each faction can only have 1 player");
            errorAlert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * Display victory alert if the player has not selected a customisable victory
     * option
     * 
     * @param victory the specific combobox victory details
     * @return true if the alert has been displayed
     */
    public boolean victoryAlert(String victory) {
        if (victory == null) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Victory not valid");
            errorAlert.setContentText("You must customise your victory");
            errorAlert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * Display victory alert if at least 2 victory goals of the same type have been
     * selected
     * 
     * @param victory the specific combobox victory details
     * @return true if the alert has been displayed
     */
    public boolean victoryDupAlert(String victory) {
        if (victory.equals("And") || victory.equals("Or")) {
            return false;
        }

        if (playingVictories != null && playingVictories.contains(victory)) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Duplicate victory");
            errorAlert.setContentText("You have to select unique victories for conjunctions");
            errorAlert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * Getter method for the selected number of players
     * 
     * @return number of players
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Getter method for the currently selected playing factions
     * 
     * @return list of player factions
     */
    public List<String> getPlayingFactions() {
        return playingFactions;
    }

    /**
     * Getter method for the selected playing factions
     * 
     * @return list of player factions
     */
    public List<String> getAllFactionsList() {
        return allFactionsList;
    }

    /**
     * Getter method for all provinces
     * 
     * @return list of provinces
     */
    public List<String> getAllProvincesList() {
        return allProvincesList;
    }

    /**
     * Initialise all provinces list
     */
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
    }

    /**
     * Initialise all factions list
     */
    public void initialiseAllFactionsList() {
        allFactions = "Rome, Gaul, Celtic Briton, Germania, Carthaginian, Spain, Numidian, Egyptians, "
                + "The Seleucid Empire, People of Pontus, Amenia, Parthian, Greek City States, "
                + "Macedonian, Thracian, Dacians, ";
        allFactionsList = new ArrayList<String>(Arrays.asList(allFactions.split(", ")));
    }

    /**
     * Randomly allocate provinces to the player
     * 
     * @param num              number of players to distribute the provinces between
     * @param allProvincesList list of all initialised provinces
     * @return JSONObject for province distribution
     */
    public JSONObject distributeProvinces(int num, List<String> allProvincesList) {
        JSONObject distribution = new JSONObject();

        // Create a random start number so that each game the players will be assigned
        // new provinces
        Random rand = new Random();
        int start = rand.nextInt(allProvincesList.size());
        int loop = (int) Math.ceil((double) allProvincesList.size() / (double) numPlayers);

        for (int i = 0; i < numPlayers; i++) {
            List<String> newList = new ArrayList<String>();
            newList.addAll(allProvincesList);
            List<String> provinces;

            int lower = (start + loop * (i)) % allProvincesList.size();
            int upper = lower + loop;
            boolean end = false;
            if (upper > allProvincesList.size()) {
                upper = upper % allProvincesList.size();
                end = true;
            }
            if (i == 0) {
                if (end) {
                    provinces = newList.subList(lower, allProvincesList.size());
                    provinces.addAll(newList.subList(0, upper));
                } else {
                    provinces = allProvincesList.subList(start, upper);
                }
            } else if (i == numPlayers - 1) {
                if (end) {
                    provinces = newList.subList(lower, allProvincesList.size());
                    List<String> sub = newList.subList(0, upper);
                    provinces.addAll(sub);
                } else {
                    provinces = allProvincesList.subList(lower, upper);
                }
            } else {
                if (end) {
                    provinces = newList.subList(lower, allProvincesList.size());
                    List<String> sub = newList.subList(0, upper);
                    provinces.addAll(sub);
                } else {
                    provinces = allProvincesList.subList(lower, upper);
                }
            }
            provinces = checkDup(provinces);
            distribution.put(playingFactions.get(i), provinces);
            System.out.println("Faction: " + playingFactions.get(i) + ", Assigned: " + provinces.size() + ", Total: "
                    + allProvincesList.size());
            allocatedProvinces.addAll(provinces);
        }

        // Save the province distributions in a file
        String filename = "province_ownership.json";
        File file = new File("src/unsw/gloriaromanus/" + filename);

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

    /**
     * Filters the already seen provinces out
     * 
     * @param toCheck province list to remove duplication
     * @return list of filtered toCheck province list
     */
    public List<String> checkDup(List<String> toCheck) {
        List<String> filter = new ArrayList<String>();
        for (String province : toCheck) {
            if (!allocatedProvinces.contains(province)) {
                filter.add(province);
            }
        }
        return filter;
    }

    public void setMenuMusic(MusicApplication menuMusic) {
        this.menuMusic = menuMusic;
    }
}
