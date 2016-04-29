package genome;
/**
 * Created by Matthijs on 24-4-2016.
 *
 */

/**
 * Class that contains the graph nodes
 */

//TODO test and commment better


public class Node {

    private int id; //node id
    private String sequence; //dna in node
    private double x;
    private double y;
    private String[] genomes; //genomes that contain this node
    private String referenceGenome; // the refrence genome of this node
    private int referenceCoordinate; //coordinate of this node in the refr genome
    private int weight; // amount of genomes that contain this node

    public Node(){
    }

    public Node(int id, String sequence, String[] genomes, String referenceGenome, int referenceCoordinate) {
        this.id = id;
        this.sequence = sequence;
        this.genomes = genomes;
        this.referenceGenome = referenceGenome;
        this.referenceCoordinate = referenceCoordinate;

        x=0.;
        y=0.;

        this.weight=genomes.length;
    }

    public void setx(double x) {
        this.x = x;
    }

    public void sety(double y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public String getSequence() {
        return sequence;
    }

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
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

    public void updatex(int i){
        x = Math.max(i, x);
    }
    
    public String toString(){                                          
    	return  "id                  " + id                  + "\n" +
		    	"sequence            " + sequence            + "\n" +
		    	"x                   " + x                   + "\n" +
		    	"y                   " + y                   + "\n" +
		    	"genomes             " + genomes             + "\n" +
		    	"referenceGenome     " + referenceGenome     + "\n" +
		    	"referenceCoordinate " + referenceCoordinate + "\n" +
		    	"weight              " + weight              + "\n";
    }
}
