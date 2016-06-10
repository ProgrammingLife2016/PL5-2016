package com.pl.tagc.tagcwebapp;

import controller.Controller;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
     * @param xleft  the left bound of the bounding box
     * @param xright the right bound of the bounding box
     * @param zoom   the zoom level that decides which nodes get filtered
     * @return the node list object
     */
    @GET
    @Path("/getnodes")
    @Produces("application/json")
    public Response requestNodes(@DefaultValue("0") @QueryParam("xleft") int xleft,
                                 @DefaultValue("100") @QueryParam("xright") int xright,
                                 @DefaultValue("1") @QueryParam("zoom") int zoom,
                                 @DefaultValue("false") @QueryParam("isMiniMap") boolean isMiniMap) {
        NodeListObject nodeList =
                new NodeListObject(new CopyOnWriteArrayList<>(
                        Controller.getDC().getRibbonNodes(xleft, xright, zoom, isMiniMap)));
        return Response.ok() //200
                .entity(nodeList)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();
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
                new PhylogeneticTreeObject(
                        Controller.getDC().loadPhylogeneticTree(treeId).getRoot());
        return Response.ok() //200
                .entity(result)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();
    }


    /**
     * Uses the genome ids to set the genomes as active in the backend. Which means that
     * they will be used to generate the ribbongraph when getnodes is called.
     *
     * @param ids the genome ids
     * @return the list      List of unrecognized genomes.
     */
    @POST
    @Path("/setactivegenomes")
    public Response setActiveGenomes(@FormParam("names[]") List<String> ids) {
        ArrayListObject result =
                new ArrayListObject(Controller.getDC().setActiveGenomes((ArrayList<String>) ids));
        return Response.ok() //200
                .entity(result)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();
    }

}
