package ribbonnodes;

import datatree.DataNode;
import datatree.DataTree;
import datatree.TempReadWriteTree;
import genome.Genome;
import genome.GenomeGraph;
import mutation.AbstractMutation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import parser.Parser;
import phylogenetictree.PhylogeneticTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Test class to test the ribbonController.
 * Created by Matthijs on 8-6-2016.
 */
public class RibbonControllerTest {

    /**
     * The controller to test.
     */
    private RibbonController controller;

    /**
     * The DataTree mock.
     */
    private DataTree tree;

    /**
     * The graph Mock.
     */
    private GenomeGraph graph;

    /**
     * Initialize the controller.
     *
     * @throws Exception if fail.
     */
    @Before
    public void setUp() throws Exception {
        graph = Mockito.mock(GenomeGraph.class);
        ArrayList<Genome> genomes = new ArrayList<>();
        genomes.add(new Genome("1"));
        Mockito.when(graph.getActiveGenomes()).thenReturn(genomes);
        tree = Mockito.mock(DataTree.class);
        controller = new RibbonController(graph, tree);

    }


//    @Test
//    public void testUsability() throws Exception {
//        String gfaFile = "data/TB328.gfa";
//        GenomeGraph genomeGraph = Parser.parse(gfaFile);
//
//        PhylogeneticTree phylogeneticTree = new PhylogeneticTree();
//        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk",
//                new ArrayList<>(genomeGraph.getGenomes().keySet()));
//        DataTree dataTree = new DataTree(new DataNode(phylogeneticTree.getRoot(),
//                null, 0));
//
//        if (gfaFile.equals("data/TB328.gfa")) {
//            TempReadWriteTree.readFile(dataTree,
//            genomeGraph.getStrands(), "data/tempTree.txt");
//        } else {
//            dataTree.addStrandsFromGenomes(new ArrayList<>(genomeGraph.getGenomes().values()));
//
//        }
//        RibbonController ribbonController = new RibbonController(genomeGraph, dataTree);
//
//        genomeGraph.setGenomesAsActive(new ArrayList<>(
//            Arrays.asList("TKK_03_0059", "TKK-01-0058")));
//        ribbonController.getRibbonNodes(0, 10000000, 1, true);
//    }

