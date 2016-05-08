package com.pl.tagc.tagcwebapp;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import genome.DataContainer;
import genome.Node;

//The Java class will be hosted at the URI path "/api"
@Path("/api")
public class NodeService {
	
	// The Java method will process HTTP GET requests
	@GET
	// The Java method will produce content identified by the MIME Media
	// type "application/json"
	@Path("/getnodes")
	@Produces("application/json")
	public NodeListObject requestNodes(@DefaultValue("0") @QueryParam("xleft") double xleft,
			@DefaultValue("0") @QueryParam("ytop") double ytop, @DefaultValue("100") @QueryParam("xright") double xright,
			@DefaultValue("100") @QueryParam("ybtm") double ybtm) {
		NodeListObject r = new NodeListObject(DataContainer.DC.getNodes(xleft, ytop, xright, ybtm));
		return r;
	}

	// The Java method will process HTTP GET requests
	@GET
	// The Java method will produce content identified by the MIME Media
	// type "application/json"
	@Path("/getdimensions")
	@Produces("application/json")
	public DimensionsObject requestDimensions() {
		return new DimensionsObject(DataContainer.getDc().getDataWidth(),
				DataContainer.getDc().getDataHeight());
	}

}
