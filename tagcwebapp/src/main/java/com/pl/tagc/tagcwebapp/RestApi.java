package com.pl.tagc.tagcwebapp;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import controller.Controller;

import java.util.concurrent.CopyOnWriteArrayList;

//The Java class will be hosted at the URI path "/api"
@Path("/api")
public class RestApi {

	// The Java method will process HTTP GET requests
	@GET
	// The Java method will produce content identified by the MIME Media
	// type "application/json"
	@Path("/getnodes")
	@Produces("application/json")

	public NodeListObject requestNodes(@DefaultValue("0") @QueryParam("xleft") int xleft,
			@DefaultValue("100") @QueryParam("xright") int xright,
			@DefaultValue("1") @QueryParam("zoom") int zoom) {
		NodeListObject r = new NodeListObject(new CopyOnWriteArrayList<>(Controller.DC.getRibbonNodes(xleft, xright, zoom)));

		return r;
	}

	// The Java method will process HTTP GET requests
	@GET
	// The Java method will produce content identified by the MIME Media
	// type "application/json"
	@Path("/getdimensions")
	@Produces("application/json")
	public DimensionsObject requestDimensions() {
		return new DimensionsObject(controller.Controller.DC.getDataWidth());
	}
	
	// The Java method will process HTTP GET requests
	@GET
	// The Java method will produce content identified by the MIME Media
	// type "application/json"
	@Path("/getphylogenetictree")
	@Produces("application/json")
	public PhylogeneticTreeObject requestPhylogeneticTree() {
		return new PhylogeneticTreeObject(controller.Controller.DC.getPhylogeneticTree());
	}

}
