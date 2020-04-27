package map;

import javafx.concurrent.Task;

public class MapTask extends Task<Map> {

    private OptionCollection options;
    private int width;
    private boolean testingMode = false;

    public MapTask(int width, OptionCollection options) {
        this.options = options;
        this.width = width;
    }

    public void enableTestingMode(){
        this.testingMode = true;
    }

    private void maybeUpdateMessage(String message){
        if(!testingMode && !isCancelled()){
            updateMessage(message);
        }
    }

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
