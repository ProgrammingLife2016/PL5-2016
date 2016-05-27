package com.pl.tagc.tagcwebapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import controller.Controller;

//The Java class will be hosted at the URI path "/api"
/**
 * The Class RestApi. The Java class will be hosted at the URI path "/api". The
 * annotation @GET in the class means the Java method will process HTTP GET
 * request. The annotation @Produces("application/json") means the method will
 * produce content identified by the MIME Media type "application/json".
 * 
 * @author Kasper Grabarz
 */

@Path("/api")
public class RestApi {

	/**
	 * Requests nodes that have their coordinates within the bounding box
	 * defined by the parameters.
	 *
	 * @param xleft
	 *            the left bound of the bounding box
	 * @param xright
	 *            the right bound of the bounding box
	 * @param zoom
	 *            the zoom level that decides which nodes get filtered
	 * @return the node list object
	 */
	@GET
	@Path("/getnodes")
	@Produces("application/json")
	public Response requestNodes(@DefaultValue("0") @QueryParam("xleft") int xleft,
			@DefaultValue("100") @QueryParam("xright") int xright,
			@DefaultValue("1") @QueryParam("zoom") int zoom) {
		NodeListObject nodeList =
				new NodeListObject(new CopyOnWriteArrayList<>(
						Controller.DC.getRibbonNodes(xleft, xright, zoom)));
		return Response.ok() //200
				.entity(nodeList)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.allow("OPTIONS").build();
	}
	

	/**
	 * Request ribbon graph.
	 *
	 * @param names
	 *            the names
	 */
	@POST
	@Path("/getribbongraph")
	public void requestRibbonGraph(@FormParam("names[]") List<String> names) {
		Controller.DC.setActiveGenomes((ArrayList<String>) names);
	}

	/**
	 * Request phylogenetic tree.
	 *
	 * @param treeId The tree id that will be used to load the right tree from the backend.
	 * @return the phylogenetic tree object
	 */
	@GET
	@Path("/getphylogenetictree")
	@Produces("application/json")
	public Response requestPhylogeneticTree(
			@DefaultValue("1") @QueryParam("treeId") int treeId) {
		PhylogeneticTreeObject result =
				new PhylogeneticTreeObject(Controller.DC.loadPhylogeneticTree(treeId).getRoot());
		return Response.ok() //200
				.entity(result)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.allow("OPTIONS").build();
	}

}
