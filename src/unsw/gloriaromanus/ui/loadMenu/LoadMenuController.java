package unsw.gloriaromanus.ui.loadMenu;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.List;

import unsw.gloriaromanus.GloriaRomanusApplication;
import unsw.gloriaromanus.GloriaRomanusController;
import unsw.gloriaromanus.ui.mainMenu.MainMenuController;

public class LoadMenuController {

    @FXML
    private Label load_game_text;
    @FXML
    private ComboBox<String> load_game;
    @FXML
    private HBox buttons;
    @FXML
    private Button cancel_btn;
    @FXML
    private Button load_btn;

    private MainMenuController parentController;

    /**
     * Initialises the LoadMenu Scene
     */
    @FXML
    public void initialize() {
        load_game_text.setPadding(new Insets(38, 0, 0, 275));
        buttons.setSpacing(40);

        // Get names of all files in the saved games folder given
        // that have a .json suffix so that we can load it in the combobox
        File f = new File("src/unsw/gloriaromanus/saved/");
        String[] pathnames = f.list();
        List<String> filenames = new ArrayList<String>();
        for (String pathname : pathnames) {
            if (pathname.endsWith(".json")) {
                filenames.add(pathname.split(".json")[0]);
            }
        }
        load_game.getItems().addAll(filenames);
    }

    /**
     * Closes the current stage
     * 
     * @param event when the cancel button is clicked
     */
    @FXML
    public void onCancelButton(MouseEvent event) {
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        stage.close();
    }

    /**
     * Loads the saved stage of the game and closes the current stage
     * 
     * @param event when the load button is clicked
     */
    @FXML
    public void onLoadButton(MouseEvent event) {
        String filename = load_game.getSelectionModel().getSelectedItem();

        // Displays alert if a filename has not been selected
        if (filename == null) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("File not selected");
            errorAlert.setContentText("You must select a game file to load");
            errorAlert.showAndWait();
            return;
        }

        // Runs the Gloria Romanus Game and loads the saved state of the previous game
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    GloriaRomanusApplication gloriaApp = new GloriaRomanusApplication();
                    gloriaApp.start(new Stage());
                    GloriaRomanusController gloriaController = gloriaApp.getController();
                    gloriaController.loadGame(filename + ".json", false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Closes the loadMenu and the parent mainMenu scenes
        parentController.closeGame();
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        stage.close();
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
}
