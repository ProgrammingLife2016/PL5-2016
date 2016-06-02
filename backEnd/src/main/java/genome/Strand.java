package genome;
/**
 * Created by Matthijs on 24-4-2016.
 */

import org.neo4j.graphdb.Node;

import mutation.AbstractMutation;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that contains the graph nodes.
 */
public class Strand {

    private int id; //node id
    private int x; //The x coordinate of this Strand.
    private String sequence; //dna in node
    private String[] genomes; //genomes that contain this node
    private String referenceGenome; // the refrence genome of this node
    private int referenceCoordinate; //coordinate of this node in the refr genome
    private int weight; // amount of genomes that contain this node
    private ArrayList<StrandEdge> edges; // The edges going out of this strand.
    private ArrayList<AbstractMutation> mutations; // The mutations on this Strand.

    /**
     * Constructor to create a node.
     *
     * @param id                  The node id.
     * @param sequence            The sequence of the node.
     * @param newGenomes          The genomes id's passing through.
     * @param referenceGenome     The reference genome id.
     * @param referenceCoordinate the reference coordinate.
     */
    public Strand(int id, String sequence, String[] newGenomes, String referenceGenome,
                  int referenceCoordinate) {
        this.id = id;
        this.sequence = sequence;
        genomes = new String[newGenomes.length];
        for (int i = 0; i < newGenomes.length; i++) {
            genomes[i] = newGenomes[i];
        }
        this.referenceGenome = referenceGenome;
        this.referenceCoordinate = referenceCoordinate;
        this.weight = genomes.length;
        this.edges = new ArrayList<>();
        this.x = 0;
        this.mutations = new ArrayList<>();
    }

    /**
     * Constructor to create a new strand.
     *
     * @param o The Object (returned by the Cypherquery) from which a strand should be created.
     */
    public Strand(Object o) {
        Node no = (Node) o;
        this.id = java.lang.Math.toIntExact((long) no.getProperty("id"));
        this.sequence = (String) no.getProperty("sequence");
        //String genomes = (String) no.getProperty("genomes");
        this.referenceGenome = (String) no.getProperty("refGenome");
        this.referenceCoordinate = java.lang.Math.toIntExact((long) no.getProperty("refCoor"));
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
     * Get the sequence from the node.
     *
     * @return The sequence.
     */
    public String getSequence() {
        return sequence;
    }


    /**
     * Get the genomes passing through the node.
     *
     * @return The genomes id's.
     */
    public ArrayList<String> getGenomes() {
        ArrayList<String> res = new ArrayList<>(Arrays.asList(genomes));
        return res;
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
     * Get the reference coordinate.
     *
     * @return The reference coordinate.
     */
    public int getReferenceCoordinate() {
        return referenceCoordinate;
    }

    /**
     * Set the reference coordinate.
     *
     * @param referenceCoordinate The reference coordinate.
     */
    public void setReferenceCoordinate(int referenceCoordinate) {
        this.referenceCoordinate = referenceCoordinate;
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
     * Set the sequence.
     *
     * @param sequence The sequence.
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * Set the genomes id's passing through.
     *
     * @param newGenomes The genomes id's.
     */
    public void setGenomes(String[] newGenomes) {
        genomes = new String[newGenomes.length];
        for (int i = 0; i < newGenomes.length; i++) {
            genomes[i] = newGenomes[i];
        }
        this.weight = genomes.length;

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
     * Add an edge to the edges.
     *
     * @param edge The added edge.
     */
    public void addEdge(StrandEdge edge) {
        edges.add(edge);
    }


    /**
     * Get the edges.
     *
     * @return Edges.
     */
    public ArrayList<StrandEdge> getEdges() {
        return edges;
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
     * @return Mutations.
     */
    public ArrayList<AbstractMutation> getMutations() {
    	return mutations;
    }
    
    /**
     * Add a mutation to the mutations.
     * @param mutation The added mutation.
     */
    public void addMutation(AbstractMutation mutation) {
    	mutations.add(mutation);
    }
}
