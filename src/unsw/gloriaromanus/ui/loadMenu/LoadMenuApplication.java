package unsw.gloriaromanus.ui.loadMenu;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import unsw.gloriaromanus.ui.mainMenu.MainMenuController;

public class LoadMenuApplication extends Application {
    private static LoadMenuController controller;
    private MainMenuController parentController;

    /**
     * Creates the Load Menu Screen
     * 
     * @param stage stage to create
     */
    @Override
    public void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loadMenu.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setParentController(parentController);
        Scene scene = new Scene(root);

        // set up the stage
        stage.setTitle("Gloria Romanus");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Getter method loadMenu's parent controller
     * 
     * @return parent controller of the loadMenuApplication which is of
     *         mainMenuController type
     */
    public MainMenuController getParentController() {
        return parentController;
    }

    /**
     * Setter method for loadMenu's parent controller
     * 
     * @param parentController parent controller of the loadMenuApplication which is
     *                         of mainMenuController type
     */
    public void setParentController(MainMenuController parentController) {
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
