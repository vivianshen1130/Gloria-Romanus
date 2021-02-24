package unsw.gloriaromanus.ui.winMenu;

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

public class WinMenuController {
    @FXML
    private Label victory_text;
    @FXML
    private Label you_won_text;
    @FXML
    private HBox buttons;
    @FXML
    private Button play_btn;
    @FXML
    private Button end_btn;

    private String faction;
    private MusicApplication gameMusic = null;
    private GloriaRomanusController parentController;

    /**
     * Initialises the Win Menu Scene
     */
    @FXML
    public void initialize() {
        if (gameMusic != null) {
            gameMusic.startVictoryMusic();
        }
        victory_text.setText("Victory for " + faction);
        victory_text.setPadding(new Insets(64, 0, 0, 0));
        you_won_text.setPadding(new Insets(117, 0, 0, 0));
        buttons.setSpacing(40);
        buttons.setPadding(new Insets(300, 0, 0, 20));
    }

    /**
     * Transition to Main Menu Scene and closes the current Win Menu Scene
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
     * Closes the current Win Menu Scene and resumes the Gloria Romanus Game
     * 
     * @param event when the play button is clicked
     */
    @FXML
    public void onPlayButton(MouseEvent event) {
        gameMusic.stopVictoryMusic();
        gameMusic.resumeGameMusic();
        Stage stage = (Stage) play_btn.getScene().getWindow();
        stage.close();
    }

    /**
     * Setter function for winning faction name
     * 
     * @return name of the winning faction
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

    /**
     * Closes the current Win Menu Scene
     */
    public void closeGame() {
        Stage stage = (Stage) play_btn.getScene().getWindow();
        stage.close();
    }
}
