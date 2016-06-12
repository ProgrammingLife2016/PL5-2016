package com.pl.tagc.tagcwebapp;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Interface that contracts the interaction between the frontend and the Backend.
 * Created by Matthijs on 23-5-2016.
 */
public interface BackEndInterface {

    /**
     * Request and generate a list of ribbonNodes to draw.
     *
     * @param minX      The minX to get the nodes from.
     * @param maxX      The maxX to get the nodes from.
     * @param zoomLevel The zoomLevel to calculate the Nodes for.
     * @param isMiniMap determine wether this is a minimap call or not.
     * @return A list of RibbonNodes to draw.
     */
    Response getRibbonNodes(int minX, int maxX, int zoomLevel, boolean isMiniMap);

    /**
     * Sets the active genomes.
     *
     * @param ids the ids
     * @return the array list object
     */
    ArrayListObject setActiveGenomes(List<String> ids);

    /**
     * Load phylogenetic tree.
     *
     * @param treeId the tree id
     * @return the phylogenetic tree
     */
    Response loadPhylogeneticTree(int treeId);

    /**
     * Search.
     *
     * @param searchString the search string
     * @param searchType   the search type
     * @return the search result object
     */
    SearchResultObject search(String searchString, String searchType);
}
