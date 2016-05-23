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
    	data = new Controller();
    }
    
    /**
     * Test data width.
     */
    @Test
    public void testDataWidth() {
    	//data.setDataWidth(5.0);
    	assertEquals(data.getDataWidth(), 5.0, 0.001);
    }
}