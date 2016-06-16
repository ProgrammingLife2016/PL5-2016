package com.pl.tagc.tagcwebapp;

import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;

/**
 * The Class AdaptedRibbonNodeTest.
 */
public class AdaptedRibbonNodeTest {

	/**
	 * Tests the getters and setters.
	 */
	@Test
	public void test() {
		BeanTester beanTester = new BeanTester();
		Configuration config = new ConfigurationBuilder().ignoreProperty("mutations").build();
		beanTester.testBean(AdaptedRibbonNode.class,config);
	}

}
