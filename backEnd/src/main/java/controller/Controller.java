package controller;

import datatree.DataNode;
import datatree.DataTree;
import datatree.TempReadWriteTree;
import parser.Parser;
import mutation.Mutations;

import java.util.ArrayList;
import java.util.List;

import phylogenetictree.PhylogeneticTree;
import ribbonnodes.RibbonNode;

/**
 * Created by Matthijs on 24-4-2016.
 */

/**
 * Controller. This class connects the classes together.
 */
public class Controller implements FrontEndBackEndInterface {

    /**
     * The genome graph.
     */
    private GenomeGraph genomeGraph;

    /**
     * The phylogenetic tree.
     */
    private PhylogeneticTree phylogeneticTree = new PhylogeneticTree();

    /**
     * The data tree.
     */
    private DataTree dataTree;

    /**
     * The ribbon controller.
     */
    private RibbonController ribbonController;

    /**
     * Controller Singleton.
     */
    private static controller.Controller dc = null;

    /**
     * Constructor.
     */
    public Controller() {
        String gfaFile = "data/TB328.gfa";
        genomeGraph = Parser.parse(gfaFile);
        genomeGraph.generateGenomes();
        genomeGraph.findStartAndCalculateX();
        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk",
                new ArrayList<>(genomeGraph.getGenomes().keySet()));
        dataTree = new DataTree(new DataNode(phylogeneticTree.getRoot(),
                null, 0));
        dataTree.setMinStrandsToReturn(genomeGraph.getStrandNodes().size() / 8);

        if (gfaFile.equals("data/TB328.gfa")) {
            TempReadWriteTree.readFile(dataTree, genomeGraph.getStrandNodes(), "data/tempTree.txt");
        } else {
            dataTree.addStrands(new ArrayList<>(genomeGraph.getGenomes().values()));

        }
        ribbonController = new RibbonController(genomeGraph, dataTree);
        Mutations mutations = new Mutations(genomeGraph);
        mutations.computeAllMutations();
        dc = this;
        genomeGraph.loadMetaData(Parser.parseGenomeMetadata("data/metadata.csv"));
    }

    /**
     * Wrapper method that returns a list of filtered node for the particular query.
     *
     * @param minX      The minimal X of the nodes.
     * @param maxX      The maximal X of the nodes.
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
            phylogeneticTree.parseTree("testGenomeNwk",
                    new ArrayList<>(genomeGraph.getGenomes().keySet()));
            return phylogeneticTree;
        } else {
            return phylogeneticTree;
        }
    }


    /**
     * Setter for the activeGenomes.
     *
     * @param activeGenomes The genomeIDS.
     * @return the list   	The list of unrecognized genomes.
     */
    public List<String> setActiveGenomes(ArrayList<String> activeGenomes) {
        System.out.println(activeGenomes);
        return genomeGraph.setGenomesAsActive(activeGenomes);
    }

    /**
     * Get the singleton dc.
     * If dc is not instantiated yet, do this first.
     *
     * @return The controller dc.
     */
    public static Controller getDC() {
        if (dc == null) {
            dc = new Controller();
        }
        return dc;
    }


}
