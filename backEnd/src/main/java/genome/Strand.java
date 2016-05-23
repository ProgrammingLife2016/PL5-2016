package genome;
/**
 * Created by Matthijs on 24-4-2016.
 */

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that contains the graph nodes.
 */
public class Strand {

    private int id; //node id
    private String sequence; //dna in node
    private String[] genomes; //genomes that contain this node
    private String referenceGenome; // the refrence genome of this node
    private int referenceCoordinate; //coordinate of this node in the refr genome
    private int weight; // amount of genomes that contain this node
    private ArrayList<StrandEdge> edges; // The edges going out of this strand.

    /**
     * Constructor to create a node.
     * @param id The node id.
     * @param sequence The sequence of the node.
     * @param newGenomes The genomes id's passing through.
     * @param referenceGenome The reference genome id.
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
    }


    /**
     * Get the node id.
     * @return The node id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the sequence from the node.
     * @return The sequence.
     */
    public String getSequence() {
        return sequence;
    }


    /**
     * Get the genomes passing through the node.
     * @return The genomes id's.
     */
    public ArrayList<String> getGenomes() {
	    ArrayList<String> res = new ArrayList<>(Arrays.asList(genomes));   
        return res;
    }

    /**
     * Get the weight of the node.
     * @return The weight.
     */
    public int getWeight() {
        return weight;
    }


    /**
     * Get the reference genome.
     * @return The reference genome id.
     */
    public String getReferenceGenome() {
        return referenceGenome;
    }

    /**
     * Set the reference genome id.
     * @param referenceGenome The refrence genome id.
     */
    public void setReferenceGenome(String referenceGenome) {
        this.referenceGenome = referenceGenome;
    }

    /**
     * Get the reference coordinate.
     * @return The reference coordinate.
     */
    public int getReferenceCoordinate() {
        return referenceCoordinate;
    }

    /**
     * Set the reference coordinate.
     * @param referenceCoordinate The reference coordinate.
     */
    public void setReferenceCoordinate(int referenceCoordinate) {
        this.referenceCoordinate = referenceCoordinate;
    }

    /**
     * Set the node id.
     * @param id The node id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set the sequence.
     * @param sequence The sequence.
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * Set the genomes id's passing through.
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
     * @param weight The weight.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Retains all the genomes contained in retainGenomes.
     * @param retainGenomes the Genomes to retain.
     */
    public void retainGenomes(ArrayList<String> retainGenomes) {
        ArrayList<String> newGenomes = new ArrayList<>(Arrays.asList(genomes));
        newGenomes.retainAll(retainGenomes);
        genomes = new String[newGenomes.size()];
        for (int i = 0; i < genomes.length; i++) {
            genomes[i] = newGenomes.get(i);
        }
        this.weight = genomes.length;
    }
    
    /**
     * Add an edge to the edges.
     * @param edge The added edge.
     */
    public void addEdge(StrandEdge edge) {
    	edges.add(edge);
    }
    

    
    /**
     * Get the edges.
     * @return Edges.
     */
    public ArrayList<StrandEdge> getEdges() {
    	return edges;
    }
}
