package ui;

import java.io.FileOutputStream;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;


import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import java.lang.Thread;

import javax.imageio.ImageIO;
import java.io.*;

import com.google.gson.Gson;

import map.*;
import file.*;

/**
 * Main UI class for MapApp
 *
 * <p>
 *
 * The intended way to use this is to first create an instance via the constructor and then call run().
 */
public class Ui extends Application {

    private TextField seed;

    private Slider mountainScale;
    private Slider mountainCutoff;
    private Slider largeFeatureScale;
    private Slider seaCutoff;
    private Slider erosionIterations;

    private Button updateButton;
    private MenuItem exportItem;
    private MenuItem exportOBJItem;
    private MenuItem saveItem;
    private MenuItem openItem;

    private Label status;

    private Map map;
    private MapTask task;

    private ImageView result;

    private Stage window;

    private MapAppFile file;

    /**
     * Inherited from javafx.application.Application
     */
    @Override
    public void start(Stage window) {

        this.window = window;
        constructControls();
        this.file = new MapAppFile(readState());
        setWindowParameters();
        hookUpdate();
        hookExport();
        hookExportOBJ();
        hookSave();
        hookOpen();
    }

    private Pane constructLayout() {

        SplitPane mainSplit = new SplitPane();

        VBox parameterPane = new VBox(new Label("Random seed"), seed, new Label("Mountain scale multiplier (smaller number = smaller mountains)"), mountainScale, new Label("Mountain cutoff"), mountainCutoff, new Label("Larger feature scale multiplier (smaller number = smaller features)"), largeFeatureScale, new Label("Sea level cutoff"), seaCutoff, new Label("Erosion iterations"), erosionIterations, updateButton);
        VBox resultPane = new VBox(result);

        mainSplit.getItems().addAll(parameterPane, resultPane);

        BorderPane mainBorderPane = new BorderPane();
        mainBorderPane.setTop(constructMenu());
        mainBorderPane.setCenter(mainSplit);
        mainBorderPane.setBottom(status);

        return mainBorderPane;
    }

    private Menu constructEditMenu(){
        Menu editMenu = new Menu("Edit");
        
        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setOnAction(e -> {
            undo();
        });
        undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        MenuItem redoItem = new MenuItem("Redo");
        redoItem.setOnAction(e -> {
            redo();
        });
        redoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        
        editMenu.getItems().add(undoItem);
        editMenu.getItems().add(redoItem);

        return editMenu;
    }

    private Menu constructFileMenu(){
        Menu fileMenu = new Menu("File");
        
        exportItem = new MenuItem("Export map (.PNG)");
        exportItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        exportItem.setDisable(true);

        exportOBJItem = new MenuItem("Export map (.OBJ)");
        exportOBJItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        exportOBJItem.setDisable(true);

        saveItem = new MenuItem("Save map (.MAP)");
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        openItem = new MenuItem("Open map (.MAP)");
        openItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        fileMenu.getItems().add(openItem);
        fileMenu.getItems().add(saveItem);
        fileMenu.getItems().add(exportItem);
        fileMenu.getItems().add(exportOBJItem);
        
        return fileMenu;

    }

    private MenuBar constructMenu(){

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(constructFileMenu());
        menuBar.getMenus().add(constructEditMenu());
        return menuBar;
    }

    private OptionCollection readState(){
        return new OptionCollection(seed.getText(), mountainScale.getValue(), mountainCutoff.getValue(), largeFeatureScale.getValue(), seaCutoff.getValue(), ((int) erosionIterations.getValue()));
    }

    private void updateFile(){
        this.file.update(readState());
    }

    private void apply(OptionCollection state){
        seed.setText(state.seed);
        mountainScale.setValue(state.mountainScale);
        mountainCutoff.setValue(state.mountainCutoff);
        largeFeatureScale.setValue(state.largeFeatureScale);
        seaCutoff.setValue(state.seaCutoff);
        erosionIterations.setValue(state.erosionIterations);
    }

    private void undo(){
        // Check if there is new text and commit that
        if(!seed.getText().equals(file.state().seed))
            updateFile();
        file.undo();
        apply(file.state());

    }

