package map;

public class OptionCollection {

    final String seed;
    final double mountainScale;
    final double mountainCutoff;
    final double largeFeatureScale;
    final double seaCutoff;
    final int erosionIterations;

    public OptionCollection(String seed, double mountainScale, double mountainCutoff, double largeFeatureScale, double seaCutoff, int erosionIterations) {
        this.seed = seed;
        this.mountainScale = mountainScale;
        this.mountainCutoff = mountainCutoff;
        this.largeFeatureScale = largeFeatureScale;
        this.seaCutoff = seaCutoff;
        this.erosionIterations = erosionIterations;
    }

    public OptionCollection withSeed(String seed) {
        return new OptionCollection(seed, mountainScale, mountainCutoff, largeFeatureScale, seaCutoff, erosionIterations);
    }

    public OptionCollection withMountainScale(double mountainScale) {
        return new OptionCollection(seed, mountainScale, mountainCutoff, largeFeatureScale, seaCutoff, erosionIterations);
    }

    public OptionCollection withMountainCutoff(double mountainCutoff) {
        return new OptionCollection(seed, mountainScale, mountainCutoff, largeFeatureScale, seaCutoff, erosionIterations);
    }

    public OptionCollection withLargeFeatureScale(double largeFeatureScale) {
        return new OptionCollection(seed, mountainScale, mountainCutoff, largeFeatureScale, seaCutoff, erosionIterations);
    }

    public OptionCollection withSeaCutoff(double seaCutoff) {
        return new OptionCollection(seed, mountainScale, mountainCutoff, largeFeatureScale, seaCutoff, erosionIterations);
    }

    public OptionCollection withErosionIterations(int erosionIterations) {
        return new OptionCollection(seed, mountainScale, mountainCutoff, largeFeatureScale, seaCutoff, erosionIterations);
    }

}