    /**
     * Test that getRibbonNodes calls the right methods.
     *
     * @throws Exception if fail.
     */
    @SuppressWarnings("CPD-START")
    @Test
    public void testGetRibbonNodes() throws Exception {
        RibbonController testController = Mockito.mock(RibbonController.class);
        Mockito.doCallRealMethod().when(testController).getRibbonNodes(0, 1000, 10, false);
        testController.getRibbonNodes(0, 1000, 10, false);
        Mockito.verify(testController, Mockito.times(1)).createNodesFromStrands(new ArrayList<>(),
                new ArrayList<>(Arrays.asList("1")));
        Mockito.verify(testController, Mockito.times(1)).spreadYCoordinates(
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList("1")));
        Mockito.verify(testController, Mockito.times(1)).addEdges(new ArrayList<>(), false);
        Mockito.verify(testController, Mockito.times(1)).collapseRibbons(new ArrayList<>(), 0, 1000);


    }

    /**
     * Test if the collapsing of ribbons works correctly.
     *
     * @throws Exception if fail.
     */

    @Test
    public void testCollapseRibbons() throws Exception {
        HashSet<String> genomes = new HashSet<String>();
        genomes.add("1");
        ArrayList<RibbonNode> nodes = new ArrayList<>();
        RibbonNode node1 = new RibbonNode(0, genomes);
        RibbonNode node2 = new RibbonNode(1, genomes);
        RibbonNode node3 = new RibbonNode(2, genomes);
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        RibbonEdge edge1 = new RibbonEdge(node1, node2);
        RibbonEdge edge2 = new RibbonEdge(node2, node3);
        node1.addEdge(edge1);
        node2.addEdge(edge1);
        node2.addEdge(edge2);
        node3.addEdge(edge2);
        assertEquals(3, nodes.size());
        controller.collapseRibbons(nodes, -1, 10);
        assertEquals(2, nodes.size());
    }




    /**
     * Assert the nodes are split to one ribbon with common nodes in the middle,
     * and other ribbons placed at their respective Ys containing only one genome.
     *
     * @throws Exception if fail.
     */

    @Test
    public void testSpreadYCoordinates() throws Exception {
        ArrayList<String> actGen = new ArrayList<>();
        actGen.add("1");
        actGen.add("2");
        actGen.add("3");
        ArrayList<RibbonNode> nodes = new ArrayList<>();
        RibbonNode node1 = new RibbonNode(0, new HashSet<String>(Arrays.asList("1")));
        RibbonNode node2 = new RibbonNode(1, new HashSet<String>(Arrays.asList("1", "2")));
        RibbonNode node3 = new RibbonNode(2, new HashSet<String>(Arrays.asList("1", "2", "3")));
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        controller.spreadYCoordinates(nodes, actGen);
        assertEquals(nodes.size(), 3);

       
    }

    /**
     * Test if the edges are added correctly to the RibbonNodes for a genome.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testAddEdges() throws Exception {
    	HashSet<String> genomes = new HashSet<String>();
        genomes.add("1");
        ArrayList<RibbonNode> nodes = new ArrayList<>();
        RibbonNode node1 = new RibbonNode(0, genomes);
        RibbonNode node2 = new RibbonNode(1, genomes);
        RibbonNode node3 = new RibbonNode(2, genomes);
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        controller.addEdges(nodes, false);

        assertNotNull(node1.getOutEdge(node1.getId(), node2.getId()));
        assertNotNull(node2.getInEdge(node1.getId(), node2.getId()));
        assertNotNull(node2.getOutEdge(node2.getId(), node3.getId()));
        assertNotNull(node3.getInEdge(node2.getId(), node3.getId()));
    }

    /**
     * Assert an edge is added to a node or the weight is incremented when the edge already exists.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testAddEdgeReturnEnd() throws Exception {

        ArrayList<RibbonNode> nodes = new ArrayList<>();
        RibbonNode node1 = new RibbonNode(0, new HashSet<String>(Arrays.asList("1", "2")));
        RibbonNode node2 = new RibbonNode(1, new HashSet<String>(Arrays.asList("1", "2")));
        nodes.add(node1);
        nodes.add(node2);

        assertEquals(node1.getOutEdges().size(), 0);
        assertEquals(controller.addEdgeReturnEnd(nodes, node1, new Genome("1"), false), node2);
        assertEquals(node1.getOutEdge(node1.getId(), node2.getId()).getWeight(), 1);
        assertEquals(controller.addEdgeReturnEnd(nodes, node1, new Genome("2"), false), node2);
        assertEquals(node1.getOutEdge(node1.getId(), node2.getId()).getWeight(), 2);


    }


    /**
     * Assert the next node with a certain genome is returned.
     *
     * @throws Exception if fail
     */
    @SuppressWarnings("CPD-END")

    @Test
    public void testFindNextNodeWithGenome() throws Exception {

        ArrayList<RibbonNode> nodes = new ArrayList<>();
        RibbonNode node1 = new RibbonNode(0, new HashSet<String>(Arrays.asList("1")));
        RibbonNode node2 = new RibbonNode(1, new HashSet<String>(Arrays.asList("1", "2")));
        RibbonNode node3 = new RibbonNode(2, new HashSet<String>(Arrays.asList("1", "2", "3")));
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        assertEquals(controller.findNextNodeWithGenome(nodes, new Genome("2"), 1), node3);
    }
 

    /**
     * Test the adding of mutation labels. Empty for now.
     *
     * @throws Exception if fail.
     */

}