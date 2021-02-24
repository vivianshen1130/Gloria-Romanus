package unsw.gloriaromanus.ui.saveMenu;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

import unsw.gloriaromanus.GloriaRomanusController;
import unsw.gloriaromanus.music.MusicApplication;
import unsw.gloriaromanus.ui.mainMenu.MainMenuApplication;
import unsw.gloriaromanus.ui.settingsMenu.SettingsMenuController;

public class SaveMenuController {

    @FXML
    private Label save_game_text;
    @FXML
    private Label filename_text;
    @FXML
    private TextField filename;
    @FXML
    private HBox buttons;
    @FXML
    private Button cancel_btn;
    @FXML
    private Button save_btn;

    private GloriaRomanusController grandparentController;
    private SettingsMenuController parentController;
    private MusicApplication gameMusic;

    /**
     * Initialises the Save Menu Scene
     */
    @FXML
    public void initialize() throws JsonParseException, JsonMappingException, IOException {
        save_game_text.setPadding(new Insets(100, 0, 0, 0));
        filename_text.setPadding(new Insets(250, 0, 0, 0));
        // filename.setM(new Insets(100, 0, 0, 0));
        // filename.setAlignment(Pos.CENTER);
        // filename.setPadding(arg0);
        buttons.setSpacing(40);
        buttons.setPadding(new Insets(420, 0, 0, 280));
    }

    /**
     * Closes the current stage
     * 
     * @param event when the cancel button is clicked
     */
    @FXML
    public void onCancelButton(MouseEvent event) {
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        stage.close();
    }

    /**
     * Saves the current stage of the game, transitions to the Main Menu and closes
     * the current stage
     * 
     * @param event when the load button is clicked
     */
    @FXML
    public void onSaveButton(MouseEvent event) {
        String name = filename.getText();

        // Display alert if the filename has not been specified
        if (name == null || name.equals("")) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Filename not found");
            errorAlert.setContentText("You must create a name for your saved game file");
            errorAlert.showAndWait();
            return;
        }

        // Save state of the game
        name += ".json";
        grandparentController.saveGame(name, false);

        // Close other windows of the game
        grandparentController.closeGame();
        parentController.closeGame();

        // Transitions to the Main Menu Scene
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    new MainMenuApplication().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Closes the current Save menu scene
        gameMusic.stopGameMusic();
        parentController.closeGame();
        grandparentController.closeGame();
        Stage stage = (Stage) save_btn.getScene().getWindow();
        stage.close();
    }

    /**
     * Getter method for Save Menu's grandparent controller
     * 
     * @return grandparent controller
     */
    public GloriaRomanusController getGrandparentController() {
        return grandparentController;
    }

    /**
     * Setter method for Save Menu's grandparent controller
     * 
     * @param grandparentController grandparent controller
     */
    public void setGrandparentController(GloriaRomanusController grandparentController) {
        this.grandparentController = grandparentController;
    }

    /**
     * Getter method for Save Menu's parent controller
     * 
     * @return parent controller
     */
    public SettingsMenuController getParentController() {
        return parentController;
    }

    /**
     * Setter method for Save Menu's parent controller
     * 
     * @param parentController parent controller
     */
    public void setParentController(SettingsMenuController parentController) {
        this.parentController = parentController;
    }

    /**
     * Setter method for game music
     * 
     * @param gameMusic game music controller
     */
    public void setGameMusic(MusicApplication gameMusic) {
        this.gameMusic = gameMusic;
    }
}
