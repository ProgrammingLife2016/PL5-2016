package controller;

import datatree.DataTree;
import genome.Genome;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;

import java.util.ArrayList;
import java.util.Arrays;

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

    /**
     * Test that getRibbonNodes calls the right methods.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetRibbonNodes() throws Exception {
        RibbonController testController = Mockito.mock(RibbonController.class);
        Mockito.doCallRealMethod().when(testController).getRibbonNodes(0, 1000, 10);
        testController.getRibbonNodes(0, 1000, 10);
        Mockito.verify(graph, Mockito.times(1)).getActiveGenomes();
        Mockito.verify(tree, Mockito.times(1)).getStrands(0, 1000, graph.getActiveGenomes(), 10);
        Mockito.verify(testController, Mockito.times(1)).createNodesFromStrands(new ArrayList<>(),
                new ArrayList<>(Arrays.asList("1")),
                10);
        Mockito.verify(testController, Mockito.times(1)).spreadYCoordinates(
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList("1")));
        Mockito.verify(testController, Mockito.times(1)).addEdges(new ArrayList<>());
        //  Mockito.verify(testController, Mockito.times(1)).collapseRibbons(new ArrayList<>());


    }

    /**
     * Test if the collapsing of ribbons works correctly.
     *
     * @throws Exception if fail.
     */

    @Test
    public void testCollapseRibbons() throws Exception {
        ArrayList<String> genomes = new ArrayList<>();
        genomes.add("1");
        ArrayList<RibbonNode> nodes = new ArrayList<>();
        RibbonNode node1 = new RibbonNode(0, genomes);
        RibbonNode node2 = new RibbonNode(1, genomes);
        RibbonNode node3 = new RibbonNode(2, genomes);
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        RibbonEdge edge1 = new RibbonEdge(0, 1);
        RibbonEdge edge2 = new RibbonEdge(1, 2);
        node1.addEdge(edge1);
        node2.addEdge(edge1);
        node2.addEdge(edge2);
        node3.addEdge(edge2);
        assertEquals(3, nodes.size());
        controller.collapseRibbons(nodes);
        assertEquals(1, nodes.size());
    }


    /**
     * Test if the getNodewithid works for an id that is contained and one that is not.
     *
     * @throws Exception if fail
     */
    @Test
    public void testGetNodeWithId() throws Exception {
        ArrayList<String> genomes = new ArrayList<>();
        genomes.add("1");
        ArrayList<RibbonNode> nodes = new ArrayList<>();
        RibbonNode node1 = new RibbonNode(0, genomes);
        RibbonNode node2 = new RibbonNode(1, genomes);
        RibbonNode node3 = new RibbonNode(2, genomes);
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        assertEquals(controller.getNodeWithId(1, nodes, 0), node2);
        assertEquals(controller.getNodeWithId(5, nodes, 0), null);
        assertEquals(controller.getNodeWithId(1, nodes, 5), null);
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
        RibbonNode node1 = new RibbonNode(0, new ArrayList<>(Arrays.asList("1")));
        RibbonNode node2 = new RibbonNode(1, new ArrayList<>(Arrays.asList("1", "2")));
        RibbonNode node3 = new RibbonNode(2, new ArrayList<>(Arrays.asList("1", "2", "3")));
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        controller.spreadYCoordinates(nodes, actGen);
        assertEquals(nodes.size(), 4);

        for (RibbonNode node : nodes) {
            if (node.getGenomes().size() == actGen.size()) {
                assertEquals(node.getY(), 0);
            } else if (node.getGenomes().size() == 1) {
                if (node.getGenomes().get(0).equals("1")) {
                    assertEquals(node.getY(), 20);
                } else if (node.getGenomes().get(0).equals("2")) {
                    assertEquals(node.getY(), -20);
                }

            } else {
                fail();
            }
        }
    }

    /**
     * Test if the edges are added correctly to the RibbonNodes for a genome.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testAddEdges() throws Exception {
        ArrayList<String> genomes = new ArrayList<>();
        genomes.add("1");
        ArrayList<RibbonNode> nodes = new ArrayList<>();
        RibbonNode node1 = new RibbonNode(0, genomes);
        RibbonNode node2 = new RibbonNode(1, genomes);
        RibbonNode node3 = new RibbonNode(2, genomes);
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        controller.addEdges(nodes);

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
        RibbonNode node1 = new RibbonNode(0, new ArrayList<>(Arrays.asList("1", "2")));
        RibbonNode node2 = new RibbonNode(1, new ArrayList<>(Arrays.asList("1", "2")));
        nodes.add(node1);
        nodes.add(node2);

        assertEquals(node1.getOutEdges().size(), 0);
        assertEquals(controller.addEdgeReturnEnd(nodes, node1, new Genome("1")), node2);
        assertEquals(node1.getOutEdge(node1.getId(), node2.getId()).getWeight(), 1);
        assertEquals(controller.addEdgeReturnEnd(nodes, node1, new Genome("2")), node2);
        assertEquals(node1.getOutEdge(node1.getId(), node2.getId()).getWeight(), 2);


    }


    /**
     * Assert the next node with a certain genome is returned.
     *
     * @throws Exception if fail
     */
    @Test
    public void testFindNextNodeWithGenome() throws Exception {

        ArrayList<RibbonNode> nodes = new ArrayList<>();
        RibbonNode node1 = new RibbonNode(0, new ArrayList<>(Arrays.asList("1")));
        RibbonNode node2 = new RibbonNode(1, new ArrayList<>(Arrays.asList("1", "2")));
        RibbonNode node3 = new RibbonNode(2, new ArrayList<>(Arrays.asList("1", "2", "3")));
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
    @Test
    public void testAddMutationLabels() throws Exception {

    }
}