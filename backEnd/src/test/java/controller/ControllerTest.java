package controller;

import genome.Strand;
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
	
	/**
	 * Setting up the Controller.
	 */
    @Before
    public void setUp() {
    	data = new Controller("data/TB10.gfa", "data/340tree.rooted.TKK.nwk");
    	String[] genomes = {"ref1", "ref2"};
    	strand1 = new Strand(1, "AA", genomes, "ref1", 0);
    	strand2 = new Strand(2, "TG", genomes, "ref1", 3);
    	
    	data.addStrand(strand1);
    	data.addStrand(strand2);
    }
    
    /**
     * Tests adding a Strand.
     */
    @Test
    public void testAddStrand() {
    	assertEquals(data.getStrandNodes().get(strand1.getId()), strand1);
    	assertEquals(data.getStrandNodes().get(strand2.getId()), strand2);
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