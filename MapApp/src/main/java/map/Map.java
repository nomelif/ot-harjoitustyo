package map;

import com.flowpowered.noise.module.source.Perlin;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Class for the representation and computation of Maps.
 */
public class Map {
    private double[] data;
    private final int width;
    private final int seed;
    private Random r;
    private Perlin p;

    /**
     * @param width Width of the map in pixels. Also used as the width of the simulation grid in cells. Must be one or greater. This is not checked.
     * @param height Height of the map in pixels. Also used as the height of the simulation grid in cells. Must be one or greater. This is not checked.
     * @param seed seed value for pseudorandom operations on a map. Two map instances with the same width, height and seed are guaranteed to stay identical if the same sequence of operatinos.
     */
    public Map(int width, int height, int seed) {
        data = new double[width * height];
        this.width = width;
        this.seed = seed;
        this.r = new Random(seed);
        this.p = new Perlin();
        this.p.setSeed(seed);
    }

    /**
     * Read the raw double from a simulation cell
     *
     * @param x horizontal coordinate of cell to be read
     *
     * This must be between [0, width[. It's the responsibility of the caller to make sure this is the case.
     *
     * @param y horizontal coordinate of cell to be read
     *
     * This must be between [0, height[. It's the responsibility of the caller to make sure this is the case.
     *
     * @return The value of the cell at (x, y)
     */
    public double index(int x, int y) {
        return this.data[y * this.getWidth() + x];
    }

    /**
     * @return The width of the map in pixels. This is also the width of the simulation in cells.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * @return The height of the map in pixels. This is also the height of the simulation in cells.
     */
    public int getHeight() {
        return this.data.length / this.width;
    }

    /**
     * Add a layer of perlin noise to the map.
     *
     * @param scale Scale multiplier to the sampling coordinates of the perlin noise. Higher values result in denser noise.
     * @param influence Influence multiplier
     * @param offset Offset multiplier.
     * 
     * The new values of the map are guaranteed to be in the range [old value + offset, old value + influence + offset]. influence + offset + max(old values of map) should not exceed one. influence + offset + min(old values of map) should not be less than zero. Neither of these are checked.
     */
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
        // If max = min, set all heights to offset + influence * 0.5. Otherwise NaNs can happen, trivially when width = height = 1

        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                if (max - min != 0) {
                    this.data[y * this.getWidth() + x] += offset + influence * (newMap[y * this.getWidth() + x] - min) / (max - min);
                } else {
                    this.data[y * this.getWidth() + x] += offset + influence * 0.5;
                }
            }
        }

    }

    /**
     * Cut everything below cutoff, then move everything down by cutoff.
     *
     * @param cutoff Threshold below which everything is treated as sea. Should be non-negative. This is not checked.
     *
     * The new values of the map are guaranteed to be in the range [0, 1-cutoff].
     */

    public void waterCutoff(double cutoff) {

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

    /**
     * Simulate erosion
     *
     * @param drops Number of drops to simulate.
     * @param iterations How many iterations to calculate the behavior of the drop for.
     *
     * Each drop is simulated sequentially. It is possible for a drop to last less than the specified number of iterations: a drop is killed if it can no longer flow downwards. Therefore it is not safe to assume that doErosion terminates in a time that is a function of drops and iterations. It's guaranteed the values of the map after a call to doErosion are within the range of the values that the map had before.
     */

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
                        if (dx == 0 && dy == 0) { // Discount (x, y)
                            continue;
                        }

                        // Discount locations that are off the map

                        if (x + dx < 0 || x + dx > this.getWidth() - 1 || y + dy < 0 || y + dy > this.getHeight() - 1) {
                            continue;
                        }

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

                if (bestDelta > 0) { // If there is no way down, the drop dies
                    break;
                }

                // bestDelta / 50 was found to be a good value experimentally

                data[y * this.getWidth() + x] += bestDelta / 50;
                y += bestDy;
                x += bestDx;
                data[y * this.getWidth() + x] -= bestDelta / 50;
            }
        }
    }

    /**
     * @return a BufferedImage height map corresponding to this Map. Darker colors are lower and lighter higher.
     */
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

    /**
     * @return A String containing a standard Wavefront OBJ 3D model of the map.
     *
     * This respects the Y-up convention. The X axis is guaranteed to be in the range [0, 1], the Y axis in the range [0, 0.2] and the Z axis in the range [0, height/width]. The mesh is guaranteed to only contain triangular (and therefore flat) faces. Calling toWavefrontOBJ with a map with width <= 1 or height <= 1 is undefined.
     */
    public String toWavefrontOBJ() {
        StringBuilder result = new StringBuilder();
        double w = this.getWidth();

        // Define vertices (Y up)

        for (int i = 0; i < data.length; i++) {
            result.append("v " + ((i % this.getWidth()) / w) + " " + (data[i] * 0.20) + " " + ((i / this.getWidth()) / w) + "\n");
        }

        // Enable smoothing
        
        result.append("s 1\n");

        // Define faces (triangular for better support)
        
        for (int x = 0; x < this.getWidth() - 1; x++) {
            for (int y = 0; y < this.getHeight() - 1; y++) {
                result.append("f " + (y * this.getWidth() + x + 1) + " " + ((y + 1) * this.getWidth() + x + 1) + " " + (y * this.getWidth() + x + 2) + "\n");
                result.append("f " + ((y + 1) * this.getWidth() + x + 1) + " " + ((y + 1) * this.getWidth() + x + 2) + " " + (y * this.getWidth() + x + 2) + "\n");
            }
        }

        return result.toString();
    }

}
