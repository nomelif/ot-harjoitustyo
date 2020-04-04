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

        status = new Label("");

        VBox parameterPane = new VBox(new Label("Random seed"), seed, new Label("Mountain scale multiplier (smaller number = smaller mountains)"), mountainScale, new Label("Mountain cutoff"), mountainCutoff, new Label("Larger feature scale multiplier (smaller number = smaller features)"), largeFeatureScale, new Label("Sea level cutoff"), seaCutoff, updateButton);
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
