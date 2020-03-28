
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
                Map m = new Map(w, h);
                assertEquals(w, m.getWidth());
                assertEquals(h, m.getHeight());
            }
        }
    }

    // Test that makePerlin doesn't create values outside the range [0, 1]
    
    @Test
    public void testPerlinNotGreaterThanOne(){
        Map m = new Map(512, 512);
        m.makePerlin();
        for(int y = 0; y < m.getHeight(); y++){
            for(int x = 0; x < m.getWidth(); x++){
                assertTrue("Got value: "+m.index(x, y)+" for coordinates ("+x+", "+y+")", m.index(x, y) <= 1);
            }
        }
    }

    @Test
    public void testPerlinNotLessThanZero(){
        Map m = new Map(512, 512);
        m.makePerlin();
        for(int y = 0; y < m.getHeight(); y++){
            for(int x = 0; x < m.getWidth(); x++){
                assertTrue("Got value: "+m.index(x, y) + " for coordinates ("+x+", "+y+")", m.index(x, y) >= 0);
            }
        }
    }

    // Rough sanity check that toBufferedImage returns a buffered image of the right size
    
    @Test
    public void testBufferedImageWidthAndHeight() {
        for(int w = 1; w < 100; w++){
            for(int h = 1; h < 100; h++){
                Map m = new Map(w, h);
                BufferedImage b = m.toBufferedImage();
                assertEquals(w, b.getWidth());
                assertEquals(h, b.getHeight());
            }
        }
    }
}
