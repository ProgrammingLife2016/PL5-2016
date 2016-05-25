package com.pl.tagc.tagcwebapp;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the tagcwebapp. Tests that the resource is reachable and returns data
 * in the right format.
 *
 * @author Kasper Grabarz
 */
public class RestApiTest extends JerseyTest {

	/* (non-Javadoc)
	 * @see org.glassfish.jersey.test.JerseyTest#configure()
	 */
	@Override
	protected ResourceConfig configure() {
		enable(TestProperties.LOG_TRAFFIC);
		return new ResourceConfig(RestApi.class);
	}

	
	/**
	 * Tests that the getphylogenetictree REST endpoint returns the test tree loaded
	 * from the nwk file in the expected format.
	 *
	 * @throws Exception 
	 */
	@Test
	public void testGetPhylogeneticTree() throws Exception {
		
		final String expectedResponse = IOUtils.toString(
				Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testGenomeNwkResponse"),
			      "UTF-8"
			    );
		
		final String response = target().path("api/getphylogenetictree").queryParam("treeId", 0)
				.request().get(String.class);
		assertEquals(expectedResponse, response);
	}

}
