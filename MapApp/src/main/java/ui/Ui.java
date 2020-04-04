package ui;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import map.Map;

public class Ui extends Application {

    private TextField seed;

    private Slider mountainScale;
    private Slider mountainCutoff;
    private Slider largeFeatureScale;
    private Slider seaCutoff;
    private Slider erosionIterations;

    private Button updateButton;

    private Label status;

    @Override
    public void start(Stage window){

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
            int width = 512;
            int seedValue = seed.getText().hashCode();
            System.out.println(seedValue);
            Map m = new Map(width, width, seedValue);

            status.setText("Generating mountains");
            m.makePerlin(mountainScale.getValue(), 1, 0);
            m.waterCutoff(mountainCutoff.getValue());

            status.setText("Generating large features");
            m.makePerlin(largeFeatureScale.getValue(), 0.6, 0);
            m.waterCutoff(seaCutoff.getValue());

            status.setText("Calculating erosion");

            int iterations = ((int) erosionIterations.getValue());

            for(int i = 0; i < iterations; i++){
                status.setText("Calculating erosion, iteration " + i + " / " + iterations);
                m.doErosion(100000, 500);
            }

            status.setText("Done");

        });

        status = new Label("");

        VBox parameterPane = new VBox(new Label("Random seed"), seed, new Label("Mountain scale multiplier (smaller number = smaller mountains)"), mountainScale, new Label("Mountain cutoff"), mountainCutoff, new Label("Larger feature scale multiplier (smaller number = smaller features)"), largeFeatureScale, new Label("Sea level cutoff"), seaCutoff, new Label("Erosion iterations"), erosionIterations, updateButton);
        VBox resultPane = new VBox();

        mainSplit.getItems().addAll(parameterPane, resultPane);

        BorderPane mainBorderPane = new BorderPane();
        mainBorderPane.setCenter(mainSplit);
        mainBorderPane.setBottom(status);

        Scene scene = new Scene(mainBorderPane);

        window.setScene(scene);

        window.setTitle("MapApp");
        window.show();
    }

    public static void run(){
        launch(Ui.class);
    }
}
