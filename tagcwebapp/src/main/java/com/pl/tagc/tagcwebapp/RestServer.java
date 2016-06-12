package com.pl.tagc.tagcwebapp;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpContainer;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.ext.RuntimeDelegate;

/**
 * The Class RestServer.
 */
public class RestServer {

	/** The Constant WEB_ROOT. */
	private String webRoot = "/static/";

	/** The Constant APP_PATH. */
	private String appPath = "/app/";

	/** The Constant PORT. */
	private int port = 9998;

	/**
	 * Instantiates and configures a HttpServer.
	 *
	 * @return the http server
	 */
	@SuppressWarnings("checkstyle:methodlength")
	public HttpServer startServer() {
		final HttpServer server = new HttpServer();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				server.shutdownNow();
			}
		}));

		server.addListener(new NetworkListener("grizzly", "localhost", port));
		
		for (NetworkListener l : server.getListeners()) {
		    l.getFileCache().setEnabled(false);
		}
		
		final ServerConfiguration config = server.getServerConfiguration();
		// add handler for serving static content
		config.addHttpHandler(new CLStaticHttpHandler(RestServer.class.getClassLoader(), webRoot),
				appPath);

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
	public String getAppUri() {
		return String.format("http://localhost:%s%s", port, appPath);
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