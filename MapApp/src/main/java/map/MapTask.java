package map;

import javafx.concurrent.Task;

public class MapTask extends Task<Map> {

    private boolean alive = true;

    private int width;
    private int seedValue;
    private double mountainScale;
    private double mountainCutoff;
    private double largeFeatureScale;
    private double seaCutoff;
    private int erosionIterations;

    public MapTask(int width, int seedValue, double mountainScale, double mountainCutoff, double largeFeatureScale, double seaCutoff, int erosionIterations) {

        this.width = width;
        this.seedValue = seedValue;
        this.mountainScale = mountainScale;
        this.mountainCutoff = mountainCutoff;
        this.largeFeatureScale = largeFeatureScale;
        this.seaCutoff = seaCutoff;
        this.erosionIterations = erosionIterations;

    }

    @Override
    public Map call() {
        Map m = new Map(width, width, seedValue);

        if (isCancelled()) return null;
        updateMessage("Generating mountains");
        m.makePerlin(mountainScale, 1, 0);
        m.waterCutoff(mountainCutoff);

        if (isCancelled()) return null;
        updateMessage("Generating large features");
        m.makePerlin(largeFeatureScale, 0.6, 0);
        m.waterCutoff(seaCutoff);

        int iterations = erosionIterations;

        for (int i = 0; i < iterations; i++) {
            if (isCancelled()) return null;
            updateMessage("Calculating erosion, iteration " + i + " / " + iterations);
            m.doErosion(100000, 500);
        }

        if (isCancelled()) return null;
        updateMessage("Done");
        return m;
    }
}
