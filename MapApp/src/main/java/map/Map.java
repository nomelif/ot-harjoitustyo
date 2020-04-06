package map;

import com.flowpowered.noise.module.source.Perlin;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Map {
    private double[] data;
    private int width;
    private int seed;
    Random r;
    Perlin p;

    public Map(int width, int height, int seed) {
        data = new double[width * height];
        this.width = width;
        this.seed = seed;
        this.r = new Random(seed);
        this.p = new Perlin();
        this.p.setSeed(seed);
    }

    public double index(int x, int y) {
        return this.data[y * this.getWidth() + x];
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.data.length / this.width;
    }

    public void makePerlin(double scale, double influence, double offset) {

        // The new perlin will be generated into a new array and then regularized

        double[] newMap = new double[data.length];

        double min = 1;
        double max = -1;

        // Generation step
        
        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                newMap[y * this.getWidth() + x] += p.getValue(scale * ((double) x + 1) / this.getWidth(), scale * ((double) y + 1) / this.getHeight(), 0);
                max = Math.max(max, newMap[y * this.getWidth() + x]);
                min = Math.min(min, newMap[y * this.getWidth() + x]);
            }
        }

        // Regularization step (clamp to the range [offset, influence + offset])

        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                this.data[y * this.getWidth() + x] += offset + influence * (newMap[y * this.getWidth() + x] - min) / (max - min);
            }
        }

    }

    public void waterCutoff(double cutoff) {

        // Cut everything below cutoff, then move everything down by cutoff

        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                if (this.data[y * this.getWidth() + x] < cutoff) {
                    this.data[y * this.getWidth() + x] = 0;
                } else {
                    this.data[y * this.getWidth() + x] -= cutoff;
                }
            }
        }
    }

    public void doErosion(int drops, int iterations) {

        // Track the specified amount of drops for the specified amount of iterations

        for (int d = 0; d < drops; d++) {

            // Start the drop from a random location

            int x = r.nextInt(this.getWidth());
            int y = r.nextInt(this.getHeight());
            for (int i = 0; i < iterations; i++) {

                // The ideal point for the drop to continue to is (bestDx, bestDy)
                // (= the point where the local gradient leads)

                int bestDx = -1;
                int bestDy = -1;
                double bestDelta = 1; // Measure of local gradient
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx == 0 && dy == 0) continue; // Discount (x, y)

                        // Discount locations that are off the map

                        if (x + dx < 0 || x + dx > this.getWidth() - 1 || y + dy < 0 || y + dy > this.getHeight() - 1) continue;

                        // Compute the gradient (compensate for diagonal points being further away)

                        double delta = (data[(y + dy) * this.getWidth() + x + dx] - data[y * this.getWidth() + x]) / Math.sqrt(dx * dx + dy * dy);

                        // Update info on the optimal path

                        if (bestDelta > delta) {
                            bestDelta = delta;
                            bestDx = dx;
                            bestDy = dy;
                        }
                    }
                }
                if (bestDelta > 0) break; // If there is no way down, the drop dies

                // bestDelta / 50 was found to be a good value experimentally

                data[y * this.getWidth() + x] += bestDelta / 50;
                y += bestDy;
                x += bestDx;
                data[y * this.getWidth() + x] -= bestDelta / 50;
            }
        }
    }

    public BufferedImage toBufferedImage() {

        // TODO: Make this output 16 bit greyscale png. The original data is 64 bit float.

        BufferedImage result = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                int darkness = ((int) (this.data[y * this.getWidth() + x] * 255));
                int rgb = darkness;
                rgb = (rgb << 8) + darkness;
                rgb = (rgb << 8) + darkness;
                result.setRGB(x, y, rgb);
            }
        }
        return result;
    }

}
