package com.pl.tagc.tagcwebapp;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.ext.RuntimeDelegate;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpContainer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.ServerConfiguration;

/**
 * Instructions:
 * 
 * http://localhost:9998/app/index.htm Can be used to access the static file
 * index.htm.
 * 
 * http://localhost:9998/api/<apicall> Can be used to make api calls to the
 * server. For a list of api calls please see RestApi.java.
 * 
 */

final class RestServer {

	private RestServer() {
	}

	/** The Constant WEB_ROOT. */
	public static final String WEB_ROOT = "/static/";

	/** The Constant APP_PATH. */
	public static final String APP_PATH = "/app/";

	/** The Constant PORT. */
	public static final int PORT = 9998;

	/**
	 * Instantiates and configures a HttpServer.
	 *
	 * @return the http server
	 */
	public HttpServer startServer() {
		final HttpServer server = new HttpServer();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				server.shutdownNow();
			}
		}));

		server.addListener(new NetworkListener("grizzly", "localhost", PORT));
		
		for (NetworkListener l : server.getListeners()) {
		    l.getFileCache().setEnabled(false);
		}
		
		final ServerConfiguration config = server.getServerConfiguration();
		// add handler for serving static content
		config.addHttpHandler(new CLStaticHttpHandler(RestServer.class.getClassLoader(), WEB_ROOT),
				APP_PATH);

		// add handler for serving JAX-RS resources
		config.addHttpHandler(
				RuntimeDelegate.getInstance().createEndpoint(createResourceConfig(),
						GrizzlyHttpContainer.class), "");

		try {
			server.start();
		} catch (Exception ex) {
			throw new ProcessingException("Exception thrown when trying to start grizzly server",
					ex);
		}
		
		System.out.println(String.format("Application started.%n" + "Access it at %s%n"
				+ "Stop the application using CTRL+C", getAppUri()));
		
		return server;
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
		return new ResourceConfig().registerClasses(RestApi.class).
				property(MarshallerProperties.JSON_MARSHAL_EMPTY_COLLECTIONS, false);
	}
}