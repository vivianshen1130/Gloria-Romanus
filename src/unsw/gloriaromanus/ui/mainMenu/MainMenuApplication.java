package unsw.gloriaromanus.ui.mainMenu;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
// import unsw.gloriaromanus.MusicApplication;

public class MainMenuApplication extends Application {
    private static MainMenuController controller;

    /**
     * Creates the Main Menu Screen
     * 
     * @param stage stage to create
     */
    @Override
    public void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
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

    /**
     * Opens and runs application.
     *
     * @param args arguments passed to this application
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}