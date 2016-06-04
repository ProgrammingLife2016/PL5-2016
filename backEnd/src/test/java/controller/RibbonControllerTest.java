package controller;

import datatree.DataNode;
import datatree.DataTree;
import genome.GenomeGraph;
import genome.Strand;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * A class that tests the RibbonController.
 * Created by Matthijs on 30-5-2016.
 */
public class RibbonControllerTest {

    private RibbonController controller; // the ribboncontroller to test.

    /**
     * Set up the test.
     *
     * @throws Exception if fail.
     */
    @SuppressWarnings("checkstyle:methodlength")
    @Before
    public void setUp() throws Exception {
        GenomeGraph genomeGraph = new GenomeGraph();


        String[] strand1Genomes = {"1"};
		HashSet<String> genomeSet = new HashSet<String>(Arrays.asList(strand1Genomes));
        Strand strand1 = new Strand(1, "tagc", genomeSet, "1", 0);

        String[] strand2Genomes = {"2"};
        genomeSet = new HashSet<String>(Arrays.asList(strand2Genomes));
        Strand strand2 = new Strand(10, "tagc", genomeSet, "2", 0);

        String[] strand12Genomes = {"1", "2"}; 
        genomeSet = new HashSet<String>(Arrays.asList(strand12Genomes));
        Strand stand12 = new Strand(5, "tagc", genomeSet, "2", 0);

        DataNode root = new DataNode(null, 0);
        DataNode child1 = new DataNode(root, 0);
        DataNode child2 = new DataNode(root, 1);
        ArrayList<String> child1Genomes = new ArrayList<>();
        ArrayList<String> child2Genomes = new ArrayList<>();
        ArrayList<String> parentGenomes = new ArrayList<>();
        child1Genomes.add("1");
        child2Genomes.add("2");
        parentGenomes.add("1");
        parentGenomes.add("2");
        child1.setGenomes(child1Genomes);
        child2.setGenomes(child2Genomes);
        root.addChild(child1);
        root.addChild(child2);
        root.setGenomes(parentGenomes);

        DataTree dataTree = new DataTree(root);
        controller = new RibbonController(genomeGraph, dataTree);


        genomeGraph.addStrand(strand1);
        genomeGraph.addStrand(strand2);
        genomeGraph.addStrand(stand12);

        genomeGraph.generateGenomes();
        genomeGraph.findStartAndCalculateX();
        dataTree.addStrands(new ArrayList<>(genomeGraph.getGenomes().values()));
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


}