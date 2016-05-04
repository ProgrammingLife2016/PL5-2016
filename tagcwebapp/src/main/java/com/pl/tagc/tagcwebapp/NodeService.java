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

// The Java class will be hosted at the URI path "/getnodes"
@Path("/getnodes")
public class NodeService {

	private final HashMap<Integer, Node> cList = DataContainer.DC.getNodes();

	// The Java method will process HTTP GET requests
	@GET
	// The Java method will produce content identified by the MIME Media
	// type "application/json"
	@Produces("application/json")
	public ResultObject requestNodes(@DefaultValue("0") @QueryParam("xleft") double xLeft,
			@DefaultValue("0") @QueryParam("ytop") double yTop, @DefaultValue("100") @QueryParam("xright") double xRight,
			@DefaultValue("100") @QueryParam("ybtm") double yBottom) {
		return getNodes(xLeft, yTop, xRight, yBottom);
	}

	private ResultObject getNodes(double xLeft, double yTop, double xRight, double yBottom) {
		CopyOnWriteArrayList<Node> res = new CopyOnWriteArrayList<Node>();
		ArrayList<Node> correctNodes = new ArrayList<>();
		for (Node n : cList.values()) {
			if (n.getxCoordinate() < xRight && n.getxCoordinate() > xLeft && n.getyCoordinate() > yTop
					&& n.getyCoordinate() < yBottom) {
				correctNodes.add(n);
			}
		}

		Collections.sort(correctNodes, (n1, n2) -> n2.getWeight() - n1.getWeight());

		for (Node n : correctNodes) {
			res.add(n);
		}

		ResultObject reso = new ResultObject(res);
		return reso;

	}
}
