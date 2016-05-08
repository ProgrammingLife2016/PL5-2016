package genome;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthijs on 24-4-2016.
 */
public class EdgeTest {

	private Edge edge;
	
	/**
	 * Setting up the Edge with whom we test.
	 */
    @Before
    public void setUp() {
    	edge = new Edge(1, 2);
    }
    
    /**
     * Test getting the startId of the edge.
     */
    @Test
    public void testGetStartId() {
    	assertEquals(edge.getStart(), 1);
    }
    
    /**
     * Test getting the endId of the edge.
     */
    @Test
    public void testGetEndId() {
    	assertEquals(edge.getEnd(), 2);
    }
    
    /**
     * Tests setting and getting the weight of an edge.
     */
    @Test
    public void testWeight() {
    	assertEquals(edge.getWeight(), 0);
    	edge.setWeight(10);
    	assertEquals(edge.getWeight(), 10);
    }
}