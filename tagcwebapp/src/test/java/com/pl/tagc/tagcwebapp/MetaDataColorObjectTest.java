package com.pl.tagc.tagcwebapp;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.HashMap;

import org.junit.Test;

/**
 * 
 * @author Jeffrey Helgers.
 * Test the metaDataColorObject.
 */
public class MetaDataColorObjectTest {

	/**
	 * Test getting the list of the metadata.
	 */
	@Test
	public void test() {
		HashMap<String, Color> map = new HashMap<>();
		map.put("test", new Color(2));
		MetaDataColorObject meta = new MetaDataColorObject(map);
		meta.setlist(map);
		assertEquals(meta.getlist().get("test").length(), 6);
	}

}
