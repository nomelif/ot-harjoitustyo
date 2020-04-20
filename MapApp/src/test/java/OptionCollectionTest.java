
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import map.OptionCollection;

public class OptionCollectionTest {

    public OptionCollectionTest() {
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

    // Test that an OptionCollection doesn't lose information

    @Test
    public void testOptionCollectionConstruction() {
        String[] seeds = new String[]{"irqhubjjjw", "gvxvvpwjbc", "xhikdxrgtc", "chjntsriwe", "rrhqkqhxqv", "xljrufnqpn", "giggybytgp", "srmgcyayhx", "rjtlzdnpsl", "onfvvujdux"};
        double[] mountainScales = new double[]{0.5307718733772157, 0.9980566593749619, 0.7547573416411235, 0.5289045662980886, 0.4895433934939605, 0.29714485291200066, 0.13417584224389556, 0.4953162792767859, 0.8760382634004613, 0.7517280634714496};
        double[] mountainCutoffs = new double[]{0.8695450019790698, 0.78200580481437, 0.1804670943875072, 0.10396829449836575, 0.5466415102645301, 0.22963171913770175, 0.2583408273942259, 0.7519657374656038, 0.4191374850390841, 0.7165152542148352};
        double[] largeFeatureScales = new double[]{0.556970429887087, 0.7984905939123088, 0.28760598901932977, 0.33209089632821254, 0.4206201139503686, 0.21516104356464683, 0.6100791894282644, 0.7841532854081026, 0.30688093808484584, 0.6448030732731151};
        double[] seaCutoffs = new double[]{0.9032700108609004, 0.27376892023337607, 0.3303991753395543, 0.23378238020847597, 0.33967970769212064, 0.03983470939518119, 0.2653488142589967, 0.6148460939911095, 0.18566275577595526, 0.9431413912795532};
        int[] erosionIterations = new int[]{3570, 7461, 8735, 9863, 3345, 2862, 7583, 9472, 5392, 3614};

        for(int i = 0; i < 10; i++){
            OptionCollection o = new OptionCollection(seeds[i], mountainScales[i], mountainCutoffs[i], largeFeatureScales[i], seaCutoffs[i], erosionIterations[i]);
            assertEquals(seeds[i], o.seed);
            assertEquals(mountainScales[i], o.mountainScale, 0);
            assertEquals(mountainCutoffs[i], o.mountainCutoff, 0);
            assertEquals(largeFeatureScales[i], o.largeFeatureScale, 0);
            assertEquals(seaCutoffs[i], o.seaCutoff, 0);
            assertEquals(erosionIterations[i], o.erosionIterations, 0);
        }
    }

    // Test that the various withX -methods do what they should

    @Test
    public void testWithSeed() {
        String[] seeds = new String[]{"irqhubjjjw", "gvxvvpwjbc", "xhikdxrgtc", "chjntsriwe", "rrhqkqhxqv", "xljrufnqpn", "giggybytgp", "srmgcyayhx", "rjtlzdnpsl", "onfvvujdux"};
        double[] mountainScales = new double[]{0.5307718733772157, 0.9980566593749619, 0.7547573416411235, 0.5289045662980886, 0.4895433934939605, 0.29714485291200066, 0.13417584224389556, 0.4953162792767859, 0.8760382634004613, 0.7517280634714496};
        double[] mountainCutoffs = new double[]{0.8695450019790698, 0.78200580481437, 0.1804670943875072, 0.10396829449836575, 0.5466415102645301, 0.22963171913770175, 0.2583408273942259, 0.7519657374656038, 0.4191374850390841, 0.7165152542148352};
        double[] largeFeatureScales = new double[]{0.556970429887087, 0.7984905939123088, 0.28760598901932977, 0.33209089632821254, 0.4206201139503686, 0.21516104356464683, 0.6100791894282644, 0.7841532854081026, 0.30688093808484584, 0.6448030732731151};
        double[] seaCutoffs = new double[]{0.9032700108609004, 0.27376892023337607, 0.3303991753395543, 0.23378238020847597, 0.33967970769212064, 0.03983470939518119, 0.2653488142589967, 0.6148460939911095, 0.18566275577595526, 0.9431413912795532};
        int[] erosionIterations = new int[]{3570, 7461, 8735, 9863, 3345, 2862, 7583, 9472, 5392, 3614};

        String[] newVals = new String[]{"tiwdxkshlq", "fninkftiui", "ixbgfmneyl", "tbiwahvodx", "oqeisfzbdc", "ojvjtqklfi", "cqbdrwybcs", "xcezneyuef", "xvczxqhvpm", "ncmtwdcxhx"};

        for(int i = 0; i < 10; i++){
            OptionCollection o = new OptionCollection(seeds[i], mountainScales[i], mountainCutoffs[i], largeFeatureScales[i], seaCutoffs[i], erosionIterations[i]).withSeed(newVals[i]);
            assertEquals(newVals[i], o.seed);
            assertEquals(mountainScales[i], o.mountainScale, 0);
            assertEquals(mountainCutoffs[i], o.mountainCutoff, 0);
            assertEquals(largeFeatureScales[i], o.largeFeatureScale, 0);
            assertEquals(seaCutoffs[i], o.seaCutoff, 0);
            assertEquals(erosionIterations[i], o.erosionIterations, 0);
        }
    }

    @Test
    public void testWithMountainScale() {
        String[] seeds = new String[]{"irqhubjjjw", "gvxvvpwjbc", "xhikdxrgtc", "chjntsriwe", "rrhqkqhxqv", "xljrufnqpn", "giggybytgp", "srmgcyayhx", "rjtlzdnpsl", "onfvvujdux"};
        double[] mountainScales = new double[]{0.5307718733772157, 0.9980566593749619, 0.7547573416411235, 0.5289045662980886, 0.4895433934939605, 0.29714485291200066, 0.13417584224389556, 0.4953162792767859, 0.8760382634004613, 0.7517280634714496};
        double[] mountainCutoffs = new double[]{0.8695450019790698, 0.78200580481437, 0.1804670943875072, 0.10396829449836575, 0.5466415102645301, 0.22963171913770175, 0.2583408273942259, 0.7519657374656038, 0.4191374850390841, 0.7165152542148352};
        double[] largeFeatureScales = new double[]{0.556970429887087, 0.7984905939123088, 0.28760598901932977, 0.33209089632821254, 0.4206201139503686, 0.21516104356464683, 0.6100791894282644, 0.7841532854081026, 0.30688093808484584, 0.6448030732731151};
        double[] seaCutoffs = new double[]{0.9032700108609004, 0.27376892023337607, 0.3303991753395543, 0.23378238020847597, 0.33967970769212064, 0.03983470939518119, 0.2653488142589967, 0.6148460939911095, 0.18566275577595526, 0.9431413912795532};
        int[] erosionIterations = new int[]{3570, 7461, 8735, 9863, 3345, 2862, 7583, 9472, 5392, 3614};

        double[] newVals = new double[]{0.5169524524817886, 0.7688966593201623, 0.7263040823221367, 0.5870414482759931, 0.38082569524469234, 0.22654164611366767, 0.2574900625329424, 0.1595601935918326, 0.5116885544902983, 0.09113482653020111};

        for(int i = 0; i < 10; i++){
            OptionCollection o = new OptionCollection(seeds[i], mountainScales[i], mountainCutoffs[i], largeFeatureScales[i], seaCutoffs[i], erosionIterations[i]).withMountainScale(newVals[i]);
            assertEquals(seeds[i], o.seed);
            assertEquals(newVals[i], o.mountainScale, 0);
            assertEquals(mountainCutoffs[i], o.mountainCutoff, 0);
            assertEquals(largeFeatureScales[i], o.largeFeatureScale, 0);
            assertEquals(seaCutoffs[i], o.seaCutoff, 0);
            assertEquals(erosionIterations[i], o.erosionIterations, 0);
        }
    }

    @Test
    public void testWithMountainCutoffs() {
        String[] seeds = new String[]{"irqhubjjjw", "gvxvvpwjbc", "xhikdxrgtc", "chjntsriwe", "rrhqkqhxqv", "xljrufnqpn", "giggybytgp", "srmgcyayhx", "rjtlzdnpsl", "onfvvujdux"};
        double[] mountainScales = new double[]{0.5307718733772157, 0.9980566593749619, 0.7547573416411235, 0.5289045662980886, 0.4895433934939605, 0.29714485291200066, 0.13417584224389556, 0.4953162792767859, 0.8760382634004613, 0.7517280634714496};
        double[] mountainCutoffs = new double[]{0.8695450019790698, 0.78200580481437, 0.1804670943875072, 0.10396829449836575, 0.5466415102645301, 0.22963171913770175, 0.2583408273942259, 0.7519657374656038, 0.4191374850390841, 0.7165152542148352};
        double[] largeFeatureScales = new double[]{0.556970429887087, 0.7984905939123088, 0.28760598901932977, 0.33209089632821254, 0.4206201139503686, 0.21516104356464683, 0.6100791894282644, 0.7841532854081026, 0.30688093808484584, 0.6448030732731151};
        double[] seaCutoffs = new double[]{0.9032700108609004, 0.27376892023337607, 0.3303991753395543, 0.23378238020847597, 0.33967970769212064, 0.03983470939518119, 0.2653488142589967, 0.6148460939911095, 0.18566275577595526, 0.9431413912795532};
        int[] erosionIterations = new int[]{3570, 7461, 8735, 9863, 3345, 2862, 7583, 9472, 5392, 3614};

        double[] newVals = new double[]{0.5169524524817886, 0.7688966593201623, 0.7263040823221367, 0.5870414482759931, 0.38082569524469234, 0.22654164611366767, 0.2574900625329424, 0.1595601935918326, 0.5116885544902983, 0.09113482653020111};

        for(int i = 0; i < 10; i++){
            OptionCollection o = new OptionCollection(seeds[i], mountainScales[i], mountainCutoffs[i], largeFeatureScales[i], seaCutoffs[i], erosionIterations[i]).withMountainCutoff(newVals[i]);
            assertEquals(seeds[i], o.seed);
            assertEquals(mountainScales[i], o.mountainScale, 0);
            assertEquals(newVals[i], o.mountainCutoff, 0);
            assertEquals(largeFeatureScales[i], o.largeFeatureScale, 0);
            assertEquals(seaCutoffs[i], o.seaCutoff, 0);
            assertEquals(erosionIterations[i], o.erosionIterations, 0);
        }
    }

    @Test
    public void testWithLargeFeatureScale() {
        String[] seeds = new String[]{"irqhubjjjw", "gvxvvpwjbc", "xhikdxrgtc", "chjntsriwe", "rrhqkqhxqv", "xljrufnqpn", "giggybytgp", "srmgcyayhx", "rjtlzdnpsl", "onfvvujdux"};
        double[] mountainScales = new double[]{0.5307718733772157, 0.9980566593749619, 0.7547573416411235, 0.5289045662980886, 0.4895433934939605, 0.29714485291200066, 0.13417584224389556, 0.4953162792767859, 0.8760382634004613, 0.7517280634714496};
        double[] mountainCutoffs = new double[]{0.8695450019790698, 0.78200580481437, 0.1804670943875072, 0.10396829449836575, 0.5466415102645301, 0.22963171913770175, 0.2583408273942259, 0.7519657374656038, 0.4191374850390841, 0.7165152542148352};
        double[] largeFeatureScales = new double[]{0.556970429887087, 0.7984905939123088, 0.28760598901932977, 0.33209089632821254, 0.4206201139503686, 0.21516104356464683, 0.6100791894282644, 0.7841532854081026, 0.30688093808484584, 0.6448030732731151};
        double[] seaCutoffs = new double[]{0.9032700108609004, 0.27376892023337607, 0.3303991753395543, 0.23378238020847597, 0.33967970769212064, 0.03983470939518119, 0.2653488142589967, 0.6148460939911095, 0.18566275577595526, 0.9431413912795532};
        int[] erosionIterations = new int[]{3570, 7461, 8735, 9863, 3345, 2862, 7583, 9472, 5392, 3614};

        double[] newVals = new double[]{0.5169524524817886, 0.7688966593201623, 0.7263040823221367, 0.5870414482759931, 0.38082569524469234, 0.22654164611366767, 0.2574900625329424, 0.1595601935918326, 0.5116885544902983, 0.09113482653020111};

        for(int i = 0; i < 10; i++){
            OptionCollection o = new OptionCollection(seeds[i], mountainScales[i], mountainCutoffs[i], largeFeatureScales[i], seaCutoffs[i], erosionIterations[i]).withLargeFeatureScale(newVals[i]);
            assertEquals(seeds[i], o.seed);
            assertEquals(mountainScales[i], o.mountainScale, 0);
            assertEquals(mountainCutoffs[i], o.mountainCutoff, 0);
            assertEquals(newVals[i], o.largeFeatureScale, 0);
            assertEquals(seaCutoffs[i], o.seaCutoff, 0);
            assertEquals(erosionIterations[i], o.erosionIterations, 0);
        }
    }

    @Test
    public void testWithSeaCutoff() {
        String[] seeds = new String[]{"irqhubjjjw", "gvxvvpwjbc", "xhikdxrgtc", "chjntsriwe", "rrhqkqhxqv", "xljrufnqpn", "giggybytgp", "srmgcyayhx", "rjtlzdnpsl", "onfvvujdux"};
        double[] mountainScales = new double[]{0.5307718733772157, 0.9980566593749619, 0.7547573416411235, 0.5289045662980886, 0.4895433934939605, 0.29714485291200066, 0.13417584224389556, 0.4953162792767859, 0.8760382634004613, 0.7517280634714496};
        double[] mountainCutoffs = new double[]{0.8695450019790698, 0.78200580481437, 0.1804670943875072, 0.10396829449836575, 0.5466415102645301, 0.22963171913770175, 0.2583408273942259, 0.7519657374656038, 0.4191374850390841, 0.7165152542148352};
        double[] largeFeatureScales = new double[]{0.556970429887087, 0.7984905939123088, 0.28760598901932977, 0.33209089632821254, 0.4206201139503686, 0.21516104356464683, 0.6100791894282644, 0.7841532854081026, 0.30688093808484584, 0.6448030732731151};
        double[] seaCutoffs = new double[]{0.9032700108609004, 0.27376892023337607, 0.3303991753395543, 0.23378238020847597, 0.33967970769212064, 0.03983470939518119, 0.2653488142589967, 0.6148460939911095, 0.18566275577595526, 0.9431413912795532};
        int[] erosionIterations = new int[]{3570, 7461, 8735, 9863, 3345, 2862, 7583, 9472, 5392, 3614};

        double[] newVals = new double[]{0.5169524524817886, 0.7688966593201623, 0.7263040823221367, 0.5870414482759931, 0.38082569524469234, 0.22654164611366767, 0.2574900625329424, 0.1595601935918326, 0.5116885544902983, 0.09113482653020111};

        for(int i = 0; i < 10; i++){
            OptionCollection o = new OptionCollection(seeds[i], mountainScales[i], mountainCutoffs[i], largeFeatureScales[i], seaCutoffs[i], erosionIterations[i]).withSeaCutoff(newVals[i]);
            assertEquals(seeds[i], o.seed);
            assertEquals(mountainScales[i], o.mountainScale, 0);
            assertEquals(mountainCutoffs[i], o.mountainCutoff, 0);
            assertEquals(largeFeatureScales[i], o.largeFeatureScale, 0);
            assertEquals(newVals[i], o.seaCutoff, 0);
            assertEquals(erosionIterations[i], o.erosionIterations, 0);
        }
    }

    @Test
    public void testWithErosionIterations() {
        String[] seeds = new String[]{"irqhubjjjw", "gvxvvpwjbc", "xhikdxrgtc", "chjntsriwe", "rrhqkqhxqv", "xljrufnqpn", "giggybytgp", "srmgcyayhx", "rjtlzdnpsl", "onfvvujdux"};
        double[] mountainScales = new double[]{0.5307718733772157, 0.9980566593749619, 0.7547573416411235, 0.5289045662980886, 0.4895433934939605, 0.29714485291200066, 0.13417584224389556, 0.4953162792767859, 0.8760382634004613, 0.7517280634714496};
        double[] mountainCutoffs = new double[]{0.8695450019790698, 0.78200580481437, 0.1804670943875072, 0.10396829449836575, 0.5466415102645301, 0.22963171913770175, 0.2583408273942259, 0.7519657374656038, 0.4191374850390841, 0.7165152542148352};
        double[] largeFeatureScales = new double[]{0.556970429887087, 0.7984905939123088, 0.28760598901932977, 0.33209089632821254, 0.4206201139503686, 0.21516104356464683, 0.6100791894282644, 0.7841532854081026, 0.30688093808484584, 0.6448030732731151};
        double[] seaCutoffs = new double[]{0.9032700108609004, 0.27376892023337607, 0.3303991753395543, 0.23378238020847597, 0.33967970769212064, 0.03983470939518119, 0.2653488142589967, 0.6148460939911095, 0.18566275577595526, 0.9431413912795532};
        int[] erosionIterations = new int[]{3570, 7461, 8735, 9863, 3345, 2862, 7583, 9472, 5392, 3614};

        int[] newVals = new int[]{9463, 9495, 1411, 4319, 2196, 6266, 8158, 8764, 7324, 5011};

        for(int i = 0; i < 10; i++){
            OptionCollection o = new OptionCollection(seeds[i], mountainScales[i], mountainCutoffs[i], largeFeatureScales[i], seaCutoffs[i], erosionIterations[i]).withErosionIterations(newVals[i]);
            assertEquals(seeds[i], o.seed);
            assertEquals(mountainScales[i], o.mountainScale, 0);
            assertEquals(mountainCutoffs[i], o.mountainCutoff, 0);
            assertEquals(largeFeatureScales[i], o.largeFeatureScale, 0);
            assertEquals(seaCutoffs[i], o.seaCutoff, 0);
            assertEquals(newVals[i], o.erosionIterations, 0);
        }
    }
}
