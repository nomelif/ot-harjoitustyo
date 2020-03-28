package map;

import com.flowpowered.noise.module.source.Perlin;
import java.awt.image.BufferedImage;

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
