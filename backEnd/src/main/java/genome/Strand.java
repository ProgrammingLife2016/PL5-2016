package genome;
/**
 * Created by Matthijs on 24-4-2016.
 */

import java.util.ArrayList;

/**
 * Class that contains the graph nodes.
 */
public class Strand {

    private int id; //node id
    private String sequence; //dna in node
    private String[] genomes; //genomes that contain this node
    private String referenceGenome; // the refrence genome of this node
    private ArrayList<StrandEdge> edges;
    private int referenceCoordinate; //coordinate of this node in the refr genome
    private int weight; // amount of genomes that contain this node

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
        this.edges = new ArrayList<>();

        this.weight = genomes.length;
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
    public String[] getGenomes() {
        String[] res = new String[genomes.length];
        for (int i = 0; i < genomes.length; i++) {
            res[i] = genomes[i];
        }
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
     * Return the edges of this StrandNode.
     *
     * @return the edges
     */
    public ArrayList<StrandEdge> getEdges() {
        return edges;
    }

    /**
     * Add an edge to this strandNode.
     *
     * @param edge the edge to add
     */

    public void addEdge(StrandEdge edge) {
        this.edges.add(edge);
    }
}
