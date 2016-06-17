package strand;

/**
 * Created by Matthijs on 24-4-2016.
 */

import mutation.AbstractMutation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import genomefeature.GenomicFeature;

/**
 * Class that contains the graph nodes.
 */
public class Strand {

    /**
     * Node id.
     */
    private int id;

    /**
     * The x coordinate of this Strand.
     */
    private int x;

    /**
     * Dna in node.
     */
    private String sequence;

    /**
     * Genomes that contain this node.
     */
    private HashSet<String> genomes;

    /**
     * The refrence genome of this node.
     */
    private String referenceGenome;

    /**
     * The start coordinate.
     */
    private int startCoordinate;

    /**
     * The end coordinate.
     */
    private int endCoordinate;

    /**
     * Amount of genomes that contain this node.
     */
    private int weight;

    /**
     * The edges going out of this strand.
     */
    private ArrayList<StrandEdge> outgoingEdges;

    /**
     * The incoming edges.
     */
    private ArrayList<StrandEdge> incomingEdges;

    /**
     * The mutations on this Strand.
     */
    private ArrayList<AbstractMutation> mutations;

    /**
     * The genomic features.
     */
    private ArrayList<GenomicFeature> genomicFeatures = new ArrayList<GenomicFeature>();

    /**
     * Instantiates an empty new strand.
     */
    public Strand() {
    }

    /**
     * Constructor to create a strand.
     *
     * @param id              The node id.
     * @param sequence        The sequence of the node.
     * @param newGenomes      The genomes id's passing through.
     * @param referenceGenome The reference genome id.
     * @param startCoordinate the reference coordinate.
     */
    public Strand(int id, String sequence, HashSet<String> newGenomes, String referenceGenome,
                  int startCoordinate) {
        this.id = id;
        this.sequence = sequence;
        genomes = newGenomes;
        this.referenceGenome = referenceGenome;
        this.startCoordinate = startCoordinate;
        this.endCoordinate = startCoordinate + sequence.length() - 1;
        this.weight = genomes.size();
        this.outgoingEdges = new ArrayList<>();
        this.incomingEdges = new ArrayList<>();
        this.x = 0;
        this.mutations = new ArrayList<>();
    }

    /**
     * Instantiates a new strand, only with an id.
     * This is used for testing.
     *
     * @param id the id
     */
    public Strand(int id) {
        this.id = id;
    }

    /**
     * Get the node id.
     *
     * @return The node id.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the node id.
     *
     * @param id The node id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the sequence from the node.
     *
     * @return The sequence.
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * Set the dna sequence.
     *
     * @param sequence The sequence.
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * Get the genomes passing through the node.
     *
     * @return The genomes id's.
     */
    public HashSet<String> getGenomes() {
        return genomes;
    }

    /**
     * Set the genomes id's passing through.
     *
     * @param newGenomes The genomes id's.
     */
    public void setGenomes(HashSet<String> newGenomes) {
        genomes = new HashSet<String>(newGenomes);
        this.weight = genomes.size();
    }

    /**
     * Get the weight of the node.
     *
     * @return The weight.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Set the weight.
     *
     * @param weight The weight.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Get the reference genome.
     *
     * @return The reference genome id.
     */
    public String getReferenceGenome() {
        return referenceGenome;
    }

    /**
     * Set the reference genome id.
     *
     * @param referenceGenome The refrence genome id.
     */
    public void setReferenceGenome(String referenceGenome) {
        this.referenceGenome = referenceGenome;
    }

    /**
     * Add an edge to the edges.
     *
     * @param edge The added edge.
     */
    public void addEdge(StrandEdge edge) {
        assert edge.contains(this);
        if (edge.getStart().equals(this)) {
            outgoingEdges.add(edge);
        } else {
            incomingEdges.add(edge);
        }
    }

    /**
     * Getter for x.
     *
     * @return the x coordinate of this strand.
     */
    public int getX() {
        return x;
    }

    /**
     * The setter for x.
     *
     * @param x The x to set this nodes x coordinate to.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get all the mutations started from this Strand.
     *
     * @return Mutations.
     */
    public ArrayList<AbstractMutation> getMutations() {
        return mutations;
    }

    /**
     * Add a mutation to the mutations.
     *
     * @param mutation The added mutation.
     */
    public void addMutation(AbstractMutation mutation) {
        mutations.add(mutation);
    }

    /**
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "" + id;
    }

    /**
     * Gets the genomic features.
     *
     * @return the genomic features
     */
    public ArrayList<GenomicFeature> getGenomicFeatures() {
        return genomicFeatures;
    }

    /**
     * Adds the genomic feature.
     *
     * @param genomicFeature the genomic feature
     */
    public void addGenomicFeature(GenomicFeature genomicFeature) {
        genomicFeatures.add(genomicFeature);
    }


    /**
     * Contains.
     *
     * @param id the genomeId to check for.
     * @return true, if successful
     */
    private boolean contains(String id) {
        return genomes.contains(id);
    }

    /**
     * Gets the outgoing edges.
     *
     * @return the outgoing edges
     */
    public ArrayList<StrandEdge> getOutgoingEdges() {
        return outgoingEdges;
    }


    /**
     * Gets the next strands with.
     *
     * @param genomeId the genome
     * @return the next strands with
     */
    public List<Strand> getNextStrandsWith(String genomeId) {
        ArrayList<Strand> strands = new ArrayList<Strand>();
        for (StrandEdge edge : outgoingEdges) {
            Strand neighbor = edge.getEnd();
            if (neighbor.contains(genomeId)) {
                strands.add(neighbor);
            }
        }
        return strands;

    }

    /**
     * Gets the previous strands with.
     *
     * @param genome the genome
     * @return the previous strands with
     */
    public List<Strand> getPreviousStrandsWith(String genome) {
        ArrayList<Strand> strands = new ArrayList<Strand>();
        for (StrandEdge edge : incomingEdges) {
            Strand neighbor = edge.getStart();
            if (neighbor.contains(genome)) {
                strands.add(neighbor);
            }
        }
        return strands;

    }

    /**
     * Gets the start coordinate.
     *
     * @return the start coordinate
     */
    public int getStartCoordinate() {
        return startCoordinate;
    }

    /**
     * Sets the start coordinate.
     *
     * @param startCoordinate the new start coordinate
     */
    public void setStartCoordinate(int startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    /**
     * Gets the end coordinate.
     *
     * @return the end coordinate
     */
    public int getEndCoordinate() {
        return endCoordinate;
    }

    /**
     * Sets the end coordinate.
     *
     * @param endCoordinate the new end coordinate
     */
    public void setEndCoordinate(int endCoordinate) {
        this.endCoordinate = endCoordinate;
    }
}
