package strand;

import org.junit.Before;
import org.junit.Test;

import genomefeature.GenomicFeature;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * The Class StrandAnnotatorTest.
 */
public class StrandAnnotatorTest {

    /**
     * The strands.
     */
    private ArrayList<Strand> strands = new ArrayList<Strand>();

    /**
     * The features.
     */
    private ArrayList<GenomicFeature> features = new ArrayList<GenomicFeature>();

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        for (int i = 0; i < 20; i++) {
            Strand strand = new Strand(i);
            strand.setStartCoordinate(0);
            strand.setEndCoordinate(0);
            strands.add(i, strand);
        }
        features.add(new GenomicFeature(200, 250, "200_250_feature"));
        features.add(new GenomicFeature(1000, 1000, "1000_1000_feature"));
    }

    /**
     * Test strand with two features.
     */
    @Test
    public void testStrandWithTwoFeatures() {

        Strand strand11 = strands.get(11);
        strand11.setStartCoordinate(200);
        strand11.setEndCoordinate(1000);

        StrandAnnotator.annotate(strands, features);

        for (Strand strand : strands) {
            if (strand.equals(strand11)) {
                assert strand.getGenomicFeatures().size() == 2;
                String featureName1 = strand.getGenomicFeatures().get(0).getDisplayName();
                assert featureName1.equals("200_250_feature");
                String featureName2 = strand.getGenomicFeatures().get(1).getDisplayName();
                assert featureName2.equals("1000_1000_feature");
            } else {
                assert strand.getGenomicFeatures().size() == 0;
            }
        }
    }

    /**
     * Test strand with two features.
     */
    @Test
    public void testTwoStrandsWithOneFeatures() {

        Strand strand11 = strands.get(11);
        strand11.setStartCoordinate(200);
        strand11.setEndCoordinate(239);

        Strand strand14 = strands.get(14);
        strand14.setStartCoordinate(240);
        strand14.setEndCoordinate(1000);

        StrandAnnotator.annotate(strands, features);

        for (Strand strand : strands) {
            if (strand.equals(strand11)) {
                assert strand.getGenomicFeatures().size() == 1;
                String featureName1 = strand.getGenomicFeatures().get(0).getDisplayName();
                assert featureName1.equals("200_250_feature");
            } else if (strand.equals(strand14)) {
                assert strand.getGenomicFeatures().size() == 2;
                String featureName1 = strand.getGenomicFeatures().get(0).getDisplayName();
                assert featureName1.equals("200_250_feature");
                String featureName2 = strand.getGenomicFeatures().get(1).getDisplayName();
                assert featureName2.equals("1000_1000_feature");
            } else {
                assert strand.getGenomicFeatures().size() == 0;
            }
        }
    }

    /**
     * Test strand with two features.
     */
    @Test(expected = AssertionError.class)
    public void testAssertionThrownWhenFeatureNotInStrands() {
        StrandAnnotator.annotate(strands, features);
    }

    /**
     * Test constructor is private.
     *
     * @throws NoSuchMethodException     the no such method exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws InvocationTargetException the invocation target exception
     * @throws InstantiationException    the instantiation exception
     */
    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Constructor<StrandAnnotator> constructor = StrandAnnotator.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
