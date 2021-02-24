package unsw.gloriaromanus.ui.saveMenu;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import unsw.gloriaromanus.GloriaRomanusController;
import unsw.gloriaromanus.music.MusicApplication;
import unsw.gloriaromanus.ui.settingsMenu.SettingsMenuController;

public class SaveMenuApplication extends Application {
    private static SaveMenuController controller;
    private GloriaRomanusController grandparentController;
    private SettingsMenuController parentController;
    private MusicApplication gameMusic;

    /**
     * Creates the Save Menu Screen
     * 
     * @param stage stage to create
     */
    @Override
    public void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("saveMenu.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setGrandparentController(grandparentController);
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

    /**
     * Opens and runs application.
     *
     * @param args arguments passed to this application
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
