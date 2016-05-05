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

public class Main {

	public static final String WEB_ROOT = "/static/";
	public static final String APP_PATH = "/app/";
	public static final int PORT = 9998;

	public static HttpServer startServer(String webRootPath) {
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
			final HttpServer server = startServer(args.length >= 1 ? args[0] : null);
			System.out.println(String.format(
					"Application started.\n" + "Access it at %s\n" + "Stop the application using CTRL+C", getAppUri()));

			Thread.currentThread().join();
		} catch (InterruptedException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static String getAppUri() {
		return String.format("http://localhost:%s%s", PORT, APP_PATH);
	}

	/**
	 * Create Jersey server-side application resource configuration.
	 *
	 * @return Jersey server-side application configuration.
	 */
	public static ResourceConfig createResourceConfig() {
		return new ResourceConfig().registerClasses(NodeService.class);
	}
}

// import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
// import com.sun.jersey.api.core.PackagesResourceConfig;
// import com.sun.jersey.api.core.ResourceConfig;
// import org.glassfish.grizzly.http.server.HttpServer;
// import org.glassfish.grizzly.http.server.StaticHttpHandler;
// import javax.ws.rs.core.UriBuilder;
// import java.io.IOException;
// import java.net.URI;
//
// /*
// * Instructions:
// *
// * To use this application
// http://localhost:9998/api/getnodes?xleft=10&xright=70&ytop=30&ybtm=80
// * can be used to get a json list of nodes that are in the viewport defined by
// the four
// * values: xleft xright ytop and ybtm. http://localhost:9998/app/index.htm can
// be used
// * to access the static file index.htm.
// *
// * */
//
// public class Main {
//
// private static int getPort(int defaultPort) {
// //grab port from environment, otherwise fall back to default port 9998
// String httpPort = System.getProperty("jersey.test.port");
// if (null != httpPort) {
// try {
// return Integer.parseInt(httpPort);
// } catch (NumberFormatException e) {
// System.out.println(e.getMessage());
// }
// }
// return defaultPort;
// }
//
// private static URI getBaseURI() {
// return UriBuilder.fromUri("http://localhost/").port(getPort(9998)).build();
// }
//
// public static final URI BASE_URI = getBaseURI();
//
// protected static HttpServer startServer() throws IOException {
// ResourceConfig resourceConfig = new
// PackagesResourceConfig("com.pl.tagc.tagcwebapp");
//
// System.out.println("Starting grizzly2...");
// return GrizzlyServerFactory.createHttpServer(BASE_URI, resourceConfig);
// }
//
// public static void main(String[] args) throws IOException {
// // Grizzly 2 initialization
// HttpServer httpServer = startServer();
// //StaticHttpHandler staticHttpHandler = new
// StaticHttpHandler("tagcwebapp/static/");
// StaticHttpHandler staticHttpHandler = new StaticHttpHandler("static/");
// staticHttpHandler.setFileCacheEnabled(false);
// httpServer.getServerConfiguration().addHttpHandler(staticHttpHandler,
// "/app");
//
// System.out.println(String.format("Jersey app started with WADL available at "
// + "%sapplication.wadl\nHit enter to stop it...",
// BASE_URI));
// System.in.read();
// httpServer.stop();
// }
// }
