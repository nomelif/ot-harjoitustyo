package ui;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import java.lang.Thread;

import javax.imageio.ImageIO;
import java.io.*;

import map.*;
import file.*;

public class Ui extends Application {

    private TextField seed;

    private Slider mountainScale;
    private Slider mountainCutoff;
    private Slider largeFeatureScale;
    private Slider seaCutoff;
    private Slider erosionIterations;

    private Button updateButton;
    private Button saveButton;

    private Label status;

    private Map map;
    private MapTask task;

    private ImageView result;

    private Stage window;

    private MapAppFile file;

    @Override
    public void start(Stage window) {

        this.window = window;
        constructControls();
        this.file = new MapAppFile(readState());
        hookButtons();
        setWindowParameters();
    }

    private Pane constructLayout() {

        SplitPane mainSplit = new SplitPane();

        VBox parameterPane = new VBox(new Label("Random seed"), seed, new Label("Mountain scale multiplier (smaller number = smaller mountains)"), mountainScale, new Label("Mountain cutoff"), mountainCutoff, new Label("Larger feature scale multiplier (smaller number = smaller features)"), largeFeatureScale, new Label("Sea level cutoff"), seaCutoff, new Label("Erosion iterations"), erosionIterations, new HBox(updateButton, saveButton));
        VBox resultPane = new VBox(result);

        mainSplit.getItems().addAll(parameterPane, resultPane);

        BorderPane mainBorderPane = new BorderPane();
        mainBorderPane.setTop(constructMenu());
        mainBorderPane.setCenter(mainSplit);
        mainBorderPane.setBottom(status);

        return mainBorderPane;
    }

    private MenuBar constructMenu(){
        Menu editMenu = new Menu("Edit");
        
        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setOnAction(e -> {
            undo();
        });
        MenuItem redoItem = new MenuItem("Redo");
        redoItem.setOnAction(e -> {
            redo();
        });
        
        editMenu.getItems().add(undoItem);
        editMenu.getItems().add(redoItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(editMenu);
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
        window.setHeight(567);
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
        saveButton = new Button("Save map");
        saveButton.setDisable(true);

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
                saveButton.setDisable(false);
            });

            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(task);
            executorService.shutdown();
        });
    }

    private void hookSave() {

        FileChooser fileChooser = new FileChooser();

        saveButton.setOnAction(actionEvent -> {
            File file = fileChooser.showSaveDialog(window);
            if (file != null) {
                try {
                    ImageIO.write(map.toBufferedImage(), "png", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void hookButtons() {
        hookUpdate();
        hookSave();
    }

    public static void run() {
        launch(Ui.class);
    }
}
