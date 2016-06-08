package com.pl.tagc.tagcwebapp;

import ribbonnodes.RibbonNode;

import java.util.ArrayList;
import java.util.List;

import phylogenetictree.PhylogeneticTree;

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
     * @return A list of RibbonNodes to draw.
     */
    ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, int zoomLevel);

    /**
     * Sets the active genomes.
     *
     * @param activeGenomes the active genomes
     * @return the list
     */
    List<String> setActiveGenomes(ArrayList<String> activeGenomes);

    /**
     * Load phylogenetic tree.
     *
     * @param treeId the tree id
     * @return the phylogenetic tree 
     */
    PhylogeneticTree loadPhylogeneticTree(int treeId);

}
