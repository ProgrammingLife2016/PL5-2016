package genome;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
    	String[] genomeIds = {"ref1", "ref2"};
		HashSet<String> genomeSet = new HashSet<String>(Arrays.asList(genomeIds));
		HashMap<String, Genome> genomes = new HashMap<>();
		genomes.put("ref1", new Genome("ref1"));
		
    	strand1 = new Strand(1, "AA", genomeSet, "ref1", 0);
    	strand2 = new Strand(2, "TG", genomeSet, "ref1", 3);
    	
    	data.addStrand(strand1);
    	data.addStrand(strand2);
    	data.setGenomes(genomes);
    }
    
    /**
     * Tests adding a Strand.
     */
    @Test
    public void testGetStrands() {
    	assertEquals(data.getStrands().get(strand1.getId()), strand1);
    	assertEquals(data.getStrands().get(strand2.getId()), strand2);
    }
    
    /**
     * Test getting the genomes in the graph.
     */
    @Test
    public void testGetGenomes() {
    	assertEquals(data.getGenomes().get("ref1").getId(), "ref1");
    	assertEquals(data.getGenome("ref1").getId(), "ref1");
    }
    
    /**
     * Test getting the active genomes in the graph.
     */
    @Test
    public void testGetActiveGenomes() {
    	ArrayList<String> active = new ArrayList<>(Arrays.asList("ref1", "ref2"));
    	List<String> notPresent = data.setGenomesAsActive(active);
    	assertEquals(data.getActiveGenomes().size(), 1);
    	assertEquals(data.getActiveGenomes().get(0).getId(), "ref1");
    	assertEquals(notPresent.size(), 1);
    	assertEquals(notPresent.get(0), "ref2");
    }
}
