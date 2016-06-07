package controller;

import datatree.DataNode;
import datatree.DataTree;
import genome.Genome;
import genome.GenomeGenerator;
import genome.GenomeGraph;
import genome.Strand;
import genome.StrandEdge;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * A class that tests the RibbonController.
 * Created by Matthijs on 30-5-2016.
 */
public class RibbonControllerTest {

    /** The controller. */
    private RibbonController controller; // the ribboncontroller to test.

    /**
     * Set up the test.
     *
     * @throws Exception if fail.
     */
    @Before
    public void setUp() throws Exception {
        GenomeGraph genomeGraph = new GenomeGraph();
        String[] genomeIds = new String[]{"1", "2"};
        HashMap<Integer, Strand> strands =  generateThreeStrandsWithTwoGenomes(genomeIds);
        genomeGraph.setStrandNodes(strands);
        genomeGraph.setGenomes(GenomeGenerator.generateGenomes(genomeIds, genomeGraph));
        genomeGraph.findStartAndCalculateX();
        ArrayList<Genome> genomes = new ArrayList<>(genomeGraph.getGenomes().values());
        
        DataNode root = new DataNode(null, 0);
        DataNode child1 = new DataNode(root, 0);
        DataNode child2 = new DataNode(root, 1);
        root.addGenomeId(genomes.get(0).getId());
        root.addGenomeId(genomes.get(1).getId());
        child1.addGenomeId(genomes.get(0).getId());
        child2.addGenomeId(genomes.get(1).getId());
        root.addChild(child1);
        root.addChild(child2);
        DataTree dataTree = new DataTree(root);
        
        controller = new RibbonController(genomeGraph, dataTree);

        dataTree.addStrandsFromGenomes(genomes);
        // add all genomes to active genomes.
        genomeGraph.setGenomesAsActive(new ArrayList<>(genomeGraph.getGenomes().keySet()));

    }

	/**
     * Test if the getRibbonNodes method returns the right ribbonNodes.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetRibbonNodes() throws Exception {

        assertEquals(3, controller.getRibbonNodes(0, 1, 2).size());
        assertEquals(3, controller.getRibbonNodes(0, 1, 0).size());
        assertEquals(3, controller.getRibbonNodes(100, 1000, 0).size());

    }
    
    /**
     * Generate three strands with two genomes.
     * @param genomeIds 
     *
     * @return the hash map
     */
    private HashMap<Integer, Strand> generateThreeStrandsWithTwoGenomes(String[] genomeIds) {
    	String[] strand1Genomes = {genomeIds[0]};
		HashSet<String> genomeSet = new HashSet<String>(Arrays.asList(strand1Genomes));
        Strand strand1 = new Strand(1, "tagc", genomeSet, "1", 0);

        String[] strand2Genomes = {genomeIds[1]};
        genomeSet = new HashSet<String>(Arrays.asList(strand2Genomes));
        Strand strand2 = new Strand(10, "tagc", genomeSet, "2", 0);

        String[] strand12Genomes = {genomeIds[0], genomeIds[1]}; 
        genomeSet = new HashSet<String>(Arrays.asList(strand12Genomes));
        Strand strand12 = new Strand(5, "tagc", genomeSet, "2", 0);
        HashMap<Integer, Strand> strands = new HashMap<Integer, Strand>();
        StrandEdge edge1 = new StrandEdge(strand1,strand12);
        StrandEdge edge2 = new StrandEdge(strand2,strand12);
        strand1.addEdge(edge1);
        strand2.addEdge(edge2);
        strand12.addEdge(edge1);
        strand12.addEdge(edge2);
        strands.put(strand1.getId(), strand1);
        strands.put(strand2.getId(), strand2);
        strands.put(strand12.getId(), strand12);        
		return strands;
	}



}