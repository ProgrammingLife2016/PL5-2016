package com.pl.tagc.tagcwebapp;

import org.junit.Test;
import org.meanbean.test.BeanTester;

/**
 * The Class AdaptedPhylogeneticNodeTest.
 */
public class AdaptedPhylogeneticNodeTest {

	/**
	 * Tests the getters and setters.
	 */
	@Test
	public void test() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(AdaptedPhylogeneticNode.class);
	}

}
