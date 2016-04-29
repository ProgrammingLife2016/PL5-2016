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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public String getSequence() {
        return sequence;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String[] getGenomes() {
        return genomes;
    }

    public String getReferenceGenome() {
        return referenceGenome;
    }

    public int getReferenceCoordinate() {
        return referenceCoordinate;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
		this.weight = weight;
	}

	public void updateX(int i){
        x = Math.max(i, x);
    }
    
    public void setId(int id) {
		this.id = id;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public void setGenomes(String[] genomes) {
		this.genomes = genomes;
	}

	public void setReferenceGenome(String referenceGenome) {
		this.referenceGenome = referenceGenome;
	}

	public void setReferenceCoordinate(int referenceCoordinate) {
		this.referenceCoordinate = referenceCoordinate;
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
