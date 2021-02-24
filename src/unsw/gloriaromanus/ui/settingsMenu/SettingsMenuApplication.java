package unsw.gloriaromanus.ui.settingsMenu;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.gloriaromanus.GloriaRomanusController;
import unsw.gloriaromanus.music.MusicApplication;

public class SettingsMenuApplication extends Application {
    private static SettingsMenuController controller;
    private GloriaRomanusController parentController;
    private MusicApplication gameMusic;

    /**
     * Creates the Settings Menu Screen
     * 
     * @param stage stage to create
     */
    @Override
    public void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settingsMenu.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setParentController(parentController);
        controller.setGameMusic(gameMusic);
        Scene scene = new Scene(root);

        // set up the stage
        stage.setTitle("Gloria Romanus");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Getter method for Save Menu's parent controller
     * 
     * @return parent controller
     */
    public GloriaRomanusController getParentController() {
        return parentController;
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
     * Setter method for game music
     * 
     * @param gameMusic game music controller
     */
    public void setGameMusic(MusicApplication gameMusic) {
        this.gameMusic = gameMusic;
    }

    /**
     * Opens and runs application.
     *
     * @param args arguments passed to this application
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
