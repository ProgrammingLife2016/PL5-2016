package genome;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthijs on 24-4-2016.
 */
public class StrandEdgeTest {

	private StrandEdge strandEdge;

	/**
	 * Setting up the StrandEdge with whom we test.
	 */
    @Before
    public void setUp() {
    	strandEdge = new StrandEdge(1, 2);
    }
    
    /**
     * Test getting the startId of the StrandEdge.
     */
    @Test
    public void testGetStartId() {
    	assertEquals(strandEdge.getStart(), 1);
    }
    
    /**
     * Test getting the endId of the StrandEdge.
     */
    @Test
    public void testGetEndId() {
    	assertEquals(strandEdge.getEnd(), 2);
    }
    
    /**
     * Tests setting and getting the weight of an StrandEdge.
     */
    @Test
    public void testWeight() {
    	assertEquals(strandEdge.getWeight(), 1);
    	strandEdge.setWeight(10);
    	assertEquals(strandEdge.getWeight(), 10);
    }
}