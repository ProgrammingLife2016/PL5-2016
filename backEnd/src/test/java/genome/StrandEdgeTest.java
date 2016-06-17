package genome;

import org.junit.Before;
import org.junit.Test;

import strand.Strand;
import strand.StrandEdge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Matthijs on 24-4-2016.
 */
public class StrandEdgeTest {

    private StrandEdge strandEdge;
    private Strand strand1;
    private Strand strand2;

    /**
     * Setting up the StrandEdge with whom we test.
     */
    @Before
    public void setUp() {
        strand1 = new Strand(1);
        strand2 = new Strand(2);
        strandEdge = new StrandEdge(strand1, strand2);
    }

    /**
     * Test getting the startId of the StrandEdge.
     */
    @Test
    public void testGetStartId() {
        assertEquals(strandEdge.getStart().getId(), 1);
    }

    /**
     * Test getting the endId of the StrandEdge.
     */
    @Test
    public void testGetEndId() {
        assertEquals(strandEdge.getEnd().getId(), 2);
    }

    /**
     * Tests setting and getting the weight of an StrandEdge.
     */
    @Test
    public void testWeight() {
        assertEquals(strandEdge.getWeight(), 1);
        strandEdge.setWeight(10);
        assertEquals(strandEdge.getWeight(), 10);
    }

    /**
     * Test if the edge contains specific genomes.
     */
    @Test
    public void testContains() {
        assertTrue(strandEdge.contains(strand1));
        assertTrue(strandEdge.contains(strand2));
        assertFalse(strandEdge.contains(new Strand(1)));
    }
}