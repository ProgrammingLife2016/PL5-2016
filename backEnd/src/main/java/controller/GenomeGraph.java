package controller;

import java.util.ArrayList;
import java.util.HashMap;

import genome.Genome;
import genome.Strand;

/**
 * The Class GenomeGraph.
 */
public class GenomeGraph {

	/** The strand nodes. */
	private HashMap<Integer, Strand> strandNodes;
    
    /** The genomes. */
    private HashMap<String, Genome> genomes;
    
    /** The active genomes. */
    private ArrayList<String> activeGenomes; //The current genomes selected in the GUI.
	

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

        for (String genomeID : strand.getGenomes()) {
            if (!genomes.containsKey(genomeID)) {
                genomes.put(genomeID, new Genome(genomeID));
                //HARDCODED ACTIVE GENOMES
                if (!genomeID.equals("MT_H37RV_BRD_V5.ref.fasta")) {
                    activeGenomes.add(genomeID);
                }
            } else {
                genomes.get(genomeID).addStrand(strand);
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
    public ArrayList<String> getActiveGenomes() {
        return activeGenomes;
    }


    /** 
     * Setter for the activeGenomes.
     * @param activeGenomes The genomeIDS.
     */
    public void setActiveGenomes(ArrayList<String> activeGenomes) {
        this.activeGenomes = activeGenomes;
    }
    
    
}
