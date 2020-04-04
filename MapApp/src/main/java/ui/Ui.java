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
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import java.lang.Thread;

import javax.imageio.ImageIO;
import java.io.*;

import map.*;

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

    @Override
    public void start(Stage window){

        FileChooser fileChooser = new FileChooser();

        SplitPane mainSplit = new SplitPane();

        seed = new TextField();

        mountainScale = new Slider(0, 100, 10);
        mountainScale.setShowTickMarks(true);
        mountainScale.setShowTickLabels(true);

        mountainCutoff = new Slider(0, 1, 0.6);
        mountainCutoff.setShowTickMarks(true);
        mountainCutoff.setShowTickLabels(true);

        largeFeatureScale = new Slider(0, 10, 1);
        largeFeatureScale.setShowTickMarks(true);
        largeFeatureScale.setShowTickLabels(true);

        seaCutoff = new Slider(0, 1, 0.3);
        seaCutoff.setShowTickMarks(true);
        seaCutoff.setShowTickLabels(true);

        erosionIterations = new Slider(0, 1000, 500);
        erosionIterations.setShowTickMarks(true);
        erosionIterations.setShowTickLabels(true);

        updateButton = new Button("Update map");
        updateButton.setOnAction(actionEvent -> {

            if(task != null){
                task.cancel();
            }

            task = new MapTask(512, seed.getText().hashCode(), mountainScale.getValue(), mountainCutoff.getValue(), largeFeatureScale.getValue(), seaCutoff.getValue(), ((int) erosionIterations.getValue()));

            status.textProperty().bind(task.messageProperty());

            task.setOnSucceeded((succeededEvent) -> {
                map = task.getValue();
                result.setImage(SwingFXUtils.toFXImage(map.toBufferedImage(), null));
            });

            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(task);
            executorService.shutdown();
        });

        saveButton = new Button("Save map");
        saveButton.setOnAction(actionEvent -> {
            File file = fileChooser.showOpenDialog(window);
                if (file != null) {
                    try{
                        ImageIO.write(map.toBufferedImage(), "png", file);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
        });

        result = new ImageView();

        status = new Label("");

        VBox parameterPane = new VBox(new Label("Random seed"), seed, new Label("Mountain scale multiplier (smaller number = smaller mountains)"), mountainScale, new Label("Mountain cutoff"), mountainCutoff, new Label("Larger feature scale multiplier (smaller number = smaller features)"), largeFeatureScale, new Label("Sea level cutoff"), seaCutoff, new Label("Erosion iterations"), erosionIterations, new HBox(updateButton, saveButton));
        VBox resultPane = new VBox(result);

        mainSplit.getItems().addAll(parameterPane, resultPane);

        BorderPane mainBorderPane = new BorderPane();
        mainBorderPane.setCenter(mainSplit);
        mainBorderPane.setBottom(status);

        Scene scene = new Scene(mainBorderPane);

        window.setScene(scene);

        window.setTitle("MapApp");
        window.show();
    }

    public void setStatus(String text){
        status.setText(text);
    }

    public static void run(){
        launch(Ui.class);
    }
}
