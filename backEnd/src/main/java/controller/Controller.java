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
 * Controller. This class connects the classes together.
 */
public class Controller implements FrontEndBackEndInterface {
    
    /** The genome graph. */
    private GenomeGraph genomeGraph;
    
    /** The phylogenetic tree. */
    private PhylogeneticTree phylogeneticTree = new PhylogeneticTree();
    
    /** The data tree. */
    private DataTree dataTree;

    private RibbonController ribbonController;
    
    /**
     * Controller Singleton.
     */
    public static controller.Controller DC;

    /**
     * Constructor.
     */
    public Controller() {
    	genomeGraph = Parser.parse("data/TB10.gfa");
    	genomeGraph.generateGenomes();
        genomeGraph.calculateXStrands();
        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk");
        dataTree = new DataTree(new DataNode(phylogeneticTree.getRoot(),
        		null, 0));
        dataTree.addStrands(new ArrayList<>(genomeGraph.getGenomes().values()));
        ribbonController= new RibbonController(genomeGraph,dataTree);
        DC = this;
    }

    
    /**
     * Wrapper method that returns a list of filtered node for the particular query.
     * @param minX The minimal X of the nodes.
     * @param maxX The maximal X of the nodes.
     * @param zoomLevel The zoomlevel to filter to.
     * @return The list of ribbonNodes.
     */
    public ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, int zoomLevel) {
        return ribbonController.getRibbonNodes(minX, maxX, zoomLevel);
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
     * Setter for the activeGenomes.
     * @param activeGenomes The genomeIDS.
     */
    public void setActiveGenomes(ArrayList<String> activeGenomes) {
        genomeGraph.setActiveGenomes(activeGenomes);
    }
    
}
