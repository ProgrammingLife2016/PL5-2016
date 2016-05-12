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
 * Datacontainer that stores the edges and nodes of a particular genome.
 */
public class Controller {

    private HashMap<Integer, Strand> nodes;
    private HashMap<String, StrandEdge> edges;
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
        nodes = new HashMap<>();
        edges = new HashMap<>();
        genomes = new HashMap<>();
        phylogeneticTree = new PhylogeneticTree();
        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk");
    }

    /**
     * Adding a strand to the data.
     *
     * @param strand The added strand.
     */
    public void addNode(Strand strand) {
        nodes.put(strand.getId(), strand);

        for (String genomeID : strand.getGenomes()) {
            if (!genomes.containsKey(genomeID)) {
                genomes.put(genomeID, new Genome());
            }
            genomes.get(genomeID).addNode(strand);
        }
    }

    /**
     * Adding an StrandEdge to the data.
     *
     * @param StrandEdge The added StrandEdge.
     */
    public void addEdge(StrandEdge StrandEdge) {
        edges.put(StrandEdge.getStart() + "|" + StrandEdge.getEnd(), StrandEdge);
    }

    /**
     * Get all the node in the data.
     *
     * @return Nodes.
     */
    public HashMap<Integer, Strand> getNodes() {
        return nodes;
    }

    /**
     * Get all the edges in the data.
     *
     * @return Edges.
     */
    public HashMap<String, StrandEdge> getEdges() {
        return edges;
    }

    /**
     * Compute and order all the nodes according to their x and y coordinate.
     *
     * @return The ordered set.
     */
    public HashMap<Integer, HashSet<Strand>> calculateCoordinates() {
        double maxWidth = 0;
        double maxHeight = 0;

        // calculate the x-coordinates
        for (HashMap.Entry<String, Genome> entry : genomes.entrySet()) {
            ArrayList<Strand> currentGenomeStrands = entry.getValue().getStrands();

            currentGenomeStrands.get(0).updatexCoordinate(0);
            Strand prevStrand = currentGenomeStrands.get(0);
            for (int i = 1; i < currentGenomeStrands.size(); i++) {
                Strand currentStrand = currentGenomeStrands.get(i);
                currentGenomeStrands.get(i).updatexCoordinate(i); // update the
                // nodes
                // x-coordinate

                StrandEdge currentStrandEdge = edges.get(prevStrand.getId() + "|" + currentStrand.getId());
                currentStrandEdge.setWeight(currentStrandEdge.getWeight() + 1);
                prevStrand = currentStrand;
            }
        }

        HashMap<Integer, HashSet<Strand>> nodesByxCoordinate = new HashMap<>();
        for (HashMap.Entry<Integer, Strand> entry : nodes.entrySet()) {
            if (!nodesByxCoordinate.containsKey((int) entry.getValue().getxCoordinate())) {
                nodesByxCoordinate.put((int) entry.getValue().getxCoordinate(), new HashSet<>());
            }
            nodesByxCoordinate.get((int) entry.getValue().getxCoordinate()).add(entry.getValue());
        }

        for (HashMap.Entry<Integer, HashSet<Strand>> c : nodesByxCoordinate.entrySet()) {
            int y = 0;
            for (Strand strand : c.getValue()) {
                strand.setyCoordinate(y);
                maxHeight = Math.max(maxHeight, y);
                maxWidth = Math.max(maxWidth, strand.getxCoordinate());
                y++;
            }
        }

        dataWidth = maxWidth;
        dataHeight = maxHeight;

        return nodesByxCoordinate;
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
     * Get the nodes in a certain area, on a certain zoomlevel based on that area.
     *
     * @param xleft  The leftmost coordinate.
     * @param ytop   The upper coordinate.
     * @param xright The rightmost coordinate.
     * @param ybtm   The lower coordinate.
     * @return A list of nodes that fall into this zoomlevel and area.
     */
    public CopyOnWriteArrayList<Strand> getNodes(double xleft,
                                                 double ytop,
                                                 double xright,
                                                 double ybtm) {

        CopyOnWriteArrayList<Strand> res = new CopyOnWriteArrayList<Strand>();
        ArrayList<Strand> correctStrands = new ArrayList<>();
        for (Strand n : nodes.values()) {
            if (n.getxCoordinate() < xright
                    && n.getxCoordinate() > xleft
                    && n.getyCoordinate() > ytop
                    && n.getyCoordinate() < ybtm) {
                correctStrands.add(n);
            }
        }

        Collections.sort(correctStrands, (n1, n2) -> n2.getWeight() - n1.getWeight());

        int count = 0;
        for (Strand n : correctStrands) {

            res.add(n);
            count++;

            if (count == 20) {
                break;
            }

        }

        return res;
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
