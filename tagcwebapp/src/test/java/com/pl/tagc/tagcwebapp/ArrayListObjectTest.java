package com.pl.tagc.tagcwebapp;


import org.junit.Test;
import org.meanbean.test.BeanTester;

/**
 * The Class ArrayListObjectTest.
 */
public class ArrayListObjectTest {

    /**
     * Tests the getters and setters.
     */
    @Test
    public void test() {
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(ArrayListObject.class);
    }

}
