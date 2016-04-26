package genome;
/**
 * Created by Matthijs on 24-4-2016.
 *
 */

import java.util.ArrayList;

/**
 * Class that contains the graph nodes
 */

//TODO test and commment better


public class Node {

    private int id; //node id
    private String sequence; //dna in node
    private double xCoordinate;
    private double yCoordinate;
    private String[] genomes; //genomes that contain this node
    private String referenceGenome; // the refrence genome of this node
    private int referenceCoordinate; //coordinate of this node in the refr genome
    private int weight; // amount of genomes that contain this node

    public Node(int id, String sequence, String[] genomes, String referenceGenome, int referenceCoordinate) {
        this.id = id;
        this.sequence = sequence;
        this.genomes = genomes;
        this.referenceGenome = referenceGenome;
        this.referenceCoordinate = referenceCoordinate;

        xCoordinate=0.;
        yCoordinate=0.;

        this.weight=genomes.length;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getId() {
        return id;
    }

    public String getSequence() {
        return sequence;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public String[] getGenomes() {
        return genomes;
    }

    public String getRefrenceGenome() {
        return referenceGenome;
    }

    public int getRefrenceCoordinate() {
        return referenceCoordinate;
    }

    public int getWeight() {
        return weight;
    }
}
