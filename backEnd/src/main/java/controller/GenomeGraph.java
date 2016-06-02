package controller;

import genome.Genome;
import genome.GenomeMetadata;
import genome.Strand;
import genome.StrandEdge;

import java.util.ArrayList;
import java.util.HashMap;

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
                for (StrandEdge edge : strand.getEdges()) {
                    Strand nextStrand = strandNodes.get(edge.getEnd());
                    if (nextStrand.getX() < strand.getX() + 1) {
                        nextStrand.setX(strand.getX() + 1);
                        nextStrands.add(nextStrand);
                    }
                }
            }
            currentStrands = nextStrands;
            nextStrands = new ArrayList<>();

        }

    }

    /**
     * Generates the genomes from the the information contained within the strand nodes.
     */
    public void generateGenomes() {

        genomes = new HashMap<String, Genome>();

        for (Strand strand : strandNodes.values()) {
            for (String genomeID : strand.getGenomes()) {

                if (!genomes.containsKey(genomeID)) {
                    Genome genome = new Genome(genomeID);
                    genome.addStrand(strand);
                    genomes.put(genomeID, genome);
                } else {
                    genomes.get(genomeID).addStrand(strand);
                }

            }
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
     */
    public void setGenomesAsActive(ArrayList<String> ids) {
    	
        this.activeGenomes = new ArrayList<Genome>();
        for (String genomeId: ids) {
        	activeGenomes.add(genomes.get(genomeId));
        }
        
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

}
