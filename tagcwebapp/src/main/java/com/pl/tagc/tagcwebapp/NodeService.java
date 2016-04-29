package com.pl.tagc.tagcwebapp;

import com.sun.jersey.spi.resource.Singleton;
import genome.DataContainer;
import genome.Node;
import parser.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.concurrent.CopyOnWriteArrayList;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


// The Java class will be hosted at the URI path "/getnodes"
@Path("/getnodes")
@Singleton
public class NodeService {
	
	private final HashMap<Integer, Node> cList = DataContainer.DC.getNodes();

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
		System.out.println(cList.size());
		for(Node n: cList.values())

		{
			if(n.getxCoordinate() < xright && n.getxCoordinate() > xleft && n.getyCoordinate() > ytop && n.getyCoordinate() < ybtm)
			{
				correctNodes.add(n);
			}
			//System.out.println(n.getxCoordinate() + " " + );
		}
		System.out.println(correctNodes.size());
		Collections.sort(correctNodes,

				(n1, n2) -> n2.getWeight() - n1.getWeight());


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
