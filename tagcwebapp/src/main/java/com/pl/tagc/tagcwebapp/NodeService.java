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
@Path("/api")
@Singleton
public class NodeService {
	
	private final HashMap<Integer, Node> cList = DataContainer.DC.getNodes();

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
     return new DimensionsObject(DataContainer.DC.getDataWidth(), DataContainer.DC.getDataHeight());
	}
	
	private NodeListObject getNodes(double xleft, double ytop, double xright, double ybtm) {
		CopyOnWriteArrayList<Node> res= new CopyOnWriteArrayList<Node>();
		ArrayList<Node> correctNodes = new ArrayList<>();
		System.out.println(cList.size());
		double sum = 0;
		int nn = 0;
		for(Node n: cList.values())

		{
			if(n.getX() < xright && n.getX() > xleft && n.getY() > ytop && n.getY() < ybtm)
			{
				correctNodes.add(n);
			}else if(n.getX() != 0.0 || n.getY() != 0.0){
				//System.out.println(n.toString());
				sum = sum + n.getY();
				nn++;
			}
		
		}
		System.out.println(sum/nn);
		System.out.println(correctNodes.size());
		Collections.sort(correctNodes,

				(n1, n2) -> n2.getWeight() - n1.getWeight());


		int count = 0;
		for (Node n: correctNodes) {
			res.add(n);
			//System.out.println(n.toString());
//			if (count++ > 20) {
//				break;
//			}
		}
		
		NodeListObject reso = new NodeListObject(res);
		return reso;

	}
}
