package genome;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthijs on 24-4-2016.
 */
public class strandEdgeTest {

	private StrandEdge StrandEdge;

	/**
	 * Setting up the StrandEdge with whom we test.
	 */
    @Before
    public void setUp() {
    	StrandEdge = new StrandEdge(1, 2);
    }
    
    /**
     * Test getting the startId of the StrandEdge.
     */
    @Test
    public void testGetStartId() {
    	assertEquals(StrandEdge.getStart(), 1);
    }
    
    /**
     * Test getting the endId of the StrandEdge.
     */
    @Test
    public void testGetEndId() {
    	assertEquals(StrandEdge.getEnd(), 2);
    }
    
    /**
     * Tests setting and getting the weight of an StrandEdge.
     */
    @Test
    public void testWeight() {
    	assertEquals(StrandEdge.getWeight(), 1);
    	StrandEdge.setWeight(10);
    	assertEquals(StrandEdge.getWeight(), 10);
    }
}