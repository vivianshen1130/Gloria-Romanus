package unsw.gloriaromanus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.sound.sampled.AudioInputStream;
import javax.swing.JOptionPane;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Collections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.application.Platform;
import javafx.stage.Stage;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.FeatureTable;
import com.esri.arcgisruntime.data.GeoPackage;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.internal.httpclient.impl.client.NullBackoffStrategy;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyLayerResult;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol.HorizontalAlignment;
import com.esri.arcgisruntime.symbology.TextSymbol.VerticalAlignment;
import com.esri.arcgisruntime.data.Feature;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.json.JSONArray;
import org.json.JSONObject;

import unsw.gloriaromanus.faction.Faction;
import unsw.gloriaromanus.music.MusicApplication;
import unsw.gloriaromanus.province.Province;
import unsw.gloriaromanus.ui.loseMenu.LoseMenuApplication;
import unsw.gloriaromanus.ui.settingsMenu.SettingsMenuApplication;
import unsw.gloriaromanus.ui.winMenu.WinMenuApplication;
import unsw.gloriaromanus.unit.Unit;
import unsw.gloriaromanus.unit.artillery.Artillery;
import unsw.gloriaromanus.unit.cavalry.Chariot;
import unsw.gloriaromanus.unit.cavalry.Elephant;
import unsw.gloriaromanus.unit.cavalry.HorseArcher;
import unsw.gloriaromanus.unit.cavalry.Lancer;
import unsw.gloriaromanus.unit.cavalry.MeleeCavalry;
import unsw.gloriaromanus.unit.infantry.Berserker;
import unsw.gloriaromanus.unit.infantry.Druid;
import unsw.gloriaromanus.unit.infantry.Hoplite;
import unsw.gloriaromanus.unit.infantry.JavelinSkirmisher;
import unsw.gloriaromanus.unit.infantry.MeleeInfantry;
import unsw.gloriaromanus.unit.infantry.MissileInfantry;
import unsw.gloriaromanus.unit.infantry.Pikeman;
import unsw.gloriaromanus.unit.infantry.Spearman;
import unsw.gloriaromanus.victory.CampaignVictory;
import unsw.gloriaromanus.battleResolver.BattleResolver;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GloriaRomanusController {

    // @FXML
    // private StackPane stackPaneMain;
    // private ArrayList<Pair<MenuController, VBox>> controllerParentPairs;
    private GloriaRomanusController controller;

    @FXML
    private MapView mapView;
    @FXML
    private Button settings;
    @FXML
    private TextField invading_province;
    @FXML
    private TextField opponent_province;
    @FXML
    private TextArea output_terminal;
    @FXML
    private Label factionName;
    @FXML
    private Label wealth;
    @FXML
    private Label treasury;
    @FXML
    private Label conqueredStatus;
    @FXML
    private Label victoryGoal;
    @FXML
    private ComboBox<String> number_units;
    @FXML
    private ComboBox<String> unit_to_move;
    @FXML
    private ComboBox<String> unit_to_recruit;
    @FXML
    private ComboBox<String> set_tax_rate;
    @FXML
    private ComboBox<String> provinces_to_move_to;

    private ArcGISMap map;

    private Map<String, String> provinceToOwningFactionMap;

    private Map<String, Integer> provinceToNumberTroopsMap;

    private String humanFaction;

    private Feature currentlySelectedHumanProvince;
    private Feature currentlySelectedEnemyProvince;

    private FeatureLayer featureLayer_provinces;

    private String filename = null;
    private int totalTurns = 0;
    private int turnNumber = 1;

    private List<Faction> allFactions = new ArrayList<Faction>();
    private List<String> allFactionsName = new ArrayList<String>();
    private List<Province> allProvinces = new ArrayList<Province>();
    private List<String> allProvincesName = new ArrayList<String>();
    private List<Unit> allUnits = new ArrayList<Unit>();
    private List<String> victory = new ArrayList<String>();
    private CampaignVictory campaignVictory = new CampaignVictory();

    private MusicApplication gameMusic = null;

    /**
     * Initializes the game and the map
     * 
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @FXML
    public void initialize() throws JsonParseException, JsonMappingException, IOException {
        if (gameMusic != null) {
            gameMusic.stopGameMusic();
        }
        gameMusic = new MusicApplication();
        gameMusic.start(new Stage());

        controller = this;
        provinceToOwningFactionMap =

                getProvinceToOwningFactionMap();

        provinceToNumberTroopsMap = new HashMap<String, Integer>();

        Random r = new Random();
        for (String provinceName : provinceToOwningFactionMap.keySet()) {
            provinceToNumberTroopsMap.put(provinceName, r.nextInt(500));
        }

        currentlySelectedHumanProvince = null;
        currentlySelectedEnemyProvince = null;

        initializeProvinceLayers();

        unit_to_recruit.getItems().clear();
        List<String> list = Arrays.asList("Artillery (10, 1)", "Chariot (10, 1)", "Elephant (10, 2)",
                "Horse Archer (10, 1)", "Lancer (10, 1)", "Melee Cavalry (10, 1)", "Berserker (10, 1)", "Druid (10, 1)",
                "Hoplite (10, 1)", "Infantry (10, 1)", "Javelin Skirmisher (10, 1)", "Melee Infantry (10, 1)",
                "Missile Infantry (10, 1)", "Spearman (10, 1)");
        unit_to_recruit.getItems().addAll(list);
        unit_to_move.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> selected, String oldUnit, String newUnit) {
                System.out.println("updated unit to move");
                String unitToMove = unit_to_move.getSelectionModel().getSelectedItem();
                int index = unit_to_move.getSelectionModel().getSelectedIndex();
                if (currentlySelectedHumanProvince != null && unitToMove != null) {
                    String p = (String) currentlySelectedHumanProvince.getAttributes().get("name");
                    Province startingProvince = getProvinceByName(p);
                    Unit unit = startingProvince.getUnitFromIndex(index);
                    updateDestProvinces(startingProvince, unit);
                }
            }
        });

        updateFactionDetails(0);
        victoryGoal.setText(victory.toString().replaceAll("[\\[\\],]", "").toLowerCase());

        addAllPointGraphics(); // reset graphics

    }

    /**
     * Manage the user's ability to customise their victory goals
     * 
     * @param victoryType the number of victories selected by the user
     * @param customise   the set of customised victories that the user has chosen
     */
    public void manageCustomisation(int victoryType, List<String> customise) {
        if (victoryType == 0 || customise.isEmpty()) {
            victory = campaignVictory.createCampaignVictory();
            System.out.println("Make victory for me: " + victory);
            // Update graphics
            try {
                initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        victory = customise;
        campaignVictory = new CampaignVictory(victory);
        System.out.println("Customised victory is: " + customise);
        // Update graphics
        try {
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the click of the invade button If the user has not selected all of
     * the necessary elements they receive an error message Otherwise, a battle
     * resolver is created and determines whether or not a region has been invaded
     */
    @FXML
    public void clickedInvadeButton(ActionEvent e) throws IOException {
        if (currentlySelectedHumanProvince != null && currentlySelectedEnemyProvince != null) {
            String humanProvince = (String) currentlySelectedHumanProvince.getAttributes().get("name");
            String enemyProvince = (String) currentlySelectedEnemyProvince.getAttributes().get("name");
            Province invadingProvince = getProvinceByName(humanProvince);
            Province defendingProvince = getProvinceByName(enemyProvince);
            int numToInvade = 0;
            if (number_units.getSelectionModel().getSelectedItem() != null) {
                numToInvade = Integer.parseInt(number_units.getSelectionModel().getSelectedItem());
            }

            if (confirmIfProvincesConnected(humanProvince, enemyProvince) && numToInvade > 0) {
                Random r = new Random();
                BattleResolver battleResolver = new BattleResolver(invadingProvince, numToInvade, defendingProvince,
                        defendingProvince.getUnits().size(), r);

                boolean result = battleResolver.manageBattle();
                if (result) {
                    // human won. transfer the remaining active troops into the new province
                    // enemy loses all troops
                    // int numTroopsToTransfer = provinceToNumberTroopsMap.get(humanProvince) * 2 /
                    // 5;
                    // provinceToNumberTroopsMap.put(enemyProvince, numTroopsToTransfer);
                    // provinceToNumberTroopsMap.put(humanProvince,
                    // provinceToNumberTroopsMap.get(humanProvince) - numTroopsToTransfer);
                    // provinceToOwningFactionMap.put(enemyProvince, humanFaction);

                    printMessageToTerminal("Won battle!");
                } else {
                    // enemy won. human loses all active troops
                    // int numTroopsLost = provinceToNumberTroopsMap.get(humanProvince) * 3 / 5;
                    // provinceToNumberTroopsMap.put(humanProvince,
                    // provinceToNumberTroopsMap.get(humanProvince) - numTroopsLost);
                    printMessageToTerminal("Lost battle!");
                }
                updateFactionDetails(totalTurns % allFactions.size());
                resetSelections(); // reset selections in UI
                updateComboBox();
                addAllPointGraphics(); // reset graphics
                updateFactionDetails(totalTurns % allFactions.size());
            } else if (confirmIfProvincesConnected(humanProvince, enemyProvince)) {
                printMessageToTerminal("You must have at least one available unit to invade!");
            } else {
                printMessageToTerminal("Provinces not adjacent, cannot invade!");
            }

        }
    }

    /**
     * Handles the click of the recruit button If the user has not selected all of
     * the necessary elements they receive an error message Otherwise, a unit is
     * recruited to the selected province
     */
    @FXML
    public void clickedRecruitButton(ActionEvent e) throws IOException {
        if (currentlySelectedHumanProvince != null) {
            String humanProvince = (String) currentlySelectedHumanProvince.getAttributes().get("name");
            Province province = getProvinceByName(humanProvince);
            String unitType = unit_to_recruit.getSelectionModel().getSelectedItem();
            if (unitType != null) {
                Unit unit;
                switch (unitType) {
                    case "Artillery (10, 1)":
                        unit = new Artillery(totalTurns);
                        break;
                    case "Chariot (10, 1)":
                        unit = new Chariot(totalTurns);
                        break;
                    case "Elephant (10, 2)":
                        unit = new Elephant(totalTurns);
                        break;
                    case "Horse Archer (10, 1)":
                        unit = new HorseArcher(totalTurns);
                        break;
                    case "Lancer (10, 1)":
                        unit = new Lancer(totalTurns);
                        break;
                    case "Melee Cavalry (10, 1)":
                        unit = new MeleeCavalry(totalTurns);
                        break;
                    case "Berserker (10, 1)":
                        unit = new Berserker(totalTurns);
                        break;
                    case "Druid (10, 1)":
                        unit = new Druid(totalTurns);
                        break;
                    case "Hoplite (10, 1)":
                        unit = new Hoplite(totalTurns);
                        break;
                    case "Javelin Skirmisher (10, 1)":
                        unit = new JavelinSkirmisher(totalTurns);
                        break;
                    case "Melee Infantry (10, 1)":
                        unit = new MeleeInfantry(totalTurns);
                        break;
                    case "Pikeman (10, 1)":
                        unit = new Pikeman(totalTurns);
                        break;
                    default:
                        unit = new Spearman(totalTurns);
                }
                if (unit.getCost() <= province.getFaction().getTreasury() && province.getNumTraining() < 2) {
                    province.addUnit(unit);
                    province.setNumTraining(province.getNumTraining() + 1);
                    province.getFaction().setTreasury(province.getFaction().getTreasury() - unit.getCost());
                    String[] unitName = unit.getClass().getName().split("\\.");
                    printMessageToTerminal(province.getFaction().getName() + " recruited 1 "
                            + unitName[unitName.length - 1].toLowerCase() + " unit");
                    updateFactionDetails(totalTurns % allFactions.size());
                } else if (province.getNumTraining() < 2) {
                    printMessageToTerminal("Insufficient funds to recruit these troops");
                } else {
                    printMessageToTerminal("Already training 2 units in this province");
                }
            } else {
                printMessageToTerminal("Must select a type of unit to recruit");
            }
        } else {
            printMessageToTerminal("Must select a province to recruit troops");
        }
    }

    /**
     * Handles the click of the move button If the user has not selected all of the
     * necessary elements they receive an error message Otherwise, the selected unit
     * is moved from the starting to ending province
     */
    @FXML
    public void clickedMoveButton(ActionEvent e) throws IOException {
        if (currentlySelectedHumanProvince == null) {
            printMessageToTerminal("Must select a province to move from");
            return;
        }
        String unitType = unit_to_move.getSelectionModel().getSelectedItem();
        if (unitType == null) {
            printMessageToTerminal("Must select a unit to move between provinces");
            return;
        }
        if (provinces_to_move_to.getSelectionModel().getSelectedItem() == null) {
            printMessageToTerminal("Must select a province to move to");
            return;
        } else {
            String p = (String) currentlySelectedHumanProvince.getAttributes().get("name");
            Province startingProvince = getProvinceByName(p);
            p = provinces_to_move_to.getSelectionModel().getSelectedItem();
            Province endingProvince = getProvinceByName(p);
            int index = unit_to_move.getSelectionModel().getSelectedIndex();
            Unit unit = startingProvince.getUnitFromIndex(index);

            int movement = startingProvince.canMoveTroops(endingProvince, unit, false);
            if (movement != -1) {
                System.out.println("MOVEMENT: " + movement);
                startingProvince.removeUnit(unit);
                endingProvince.addUnit(unit);
                unit.setCurrentMovementPoints(unit.getCurrentMovementPoints() - 4*(movement+1));
                updateComboBox(startingProvince);
                addAllPointGraphics();
            } else {
                printMessageToTerminal("unit cannot move from starting province to finishing province");
            }
        }
    }

    /**
     * Handles the click of the set tax button If the user has not selected all of
     * the necessary elements they receive an error message Otherwise, the tax rate
     * of the province is set to the selected rate
     */
    @FXML
    public void clickedSetTaxButton(ActionEvent e) throws IOException {
        if (currentlySelectedHumanProvince == null) {
            printMessageToTerminal("Must select a province to set tax rate");
            return;
        }
        String taxRate = set_tax_rate.getSelectionModel().getSelectedItem();
        if (taxRate == null) {
            printMessageToTerminal("Must select a tax rate to set tax rate");
            return;
        } else {
            String p = (String) currentlySelectedHumanProvince.getAttributes().get("name");
            Province province = getProvinceByName(p);
            province.setTaxRate(taxRate);
            printMessageToTerminal(p + " tax rate set to: " + taxRate);
            updateFactionDetails(totalTurns % allFactions.size());
        }
    }

    /**
     * Handles the click of the end turn button Tick the turns over and sets the new
     * faction to be the human faction
     */
    @FXML
    public void clickedEndTurnButton(ActionEvent e) throws IOException {
        Faction faction = allFactions.get(totalTurns % allFactions.size());
        // Apply soliders upkeep costs
        int upkeepCost = faction.applyUpKeepCosts();
        printMessageToTerminal(faction.getName() + " spent " + upkeepCost + " on soldier upkeep costs");

        // TODO: TESTS FOR WIN/LOSE SCREEN
        // faction.setAllConquered(true);

        // Check if the faction has won/lost the game
        // faction.setWealth(-1000);
        // faction.setTreasury(-1000);
        // faction.setProvincesOwned(new ArrayList<Province>());
        victoryCalculation(faction);
        // Apply end turn wealth, tax calculations
        endTurnCalculation(faction);
        // Check if we need to apply any bankruptcy penalties
        faction.applyBankruptcy();

        totalTurns = totalTurns + 1;

        if (totalTurns % allFactions.size() == 0) {
            turnNumber = turnNumber + 1;
        }

        makeUnitsAvailable();
        updateComboBox();
        updateFactionDetails(totalTurns % allFactions.size());
        addAllPointGraphics();   // reset graphics
        resetSelections();
    }

    /**
     * Updates the front-end display of the faction turn
     * 
     * @param index index of the faction to check based off the allFactions list
     */
    public void updateFactionDetails(int index) {
        // Update the details on the front-end, including the details of the current
        // faction's turn, wealth, treasury, provinces owned
        Faction startingFaction = allFactions.get(index);
        humanFaction = startingFaction.getName();

        startingFaction.conquestCalculation(allProvinces);

        // Update faction javafx details
        factionName.setText(startingFaction.getName());
        wealth.setText((int) startingFaction.getWealth() + "/400000");
        treasury.setText((int) startingFaction.getTreasury() + "/100000");
        conqueredStatus.setText(startingFaction.getProvincesOwned().size() + "/" + allProvinces.size());

        // Check victory
        // victoryCalculation(startingFaction);
    }

    /**
     * Adjust the faction wealth and treasury according to the tax rate every turn
     * 
     * @param faction faction to update the details for
     */
    public void endTurnCalculation(Faction faction) {
        faction.treasuryCalculation();
        faction.wealthCalculation();
        faction.conquestCalculation(allProvinces);
    }

    /**
     * Display the win or lose screen if the win or lose conditions have been met
     * for the specified faction
     * 
     * @param faction the faction to check if they have satisfied the campaign
     *                victory
     */
    public void victoryCalculation(Faction faction) {
        if (victory == null || victory.isEmpty() || campaignVictory == null) {
            return;
        }

        if (campaignVictory.checkCampaignVictory(victory, faction.isAllConquered(), faction.getWealth(),
                faction.getTreasury())) {
            // Win condition and display win screen
            gameMusic.stopGameMusic();
            Platform.runLater(new Runnable() {
                public void run() {
                    try {
                        WinMenuApplication winMenu = new WinMenuApplication();
                        winMenu.setFaction(faction.getName());
                        winMenu.setParentController(controller);
                        winMenu.setGameMusic(gameMusic);
                        winMenu.start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            // TODO: save and load so that we can close this window
            return;
        }
        if (faction.getProvincesOwned().size() == 0) {
            // Lose condition and display defeat screen
            gameMusic.stopGameMusic();
            Platform.runLater(new Runnable() {

                public void run() {
                    try {
                        LoseMenuApplication loseMenu = new LoseMenuApplication();
                        loseMenu.setFaction(faction.getName());
                        loseMenu.setParentController(controller);
                        loseMenu.setGameMusic(gameMusic);
                        loseMenu.start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
            // TODO: save and load
            return;
        }
    }

    /**
     * Handles the click of the settings button If the setttings button is clicked,
     * the settings menu will open
     */
    @FXML
    public void clickedSettings() {
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    SettingsMenuApplication settingsmenu = new SettingsMenuApplication();
                    settingsmenu.setParentController(controller);
                    settingsmenu.setGameMusic(gameMusic);
                    settingsmenu.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Handles the click of the close game button Closes the game
     */
    @FXML
    public void closeGame() {
        Stage stage = (Stage) settings.getScene().getWindow();
        stage.close();
    }

    /**
     * run this initially to update province owner, change feature in each
     * FeatureLayer to be visible/invisible depending on owner. Can also update
     * graphics initially
     */
    private void initializeProvinceLayers() throws JsonParseException, JsonMappingException, IOException {

        Basemap myBasemap = Basemap.createImagery();
        // myBasemap.getReferenceLayers().remove(0);
        map = new ArcGISMap(myBasemap);
        mapView.setMap(map);

        // note - tried having different FeatureLayers for AI and human provinces to
        // allow different selection colors, but deprecated setSelectionColor method
        // does nothing
        // so forced to only have 1 selection color (unless construct graphics overlays
        // to give color highlighting)
        GeoPackage gpkg_provinces = new GeoPackage("src/unsw/gloriaromanus/provinces_right_hand_fixed.gpkg");
        gpkg_provinces.loadAsync();
        gpkg_provinces.addDoneLoadingListener(() -> {
            if (gpkg_provinces.getLoadStatus() == LoadStatus.LOADED) {
                // create province border feature
                featureLayer_provinces = createFeatureLayer(gpkg_provinces);
                map.getOperationalLayers().add(featureLayer_provinces);

            } else {
                System.out.println("load failure");
            }
        });

        addAllPointGraphics();
    }

    /**
     * Update the graphics on the map
     * 
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    private void addAllPointGraphics() throws JsonParseException, JsonMappingException, IOException {
        mapView.getGraphicsOverlays().clear();

        InputStream inputStream = new FileInputStream(new File("src/unsw/gloriaromanus/provinces_label.geojson"));
        FeatureCollection fc = new ObjectMapper().readValue(inputStream, FeatureCollection.class);

        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();

        for (org.geojson.Feature f : fc.getFeatures()) {
            if (f.getGeometry() instanceof org.geojson.Point) {
                org.geojson.Point p = (org.geojson.Point) f.getGeometry();
                LngLatAlt coor = p.getCoordinates();
                Point curPoint = new Point(coor.getLongitude(), coor.getLatitude(), SpatialReferences.getWgs84());
                PictureMarkerSymbol s = null;
                String province = (String) f.getProperty("name");
                Province q = getProvinceByName(province);
                TextSymbol t = new TextSymbol(10, q.getFaction().getName() + "\n" + province + "\n"
                        + q.getAvailableUnits().size() + "-" + q.getNumTroops(), 0xFFFFFFFF, HorizontalAlignment.CENTER,
                        VerticalAlignment.BOTTOM);

                String factionName = q.getFaction().getName();
                s = applyFactionIcon(factionName);

                t.setHaloColor(0xFFFFFFFF);
                t.setHaloWidth(0);
                Graphic gPic = new Graphic(curPoint, s);
                Graphic gText = new Graphic(curPoint, t);
                graphicsOverlay.getGraphics().add(gPic);
                graphicsOverlay.getGraphics().add(gText);
            } else {
                System.out.println("Non-point geo json object in file");
            }

        }

        inputStream.close();
        mapView.getGraphicsOverlays().add(graphicsOverlay);
    }

    /**
     * Create the feature layer on the map
     * 
     * @param gpkg_provinces the map with the provinces
     * @return the feature layer
     */
    private FeatureLayer createFeatureLayer(GeoPackage gpkg_provinces) {
        FeatureTable geoPackageTable_provinces = gpkg_provinces.getGeoPackageFeatureTables().get(0);

        // Make sure a feature table was found in the package
        if (geoPackageTable_provinces == null) {
            System.out.println("no geoPackageTable found");
            return null;
        }

        // Create a layer to show the feature table
        FeatureLayer flp = new FeatureLayer(geoPackageTable_provinces);

        // https://developers.arcgis.com/java/latest/guide/identify-features.htm
        // listen to the mouse clicked event on the map view
        mapView.setOnMouseClicked(e -> {
            // was the main button pressed?
            if (e.getButton() == MouseButton.PRIMARY) {
                // get the screen point where the user clicked or tapped
                Point2D screenPoint = new Point2D(e.getX(), e.getY());

                // specifying the layer to identify, where to identify, tolerance around point,
                // to return pop-ups only, and
                // maximum results
                // note - if select right on border, even with 0 tolerance, can select multiple
                // features - so have to check length of result when handling it
                final ListenableFuture<IdentifyLayerResult> identifyFuture = mapView.identifyLayerAsync(flp,
                        screenPoint, 0, false, 25);

                // add a listener to the future
                identifyFuture.addDoneListener(() -> {
                    try {
                        // get the identify results from the future - returns when the operation is
                        // complete
                        IdentifyLayerResult identifyLayerResult = identifyFuture.get();
                        // a reference to the feature layer can be used, for example, to select
                        // identified features
                        if (identifyLayerResult.getLayerContent() instanceof FeatureLayer) {
                            FeatureLayer featureLayer = (FeatureLayer) identifyLayerResult.getLayerContent();
                            // select all features that were identified
                            List<Feature> features = identifyLayerResult.getElements().stream().map(f -> (Feature) f)
                                    .collect(Collectors.toList());

                            if (features.size() > 1) {
                                printMessageToTerminal(
                                        "Have more than 1 element - you might have clicked on boundary!");
                            } else if (features.size() == 1) {
                                // note maybe best to track whether selected...
                                Feature f = features.get(0);
                                String p = (String) f.getAttributes().get("name");
                                Province province = getProvinceByName(p);
                                if (province.getFaction().getName().equals(humanFaction)) {
                                    // TODO: deal with moving between provinces
                                    // province owned by human
                                    if (currentlySelectedHumanProvince != null) {
                                        featureLayer.unselectFeature(currentlySelectedHumanProvince);
                                    }
                                    currentlySelectedHumanProvince = f;
                                    invading_province.setText(province.getName());
                                    // Province p = getProvinceByName(province);
                                    updateComboBox(province);
                                } else {
                                    if (currentlySelectedEnemyProvince != null) {
                                        featureLayer.unselectFeature(currentlySelectedEnemyProvince);
                                    }
                                    currentlySelectedEnemyProvince = f;
                                    opponent_province.setText(province.getName());
                                }

                                featureLayer.selectFeature(f);
                            }

                        }
                    } catch (InterruptedException | ExecutionException ex) {
                        // ... must deal with checked exceptions thrown from the async identify
                        // operation
                        System.out.println("InterruptedException occurred");
                    }
                });
            }
        });
        return flp;
    }

    /**
     * Update the dropdown menus on the left side of the game screen
     * 
     * @param province the province that has been selected
     */
    public void updateComboBox(Province province) {
        number_units.getItems().clear();
        for (int i = 1; i <= province.getAvailableUnits().size(); i++) {
            String str = Integer.toString(i);
            number_units.getItems().add(str);
        }

        unit_to_move.getItems().clear();
        for (Unit unit : province.getAvailableUnits()) {
            String[] unitName = unit.getClass().getName().split("\\.");
            String str = unitName[unitName.length - 1] + " (" + unit.getCurrentMovementPoints() + ") ";
            unit_to_move.getItems().add(str);
        }
        provinces_to_move_to.getItems().clear();
    }

    /**
     * Update the dropdown menus on the left side of the game screen
     */
    public void updateComboBox() {
        if (currentlySelectedHumanProvince != null) {
            String p = (String) currentlySelectedHumanProvince.getAttributes().get("name");
            Province province = getProvinceByName(p);
            number_units.getItems().clear();
            for (int i = 1; i <= province.getUnits().size(); i++) {
                String str = Integer.toString(i);
                number_units.getItems().add(str);
            }

            unit_to_move.getItems().clear();
            for (Unit unit : province.getUnits()) {
                String[] unitName = unit.getClass().getName().split("\\.");
                String str = unitName[unitName.length - 1] + " (" + unit.getCurrentMovementPoints() + ") ";
                unit_to_move.getItems().add(str);
            }
            provinces_to_move_to.getItems().clear();
        }
    }

    /**
     * Update the list of possible destination provinces in the dropdown
     * 
     * @param startingProvince the province that is selected
     * @param unitToMove       the unit to be moved
     */
    public void updateDestProvinces(Province startingProvince, Unit unitToMove) {
        provinces_to_move_to.getItems().clear();
        for (Province destProvince : allProvinces) {
            // System.out
            // .println(destProvince.getName() +
            // startingProvince.canMoveTroops(destProvince, unitToMove, false));
            if (startingProvince.getName() != destProvince.getName()
                    && startingProvince.canMoveTroops(destProvince, unitToMove, false) != -1) {
                provinces_to_move_to.getItems().add(destProvince.getName());
                // System.out.println(allProvinces.size());
            }
        }
    }

    /**
     * Connects the province to the faction that owns it
     * 
     * @return a hash table with the provinces and factions
     * @throws IOException
     */
    private Map<String, String> getProvinceToOwningFactionMap() throws IOException {
        String content = Files.readString(Paths.get("src/unsw/gloriaromanus/province_ownership.json"));
        // String content =
        // Files.readString(Paths.get("src/unsw/gloriaromanus/initial_province_ownership.json"));
        JSONObject ownership = new JSONObject(content);

        // Transform data from landlocked_provinces.json into a traversible List
        content = Files.readString(Paths.get("src/unsw/gloriaromanus/landlocked_provinces.json"));
        JSONArray landlocked = new JSONArray(content);
        List<String> landlockedList = new ArrayList<String>();
        for (int i = 0; i < landlocked.length(); i++) {
            landlockedList.add(landlocked.getString(i));
        }

        Map<String, String> m = new HashMap<String, String>();

        // Traverse map to obtain all factions and provinces
        for (String key : ownership.keySet()) {
            // key will be the faction name
            Faction faction = new Faction(key);

            if (faction != null) {
                addFaction(faction);
            }

            JSONArray ja = ownership.getJSONArray(key);
            // value is province name
            for (int i = 0; i < ja.length(); i++) {
                String value = ja.getString(i);
                if (faction != null) {
                    Province province = null;
                    if (landlockedList.contains(value)) {
                        province = new Province(faction, value, true);
                    } else {
                        province = new Province(faction, value, false);
                    }

                    if (province != null) {
                        addProvince(province);
                        // provincesGraph.addVertex(province);
                    }
                }
                m.put(value, key);
            }
        }

        createProvinceGraph();
        return m;
    }

    /**
     * Add a faction to the list of factions
     * 
     * @param faction the faction to be added to the list
     */
    public void addFaction(Faction faction) {
        if (!allFactions.contains(faction) && !allFactionsName.contains(faction.getName())) {
            allFactions.add(faction);
            allFactionsName.add(faction.getName());
        }
    }

    /**
     * Add a province to the list of provinces
     * 
     * @param province the province to be added to the list
     */
    public void addProvince(Province province) {
        if (!allProvinces.contains(province) && !allProvincesName.contains(province.getName())) {
            allProvinces.add(province);
            allProvincesName.add(province.getName());
        }
    }

    /**
     * Add a unit to the list of units
     * 
     * @param unit the unit to be added to the list
     */
    public void addUnit(Unit unit) {
        if (!allUnits.contains(unit)) {
            allUnits.add(unit);
        }
    }

    /**
     * Creates a connected graph of all of the provinces
     * 
     * @throws IOException
     */
    private void createProvinceGraph() throws IOException {
        String content = Files
                .readString(Paths.get("src/unsw/gloriaromanus/province_adjacency_matrix_fully_connected.json"));

        // Read JSON from a file into a Map
        ObjectMapper provinceMapper = new ObjectMapper();
        Map<String, LinkedHashMap<Integer, String>> map = null;
        try {
            map = provinceMapper.readValue(content, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (map != null) {
            // Create list of unique provinces to reference
            List<Object> uniqueProvinces = new ArrayList<Object>();
            for (Map.Entry<String, LinkedHashMap<Integer, String>> entry : map.entrySet()) {
                if (!uniqueProvinces.contains(entry.getKey())) {
                    uniqueProvinces.add(entry.getKey());
                }
            }

            // Creating graph edges to indicate if the provinces are adjacent
            for (Map.Entry<String, LinkedHashMap<Integer, String>> entry : map.entrySet()) {
                Province rootProvince = getProvinceByName(entry.getKey());
                if (rootProvince != null) {
                    for (Object province : uniqueProvinces) {
                        Object connected = entry.getValue().get(province);
                        if (connected.equals(true)) {
                            // Creates the provinces adjacency list
                            Province toConnect = getProvinceByName(province.toString());
                            rootProvince.addAdjacent(toConnect);
                            toConnect.addAdjacent(rootProvince);
                        }
                    }
                }
            }
        }
    }

    /**
     * Get faction object that matches the given name
     * 
     * @param name name of faction to obtain from list
     * @return faction object that matches the given name
     */
    public Faction getFactionByName(String name) {
        Faction faction = null;
        for (Faction factions : allFactions) {
            if (factions.getName().equals(name)) {
                faction = factions;
            }
        }
        return faction;
    }

    /**
     * Get province object that matches the given name
     * 
     * @param name name of province to obtain from list
     * @return province object that matches the given name
     */
    public Province getProvinceByName(String name) {
        Province province = null;
        for (Province provinces : allProvinces) {
            if (provinces.getName().equals(name)) {
                province = provinces;
            }
        }
        return province;
    }

    /**
     * Get a list of all of the provinces controlled by the player whose turn it is
     * 
     * @return a list of provinces controlled by player
     * @throws IOException
     */
    private ArrayList<String> getHumanProvincesList() throws IOException {
        // https://developers.arcgis.com/labs/java/query-a-feature-layer/
        String content = Files.readString(Paths.get("src/unsw/gloriaromanus/province_ownership.json"));
        // String content =
        // Files.readString(Paths.get("src/unsw/gloriaromanus/initial_province_ownership.json"));
        JSONObject ownership = new JSONObject(content);
        return ArrayUtil.convert(ownership.getJSONArray(humanFaction));
    }

    /**
     * returns query for arcgis to get features representing human provinces can
     * apply this to FeatureTable.queryFeaturesAsync() pass string to
     * QueryParameters.setWhereClause() as the query string
     */
    private String getHumanProvincesQuery() throws IOException {
        LinkedList<String> l = new LinkedList<String>();
        for (String hp : getHumanProvincesList()) {
            l.add("name='" + hp + "'");
        }
        return "(" + String.join(" OR ", l) + ")";
    }

    /**
     * Checks if two given provinces are connected
     * 
     * @param province1 the first province
     * @param province2 the second province
     * @return true if th two provinces are connected and false if not
     * @throws IOException
     */
    private boolean confirmIfProvincesConnected(String province1, String province2) throws IOException {
        String content = Files
                .readString(Paths.get("src/unsw/gloriaromanus/province_adjacency_matrix_fully_connected.json"));
        JSONObject provinceAdjacencyMatrix = new JSONObject(content);
        return provinceAdjacencyMatrix.getJSONObject(province1).getBoolean(province2);
    }

    /**
     * reset the provinces currently selected on the map
     */
    private void resetSelections() {
        if (currentlySelectedEnemyProvince != null) {
            featureLayer_provinces.unselectFeature(currentlySelectedEnemyProvince);
        }
        if (currentlySelectedHumanProvince != null) {
            featureLayer_provinces.unselectFeature(currentlySelectedHumanProvince);

        }
        currentlySelectedEnemyProvince = null;
        currentlySelectedHumanProvince = null;
        invading_province.setText("");
        opponent_province.setText("");
    }

    /**
     * Takes in a message and prints it to the player terminal
     * 
     * @param message the message for the player
     */
    private void printMessageToTerminal(String message) {
        output_terminal.appendText(message + "\n");
    }

    /**
     * Stops and releases all resources used in application.
     */
    void terminate() {

        if (mapView != null) {
            mapView.dispose();
        }
    }

    /**
     * Getter method for current game turn number
     * 
     * @return current game turn number
     */
    public int getTurnNumber() {
        return turnNumber;
    }

    /**
     * Setter method for turn number
     * 
     * @param turnNumber game turn number
     */
    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    /**
     * Saves the state of the game
     * 
     * @param filename intended file savename
     * @param test     if used in test or not
     */
    public void saveGame(String filename, boolean test) {
        this.filename = filename;
        JSONObject turnDetails = turnNumberDetails();

        try {
            File file = new File("src/unsw/gloriaromanus/saved/" + filename);
            if (test) {
                // Different pathing for test
                file = new File("bin/unsw/gloriaromanus/saved/" + filename);
            }
            file.createNewFile();
            FileWriter fr = new FileWriter(file);

            fr.write(turnDetails.toString() + "\n");
            fr.write(totalTurnDetails().toString() + "\n");
            fr.write(victoryDetails().toString() + "\n");

            for (Faction faction : allFactions) {
                JSONObject factionDetails = new JSONObject();
                factionDetails = faction.saveGame();
                fr.write(factionDetails.toString() + "\n");
            }

            for (Province province : allProvinces) {
                JSONObject provinceDetails = new JSONObject();
                provinceDetails = province.saveGame();
                fr.write(provinceDetails.toString() + "\n");
            }

            for (Unit unit : allUnits) {
                JSONObject unitDetails = new JSONObject();
                unitDetails = unit.saveGame();
                fr.write(unitDetails.toString() + "\n");
            }

            fr.flush();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert turn details into a JSONObject
     * 
     * @return JSONObject of turn details
     */
    public JSONObject totalTurnDetails() {
        JSONObject totalTurnDetails = new JSONObject();
        totalTurnDetails.put("type", "totalTurn");
        totalTurnDetails.put("number", getTotalTurns());
        return totalTurnDetails;
    }

    /**
     * Convert turn details into a JSONObject
     * 
     * @return JSONObject of turn details
     */
    public JSONObject turnNumberDetails() {
        JSONObject turnDetails = new JSONObject();
        turnDetails.put("type", "turnNumber");
        turnDetails.put("number", getTurnNumber());
        return turnDetails;
    }

    /**
     * Convert victory goal details into a JSONObject
     * 
     * @return JSONObject of turn details
     */
    public JSONObject victoryDetails() {
        JSONObject turnDetails = new JSONObject();
        turnDetails.put("type", "victory");
        String victoryGoals = victory.toString().replaceAll("[\\[\\]\\s]", "");
        turnDetails.put("goals", victoryGoals);
        return turnDetails;
    }

    /**
     * Add a unit to each province at the beginning of the game
     */
    public void addUnitsToProvinces() {
        for (Province p : allProvinces) {
            Unit unit = new MeleeInfantry(0);
            unit.setAvailable(true);
            p.addUnit(unit);
            System.out.println("added unit" + p.getName());
        }
    }

    /**
     * Load saved state of the game
     * 
     * @param filename instead load filename
     * @param test     if used in test
     * @throws IOException
     */
    public void loadGame(String filename, boolean test) throws IOException {
        String filepath = "src/unsw/gloriaromanus/saved/" + filename;
        if (test) {
            // for test pathing only
            filepath = "bin/unsw/gloriaromanus/saved/" + filename;
        }
        File f = new File(filepath);
        if (f.exists() && !f.isDirectory()) {
            String content = Files.readString(Paths.get(filepath));

            String str[] = content.split("\n");
            List<String> al = new ArrayList<String>();
            al = Arrays.asList(str);
            for (String s : al) {
                JSONObject details = new JSONObject(s);
                processDetails(details);
            }
        }
    }

    /**
     * Process details of the JSONObject
     * 
     * @param details JSONObject to convert
     */
    public void processDetails(JSONObject details) {
        switch (details.getString("type")) {
            case "turnNumber":
                processTurn(details);
                break;
            case "totalTurn":
                processTotalTurns(details);
                break;
            case "victory":
                processVictory(details);
                break;
            case "faction":
                processFaction(details);
                break;
            case "province":
                processProvince(details);
                break;
            case "unit":
                processUnit(details);
                break;
        }
    }

    /**
     * Use JSONObject details to set the game turn number
     * 
     * @param details JSONObject to convert
     */
    public void processTurn(JSONObject details) {
        int number = details.getInt("number");
        setTurnNumber(number);
    }

    /**
     * Use JSONObject details to set the game's total turn number
     * 
     * @param details JSONObject to convert
     */
    public void processTotalTurns(JSONObject details) {
        int number = details.getInt("number");
        setTotalTurns(number);
    }

    /**
     * Use JSONObject details to set the game's victory goals
     * 
     * @param details JSONObject to convert
     */
    public void processVictory(JSONObject details) {
        String victoryDetails = details.getString("goals");
        List<String> goals = new ArrayList<String>(Arrays.asList(victoryDetails.split(",")));
        victory = new ArrayList<String>();
        victory.addAll(goals);
        System.out.println("Loaded Victory: " + victory);
        try {
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // goals = victoryDetails.split(",");
        // victory = victoryDetails;
    }

    /**
     * Use JSONObject details to recreate the factions from the saved game
     * 
     * @param details JSONObject to convert
     */
    public void processFaction(JSONObject details) {
        String name = details.getString("name");
        double wealth = details.getDouble("wealth");
        double treasury = details.getDouble("treasury");
        boolean allConquered = details.getBoolean("allConquered");

        Faction faction = new Faction(name);
        faction.setWealth(wealth);
        faction.setTreasury(treasury);
        faction.setAllConquered(allConquered);
        addFaction(faction);
    }

    /**
     * Use JSONObject details to recreate the provinces from the saved game
     * 
     * @param details JSONObject to convert
     */
    public void processProvince(JSONObject details) {
        String name = details.getString("name");
        String factionName = details.getString("faction");
        Faction faction = getFactionByName(factionName);
        double wealth = details.getDouble("wealth");
        double treasury = details.getDouble("treasury");
        boolean landlocked = details.getBoolean("landlocked");
        String taxRate = details.getString("taxRate");

        Province province = new Province(faction, name, landlocked);
        faction.addProvince(province);

        province.setWealth(wealth);
        province.setTreasury(treasury);
        province.setTaxRate(taxRate);

        addProvince(province);
    }

    /**
     * Use JSONObject details to recreate the units from the saved game
     * 
     * @param details JSONObject to convert
     */
    public void processUnit(JSONObject details) {
        Unit unit = null;
        String type = details.getString("name");
        String provinceName = details.getString("province");
        Province province = getProvinceByName(provinceName);

        int cost = details.getInt("cost");
        int turnsToRecruit = details.getInt("turnsToRecruit");
        int movementPoints = details.getInt("movementPoints");
        boolean ranged = details.getBoolean("ranged");
        boolean available = details.getBoolean("available");
        int numTroops = details.getInt("numTroops");
        double armour = details.getDouble("armour");
        double speed = details.getDouble("speed");
        double rangedAttackDamage = details.getDouble("rangedAttackDamage");
        double defenseSkill = details.getDouble("defenseSkill");
        double shieldDefense = details.getDouble("shieldDefense");

        unit = processUnitClass(details, type, unit);

        if (unit != null && province != null) {
            addUnit(unit);
            province.addUnit(unit);

            unit.setCost(cost);
            unit.setTurnsToRecruit(turnsToRecruit);
            unit.setMovementPoints(movementPoints);
            unit.setRanged(ranged);
            unit.setAvailable(available);
            unit.setNumTroops(numTroops);
            unit.setArmour(armour);
            unit.setSpeed(speed);
            unit.setRangedAttackDamage(rangedAttackDamage);
            unit.setDefenseSkill(defenseSkill);
            unit.setShieldDefense(shieldDefense);
        }
    }

    /**
     * Use JSONObject details to find what type of unit to recreate
     * 
     * @param details JSONObject to convert
     * @param type    stores the unit class to recreate
     * @param unit    unit to create
     * @return recreated unit
     */
    public Unit processUnitClass(JSONObject details, String type, Unit unit) {
        int recruitmentStart = details.getInt("recruitmentStart");
        double meleeAttackDamage = details.getDouble("meleeAttackDamage");
        if (type.contains("Berserker")) {
            unit = new Berserker(recruitmentStart);
        } else {
            double morale = details.getDouble("morale");
            if (type.contains("Artillery")) {
                unit = new Artillery(recruitmentStart);
            } else if (type.contains("Druid")) {
                unit = new Druid(recruitmentStart);
            } else if (type.contains("Hoplite")) {
                unit = new Hoplite(recruitmentStart);
            } else if (type.contains("JavelinSkirmisher")) {
                unit = new JavelinSkirmisher(recruitmentStart);
            } else if (type.contains("MeleeInfantry")) {
                unit = new MeleeInfantry(recruitmentStart);
            } else if (type.contains("Missile")) {
                unit = new MissileInfantry(recruitmentStart);
            } else if (type.contains("Pikeman")) {
                unit = new Pikeman(recruitmentStart);
            } else if (type.contains("Spearman")) {
                unit = new Spearman(recruitmentStart);
            } else {
                double chargeStat = details.getDouble("chargeStat");
                if (type.contains("Chariot")) {
                    unit = new Chariot(recruitmentStart);
                } else if (type.contains("Elephant")) {
                    unit = new Elephant(recruitmentStart);
                } else if (type.contains("HorseArcher")) {
                    unit = new HorseArcher(recruitmentStart);
                } else if (type.contains("Lancer")) {
                    unit = new Lancer(recruitmentStart);
                } else if (type.contains("MeleeCavalry")) {
                    unit = new MeleeCavalry(recruitmentStart);
                }
                unit.setChargeStat(chargeStat);
            }
            unit.setMorale(morale);
            unit.setMeleeAttackDamage(meleeAttackDamage);
        }
        return unit;
    }

    /**
     * Resets the state of the game
     */
    public void resetState() {
        turnNumber = 0;
        allFactions = new ArrayList<Faction>();
        allProvinces = new ArrayList<Province>();
        allUnits = new ArrayList<Unit>();
        victory = new ArrayList<String>();
    }

    /**
     * Getter method for list of all factions created
     * 
     * @return list of all factions created
     */
    public List<Faction> getAllFactions() {
        return allFactions;
    }

    /**
     * Getter method for list of all provinces created
     * 
     * @return list of all provinces created
     */
    public List<Province> getAllProvinces() {
        return allProvinces;
    }

    /**
     * Getter method for list of all units created
     * 
     * @return list of all units created
     */
    public List<Unit> getAllUnits() {
        return allUnits;
    }

    /**
     * Getter method for total number of turns
     * 
     * @return the number of turns
     */
    public int getTotalTurns() {
        return totalTurns;
    }

    /**
     * Setter method for the total number of turns
     * 
     * @param totalTurns the number of turns
     */
    public void setTotalTurns(int totalTurns) {
        this.totalTurns = totalTurns;
    }

    /**
     * Returns true if the faction has enough treasury balance to recruit a unit
     * 
     * @param faction faction that wants to recruit a unit
     * @param unit    unit to recruit
     * @return true if the faction has enough treasury balance to recruit a unit
     */
    public boolean canRecruit(Faction faction, Province province, Unit unit) {
        if (unit instanceof Berserker) {
            if (!(faction.getName().equals("Gaul") || faction.getName().equals("Celtic Briton")
                    || faction.getName().equals("Germania"))) {
                return false;
            }
        }

        if (faction.getTreasury() < unit.getCost()) {
            return false;
        }
        if (province.getTrainingUnits().size() == 2) {
            return false;
        }
        return true;
    }

    /**
     * Make units available when they have finished training (used at the beginning
     * of each round)
     */
    public void makeUnitsAvailable() {
        // System.out.println("num factions: "+allFactions);
        for (Province province : allProvinces) {
            List<Unit> units = province.getUnits();

            for (Unit unit : units) {
                int available = unit.getRecruitmentStart() + unit.getTurnsToRecruit() * allFactions.size();
                if (totalTurns == available) {
                    unit.setAvailable(true);
                    province.setNumTraining(province.getNumTraining() - 1);
                } else if (totalTurns > available) {
                    unit.setAvailable(true);
                }
                unit.setCurrentMovementPoints(unit.getMovementPoints());
            }
        }
    }

    /**
     * Applies the appropriate faction icon
     * 
     * @param faction the faction the province belongs to
     * @return the symbol that corresponds to the faction
     */
    private PictureMarkerSymbol applyFactionIcon(String faction) {
        PictureMarkerSymbol s = new PictureMarkerSymbol(
                new Image((new File("images/Celtic_Druid.png")).toURI().toString()));

        if (faction.contains("Rome")) {
            s = new PictureMarkerSymbol("images/legionary.png");
        } else if (faction.contains("Gaul")) {
            s = new PictureMarkerSymbol(new Image((new File("images/Gaul.png")).toURI().toString()));
        } else if (faction.contains("Greek")) {
            s = new PictureMarkerSymbol("images/Greek.png");
        } else if (faction.contains("Germania")) {
            s = new PictureMarkerSymbol("images/Germania.png");
        } else if (faction.contains("Carthaginian")) {
            s = new PictureMarkerSymbol("images/Carthaginian.png");
        } else if (faction.contains("Spain")) {
            s = new PictureMarkerSymbol("images/Spain.png");
        } else if (faction.contains("Numidian")) {
            s = new PictureMarkerSymbol("images/Numidian.png");
        } else if (faction.contains("Egyptians")) {
            s = new PictureMarkerSymbol("images/Egyptians.png");
        } else if (faction.contains("Seleucid")) {
            s = new PictureMarkerSymbol("images/Seleucid.png");
        } else if (faction.contains("Pontus")) {
            s = new PictureMarkerSymbol("images/Pontus.png");
        } else if (faction.contains("Amenia")) {
            s = new PictureMarkerSymbol("images/Amenia.png");
        } else if (faction.contains("Parthian")) {
            s = new PictureMarkerSymbol("images/Parthian.png");
        } else if (faction.contains("Macedonian")) {
            s = new PictureMarkerSymbol("images/Macedonian.png");
        } else if (faction.contains("Thracian")) {
            s = new PictureMarkerSymbol("images/Thracian.png");
        } else if (faction.contains("Dacians")) {
            s = new PictureMarkerSymbol("images/Dacians.png");
        }
        return s;
    }
}