package com.pl.tagc.tagcwebapp;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;

import genome.Node;

/**
 * 
 * @author Jeffrey Helgers.
 * Test the NodeListObject class.
 */
public class NodeListObjectTest {

	/**
	 * Test empty object.
	 */
	@Test
	public void testEmptyObject() {
		NodeListObject nodeListObject = new NodeListObject();
		assertEquals(nodeListObject.getcList(), null);
	}
	
	/**
	 * Test get the node list.
	 */
	@Test
	public void testGetcList() {
		CopyOnWriteArrayList<Node> cList = new CopyOnWriteArrayList<>();
		NodeListObject nodeListObject = new NodeListObject(cList);
		assertEquals(nodeListObject.getcList(), cList);
	}
}
