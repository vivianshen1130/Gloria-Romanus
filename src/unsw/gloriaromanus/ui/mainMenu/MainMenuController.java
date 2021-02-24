package unsw.gloriaromanus.ui.mainMenu;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import unsw.gloriaromanus.music.MusicApplication;
import unsw.gloriaromanus.ui.loadMenu.LoadMenuApplication;
import unsw.gloriaromanus.ui.playersMenu.PlayersMenuApplication;
import unsw.gloriaromanus.ui.playersMenu.PlayersMenuController;

public class MainMenuController {
    @FXML
    private ComboBox<Integer> num_players;
    @FXML
    private ComboBox<String> campaign_victory;
    @FXML
    private Button loadbtn;
    @FXML
    private Button playbtn;

    private int numPlayers;
    private int victoryType;
    private MainMenuController controller;

    private MusicApplication menuMusic;

    /**
     * Initialises the Main Menu Scene
     */
    @FXML
    public void initialize() throws JsonParseException, JsonMappingException, IOException {
        menuMusic = new MusicApplication();
        menuMusic.startMenuMusic();

        controller = this;
        num_players.getItems().clear();

        // Initialise number of players combobox
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        num_players.getItems().addAll(list);
    }

    /**
     * Transitions to the Load Menu Scene
     * 
     * @param event when the load button is clicked
     */
    @FXML
    public void onLoadButton(MouseEvent event) {
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    LoadMenuApplication loadMenu = new LoadMenuApplication();
                    loadMenu.setParentController(controller);
                    loadMenu.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Transitions to the Load Menu Scene and closes the current Main Menu Scene
     * 
     * @param event when the play button is clicked
     */
    @FXML
    public void onPlayButton(MouseEvent event) {
        // Display alerts the user has not selected a number of player or victory type
        if (playerNumAlert() || victoryTypeAlert()) {
            return;
        }

        // Obtain number of players and victory type from combobox values
        numPlayers = (int) num_players.getSelectionModel().getSelectedItem();
        victoryType = manageVictory();

        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    PlayersMenuApplication playersMenu = new PlayersMenuApplication();
                    // Add numPlayers and victoryType values inot the Players Menu Scene
                    playersMenu.setVictoryType(victoryType);
                    playersMenu.start(new Stage());
                    PlayersMenuController playerController = playersMenu.getController();
                    playerController.setNumPlayers(numPlayers);
                    playerController.setMenuMusic(menuMusic);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Close Main Menu Scene
        Stage stage = (Stage) playbtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Display alert when the user has not selected the number of players from the
     * combobox
     * 
     * @return true if the alert has been displayed
     */
    public boolean playerNumAlert() {
        if (num_players.getSelectionModel().getSelectedItem() == null) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Player number not selected");
            errorAlert.setContentText("You must pick a valid number of players (1-16 players)");
            errorAlert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * Display alert when the user has not selected the victory type from the
     * combobox
     * 
     * @return true if the alert has been displayed
     */
    public boolean victoryTypeAlert() {
        if (campaign_victory.getSelectionModel().getSelectedItem() == null) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Victory goal type not selected");
            errorAlert.setContentText("You must pick a customiseable victory option");
            errorAlert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * Convert the selected victory type to correspond with the number of victory
     * conjunctions the player has chosen.
     * 
     * @return 0 indicates that the player wants us to generate a victory, 1
     *         indicates that the player wants to generate a single victory, 2-3
     *         indicates that the player wants to generate a composite victory with
     *         2-3 conjunctions
     */
    public int manageVictory() {
        int result = 0;
        String victory = campaign_victory.getSelectionModel().getSelectedItem();
        String types = "1,2,3";
        List<String> nums = new ArrayList<String>(Arrays.asList(types.split(",")));

        // Converts the victories
        for (String num : nums) {
            if (victory.contains(num)) {
                result = Integer.parseInt(num);
            }
        }
        return result;
    }

    /**
     * Closes the current Main Menu Scene
     */
    public void closeGame() {
        Stage stage = (Stage) playbtn.getScene().getWindow();
        stage.close();
    }
}