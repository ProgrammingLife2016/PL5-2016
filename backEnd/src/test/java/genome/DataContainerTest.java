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
	private Strand strand1;
	private Strand strand2;
	private Edge edge;
	
	/**
	 * Setting up the DataContainer.
	 */
    @Before
    public void setUp() {
    	data = new DataContainer();
    	String[] genomes = {"ref1", "ref2"};
    	strand1 = new Strand(1, "AA", genomes, "ref1", 0);
    	strand2 = new Strand(2, "TG", genomes, "ref1", 3);
    	edge = new Edge(1, 2);
    	
    	data.addNode(strand1);
    	data.addNode(strand2);
    	data.addEdge(edge);
    }
    
    /**
     * Tests adding a node.
     */
    @Test
    public void testAddNode() {
    	assertEquals(data.getNodes().get(strand1.getId()), strand1);
    	assertEquals(data.getNodes().get(strand2.getId()), strand2);
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
    	assertTrue(data.calculateCoordinates().get(0).contains(strand1));
    	assertTrue(data.calculateCoordinates().get(1).contains(strand2));
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