    private void redo(){
        // Check if there is new text and commit that
        if(!seed.getText().equals(file.state().seed)){
            updateFile();
            return; // We are at the newest change then
        }
        file.redo();
        apply(file.state());
    }

    private void setWindowParameters() {
        window.setScene(new Scene(constructLayout()));
        window.setWidth(1000);
        window.setHeight(587);
        window.setTitle("MapApp");
        window.show();
    }

    private Slider monitoredSlider(double minimum, double maximum, double initial){
        Slider slider = new Slider(minimum, maximum, initial);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setOnMouseReleased(new EventHandler<MouseEvent>() {

            private double previous = initial;

            @Override
            public void handle(MouseEvent mouseEvent) {
                if(previous != slider.getValue()){
                    previous = slider.getValue();
                    updateFile();
                }
            }
        });
        return slider;
    }

    private void constructControls() {

        seed = new TextField();
        seed.focusedProperty().addListener(event -> {
            if(!seed.isFocused()) {

                // Undo and redo will have to check whether the seed matches the "current" state and might have to commit it to the MapAppFile object

                updateFile();
            }
        });

        mountainScale = monitoredSlider(0, 100, 10);
        mountainCutoff = monitoredSlider(0, 1, 0.6);
        largeFeatureScale = monitoredSlider(0, 10, 1);
        seaCutoff = monitoredSlider(0, 1, 0.3);
        erosionIterations = monitoredSlider(0, 1000, 500);

        updateButton = new Button("Update map");

        result = new ImageView();

        status = new Label("");
    }

    private void hookUpdate() {
        updateButton.setOnAction(actionEvent -> {

            if (task != null) {
                task.cancel();
            }

            task = new MapTask(512, readState());

            status.textProperty().bind(task.messageProperty());

            task.setOnSucceeded((succeededEvent) -> {
                map = task.getValue();
                result.setImage(SwingFXUtils.toFXImage(map.toBufferedImage(), null));
                exportItem.setDisable(false);
                exportOBJItem.setDisable(false);
            });

            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(task);
            executorService.shutdown();
        });
    }


    private void hookSave() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Map files", "*.map"));

        saveItem.setOnAction(actionEvent -> {
            File fileName = fileChooser.showSaveDialog(window);
            if (fileName != null) {
                Gson g = new Gson();
                try {
                    Files.writeString(fileName.toPath(), g.toJson(file));
                } catch (IOException e) {
                    new Alert(Alert.AlertType.ERROR, "Tiedoston tallentaminen epäonnistui", null).show();
                }
            }
        });
    }


    private void hookOpen() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Map files", "*.map"));

        openItem.setOnAction(actionEvent -> {
            File fileName = fileChooser.showOpenDialog(window);
            if (fileName != null) {
                Gson g = new Gson();
                try {
                    file = g.fromJson(Files.lines(fileName.toPath()).collect(Collectors.joining("\n")), MapAppFile.class);
                    apply(file.state());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Tiedoston aukaiseminen epäonnistui", null).show();
                }
            }
        });
    }

    private void hookExport() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG images", "*.png"));

        exportItem.setOnAction(actionEvent -> {
            File file = fileChooser.showSaveDialog(window);
            if (file != null) {
                try {
                    FileOutputStream stream = new FileOutputStream(file);
                    ImageIO.write(map.toBufferedImage(), "png", stream);
                    stream.close();
                } catch (IOException e) {
                    new Alert(Alert.AlertType.ERROR, "Kuvan tallentaminen epäonnistui", null).show();
                }
            }
        });
    }

    private void hookExportOBJ() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("OBJ files", "*.obj"));

        exportOBJItem.setOnAction(actionEvent -> {
            File fileName = fileChooser.showSaveDialog(window);
            if (fileName != null) {
                try {
                    Files.writeString(fileName.toPath(), map.toWavefrontOBJ());
                } catch (IOException e) {
                    new Alert(Alert.AlertType.ERROR, "3D-mallin tallentaminen epäonnistui", null).show();
                }
            }
        });
    }

    /**
     * Launch the UI.
     */
    public static void run() {
        launch(Ui.class);
    }
}
