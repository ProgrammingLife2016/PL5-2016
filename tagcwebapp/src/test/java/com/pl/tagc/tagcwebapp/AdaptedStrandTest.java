package com.pl.tagc.tagcwebapp;


import org.junit.Test;
import org.meanbean.test.BeanTester;

/**
 * The Class AdaptedStrandTest.
 */
public class AdaptedStrandTest {

	/**
	 * Tests the getters and setters.
	 */
	@Test
	public void test() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(AdaptedStrand.class);
	}

}
