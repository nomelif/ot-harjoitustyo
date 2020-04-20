package map;

import javafx.concurrent.Task;

public class MapTask extends Task<Map> {

    private OptionCollection options;
    private int width;

    public MapTask(int width, OptionCollection options) {
        this.options = options;
        this.width = width;
    }

    @Override
    public Map call() {
        Map m = new Map(width, width, options.seed.hashCode());

        if (isCancelled()) {
            return null;
        }

        updateMessage("Generating mountains");
        m.makePerlin(options.mountainScale, 1, 0);
        m.waterCutoff(options.mountainCutoff);

        if (isCancelled()) {
            return null;
        }

        updateMessage("Generating large features");
        m.makePerlin(options.largeFeatureScale, 0.6, 0);
        m.waterCutoff(options.seaCutoff);

        for (int i = 0; i < options.erosionIterations; i++) {
            
            if (isCancelled()) {
                return null;
            }

            updateMessage("Calculating erosion, iteration " + i + " / " + options.erosionIterations);
            m.doErosion(100000, 500);
        }

        if (isCancelled()) {
            return null;
        }

        updateMessage("Done");
        return m;
    }
}
