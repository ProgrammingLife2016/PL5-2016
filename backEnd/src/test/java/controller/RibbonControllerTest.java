package controller;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.HashMap;
import genome.Genome;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class RibbonControllerTest.
 */
public class RibbonControllerTest {

	/** The data. */
	private GenomeGraph genomeGraph;
	
	/**
	 * Setting up the GenomeGraph.
	 */
    @Before
    public void setUp() {
    	genomeGraph = new GenomeGraph();
    	HashMap<String, Genome> genomes = new HashMap<String, Genome>();
    	genomes.put("MT_H37RV_BRD_V5.ref.fasta", new Genome("MT_H37RV_BRD_V5.ref.fasta"));
    	genomeGraph.setGenomes(genomes);
    	genomeGraph.setActiveGenomes(new ArrayList<String>(genomes.keySet()));
    }
    
    /**
     * Tests if the graph returns an empty list when given incomplete data.
     */
    @Test
    public void testGetRibbonNodes() {
    	assertTrue(RibbonController.getRibbonNodes(0, 100, 1, genomeGraph).isEmpty());
    }
}
