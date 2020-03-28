package ui;

import map.Map;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

class Main{
    public static void main(String[] args){
        System.out.println("Generating perlin noise");
        Map m = new Map(512, 512);
        m.makePerlin();
        System.out.println("Generating erosion");
        m.doErosion(1000000, 200);
        System.out.println("Generating BufferedImage");
        BufferedImage b = m.toBufferedImage();
        System.out.println("Writing BufferedImage to /tmp/test.png");
        try{
            ImageIO.write(b, "png", new File("/tmp/test.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
}
