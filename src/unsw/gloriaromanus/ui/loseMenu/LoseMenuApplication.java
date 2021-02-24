package unsw.gloriaromanus.ui.loseMenu;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import unsw.gloriaromanus.GloriaRomanusController;
import unsw.gloriaromanus.music.MusicApplication;

public class LoseMenuApplication extends Application {
    private static LoseMenuController controller;
    private String faction;

    private GloriaRomanusController parentController;
    private MusicApplication gameMusic;

    /**
     * Creates the Lose Menu Screen
     * 
     * @param stage stage to create
     */
    @Override
    public void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loseMenu.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setFaction(faction);
        controller.setParentController(parentController);
        controller.setGameMusic(gameMusic);
        controller.initialize();
        Scene scene = new Scene(root);

        // set up the stage
        stage.setTitle("Gloria Romanus");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Setter method for faction
     * 
     * @param faction name of the defeated faction
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
     * Opens and runs application.
     *
     * @param args arguments passed to this application
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
