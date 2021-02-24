package unsw.gloriaromanus.ui.loseMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import unsw.gloriaromanus.GloriaRomanusController;
import unsw.gloriaromanus.music.MusicApplication;
import unsw.gloriaromanus.ui.mainMenu.MainMenuApplication;

public class LoseMenuController {
    @FXML
    private Label defeat_text;
    @FXML
    private Label you_lost_text;
    @FXML
    private HBox buttons;
    @FXML
    private Button play_btn;
    @FXML
    private Button end_btn;

    private String faction;

    private GloriaRomanusController parentController;
    private MusicApplication gameMusic = null;

    /**
     * Initialises the loseMenu Scene
     */
    public void initialize() {
        if (gameMusic != null) {
            gameMusic.stopGameMusic();
        }
        defeat_text.setText("Defeat for " + faction);
        defeat_text.setPadding(new Insets(64, 0, 0, 0));
        you_lost_text.setPadding(new Insets(117, 0, 0, 0));
        buttons.setSpacing(40);
        buttons.setPadding(new Insets(300, 0, 0, 20));
    }

    /**
     * Loads the MainMenu scene and closes the current stage
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

        parentController.closeGame();
        Stage stage = (Stage) end_btn.getScene().getWindow();
        stage.close();
    }

    /**
     * Closes the LoseMenu scene and resumes the Gloria Romanus game scene
     * 
     * @param event when the play button is clicked
     */
    @FXML
    public void onPlayButton(MouseEvent event) {
        gameMusic.resumeGameMusic();
        Stage stage = (Stage) play_btn.getScene().getWindow();
        stage.close();
    }

    /**
     * Setter method for faction
     * 
     * @param faction name of defeated faction
     */
    public void setFaction(String faction) {
        this.faction = faction;
    }

    /**
     * Setter method for game music
     * 
     * @param gameMusic game music controller
     */
    public void setGameMusic(MusicApplication gameMusic) {
        this.gameMusic = gameMusic;
    }

    /**
     * Setter method for Save Menu's parent controller
     * 
     * @param parentController parent controller
     */
    public void setParentController(GloriaRomanusController parentController) {
        this.parentController = parentController;
    }
}
