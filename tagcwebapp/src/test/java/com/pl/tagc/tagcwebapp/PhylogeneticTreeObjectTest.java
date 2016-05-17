package com.pl.tagc.tagcwebapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import phylogenetictree.PhylogeneticTree;

/**
 * 
 * @author Jeffrey Helgers.
 * Test the PhylogeneticTreeObject class.
 */
public class PhylogeneticTreeObjectTest {

	/**
	 * Test empty tree.
	 */
	@Test
	public void testEmptyTree() {
		PhylogeneticTreeObject phylogeneticTreeObject = new PhylogeneticTreeObject();
		assertEquals(phylogeneticTreeObject.getPhylogeneticTree(), null);
	}
	
	/**
	 * Test get tree
	 */
	@Test
	public void testGetTree() {
		PhylogeneticTree tree = new PhylogeneticTree();
		PhylogeneticTreeObject phylogeneticTreeObject = new PhylogeneticTreeObject(tree);
		assertEquals(phylogeneticTreeObject.getPhylogeneticTree(), tree);
	}
}
