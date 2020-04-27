package map;

import javafx.concurrent.Task;

/**
 * Callable to represent a long-running map computation.
 */
public class MapTask extends Task<Map> {

    private OptionCollection options;
    private int width;
    private boolean testingMode = false;

    /**
     * @param width The width of the Map to compute. Functions as the height too.
     * @param options The parameters of the Map to compute.
     */
    public MapTask(int width, OptionCollection options) {
        this.options = options;
        this.width = width;
    }

    /**
     * Enables testing mode. In testing mode, updateMessage never gets called and the call() method is safe to call from a non-JavaFX context.
     */
    public void enableTestingMode() {
        this.testingMode = true;
    }

    /**
     * Calls updateMessage with message if the task has not been cancelled and testingMode is not enabled.
     * @param message Message to pass along to updateMessage.
     */
    private void maybeUpdateMessage(String message) {
        if (!testingMode && !isCancelled()) {
            updateMessage(message);
        }
    }

    /**
     * @return A Map instance matching the given OptionCollection or null if the MapTask is cancelled. When called on two MapTask instances constructed with identical OptionCollections, this is guaranteed to produce two identical Map instances if neither MapTask is cancelled.
     */
    @Override
    public Map call() {
        Map m = new Map(width, width, options.seed.hashCode());

        maybeUpdateMessage("Generating mountains");
        m.makePerlin(options.mountainScale, 1, 0);
        m.waterCutoff(options.mountainCutoff);

        maybeUpdateMessage("Generating large features");
        m.makePerlin(options.largeFeatureScale, 0.6, 0);
        m.waterCutoff(options.seaCutoff);

        for (int i = 0; i < options.erosionIterations; i++) {
            
            if (isCancelled()) {
                return null;
            }

            maybeUpdateMessage("Calculating erosion, iteration " + i + " / " + options.erosionIterations);
            m.doErosion(100000, 500);
        }

        maybeUpdateMessage("Done");
        return m;
    }
}
