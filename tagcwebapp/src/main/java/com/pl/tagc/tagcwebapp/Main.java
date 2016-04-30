package com.pl.tagc.tagcwebapp;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

/*
 * Instructions:
 * 
 * To use this application http://localhost:9998/getnodes?xleft=10&xright=70&ytop=30&ybtm=80 
 * can be used to get a json list of nodes that are in the viewport defined by the four 
 * values: xleft xright ytop and ybtm. http://localhost:9998/app/index.html can be used 
 * to access the static file index.html.
 * 
 * */

public class Main {

    private static int getPort(int defaultPort) {
        //grab port from environment, otherwise fall back to default port 9998
        String httpPort = System.getProperty("jersey.test.port");
        if (null != httpPort) {
            try {
                return Integer.parseInt(httpPort);
            } catch (NumberFormatException e) {
            	System.out.println(e.getMessage());
            }
        }
        return defaultPort;
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(getPort(9998)).build();
    }

    public static final URI BASE_URI = getBaseURI();
    
    protected static HttpServer startServer() throws IOException {
        ResourceConfig resourceConfig = new PackagesResourceConfig("com.pl.tagc.tagcwebapp");

        System.out.println("Starting grizzly2...");
        return GrizzlyServerFactory.createHttpServer(BASE_URI, resourceConfig);
    }
    
    public static void main(String[] args) throws IOException {
        // Grizzly 2 initialization
        HttpServer httpServer = startServer();
        httpServer.getServerConfiguration().addHttpHandler(
                new StaticHttpHandler("static/"), "/app");
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...",
                BASE_URI));
        System.in.read();
        httpServer.stop();
    }    
}
