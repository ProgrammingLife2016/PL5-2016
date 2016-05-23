package controller;
import controller.Controller;
import genome.Strand;
import genome.StrandEdge;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthijs on 24-4-2016.
 */
public class ControllerTest {

	private Controller data;
	private Strand strand1;
	private Strand strand2;
	private StrandEdge edge;
	
	/**
	 * Setting up the Controller.
	 */
    @Before
    public void setUp() {
    	data = new Controller();
    	String[] genomes = {"ref1", "ref2"};
    	strand1 = new Strand(1, "AA", genomes, "ref1", 0);
    	strand2 = new Strand(2, "TG", genomes, "ref1", 3);
    	edge = new StrandEdge(1, 2);
    	
    	data.addStrand(strand1);
    	data.addStrand(strand2);
    	data.addEdge(edge);
    }
    
    /**
     * Tests adding a Strand.
     */
    @Test
    public void testAddStrand() {
    	assertEquals(data.getstrandNodes().get(strand1.getId()), strand1);
    	assertEquals(data.getstrandNodes().get(strand2.getId()), strand2);
    }
    
    /**
     * Tests adding a Edge.
     */
    @Test
    public void testAddEdge() {
		assertEquals(strand1.getEdges().size(),1);
		StrandEdge edge2 = new StrandEdge(1, 5);
		data.addEdge(edge2);
		assertEquals(strand1.getEdges().size(),2);

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