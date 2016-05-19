
package com.pl.tagc.tagcwebapp;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the tagcwebapp.
 * Tests that the resource is reachable and returns JSON data.
 *
 * @author Kasper Grabarz
 */
public class RestApiTest extends JerseyTest {

    @Override
    protected ResourceConfig configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(RestApi.class);
    }

    /**
     * Test, that the resource response is in JSON format.
     */
    @Test
    public void testRestApiResource() {
        final Response response = target().path("api/getdimensions").request().get(Response.class);
        assertEquals("application", response.getMediaType().getType());
        assertEquals("json", response.getMediaType().getSubtype());
    }
}
