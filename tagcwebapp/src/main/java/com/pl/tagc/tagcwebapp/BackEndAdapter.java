package com.pl.tagc.tagcwebapp;

import genome.GraphSearcher.SearchType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.ws.rs.core.Response;
import controller.Controller;

/**
 * The Class BackEndAdapter.
 */
public final class BackEndAdapter implements BackEndInterface {

	private static BackEndAdapter ba = null;
	private static Controller controller = null;
	
	@Override
	public Response getRibbonNodes(int minX, int maxX, int zoomLevel) {
		NodeListObject nodeList =
				new NodeListObject(new CopyOnWriteArrayList<>(
						controller.getRibbonNodes(minX, maxX, zoomLevel)));
		return Response.ok() //200
				.entity(nodeList)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.allow("OPTIONS").build();
	}

	@Override
	public ArrayListObject setActiveGenomes(List<String> ids) {
		return new ArrayListObject(controller.setActiveGenomes((ArrayList<String>) ids));
	}

	@Override
	public Response loadPhylogeneticTree(int treeId) {
		PhylogeneticTreeObject result =
				new PhylogeneticTreeObject(controller.
						loadPhylogeneticTree(treeId).getRoot());
		return Response.ok() //200
				.entity(result)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.allow("OPTIONS").build();
	}

	@Override
	public SearchResultObject search(String searchString, String searchTypeStr) {
		SearchType searchType;
		switch (searchTypeStr) {
		case "GenomicFeatureSearch":
			searchType = SearchType.GenomicFeatureSearch;
			break;
		case "MetaDataSearch":
			searchType = SearchType.MetaDataSearch;
			break;
		case "MutationSearch":
			searchType = SearchType.MutationSearch;
			break;
		case "FullSearch":
			searchType = SearchType.FullSearch;
			break;
		default:
			searchType = SearchType.FullSearch;
		}
		controller.search(searchString, searchType);
		return null;
	}
	
	/**
	 * Get the singleton adapter instance.
	 *
	 * @return The adapter instance.
	 */
	public static BackEndAdapter getInstance() {
		return ba;
	}

	/**
	 * Creates the adapter instance.
	 *
	 * @param controller the controller
	 */
	public static void createInstance(Controller controller) {
		if (ba == null) {
			ba = new BackEndAdapter();
		}
	}
	
}
