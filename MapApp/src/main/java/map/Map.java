package map;

import com.flowpowered.noise.module.source.Perlin;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Map{
    private double[] data;
    private int width;

    public Map(int width, int height){
        data = new double[width*height];
        this.width = width;
    }

    public double index(int x, int y){
        return this.data[y*this.getWidth() + x];
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.data.length / this.width;
    }

    public void makePerlin(){
        Perlin p = new Perlin();

        double min = 1;
        double max = -1;

        // Generate
        
        for(int y = 0; y < this.getHeight(); y++){
            for(int x = 0; x < this.getWidth(); x++){
                this.data[y*this.getWidth() + x] = p.getValue(((double) x+1) / this.getWidth(), ((double) y+1) / this.getHeight(), 0);
                max = Math.max(max, this.data[y*this.getWidth() + x]);
                min = Math.min(min, this.data[y*this.getWidth() + x]);
            }
        }

        for(int y = 0; y < this.getHeight(); y++){
            for(int x = 0; x < this.getWidth(); x++){
                this.data[y*this.getWidth() + x] -= min;
                this.data[y*this.getWidth() + x] /= (max - min);
            }
        }

        // Regularize

    }

    public void doErosion(int drops, int iterations){
        Random r = new Random();
        for(int d = 0; d < drops; d++){
            int x = r.nextInt(this.getWidth());
            int y = r.nextInt(this.getHeight());
            for(int i = 0; i < iterations; i++){
                int bestDx = -1;
                int bestDy = -1;
                double bestDelta = 1;
                for(int dx = -1; dx <= 1; dx++){
                    for(int dy = -1; dy <= 1; dy++){
                        if(dx == 0 && dy == 0) continue;
                        if(x+dx < 0 || x + dx > this.getWidth()-1 || y+dy < 0 || y + dy > this.getHeight()-1) continue;
                        double delta = (data[(y+dy)*this.getWidth() + x+dx] - data[y*this.getWidth() + x]) / Math.sqrt(dx*dx + dy*dy);
                        if(bestDelta > delta){
                            bestDelta = delta;
                            bestDx = dx;
                            bestDy = dy;
                        }
                    }
                }
                if(bestDelta > 0) break;
                data[y*this.getWidth() + x] += bestDelta / 2;
                y += bestDy;
                x += bestDx;
                data[y*this.getWidth() + x] -= bestDelta / 2;
            }
        }
    }

    public BufferedImage toBufferedImage(){
        BufferedImage result = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y < this.getHeight(); y++){
            for(int x = 0; x < this.getWidth(); x++){
                int darkness = ((int) (this.data[y*this.getWidth() + x] * 255));
                int rgb = darkness;
                rgb = (rgb << 8) + darkness;
                rgb = (rgb << 8) + darkness;
                result.setRGB(x, y, rgb);
            }
        }
        return result;
    }

}
