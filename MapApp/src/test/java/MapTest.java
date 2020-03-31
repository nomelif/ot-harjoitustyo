
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import map.Map;
import java.awt.image.BufferedImage;

public class MapTest {

    public MapTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // Test that a map retains width and height

    @Test
    public void testMapWidthAndHeight() {
        for(int w = 1; w < 100; w++){
            for(int h = 1; h < 100; h++){
                Map m = new Map(w, h, 1337);
                assertEquals(w, m.getWidth());
                assertEquals(h, m.getHeight());
            }
        }
    }

    // Test that a map is blank by default
    
    @Test
    public void testMapStartsBlank(){
        Map m = new Map(512, 512, 1337);
        for(int y = 0; y < m.getHeight(); y++){
            for(int x = 0; x < m.getWidth(); x++){
                assertTrue("Got value: "+m.index(x, y)+" for coordinates ("+x+", "+y+")" , m.index(x, y) == 0.0);
            }
        }
    }

    // Test that a map generated with the same seed is the same map

    @Test
    public void testSameSeedGeneratesSameMap(){

        // 0 <= offset, influence <= 1

        // Test that the above is true within an error of 0.00000000001
        // Because floating point numbers

        double[] offsets = new double[]{0.013581325122757504, 0.33777086435813886, 0.39655940939648965, 0.48237969555417465, 0.1520633287230323, 0.4079673468355713, 0.18855577393866907, 0.4052922536741822, 0.26537560908991975, 0.21991110849194245};
        double[] influences = new double[]{0.23448924639727659, 0.08832509876027, 0.43055194437885985, 0.4289998538507482, 0.49735003338949646, 0.4768149750132602, 0.28113722787893974, 0.1390656826001298, 0.3474578334391082, 0.31411926416248703};
        double[] scales = new double[]{2.076228799035408, 2.0821341766580757, 9.198787952947267, 9.384514948674406, 3.8740491081562345, 0.18546243391736716, 7.747568192981359, 8.915547861644356, 7.717900904234486, 7.476294457797394};
		int[] seeds = new int[]{585, 224, 873, 17, 261, 586, 653, 924, 907, 478};
        double[] cutoffs = new double[]{0.5570824427064333, 0.1004383896492449, 0.059814371052977044, 0.4329529143305758, 0.9207673174007874, 0.23132752397556755, 0.17543163842110077, 0.29027217079867784, 0.38369656915106976, 0.6945837916026952};

        for(int i = 0; i < offsets.length; i++){
            Map m = new Map(512, 512, seeds[i]);
            m.makePerlin(scales[i], influences[i], offsets[i]);
            m.waterCutoff(cutoffs[i]);
            m.doErosion(1000000, 500);


            Map m2 = new Map(512, 512, seeds[i]);
            m2.makePerlin(scales[i], influences[i], offsets[i]);
            m2.waterCutoff(cutoffs[i]);
            m2.doErosion(1000000, 500);
            for(int y = 0; y < m.getHeight(); y++){
                for(int x = 0; x < m.getWidth(); x++){
                    assertTrue("Got value: "+m.index(x, y)+" for coordinates ("+x+", "+y+") from m and value: "+m2.index(x, y)+ " from m2 with seed: " + seeds[i] + ", scale: " + scales[i] + ", offset: " + offsets[i] + ", influence: " + influences[i] , m.index(x, y) == m2.index(x, y));
                }
            }
        }
    }

    //Test that waterCutoff moves everything down by cutoff and cuts off negative values

    @Test
    public void testWaterCutoff(){

        // Test that the above is true within an error of 0.00000000001
        // Because floating point numbers

        double[] offsets = new double[]{0.013581325122757504, 0.33777086435813886, 0.39655940939648965, 0.48237969555417465, 0.1520633287230323, 0.4079673468355713, 0.18855577393866907, 0.4052922536741822, 0.26537560908991975, 0.21991110849194245};
        double[] influences = new double[]{0.23448924639727659, 0.08832509876027, 0.43055194437885985, 0.4289998538507482, 0.49735003338949646, 0.4768149750132602, 0.28113722787893974, 0.1390656826001298, 0.3474578334391082, 0.31411926416248703};
        double[] scales = new double[]{2.076228799035408, 2.0821341766580757, 9.198787952947267, 9.384514948674406, 3.8740491081562345, 0.18546243391736716, 7.747568192981359, 8.915547861644356, 7.717900904234486, 7.476294457797394};
        double[] cutoffs = new double[]{0.5570824427064333, 0.1004383896492449, 0.059814371052977044, 0.4329529143305758, 0.9207673174007874, 0.23132752397556755, 0.17543163842110077, 0.29027217079867784, 0.38369656915106976, 0.6945837916026952};
		int[] seeds = new int[]{585, 224, 873, 17, 261, 586, 653, 924, 907, 478};

        for(int i = 0; i < offsets.length; i++){
            Map m = new Map(512, 512, seeds[i]);
            m.makePerlin(scales[i], influences[i], offsets[i]);

            Map m2 = new Map(512, 512, seeds[i]); // Safe to do based on the test above
            m2.makePerlin(scales[i], influences[i], offsets[i]);

            m2.waterCutoff(cutoffs[i]);

            for(int y = 0; y < m.getHeight(); y++){
                for(int x = 0; x < m.getWidth(); x++){
                    if(m.index(x, y) - cutoffs[i] < 0){
                        assertTrue("Got value: "+m2.index(x, y)+" for coordinates ("+x+", "+y+"), expected it being cut off to zero with seed: " + seeds[i] + ", scale: " + scales[i] + ", offset: " + offsets[i] + ", influence: " + influences[i] , Math.abs(m2.index(x, y)) < 0.00000000001);
                    }else{
                        assertTrue("Got value: "+m2.index(x, y)+" for coordinates ("+x+", "+y+"), expected it being "+(m.index(x, y) - cutoffs[i])+": " + seeds[i] + ", scale: " + scales[i] + ", offset: " + offsets[i] + ", influence: " + influences[i] , Math.abs(m2.index(x, y) - (m.index(x, y) - cutoffs[i])) < 0.00000000001);
                    }
                }
            }
        }
    }

