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

    /**
     * @param seed New value for seed
     * @return A new OptionCollection with seed replaced with the given value.
     */
    public OptionCollection withSeed(String seed) {
        return new OptionCollection(seed, mountainScale, mountainCutoff, largeFeatureScale, seaCutoff, erosionIterations);
    }

    /**
     * @param mountainScale New value for mountainScale
     * @return A new OptionCollection with mountainScale replaced with the given value.
     */
    public OptionCollection withMountainScale(double mountainScale) {
        return new OptionCollection(seed, mountainScale, mountainCutoff, largeFeatureScale, seaCutoff, erosionIterations);
    }


    /**
     * @param mountainCutoff New value for mountainCutoff
     * @return A new OptionCollection with mountainCutoff replaced with the given value.
     */
    public OptionCollection withMountainCutoff(double mountainCutoff) {
        return new OptionCollection(seed, mountainScale, mountainCutoff, largeFeatureScale, seaCutoff, erosionIterations);
    }

    /**
     * @param largeFeatureScale New value for largeFeatureScale
     * @return A new OptionCollection with largeFeatureScale replaced with the given value.
     */
    public OptionCollection withLargeFeatureScale(double largeFeatureScale) {
        return new OptionCollection(seed, mountainScale, mountainCutoff, largeFeatureScale, seaCutoff, erosionIterations);
    }

    /**
     * @param seaCutoff New value for seaCutoff
     * @return A new OptionCollection with seaCutoff replaced with the given value.
     */
    public OptionCollection withSeaCutoff(double seaCutoff) {
        return new OptionCollection(seed, mountainScale, mountainCutoff, largeFeatureScale, seaCutoff, erosionIterations);
    }

    /**
     * @param erosionIterations New value for erosionIterations
     * @return A new OptionCollection with erosionIterations replaced with the given value.
     */
    public OptionCollection withErosionIterations(int erosionIterations) {
        return new OptionCollection(seed, mountainScale, mountainCutoff, largeFeatureScale, seaCutoff, erosionIterations);
    }

}
