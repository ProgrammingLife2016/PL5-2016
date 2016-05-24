package controller;

import datatree.DataNode;
import datatree.DataTree;
import parser.Parser;
import java.util.ArrayList;
import phylogenetictree.PhylogeneticNode;
import phylogenetictree.PhylogeneticTree;
import ribbonnodes.RibbonNode;

/**
 * Created by Matthijs on 24-4-2016.
 */

/**
 * Controller returns the ribbon nodes based on a UI request.
 */
public class Controller implements FrontEndBackEndInterface {
    
    private GenomeGraph genomeGraph;
    private double dataWidth; // The with of the Data.
    private PhylogeneticTree phylogeneticTree = new PhylogeneticTree();
    private DataTree dataTree; //The dataTree containing the Strands.

    /**
     * Datacontainer Singleton, starts with empty hashmaps.
     */
    public static final controller.Controller DC = new Controller();
    

    /**
     * Constructor.
     */
    public Controller() {
    	genomeGraph = Parser.parse("data/TB10.gfa");
        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk");
        dataTree = new DataTree(new DataNode((PhylogeneticNode) phylogeneticTree.getRoot(), 
        		null, 0));

    }

    
    /**
     * Wrapper method that returns a list of filtered node for the particular query.
     * @param minX The minimal X of the nodes.
     * @param maxX The maximal X of the nodes.
     * @param zoomLevel The zoomlevel to filter to.
     * @return The list of ribbonNodes.
     */
    public ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, int zoomLevel) {
        return RibbonController.getRibbonNodes(minX, maxX, zoomLevel, genomeGraph);
    }    

    /**
     * Get the data width.
     *
     * @return The data width.
     */
    public double getDataWidth() {
        return this.dataWidth;
    }


    /**
     * Set the data width.
     *
     * @param dataWidth New data width.
     */
    public void setDataWidth(double dataWidth) {
        this.dataWidth = dataWidth;
    }


    /**
     * Getter for the phylogenicTree.
     *
     * @param treeId the tree id
     * @return The tree.
     */
    public PhylogeneticTree loadPhylogeneticTree(int treeId) {
		if (treeId == 0) {
			phylogeneticTree = new PhylogeneticTree();
			phylogeneticTree.parseTree("testGenomeNwk");
			return phylogeneticTree;
		} else {
			return phylogeneticTree;
		}
	}

    /**
     * Setter for the phylogenicTree.
     *
     * @param phylogeneticTree The tree.
     */
    public void setPhylogeneticTree(PhylogeneticTree phylogeneticTree) {
        this.phylogeneticTree = phylogeneticTree;
    }


    /**
     * Getter for the dataTree.
     *
     * @return the datatree.
     */
    public DataTree getDataTree() {
        return dataTree;
    }

    /**
     * Setter for the datatree.
     *
     * @param dataTree the datatree.
     */
    public void setDataTree(DataTree dataTree) {
        this.dataTree = dataTree;
    }


    /** 
     * Setter for the activeGenomes.
     * @param activeGenomes The genomeIDS.
     */
    public void setActiveGenomes(ArrayList<String> activeGenomes) {
        genomeGraph.setActiveGenomes(activeGenomes);
    }
    
}