    // Test that makePerlin doesn't create values outside the range [offset, offset+influence]
    
    @Test
    public void testPerlinNotGreaterThanInfluencePlusOfset(){

        // 0 <= offset, influence <= 1

        // Test that the above is true within an error of 0.00000000001
        // Because floating point numbers

        double[] offsets = new double[]{0.013581325122757504, 0.33777086435813886, 0.39655940939648965, 0.48237969555417465, 0.1520633287230323, 0.4079673468355713, 0.18855577393866907, 0.4052922536741822, 0.26537560908991975, 0.21991110849194245};
        double[] influences = new double[]{0.23448924639727659, 0.08832509876027, 0.43055194437885985, 0.4289998538507482, 0.49735003338949646, 0.4768149750132602, 0.28113722787893974, 0.1390656826001298, 0.3474578334391082, 0.31411926416248703};
        double[] scales = new double[]{2.076228799035408, 2.0821341766580757, 9.198787952947267, 9.384514948674406, 3.8740491081562345, 0.18546243391736716, 7.747568192981359, 8.915547861644356, 7.717900904234486, 7.476294457797394};
		int[] seeds = new int[]{585, 224, 873, 17, 261, 586, 653, 924, 907, 478};

        for(int i = 0; i < offsets.length; i++){
            Map m = new Map(512, 512, seeds[i]);
            m.makePerlin(scales[i], influences[i], offsets[i]);
            for(int y = 0; y < m.getHeight(); y++){
                for(int x = 0; x < m.getWidth(); x++){
                    assertTrue("Got value: "+m.index(x, y)+" for coordinates ("+x+", "+y+") with seed: " + seeds[i] + ", scale: " + scales[i] + ", offset: " + offsets[i] + ", influence: " + influences[i] , m.index(x, y) <= offsets[i] + influences[i] + 0.00000000001);
                }
            }
        }
    }

    @Test
    public void testPerlinNotLessInfluence(){

        // Test that the above is true within an error of 0.00000000001
        // Because floating point numbers

        // 0 <= offset, influence <= 1

        double[] offsets = new double[]{0.013581325122757504, 0.33777086435813886, 0.39655940939648965, 0.48237969555417465, 0.1520633287230323, 0.4079673468355713, 0.18855577393866907, 0.4052922536741822, 0.26537560908991975, 0.21991110849194245};
        double[] influences = new double[]{0.23448924639727659, 0.08832509876027, 0.43055194437885985, 0.4289998538507482, 0.49735003338949646, 0.4768149750132602, 0.28113722787893974, 0.1390656826001298, 0.3474578334391082, 0.31411926416248703};
        double[] scales = new double[]{2.076228799035408, 2.0821341766580757, 9.198787952947267, 9.384514948674406, 3.8740491081562345, 0.18546243391736716, 7.747568192981359, 8.915547861644356, 7.717900904234486, 7.476294457797394};
		int[] seeds = new int[]{585, 224, 873, 17, 261, 586, 653, 924, 907, 478};

        for(int i = 0; i < offsets.length; i++){
            Map m = new Map(512, 512, seeds[i]);
            m.makePerlin(scales[i], influences[i], offsets[i]);
            for(int y = 0; y < m.getHeight(); y++){
                for(int x = 0; x < m.getWidth(); x++){
                    assertTrue("Got value: "+m.index(x, y)+" for coordinates ("+x+", "+y+") with seed: " + seeds[i] + ", scale: " + scales[i] + ", offset: " + offsets[i] + ", influence: " + influences[i] , m.index(x, y) >= offsets[i] - 0.00000000001);
                }
            }
        }
    }

    // Rough sanity check that toBufferedImage returns a buffered image of the right size
    
    @Test
    public void testBufferedImageWidthAndHeight() {
        for(int w = 1; w < 100; w++){
            for(int h = 1; h < 100; h++){
                Map m = new Map(w, h, 1337);
                BufferedImage b = m.toBufferedImage();
                assertEquals(w, b.getWidth());
                assertEquals(h, b.getHeight());
            }
        }
    }
}
