package controller;

import datatree.DataNode;
import datatree.DataTree;
import genome.StrandEdge;
import genome.Genome;
import genome.Strand;
import parser.Parser;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import phylogenetictree.PhylogeneticNode;
import phylogenetictree.PhylogeneticTree;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;

/**
 * Created by Matthijs on 24-4-2016.
 */

/**
 * Datacontainer that stores the edges and strandNodes of a particular genome.
 */
public class Controller {

    //TODO move strand graph to seperate class.
    private HashMap<Integer, Strand> strandNodes;
    private HashMap<String, StrandEdge> strandEdges;
    private HashMap<String, Genome> genomes;
    private double dataWidth;
    private double dataHeight;
    private PhylogeneticTree phylogeneticTree;
    private DataTree dataTree;


    /**
     * Constructer for the datacontainer, starts with empty hashmaps.
     */
    public static final controller.Controller DC = Parser.parse("data/TB10.gfa");

    /**
     * Constructor.
     */
    public Controller() {
        strandNodes = new HashMap<>();
        strandEdges = new HashMap<>();
        genomes = new HashMap<>();
        phylogeneticTree = new PhylogeneticTree();
        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk");
        dataTree = new DataTree(new DataNode((PhylogeneticNode) phylogeneticTree.getRoot(), null, 0));
    }

    public ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, ArrayList<String> genomes, int zoomLevel) {
        ArrayList<RibbonNode> result = new ArrayList<>();
        ArrayList<DataNode> filteredNodes = dataTree.getDataNodes(minX, maxX, genomes, zoomLevel);

        for (DataNode node : filteredNodes) {
            for (Strand strand : node.getStrands()) {
                //Here the nodes are placed in order (notice node.getgenomes and not ribbon.getgenomes).
                RibbonNode ribbonNode = new RibbonNode(strand.getId(), node.getGenomes());
                result.add(ribbonNode);
            }
        }

        addRibbonEdges(result,genomes);

        return result;

    }

    public void addRibbonEdges(ArrayList<RibbonNode> nodes, ArrayList<String> genomes) {
        nodes.sort(new Comparator<RibbonNode>() {
            @Override
            public int compare(RibbonNode o1, RibbonNode o2) {
                if (o1.getId() > o2.getId())
                    return 1;
                else if (o1.getId() < o2.getId())
                    return -1;
                return 0;
            }
        });
//TODO make readable
        for (String genome : genomes) {
            for (int i = 0; i < nodes.size(); i++) {
                RibbonNode startNode = nodes.get(i);

                if (startNode.getGenomes().contains(genome)) {
                    for (int p = i; p < nodes.size(); p++) {
                        RibbonNode endNode = nodes.get(p);
                        if (endNode.getGenomes().contains(genome)) {
                            if (startNode.getEdge(startNode.getId(), endNode.getId()) == null) {
                                RibbonEdge edge = new RibbonEdge(startNode.getId(), endNode.getId());
                                startNode.addEdge(edge);
                                endNode.addEdge(edge);

                            }
                            else {
                                startNode.getEdge(startNode.getId(),endNode.getId()).incrementWeight();
                            }

                        }
                        i=p;
                    }



                }


            }
        }


    }

    /**
     * Adding a strand to the data.
     *
     * @param strand The added strand.
     */
    public void addStrand(Strand strand) {
        strandNodes.put(strand.getId(), strand);

        for (String genomeID : strand.getGenomes()) {
            if (!genomes.containsKey(genomeID)) {
                genomes.put(genomeID, new Genome(genomeID));
            }
            genomes.get(genomeID).addStrand(strand);
        }
    }

    /**
     * Adding an StrandEdge to the data.
     *
     * @param StrandEdge The added StrandEdge.
     */
    public void addEdge(StrandEdge StrandEdge) {
        strandEdges.put(StrandEdge.getStart() + "|" + StrandEdge.getEnd(), StrandEdge);
    }

    /**
     * Get all the node in the data.
     *
     * @return strandNodes.
     */
    public HashMap<Integer, Strand> getstrandNodes() {
        return strandNodes;
    }

    /**
     * Get all the edges in the data.
     *
     * @return Edges.
     */
    public HashMap<String, StrandEdge> getEdges() {
        return strandEdges;
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
     * Get the data height.
     *
     * @return The data height.
     */
    public double getDataHeight() {
        return this.dataHeight;
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
     * Set the data height.
     *
     * @param dataHeight New data height.
     */
    public void setDataHeight(double dataHeight) {
        this.dataHeight = dataHeight;
    }


    /**
     * Getter for the phylogenicTree.
     *
     * @return The tree.
     */
    public PhylogeneticTree getPhylogeneticTree() {
        return phylogeneticTree;
    }

    /**
     * Setter for the phylogenicTree.
     *
     * @param phylogeneticTree The tree.
     */
    public void setPhylogeneticTree(PhylogeneticTree phylogeneticTree) {
        this.phylogeneticTree = phylogeneticTree;
    }

    public DataTree getDataTree() {
        return dataTree;
    }

    public void setDataTree(DataTree dataTree) {
        this.dataTree = dataTree;
    }

    public HashMap<String, Genome> getGenomes() {
        return genomes;
    }

    public void setGenomes(HashMap<String, Genome> genomes) {
        this.genomes = genomes;
    }
}
