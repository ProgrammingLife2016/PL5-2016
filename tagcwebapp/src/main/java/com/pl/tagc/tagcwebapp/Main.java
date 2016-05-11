package com.pl.tagc.tagcwebapp;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.ext.RuntimeDelegate;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpContainer;
import org.glassfish.jersey.server.ResourceConfig;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.ServerConfiguration;

/*
 * Instructions:
 * 
 * http://localhost:9998/app/index.htm 	
 * 		Can be used to access the static file index.htm.
 * 
 * http://localhost:9998/api/<apicall>
 * 		Can be used to make api calls to the server. For a list of api calls please see RestApi.java.
 *  
 * */


public class Main {

	public static final String WEB_ROOT = "/static/";
	public static final String APP_PATH = "/app/";
	public static final int PORT = 9998;

	/**
	 * Instantiates and configures a HttpServer
	 */
	public static HttpServer startServer() {
		final HttpServer server = new HttpServer();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				server.shutdownNow();
			}
		}));

		final NetworkListener listener = new NetworkListener("grizzly", "localhost", PORT);

		server.addListener(listener);

		final ServerConfiguration config = server.getServerConfiguration();
		// add handler for serving static content
		config.addHttpHandler(new CLStaticHttpHandler(Main.class.getClassLoader(), WEB_ROOT), APP_PATH);

		// add handler for serving JAX-RS resources
		config.addHttpHandler(RuntimeDelegate.getInstance().createEndpoint(createResourceConfig(), GrizzlyHttpContainer.class),
				"");

		try {
			// Start the server.
			server.start();
		} catch (Exception ex) {
			throw new ProcessingException("Exception thrown when trying to start grizzly server", ex);
		}

		return server;
	}

	public static void main(String[] args) {

		try {
			final HttpServer server = startServer();
			System.out.println(String.format(
					"Application started.\n" + "Access it at %s\n" + "Stop the application using CTRL+C", getAppUri()));

			Thread.currentThread().join();
		} catch (InterruptedException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Gets the app uri.
	 *
	 * @return the app uri
	 */
	public static String getAppUri() {
		return String.format("http://localhost:%s%s", PORT, APP_PATH);
	}

	/**
	 * Create Jersey server-side application resource configuration.
	 *
	 * @return Jersey server-side application configuration.
	 */
	public static ResourceConfig createResourceConfig() {
		return new ResourceConfig().registerClasses(RestApi.class);
	}
}
