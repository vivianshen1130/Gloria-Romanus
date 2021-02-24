package unsw.gloriaromanus;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.gloriaromanus.music.MusicApplication;

import java.util.List;

public class GloriaRomanusApplication extends Application {

    private static GloriaRomanusController controller;
    private int victoryType;
    private List<String> customise;

    @Override
    public void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.manageCustomisation(victoryType, customise);
        // possibly need to get rid of
        controller.initialize();
        // controller.addUnitsToProvinces();
        // System.out.println("called addUnits");
        Scene scene = new Scene(root);

        // MusicApplication musicApp = new MusicApplication();
        // musicApp.start(new Stage());

        // set up the stage
        stage.setTitle("Gloria Romanus");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.setScene(scene);
        stage.show();
    }

    public static GloriaRomanusController getController() {
        return controller;
    }

    public static void setController(GloriaRomanusController controller) {
        GloriaRomanusApplication.controller = controller;
    }

    public void setVictoryType(int victoryType) {
        this.victoryType = victoryType;
    }

    public void setCustomise(List<String> customise) {
        this.customise = customise;
    }

    /**
     * Stops and releases all resources used in application.
     */
    @Override
    public void stop() {
        controller.terminate();
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