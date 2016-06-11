package ribbonnodes;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class to test the ribbonNodes.
 * Created by Matthijs on 17-5-2016.
 */
public class RibbonNodeTest {

    private RibbonNode node1;
    private RibbonNode node2;
    private RibbonEdge edge;
    private RibbonEdge edge2;


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
		HashSet<String> genomeSet = new HashSet<String>(genomes);
        node1 = new RibbonNode(0, genomeSet);
        node2 = new RibbonNode(1, genomeSet);
        edge = new RibbonEdge(0, 1);
        edge2 = new RibbonEdge(0, 6);
        node1.addEdge(edge);
        node1.addEdge(edge2);
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
        assertEquals("node1\n", node1.getLabel());
        assertEquals("node2\n", node2.getLabel());
    }

    /**
     * Test the getEdge method.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetEdge() throws Exception {
        assertEquals(node1.getOutEdge(0, 1), edge);
        assertEquals(node1.getOutEdge(0, 5), null);
        assertEquals(node2.getInEdge(0, 1), edge);
        assertEquals(node2.getInEdge(1, 0), null);

    }

    /**
     * Test the setter for the nodeLabel.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testSetLabel() throws Exception {
        assertEquals("node1\n", node1.getLabel());
        node1.setLabel("testLabel");
        assertEquals("testLabel\n", node1.getLabel());

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
        assertEquals(node1.getOutEdges().size(), 2);
        assertEquals(node1.getOutEdges().get(0), edge);
    }

    /**
     * Test the addition of an edge to this node.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testAddEdge() throws Exception {
        RibbonEdge edge2 = new RibbonEdge(1, 0);
        assertEquals(node1.getOutEdges().size(), 2);
        node1.addEdge(edge2);
        assertEquals(node1.getOutEdges().size(), 2);
        assertEquals(node1.getInEdges().get(0), edge2);
    }

    /**
     * Test the setting of edges.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testAddEdges() throws Exception {
        RibbonEdge edge2 = new RibbonEdge(0, 1);
        RibbonEdge edge3 = new RibbonEdge(0, 4);
        RibbonEdge edge4 = new RibbonEdge(0, 2);
        node1.addEdge(edge2);
        node1.addEdge(edge3);
        node1.addEdge(edge4);
        assertEquals(node1.getOutEdges().size(), 5);
        assertEquals(node1.getOutEdges().get(4), edge4);
    }

    /**
     * Test the get genomes method.
     *
     * @throws Exception If fail.
     */
    @Test
    public void testGetGenomes() throws Exception {
        assertEquals(node1.getGenomes().size(), 2);
        assertTrue(node1.getGenomes().contains("genome1"));
    }

    /**
     * Test the getter and setter of x.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetSetX() throws Exception {
        assertEquals(0, node1.getX());
        node1.setX(3);
        assertEquals(3, node1.getX());
    }

    /**
     * Test the getter and setter of y.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetSetY() throws Exception {
        assertEquals(0, node1.getY());
        node1.setY(3);
        assertEquals(3, node1.getY());
    }

    /**
     * Test the setters for the in and out edges.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testInOutSetters() {
        RibbonEdge edge3 = new RibbonEdge(0, 6);
        RibbonEdge edge4 = new RibbonEdge(7, 0);
        ArrayList<RibbonEdge> testEdges = new ArrayList<>();
        testEdges.add(edge3);
        testEdges.add(edge4);
        node1.setInEdges(testEdges);
        node1.setOutEdges(testEdges);
        assertEquals(node1.getInEdges(), testEdges);
        assertEquals(node1.getOutEdges(), testEdges);
    }
}