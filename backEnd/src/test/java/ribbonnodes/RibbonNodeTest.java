package ribbonnodes;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test class to test the ribbonNodes.
 * Created by Matthijs on 17-5-2016.
 */
public class RibbonNodeTest {

    RibbonNode node1;
    RibbonNode node2;
    RibbonEdge edge;

    /**
     * Set up the tests.
     *
     * @throws Exception if fail.
     */
    @Before
    public void setUp() throws Exception {
        ArrayList<String> genomes = new ArrayList<>();
        genomes.add("genome1");
        genomes.add("genome2");
        node1 = new RibbonNode(0, genomes);
        node2 = new RibbonNode(1, genomes);
        edge = new RibbonEdge(0, 1);
        node1.addEdge(edge);
        node2.addEdge(edge);
        node1.setLabel("node1");
        node2.setLabel("node2");

    }

    /**
     * Test getter of the label of this node.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetLabel() throws Exception {
        assertEquals("node1", node1.getLabel());
        assertEquals("node2", node2.getLabel());
    }

    /**
     * Test the getEdge method
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetEdge() throws Exception {
        assertEquals(node1.getEdge(0, 1), edge);
        assertEquals(node2.getEdge(0, 1), edge);
        assertEquals(node1.getEdge(1, 0), null);

    }

    /**
     * Test the setter for the nodeLabel.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testSetLabel() throws Exception {
        assertEquals("node1", node1.getLabel());
        node1.setLabel("testLabel");
        assertEquals("testLabel", node1.getLabel());

    }

    /**
     * Test the getter for id.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetId() throws Exception {
        assertEquals(node1.getId(), 0);
        assertEquals(node2.getId(), 1);
    }

    /**
     * Test the getter for edges.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetEdges() throws Exception {
        assertEquals(node1.getEdges().size(), 1);
        assertEquals(node1.getEdges().get(0), edge);
    }

    /**
     * Test the addition of an edge to this node.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testAddEdge() throws Exception {
        RibbonEdge edge2 = new RibbonEdge(1, 0);
        assertEquals(node1.getEdges().size(), 1);
        node1.addEdge(edge2);
        assertEquals(node1.getEdges().size(), 2);
        assertEquals(node1.getEdges().get(1), edge2);
    }

    /**
     * Test the setting of edges.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testSetEdges() throws Exception {
        RibbonEdge edge2 = new RibbonEdge(0, 1);
        RibbonEdge edge3 = new RibbonEdge(0, 4);
        RibbonEdge edge4 = new RibbonEdge(0, 2);
        ArrayList<RibbonEdge> edges = new ArrayList<>();
        edges.add(edge2);
        edges.add(edge3);
        edges.add(edge4);
        node1.setEdges(edges);
        assertEquals(node1.getEdges().size(), 3);
        assertEquals(node1.getEdges().get(2), edge4);
    }

    @Test
    public void testGetGenomes() throws Exception {
        assertEquals(node1.getGenomes().size(), 2);
        assertEquals(node1.getGenomes().get(0), "genome1");
    }
}