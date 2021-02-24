package unsw.gloriaromanus.ui.settingsMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

import unsw.gloriaromanus.GloriaRomanusController;
import unsw.gloriaromanus.music.MusicApplication;
import unsw.gloriaromanus.ui.mainMenu.MainMenuApplication;
import unsw.gloriaromanus.ui.saveMenu.SaveMenuApplication;

public class SettingsMenuController {

    @FXML
    private Label text;
    @FXML
    private HBox buttons;
    @FXML
    private Button end_btn;
    @FXML
    private Button play_btn;
    @FXML
    private Button save_btn;

    private GloriaRomanusController parentController;
    private SettingsMenuController controller;
    private MusicApplication gameMusic;

    /**
     * Initialises the Settings Menu Scene
     */
    @FXML
    private void initialize() {
        controller = this;
        text.setPadding(new Insets(38, 0, 0, 0));
        buttons.setSpacing(20);
        buttons.setPadding(new Insets(300, 0, 0, 240));
    }

    /**
     * Transitions to the Main Menu Scene and closes the current Settings Menu Scene
     * 
     * @param event when the end button is clicked
     */
    @FXML
    public void onEndButton(MouseEvent event) {
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    new MainMenuApplication().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        gameMusic.stopGameMusic();
        parentController.closeGame();
        Stage stage = (Stage) end_btn.getScene().getWindow();
        stage.close();
    }

    /**
     * Closes the current Settings Menu scene
     * 
     * @param event when the play button is clicked
     */
    @FXML
    public void onPlayButton(MouseEvent event) {
        Stage stage = (Stage) play_btn.getScene().getWindow();
        stage.close();
    }

    /**
     * Transitions to the Save Menu scene
     * 
     * @param event when the save button is clicked
     */
    @FXML
    public void onSaveButton(MouseEvent event) {
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    SaveMenuApplication saveMenu = new SaveMenuApplication();
                    saveMenu.setGrandparentController(parentController);
                    saveMenu.setParentController(controller);
                    saveMenu.setGameMusic(gameMusic);
                    saveMenu.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Closes the current Settings Menu Scene
     */
    public void closeGame() {
        Stage stage = (Stage) play_btn.getScene().getWindow();
        stage.close();
    }

    /**
     * Getter method for Settings Menu's parent controller
     * 
     * @return parent controller
     */
    public GloriaRomanusController getParentController() {
        return parentController;
    }

    /**
     * Setter method for Settings Menu's parent controller
     * 
     * @param parentController parent controller
     */
    public void setParentController(GloriaRomanusController parentController) {
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
