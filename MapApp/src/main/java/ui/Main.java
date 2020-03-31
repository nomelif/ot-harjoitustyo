package ui;

import map.Map;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Scanner;
import java.util.Locale;

class Main{
    public static void main(String[] args){

        Scanner s = new Scanner(System.in).useLocale(Locale.US);

        System.out.print("Side length of map (in pixels, recommended: 512): ");
        int width = s.nextInt();

        System.out.print("Seed value for map (positive integer): ");
        int seed = s.nextInt();

        Map m = new Map(width, width, seed);

        System.out.print("Mountain scale multiplier (bigger number, smaller mountains, recommended: 10): ");
        double mountainScale = s.nextDouble();

        System.out.print("Mountain cutoff (decimal [0, 1], recommended: 0.6): ");
        double mountainCutoff = s.nextDouble();

        System.out.println("Generating mountains");
        m.makePerlin(mountainScale, 1, 0);
        m.waterCutoff(mountainCutoff);
        
        System.out.print("Large feature scale multiplier (bigger number, smaller features, recommended: 1): ");
        double featureScale = s.nextDouble();

        System.out.print("Sea level cutoff (decimal [0, 1], recommended: 0.3): ");
        double seaCutoff = s.nextDouble();

        m.makePerlin(featureScale, 0.6, 0);
        m.waterCutoff(seaCutoff);

        System.out.print("Erosion iterations (recommended: 500): ");
        int iterations = s.nextInt();

        long t1 = System.currentTimeMillis();

        System.out.println("Generating erosion");
        System.out.print("Iteration 0 / " + iterations);
        for(int i = 0; i < iterations; i++){
            System.out.print("\rIteration " + i + " / " + iterations);
            m.doErosion(100000, 500);
        }

        long t2 = System.currentTimeMillis();

        System.out.println( "in " + (t2-t1) + "ms");
        System.out.print("Save to (.png): ");
        s.nextLine(); // Will always return an empty line

        BufferedImage b = m.toBufferedImage();
        try{
            ImageIO.write(b, "png", new File(s.nextLine()));
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
