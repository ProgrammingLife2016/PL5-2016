
package com.pl.tagc.tagcwebapp;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

// The Java class will be hosted at the URI path "/getnodes"
@Path("/getnodes")
public class NodeService {

    
    // The Java method will process HTTP GET requests
    @GET 
    // The Java method will produce content identified by the MIME Media
    // type "application/json"
    @Produces("application/json")
    public Node  smooth(
    	    @DefaultValue("0") @QueryParam("xleft") int xleft,
    	    @DefaultValue("0") @QueryParam("ytop") int ytop,
    	    @DefaultValue("100") @QueryParam("xright") int xright,
    	    @DefaultValue("10") @QueryParam("ybtm") int ybtm)
    {
		return null;
    	}
}
