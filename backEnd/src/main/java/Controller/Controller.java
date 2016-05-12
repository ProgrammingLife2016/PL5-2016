package controller;

import genome.StrandEdge;
import genome.Genome;
import genome.Strand;
import parser.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

import phylogenetictree.PhylogeneticTree;

/**
 * Created by Matthijs on 24-4-2016.
 */

/**
 * Datacontainer that stores the edges and strandNodes of a particular genome.
 */
public class Controller {

    private HashMap<Integer, Strand> strandNodes;
    private HashMap<String, StrandEdge> strandEdges;
    private HashMap<String, Genome> genomes;
    private double dataWidth;
    private double dataHeight;
    private PhylogeneticTree phylogeneticTree;


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
     * @param phylogeneticTree The tree.
     */
    public void setPhylogeneticTree(PhylogeneticTree phylogeneticTree) {
        this.phylogeneticTree = phylogeneticTree;
    }

}
