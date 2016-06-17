package controller;

import com.google.inject.internal.util.Lists;
import datatree.DataNode;
import datatree.DataTree;
import datatree.TempReadDataTree;
import genome.GSearchResult;
import genome.Genome;
import genome.GenomeGraph;
import genome.GraphSearcher.SearchType;
import metadata.MetaDataController;
import parser.Parser;
import phylogenetictree.PhylogeneticNode;
import phylogenetictree.PhylogeneticTree;
import ribbonnodes.RibbonController;
import ribbonnodes.RibbonNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by Matthijs on 24-4-2016.
 */

/**
 * Controller. This class connects the classes together.
 */
public class Controller {

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
     * The metaData controller.
     */
    private MetaDataController metaDataController;

    /**
     * Constructor.
     */
    public Controller() {
        String gfaFile = "data/TB10.gfa";
        genomeGraph = Parser.parse(gfaFile);
        genomeGraph.annotate("MT_H37RV_BRD_V5.ref",
                Parser.parseAnnotations("data/decorationV5_20130412(1).gff"));
        genomeGraph.loadMetaData(Parser.parseGenomeMetadata("data/metadata.csv"));
        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk",
                new ArrayList<>(genomeGraph.getGenomes().keySet()));
        dataTree = new DataTree(new DataNode(phylogeneticTree.getRoot(),
                null, 0));

        if (gfaFile.equals("data/TB328.gfa")) {
            TempReadDataTree.readFile(dataTree, genomeGraph.getStrands(), "data/tempTree.txt");
        } else {
            dataTree.addStrandsFromGenomes(new ArrayList<>(genomeGraph.getGenomes().values()));

        }
        ribbonController = new RibbonController(genomeGraph, dataTree);


        List<Genome> genomes = Lists.newArrayList(genomeGraph.getGenomes().values());
        metaDataController = new MetaDataController(genomes);
    }

    /**
     * Wrapper method that returns a list of filtered node for the particular query.
     *
     * @param minX      The minimal X of the nodes.
     * @param maxX      The maximal X of the nodes.
     * @param zoomLevel The zoomlevel to filter to.
     * @param isMiniMap Boolean if this is the minimap.
     * @return The list of ribbonNodes.
     */
    public ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX,
                                                int zoomLevel, boolean isMiniMap) {
        return ribbonController.getRibbonNodes(minX, maxX, zoomLevel, isMiniMap);
    }

    /**
     * Search.
     *
     * @param searchString the search string
     * @param searchType   the search type
     * @return the g search result
     */
    public GSearchResult search(String searchString, SearchType searchType) {
        return genomeGraph.search(searchString, searchType);
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
    public List<String> setActiveGenomes(List<String> activeGenomes) {
        System.out.println("Set active genomes");
        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        for (String s : activeGenomes) {
            if (s.startsWith("T")) {
                temp.add(new ArrayList<>(Arrays.asList(s)));
            } else {
                System.out.println("middle node selected");
                Integer x = Integer.parseInt(s);
                System.out.println("With id: " + x);
                PhylogeneticNode node = phylogeneticTree.getNodeWithId(x);
                System.out.println("Leaves under this node:");
                for (String a : node.getGenomes()) {
                    System.out.println(a);
                }
                temp.add(phylogeneticTree.getNodeWithId(x).getGenomes());
            }

        }
        return genomeGraph.setGenomesAsActive(temp);
    }

    /**
     * Getter for the MetaDataController.
     * @return the metaDataController
     */
    public MetaDataController getMetaDataController() {
        return metaDataController;
    }
}
