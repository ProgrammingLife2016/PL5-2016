package genome;

import genome.GraphSearcher.SearchType;

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
    private HashMap<Integer, Strand> strands;

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
        strands = new HashMap<>();
        activeGenomes = new ArrayList<>();
        genomes = new HashMap<>();
    }

    /**
     * Get all the Strand in the data.
     *
     * @return strandNodes.
     */
    public HashMap<Integer, Strand> getStrands() {
        return strands;
    }

    /**
     * Sets the strand nodes.
     *
     * @param strands the strands
     */
    public void setStrands(HashMap<Integer, Strand> strands) {
        this.strands = strands;

    }

    /**
     * Adding a strand to the data.
     *
     * @param strand The added strand.
     */
    public void addStrand(Strand strand) {
        strands.put(strand.getId(), strand);
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
        this.activeGenomes = new ArrayList<>();
        for (String genomeId : ids) {
            Genome genome = genomes.get(genomeId);
            if (genome != null) {
                activeGenomes.add(genome);
                genome.resetStrandX();
            } else {
                unrecognizedGenomes.add(genomeId);
            }

        }
        for (Genome genome : activeGenomes) {
            genome.setStrandsX();
        }
        return unrecognizedGenomes;

    }

    /**
     * Load meta data.
     *
     * @param metadata the metadata
     */
    public void loadMetaData(HashMap<String, GenomeMetadata> metadata) {

        for (Genome g : genomes.values()) {
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
        return strands.get(id);
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
     * @param genomeId    the genome id
     * @param annotations the annotations
     */
    public void annotate(String genomeId, List<GenomicFeature> annotations) {
        StrandAnnotator.annotate(genomes.get(genomeId).getStrands(), annotations);
    }

    /**
     * Search.
     *
     * @param searchString the search string
     * @param searchType   the search type
     * @return the g search result
     */
    public GSearchResult search(String searchString, SearchType searchType) {
        return GraphSearcher.search(searchString, searchType, this);
    }


}
