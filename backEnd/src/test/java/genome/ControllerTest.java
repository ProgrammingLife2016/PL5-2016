package genome;
import controller.Controller;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Matthijs on 24-4-2016.
 */
public class ControllerTest {

	private Controller data;
	private Strand strand1;
	private Strand strand2;
	private StrandEdge StrandEdge;
	
	/**
	 * Setting up the Controller.
	 */
    @Before
    public void setUp() {
    	data = new controller.Controller();
    	String[] genomes = {"ref1", "ref2"};
    	strand1 = new Strand(1, "AA", genomes, "ref1", 0);
    	strand2 = new Strand(2, "TG", genomes, "ref1", 3);
    	StrandEdge = new StrandEdge(1, 2);
    	
    	data.addStrand(strand1);
    	data.addStrand(strand2);
    	data.addEdge(StrandEdge);
    }
    
    /**
     * Tests adding a node.
     */
    @Test
    public void testAddNode() {
    	assertEquals(data.getstrandNodes().get(strand1.getId()), strand1);
    	assertEquals(data.getstrandNodes().get(strand2.getId()), strand2);
    }
    
    /**
     * Tests adding a StrandEdge.
     */
    @Test
    public void testAddEdge() {
    	assertEquals(data.getEdges().get(StrandEdge.getStart() + "|" + StrandEdge.getEnd()), StrandEdge);
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