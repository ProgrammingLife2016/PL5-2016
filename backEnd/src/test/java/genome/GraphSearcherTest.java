package genome;

import genome.GraphSearcher.SearchType;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

/**
 * The Class GraphSearcherTest.
 */
public class GraphSearcherTest {

    /**
     * The strands.
     */
    private HashMap<Integer, Strand> strands = new HashMap<Integer, Strand>();

    /**
     * The genome graph.
     */
    private GenomeGraph genomeGraph = new GenomeGraph();

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        for (int i = 0; i < 20; i++) {
            strands.put(i, new Strand(i));
        }
        for (Strand strand : strands.values()) {
            strand.addGenomicFeature(new GenomicFeature(0, 0, "test"));
        }
        genomeGraph.setStrands(strands);
    }


    /**
     * Tests that the found strand has a feature with the testName as display
     * name.
     */
    @Test
    public void testSearch() {

        strands.get(14).getGenomicFeatures().get(0).setDisplayName("hidden egg");

        GSearchResult searchResult = GraphSearcher.search("egg",
                SearchType.GenomicFeatureSearch, genomeGraph);

        Strand strand = searchResult.getgFeatureSearchMatches().get(0).getStrand();
        String featureName = strand.getGenomicFeatures().get(0).getDisplayName();

        assertTrue(searchResult.getgFeatureSearchMatches().size() == 1);
        assertTrue(featureName.equals("hidden egg"));
        assertTrue(strand.getId() == 14);

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
        Constructor<GraphSearcher> constructor = GraphSearcher.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

}
