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
	
	private final HashMap<Integer, Node> cList = DataContainer.getDc().getNodes();
	
	// The Java method will process HTTP GET requests
	@GET
	// The Java method will produce content identified by the MIME Media
	// type "application/json"
	@Path("/getnodes")
	@Produces("application/json")
	public NodeListObject requestNodes(@DefaultValue("0") @QueryParam("xleft") double xleft,
			@DefaultValue("0") @QueryParam("ytop") double ytop, @DefaultValue("100") @QueryParam("xright") double xright,
			@DefaultValue("100") @QueryParam("ybtm") double ybtm) {
		NodeListObject r = getNodes(xleft, ytop, xright, ybtm);
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

	private NodeListObject getNodes(double xLeft, double yTop, double xRight, double yBottom) {
		CopyOnWriteArrayList<Node> res = new CopyOnWriteArrayList<Node>();
		ArrayList<Node> correctNodes = new ArrayList<>();
		for (Node n : cList.values()) {
			if (n.getxCoordinate() <= xRight && n.getxCoordinate() >= xLeft && n.getyCoordinate() >= yTop
					&& n.getyCoordinate() <= yBottom) {
				correctNodes.add(n);
			}
		}

		Collections.sort(correctNodes, (n1, n2) -> n2.getWeight() - n1.getWeight());

		for (Node n : correctNodes) {
			res.add(n);
		}

		NodeListObject reso = new NodeListObject(res);
		return reso;

	}
}
