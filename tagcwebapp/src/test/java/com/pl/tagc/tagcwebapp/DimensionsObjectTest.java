package com.pl.tagc.tagcwebapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 * @author Jeffrey Helgers.
 * Test the DimensionsObject class.
 */
public class DimensionsObjectTest {

	/**
	 * Create empty dimensions object.
	 */
	@Test
	public void testEmpty() {
		DimensionsObject dimensionsObject = new DimensionsObject();
		assertEquals(dimensionsObject.getHeight(), 0, 0.001);
		assertEquals(dimensionsObject.getWidth(), 0, 0.001);
	}
	
	/**
	 * Test setting width and height.
	 */
	@Test
	public void testWidthHeight() {
		DimensionsObject dimensionsObject = new DimensionsObject(5.0, 4.0);
		assertEquals(dimensionsObject.getWidth(), 5.0, 0.001);
		assertEquals(dimensionsObject.getHeight(), 4.0, 0.001);
		dimensionsObject.setWidth(10.0);
		dimensionsObject.setHeight(9.0);
		assertEquals(dimensionsObject.getWidth(), 10.0, 0.001);
		assertEquals(dimensionsObject.getHeight(), 9.0, 0.001);
	}

}
