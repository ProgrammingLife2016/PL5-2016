package com.pl.tagc.tagcwebapp;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import controller.Controller;

import java.util.concurrent.CopyOnWriteArrayList;

//The Java class will be hosted at the URI path "/api"
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

	public NodeListObject requestNodes(@DefaultValue("0") @QueryParam("xleft") int xleft,
			@DefaultValue("100") @QueryParam("xright") int xright,
			@DefaultValue("1") @QueryParam("zoom") int zoom) {
		NodeListObject r = new NodeListObject(new CopyOnWriteArrayList<>(Controller.DC.getRibbonNodes(xleft, xright, zoom)));

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
		return new DimensionsObject(controller.Controller.DC.getDataWidth());

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
		return new NodeListObject(new CopyOnWriteArrayList<>( Controller.DC.getRibbonNodes(0, 5000, 5)));
	}

	/**
	 * Request phylogenetic tree.
	 *
	 * @return the phylogenetic tree object
	 */
	@GET
	@Path("/getphylogenetictree")
	@Produces("application/json")
	public PhylogeneticTreeObject requestPhylogeneticTree() {
		return new PhylogeneticTreeObject(Controller.DC.getPhylogeneticTree().getRoot());
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
