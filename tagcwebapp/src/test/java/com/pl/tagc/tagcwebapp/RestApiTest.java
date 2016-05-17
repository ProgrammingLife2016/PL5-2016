
package com.pl.tagc.tagcwebapp;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests the tagcwebapp.
 * Tests that the resource is reachable and returns JSON data.
 *
 * @author Kasper Grabarz
 */
public class RestApiTest extends JerseyTest {

	private WebTarget webTarget;
	
    @Override
    protected ResourceConfig configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(RestApi.class);
    }
    
//    @Before
//    public void setUp() {
//    	webTarget = target().path("api/getdimensions");
//    }

    /**
     * Test, that the resource response is in JSON format.
     */
    @Test
    public void testRestApiResource() {
    	webTarget = target().path("api/getdimensions");
        final Response response = webTarget.request().get(Response.class);
        assertEquals("application", response.getMediaType().getType());
        assertEquals("json", response.getMediaType().getSubtype());
    }
  
    @Test
    public void testGetPhylogeneticTree() {
    	webTarget = target().path("api/getphylogenetictree");
    	final Response response = webTarget.request().get(Response.class);
    	assertEquals("application", response.getMediaType().getType());
    	assertEquals("json", response.getMediaType().getSubtype());
    }
    
    @Test
    public void testGetNodes() {
    	webTarget = target().path("api/getnodes");
    	final Response response = webTarget.request().get(Response.class);
    	assertEquals("application", response.getMediaType().getType());
        assertEquals("json", response.getMediaType().getSubtype());    	
    }
}
