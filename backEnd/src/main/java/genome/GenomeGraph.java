package genome;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Class GenomeGraph.
 */
public class GenomeGraph {

    /**
     * The strand nodes.
     */
    private HashMap<Integer, Strand> strandNodes;

    /**
     * The genomes.
     */
    private HashMap<String, Genome> genomes;

    /**
     * The active genomes.
     */
    private ArrayList<Genome> activeGenomes; //The current genomes selected in the GUI.


    /**
     * Instantiates a new genome graph.
     */
    public GenomeGraph() {
        strandNodes = new HashMap<>();
        activeGenomes = new ArrayList<>();
        genomes = new HashMap<>();
    }

    /**
     * Get all the Strand in the data.
     *
     * @return strandNodes.
     */
    public HashMap<Integer, Strand> getStrandNodes() {
        return strandNodes;
    }
    
    /**
     * Sets the strand nodes.
     *
     * @param strands the strands
     */
    public void setStrandNodes(HashMap<Integer, Strand> strands) {
        this.strandNodes = strands;

    }

    /**
     * Adding a strand to the data.
     *
     * @param strand The added strand.
     */
    public void addStrand(Strand strand) {
        strandNodes.put(strand.getId(), strand);
    }


    /**
     * Method that finds the starting nodes and calculates the x coordinates for the graphnodes.
     */
    public void findStartAndCalculateX() {
        for (Strand start : strandNodes.values()) {
            if (start.getX() == 0) {
                start.setX(1);
                calculateXfromStart(start);
            }

        }


    }

    /**
     * Calculate the x coordinates for the graph nodes starting at start.
     *
     * @param start the graph node to start at.
     */
    public void calculateXfromStart(Strand start) {
        ArrayList<Strand> currentStrands = new ArrayList<>();
        ArrayList<Strand> nextStrands = new ArrayList<>();
        currentStrands.add(start);

        while (!currentStrands.isEmpty()) {
            for (Strand strand : currentStrands) {
                for (StrandEdge edge : strand.getOutgoingEdges()) {
                    Strand nextStrand = strandNodes.get(edge.getEnd().getId());
                    if (nextStrand.getX() < strand.getX() + 1) {
                        nextStrand.setX(strand.getX() + strand.getSequence().length() + 1);
                        nextStrands.add(nextStrand);
                    }
                }
            }
            currentStrands = nextStrands;
            nextStrands = new ArrayList<>();

        }

    }

    /**
     * Getter for the genomes.
     *
     * @return the genomes.
     */
    public HashMap<String, Genome> getGenomes() {
        return genomes;
    }

    /**
     * Setter for the genomes.
     *
     * @param genomes The genomes.
     */
    public void setGenomes(HashMap<String, Genome> genomes) {
        this.genomes = genomes;
    }

    /**
     * The active genomes in the Gui.
     *
     * @return the active genomeIDS.
     */
    public ArrayList<Genome> getActiveGenomes() {
        return activeGenomes;
    }


    /**
     * Sets the genomes as active.
     *
     * @param ids the new genomes as active
     * @return the list of unrecognized genomes
     */
    public List<String> setGenomesAsActive(ArrayList<String> ids) {
    	List<String> unrecognizedGenomes = new ArrayList<String>();
        this.activeGenomes = new ArrayList<Genome>();
        for (String genomeId: ids) {
        	Genome genome = genomes.get(genomeId);
        	if (genome != null) {
        		activeGenomes.add(genome);
        	}
        	else {
        		unrecognizedGenomes.add(genomeId);
        	}
        	
        }
        return unrecognizedGenomes;
        
    }

	/**
	 * Load meta data.
	 *
	 * @param metadata the metadata
	 */
	public void loadMetaData(HashMap<String, GenomeMetadata> metadata) {
		
		for (Genome g: genomes.values()) {
			g.setMetadata(metadata.get(g.getId()));
		}
		
	}

	/**
	 * Gets the strand.
	 *
	 * @param id the id
	 * @return the strand
	 */
	public Strand getStrand(int id) {
		return strandNodes.get(id);
	}

	/**
	 * Gets the genome.
	 *
	 * @param genomeId the genome id
	 * @return the genome
	 */
	public Genome getGenome(String genomeId) {
		return genomes.get(genomeId);
	}

	/**
	 * Annotate.
	 *
	 * @param genomeId the genome id
	 * @param annotations the annotations
	 */
	public void annotate(String genomeId, List<GenomicFeature> annotations) {
		StrandAnnotator.annotate(genomes.get(genomeId).getStrands(), annotations);
	}


	
}
