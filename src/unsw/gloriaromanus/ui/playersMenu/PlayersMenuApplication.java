package unsw.gloriaromanus.ui.playersMenu;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayersMenuApplication extends Application {
    private static PlayersMenuController controller;
    private int victoryType;

    /**
     * Creates the Players Menu Screen
     * 
     * @param stage stage to create
     */
    @Override
    public void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("playersMenu.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setVictoryType(victoryType);
        Scene scene = new Scene(root);

        // set up the stage
        stage.setTitle("Gloria Romanus");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Getter method for the Players Menu Controller
     * 
     * @return current scene's Players Menu Controller
     */
    public static PlayersMenuController getController() {
        return controller;
    }

    /**
     * Getter method for the victory type
     * 
     * @return integer indicating customisable victory type
     */
    public int getVictoryType() {
        return victoryType;
    }

    /**
     * Setter method for the victory type
     * 
     * @param victoryType integer indicating customisable victory type
     */
    public void setVictoryType(int victoryType) {
        this.victoryType = victoryType;
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
