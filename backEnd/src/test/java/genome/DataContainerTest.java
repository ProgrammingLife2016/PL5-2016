package genome;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Matthijs on 24-4-2016.
 */
public class DataContainerTest {

	private DataContainer data;
	private Node node1;
	private Node node2;
	private Edge edge;
	
	/**
	 * Setting up the DataContainer.
	 */
    @Before
    public void setUp() {
    	data = new DataContainer();
    	String[] genomes = {"ref1", "ref2"};
    	node1 = new Node(1, "AA", genomes, "ref1", 0);
    	node2 = new Node(2, "TG", genomes, "ref1", 3);
    	edge = new Edge(1, 2);
    	
    	data.addNode(node1);
    	data.addNode(node2);
    	data.addEdge(edge);
    }
    
    /**
     * Tests adding a node.
     */
    @Test
    public void testAddNode() {
    	assertEquals(data.getNodes().get(node1.getId()), node1);
    	assertEquals(data.getNodes().get(node2.getId()), node2);
    }
    
    /**
     * Tests adding a Edge.
     */
    @Test
    public void testAddEdge() {
    	assertEquals(data.getEdges().get(edge.getStart() + "|" + edge.getEnd()), edge);
    }
    
    /**
     * Tests giving the nodes coordinates.
     */
    @Test
    public void test() {
    	assertTrue(data.calculateCoordinates().get(0).contains(node1));
    	assertTrue(data.calculateCoordinates().get(1).contains(node2));
    }
    
    /**
     * Test data height.
     */
    @Test
    public void testDataHeight() {
    	data.setDataHeight(10.0);
    	assertEquals(data.getDataHeight(), 10.0, 0.001);
    }
    
    /**
     * Test data width.
     */
    @Test
    public void testDataWidth() {
    	data.setDataWidth(5.0);
    	assertEquals(data.getDataWidth(), 5.0, 0.001);
    }
}