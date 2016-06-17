package com.pl.tagc.tagcwebapp;

import org.junit.Test;
import org.meanbean.test.BeanTester;

/**
 * 
 * @author Jeffrey Helgers.
 * Test the metaDataObject.
 */
public class MetaDataObjectTest {

	/**
	 * Test getters and setters.
	 */
	@Test
	public void test() {
		BeanTester beanTester = new BeanTester();
        beanTester.testBean(MetaDataObject.class);
	}

}
