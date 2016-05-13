package com.pl.tagc.tagcwebapp;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import controller.Controller;

/**
 * The Class RestApi.
 * The Java class will be hosted at the URI path "/api". The annotation @GET in the class means 
 * the Java method will process HTTP GET request. The annotation @Produces("application/json") 
 * means the method will produce content identified by the MIME Media type "application/json".
 * @author Kasper Grabarz
 */
@Path("/api")
public class RestApi {

	/**
	 * Requests nodes that have their coordinates within the bounding box defined by the 
	 * parameters.
	 *
	 * @param xleft
	 *            the left bound of the bounding box
	 * @param ytop
	 *            the top bound of the bounding box
	 * @param xright
	 *            the right bound of the bounding box
	 * @param ybtm
	 *            the bottom bound of the bounding box
	 * @return the node list object
	 */	
	@GET
	@Path("/getnodes")
	@Produces("application/json")
	public NodeListObject requestNodes(@DefaultValue("0") @QueryParam("xleft") double xleft,
			@DefaultValue("0") @QueryParam("ytop") double ytop, 
			@DefaultValue("100") @QueryParam("xright") double xright,
			@DefaultValue("100") @QueryParam("ybtm") double ybtm) {
		NodeListObject r = new NodeListObject(Controller.DC.getStrands(xleft, ytop, xright, ybtm));
		return r;
	}

	/**
	 * Request dimensions.
	 *
	 * @return the dimensions object
	 */
	@GET
	@Path("/getdimensions")
	@Produces("application/json")
	public DimensionsObject requestDimensions() {
		double dataWidth = Controller.DC.getDataWidth();
		double dataHeight = Controller.DC.getDataHeight();
		return new DimensionsObject(dataWidth, dataHeight);
	}

	/**
	 * Request ribbon graph.
	 *
	 * @param names
	 *            the names 
	 * @return the node list object
	 */
	@GET
	@Path("/getribbongraph")
	@Produces("application/json")
	public NodeListObject requestRibbonGraph(@QueryParam("names") List<String> names) {
		System.out.println(names);

		// return new
		// RibbonGraphObject(DataContainer.DC.generateRibbonGraph(names));

		// dummy data for now should return a Ribbon type Object instead of a
		// NodeListObject
		return new NodeListObject(Controller.DC.getStrands(0, 0, 1000, 1000));
	}

	/**
	 * Gets the newick string.
	 *
	 * @return the newick string
	 */
	@GET
	@Path("/getnewickstring")
	@Produces("application/json")
	public NewickStringObject getNewickString() {
		return new NewickStringObject(Controller.DC.getNewickString());
	}

}
