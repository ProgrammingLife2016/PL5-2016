package com.pl.tagc.tagcwebapp;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests the tagcwebapp.
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
            RestServer server = new RestServer();
            server.startServer();
        } catch (Exception e) {
            ex = e;
        }
        assertTrue(ex == null);
    }
}