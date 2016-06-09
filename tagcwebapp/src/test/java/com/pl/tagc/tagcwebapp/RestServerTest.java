package com.pl.tagc.tagcwebapp;

import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import controller.Controller;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the tagcwebapp
 *
 * @author Kasper Grabarz
 */
public class RestServerTest {

	/**
	 * Test rest server.
	 */
	@Test
	public void testRestServer() {
		Exception ex = null;
	    try {
			@SuppressWarnings("unused")
			RestServer server = new RestServer();
			server.startServer();
	    } catch (Exception e) {
	        ex = e;
	    }
	    assertTrue(ex == null);
	}
}