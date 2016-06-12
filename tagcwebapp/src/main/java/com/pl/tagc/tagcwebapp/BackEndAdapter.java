package com.pl.tagc.tagcwebapp;

import controller.Controller;
import genome.GSearchResult;
import genome.GraphSearcher.SearchType;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The Class BackEndAdapter.
 */
public final class BackEndAdapter implements BackEndInterface {

	private static BackEndAdapter ba = null;
	private static Controller controller = null;
	private static HashMap<String, SearchType> searchTypeMap;
	private BackEndAdapter() { 
		searchTypeMap = new HashMap<String, SearchType>();
		searchTypeMap.put("GenomicFeatureSearch", SearchType.GenomicFeatureSearch);
		searchTypeMap.put("MetaDataSearch", SearchType.MetaDataSearch);
		searchTypeMap.put("MutationSearch", SearchType.MutationSearch);
		searchTypeMap.put("FullSearch", SearchType.FullSearch);
	}
	
	@Override
	public Response getRibbonNodes(int minX, int maxX, int zoomLevel, boolean isMiniMap) {
		NodeListObject nodeList =
				new NodeListObject(new CopyOnWriteArrayList<>(
						controller.getRibbonNodes(minX, maxX, zoomLevel, isMiniMap)));
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
		SearchType searchType = searchTypeMap.get(searchTypeStr);
		if (searchType == null) {
			throw new IllegalArgumentException("Unknown search type");
		}
		GSearchResult gSearchRresult = controller.search(searchString, searchType);
		SearchResultObject resultObject = new SearchResultObject();
		resultObject.setgFeatureSearchMatches(gSearchRresult.getgFeatureSearchMatches());
		return resultObject;
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
			BackEndAdapter.controller = controller;
		}
	}
	
}
