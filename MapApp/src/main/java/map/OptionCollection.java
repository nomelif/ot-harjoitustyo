package map;

/**
 * This class functions as a glorified named tuple. It has no hidden state and all its properties are final. It is meant to be used like the Haskell struct:
 * <p>
 * data OptionCollection = OptionCollection {seed :: String, mountainScale :: Double, mountainCutoff :: Double, largeFeatureScale :: Double, seaCutoff :: Double, erosionIterations :: Int}
 * <p>
 * It is Gson-friendly.
 */
public class OptionCollection {

    /**
     * seed to be used when generating a Map with MapTask.
     */
    public final String seed;
    /**
     * mountainScale to be used when generating a Map with MapTask.
     */
    public final double mountainScale;
    /**
     * mountainCutoff to be used when generating a Map with MapTask.
     */
    public final double mountainCutoff;
    /**
     * largeFeatureScale to be used when generating a Map with MapTask.
     */
    public final double largeFeatureScale;
    /**
     * seaCutoff to be used when generating a Map with MapTask.
     */
    public final double seaCutoff;
    /**
     * erosionIterations to be used when generating a Map with MapTask.
     */
    public final int erosionIterations;

    /**
     * This constructor does nothing more than set the various public instance variables to the given values.
     * @param seed Value for the public field seed
     * @param mountainScale Value for the public field mountainScale
     * @param mountainCutoff Value for the public field mountainCutoff
     * @param largeFeatureScale Value for the public field largeFeatureScale
     * @param seaCutoff Value for the public field seaCutoff
     * @param erosionIterations Value for the public field erosionIterations
     */
    public OptionCollection(String seed, double mountainScale, double mountainCutoff, double largeFeatureScale, double seaCutoff, int erosionIterations) {
        this.seed = seed;
        this.mountainScale = mountainScale;
        this.mountainCutoff = mountainCutoff;
        this.largeFeatureScale = largeFeatureScale;
        this.seaCutoff = seaCutoff;
        this.erosionIterations = erosionIterations;
    }
}
