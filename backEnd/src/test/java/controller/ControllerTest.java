package controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthijs on 24-4-2016.
 */
public class ControllerTest {

	private Controller data;
	
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
    	data.setDataWidth(5.0);
    	assertEquals(data.getDataWidth(), 5.0, 0.001);
    }
}