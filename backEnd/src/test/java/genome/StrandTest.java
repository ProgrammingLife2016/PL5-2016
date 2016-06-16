package genome;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import mutation.AbstractMutation;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by Matthijs on 24-4-2016.
 */
public class StrandTest {

    private Strand strand;

    /**
     * Sets up a strand with whom we test.
     */
    @Before
    public void setUp() {
        String[] genomes = {"ref1", "ref2"};
		HashSet<String> genomeSet = new HashSet<String>(Arrays.asList(genomes));
        strand = new Strand(1, "AA", genomeSet, "ref1", 0);
    }

    /**
     * Tests getting the id of the strand.
     */
    @Test
    public void testGetId() {
        assertEquals(strand.getId(), 1);
        strand.setId(2);
        assertEquals(strand.getId(), 2);
    }

    /**
     * Tests getting the sequence of the strand.
     */
    @Test
    public void testGetSequence() {
        assertEquals(strand.getSequence(), "AA");
        strand.setSequence("AC");
        assertEquals(strand.getSequence(), "AC");
    }

    /**
     * Tests getting the genomes of the strand.
     */
    @Test
    public void testGetGenomes() {
        assertTrue(strand.getGenomes().contains("ref1"));
        assertTrue(strand.getGenomes().contains("ref2"));
        String[] temp = {"ref3", "ref4"};
		HashSet<String> genomeSet = new HashSet<String>(Arrays.asList(temp));
        strand.setGenomes(genomeSet);
        assertTrue(strand.getGenomes().contains("ref3"));
        assertTrue(strand.getGenomes().contains("ref4"));
    }

    /**
     * Tests getting the reference genome of the strand.
     */
    @Test
    public void testGetReferenceGenome() {
        assertEquals(strand.getReferenceGenome(), "ref1");
        strand.setReferenceGenome("ref2");
        assertEquals(strand.getReferenceGenome(), "ref2");
    }

    /**
     * Tests getting the reference coordinate of the strand.
     */
    @Test
    public void testGetReferenceCoordinate() {
        assertEquals(strand.getStartCoordinate(), 0);
        strand.setStartCoordinate(10);
        assertEquals(strand.getStartCoordinate(), 10);
    }

    /**
     * Tests getting the weight of the strand.
     */
    @Test
    public void testGetWeight() {
        assertEquals(strand.getWeight(), 2);
        strand.setWeight(4);
        assertEquals(strand.getWeight(), 4);
    }


    /**
     * Test the getter of Edges.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetEdges() throws Exception {
        StrandEdge edge = new StrandEdge(strand, new Strand(2));
        strand.addEdge(edge);
        assertEquals(strand.getOutgoingEdges().get(0), edge);
    }
    
    /**
     * Test getting the mutations on this strand.
     */
    @Test
    public void testMutation() {
    	AbstractMutation mutation = Mockito.mock(AbstractMutation.class);
    	strand.addMutation(mutation);
    	assertEquals(strand.getMutations().size(), 1);
    	assertEquals(strand.getMutations().get(0), mutation);
    }
}