package controller;

import genome.Strand;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The Class GenomeGraphTest.
 */
public class GenomeGraphTest {

	/** The data. */
	private GenomeGraph data;
	
	/** The strand1. */
	private Strand strand1;
	
	/** The strand2. */
	private Strand strand2;
	
	/**
	 * Setting up the GenomeGraph.
	 */
    @Before
    public void setUp() {
    	data = new GenomeGraph();
    	String[] genomes = {"ref1", "ref2"};
    	strand1 = new Strand(1, "AA", genomes, "ref1", 0);
    	strand2 = new Strand(2, "TG", genomes, "ref1", 3);
    	
    	data.addStrand(strand1);
    	data.addStrand(strand2);
    }
    
    /**
     * Tests adding a Strand.
     */
    @Test
    public void testAddStrand() {
    	assertEquals(data.getStrandNodes().get(strand1.getId()), strand1);
    	assertEquals(data.getStrandNodes().get(strand2.getId()), strand2);
    }
}
