package com.pl.tagc.tagcwebapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


// The Java class will be hosted at the URI path "/getnodes"
@Path("/getnodes")
public class NodeService {

	private final List<Node> cList = NodeList.getInstance();

	// The Java method will process HTTP GET requests
	@GET
	// The Java method will produce content identified by the MIME Media
	// type "application/json"
	@Produces("application/json")
	public ResultObject requestNodes(@DefaultValue("0") @QueryParam("xleft") double xleft,
			@DefaultValue("0") @QueryParam("ytop") double ytop, @DefaultValue("100") @QueryParam("xright") double xright,
			@DefaultValue("100") @QueryParam("ybtm") double ybtm) {
		ResultObject r = getNodes(xleft, ytop, xright, ybtm);
		return r;
	}

	private ResultObject getNodes(double xleft, double ytop, double xright, double ybtm) {
		CopyOnWriteArrayList<Node> res= new CopyOnWriteArrayList<Node>();
		ArrayList<Node> correctNodes = new ArrayList<>();
		for(Node n: cList)
		{
			if(n.x < xright && n.x > xleft && n.y > ytop && n.y < ybtm)
			{
				correctNodes.add(n);
			}
		}
		Collections.sort(correctNodes,
				(n1, n2) -> n2.weight - n1.weight);

		int count = 0;
		for (Node n: correctNodes) {
			res.add(n);
			if (count++ > 20) {
				break;
			}
		}
		
		ResultObject reso = new ResultObject(res);
		return reso;

	}
}
