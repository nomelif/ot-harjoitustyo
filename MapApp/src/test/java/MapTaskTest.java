
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import map.MapTask;
import map.Map;
import map.OptionCollection;

public class MapTaskTest {

    public MapTaskTest() {
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

    // Test that a map task produces images of the correct size
    
    @Test
    public void testMapSize() {
        for(int width = 12; width <= 512; width+=20){
            MapTask task = new MapTask(width, new OptionCollection("1337", 10, 0.5, 1, 0.2, 0));
            task.enableTestingMode();
            Map m = task.call();
            assertEquals(m.getWidth(), width);
            assertEquals(m.getHeight(), width);
        }
    }

    // Test that a map task produces the same image for the same input
    
    @Test
    public void testMapContent(){
         String[] seeds = new String[]{"irqhubjjjw", "gvxvvpwjbc", "xhikdxrgtc", "chjntsriwe", "rrhqkqhxqv", "xljrufnqpn", "giggybytgp", "srmgcyayhx", "rjtlzdnpsl", "onfvvujdux"};
        double[] mountainScales = new double[]{0.5307718733772157, 0.9980566593749619, 0.7547573416411235, 0.5289045662980886, 0.4895433934939605, 0.29714485291200066, 0.13417584224389556, 0.4953162792767859, 0.8760382634004613, 0.7517280634714496};
        double[] mountainCutoffs = new double[]{0.8695450019790698, 0.78200580481437, 0.1804670943875072, 0.10396829449836575, 0.5466415102645301, 0.22963171913770175, 0.2583408273942259, 0.7519657374656038, 0.4191374850390841, 0.7165152542148352};
        double[] largeFeatureScales = new double[]{0.556970429887087, 0.7984905939123088, 0.28760598901932977, 0.33209089632821254, 0.4206201139503686, 0.21516104356464683, 0.6100791894282644, 0.7841532854081026, 0.30688093808484584, 0.6448030732731151};
        double[] seaCutoffs = new double[]{0.9032700108609004, 0.27376892023337607, 0.3303991753395543, 0.23378238020847597, 0.33967970769212064, 0.03983470939518119, 0.2653488142589967, 0.6148460939911095, 0.18566275577595526, 0.9431413912795532};
        int[] erosionIterations = new int[]{3, 7, 8, 9, 3, 2, 7, 9, 5, 3};
        for(int i = 0; i < 10; i++){
            OptionCollection options = new OptionCollection(seeds[i], mountainScales[i], mountainCutoffs[i], largeFeatureScales[i], seaCutoffs[i], 0);
			MapTask task1 = new MapTask(30, options);
			task1.enableTestingMode();
			Map map1 = task1.call();
			MapTask task2 = new MapTask(30, options);
			task2.enableTestingMode();
			Map map2 = task2.call();
			for(int x = 0; x < 30; x++){
				for(int y = 0; y < 30; y++){
					assertEquals(map1.index(x, y), map2.index(x, y), 0);
				}
			}
        }
    }
}
