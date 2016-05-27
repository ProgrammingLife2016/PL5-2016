package controller;

import datatree.DataNode;
import datatree.DataTree;
import org.junit.Before;
import org.junit.Test;
import parser.Parser;
import phylogenetictree.PhylogeneticNode;
import phylogenetictree.PhylogeneticTree;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Matthijs on 26-5-2016.
 */
public class RibbonControllerTest {

    private GenomeGraph genomeGraph;
    private PhylogeneticTree phylogeneticTree;
    private DataTree dataTree;
    private RibbonController ribbonController;

    @Before
    public void setUp() throws Exception {
        genomeGraph = Parser.parse("data/TB10.gfa");
        genomeGraph.generateGenomes();
        genomeGraph.calculateXStrands();
        phylogeneticTree= new PhylogeneticTree();
        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk");
        dataTree = new DataTree(new DataNode(phylogeneticTree.getRoot(),
                null, 0));
        dataTree.addStrands(new ArrayList<>(genomeGraph.getGenomes().values()));
        ribbonController= new RibbonController(genomeGraph,dataTree);

    }

    @Test
    public void testGetRibbonNodes() throws Exception {
        ribbonController.getRibbonNodes(811,864,105);
        System.out.println("not pending");
    }

    @Test
    public void testCalcYcoordinates() throws Exception {

    }

    @Test
    public void testAddEdges() throws Exception {
        ribbonController.getRibbonNodes(0,100000,1);
    }

    @Test
    public void testFindNextNodeWithGenome() throws Exception {

    }
}