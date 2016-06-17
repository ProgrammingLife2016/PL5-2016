package genome;

import metadata.GenomeMetadata;
import strand.Strand;
import strand.StrandAnnotator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import genomefeature.GenomeSearchResult;
import genomefeature.GenomicFeature;
import genomefeature.GraphSearcher;
import genomefeature.GraphSearcher.SearchType;

/**
 * The Class GenomeGraph.
 * This class holds all the information about the genomes and strands.
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
    private ArrayList<ArrayList<Genome>> activeGenomes;

    /**
     * The ids of the activeGenomes.
     */
    private ArrayList<String> activeGenomeIds;

    /**
     * Instantiates a new genome graph.
     */
    public GenomeGraph() {
        strands = new HashMap<>();
        activeGenomes = new ArrayList<>();
        activeGenomeIds = new ArrayList<>();
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
    public ArrayList<ArrayList<Genome>> getActiveGenomes() {
        return activeGenomes;
    }


    /**
     * Sets the genomes as active.
     * And returns the genomes that are selected but not present in the data.
     *
     * @param ids the new genomes as active
     * @return the list of unrecognized genomes
     */
    @SuppressWarnings("checkstyle:methodlength")
    public List<String> setGenomesAsActive(ArrayList<ArrayList<String>> ids) {
        List<String> unrecognizedGenomes = new ArrayList<>();
        this.activeGenomes = new ArrayList<>();
        this.activeGenomeIds = new ArrayList<>();
        if (ids != null) {
            for (ArrayList<String> genomeIds : ids) {
                ArrayList<Genome> input = new ArrayList<>();
                for (String genomeId : genomeIds) {
                    Genome genome = genomes.get(genomeId);
                    if (genome != null) {
                        input.add(genome);
                        genome.resetStrandX();
                    } else {
                        unrecognizedGenomes.add(genomeId);
                    }
                }
                if (input.size() > 0) {
                    activeGenomes.add(input);
                    activeGenomeIds.add(input.get(0).getId());
                }
            }
            for (ArrayList<Genome> genome : activeGenomes) {
                genome.get(0).setStrandsX();
            }
        }
        activeGenomes.sort((ArrayList<Genome> o1, 
        		ArrayList<Genome> o2) -> o1.get(0).getId().compareTo(o2.get(0).getId()));
        activeGenomeIds.sort((String o1, String o2)->o1.compareTo(o2));
        System.out.println("New genomes to compare: " + activeGenomeIds.toString());
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
     * Annotate the selected genome.
     *
     * @param genomeId    the genome id
     * @param annotations the annotations
     */
    public void annotate(String genomeId, List<GenomicFeature> annotations) {
        StrandAnnotator.annotate(genomes.get(genomeId).getStrands(), annotations);
    }

    /**
     * Search for a specific annotation.
     *
     * @param searchString the search string
     * @param searchType   the search type
     * @return the g search result
     */
    public GenomeSearchResult search(String searchString, SearchType searchType) {
        return GraphSearcher.search(searchString, searchType, this);
    }

    /**
     * Get the ids of the active genomes.
     *
     * @return The arraylist of the active genome ids.
     */
    public ArrayList<String> getActiveGenomeIds() {
        return activeGenomeIds;
    }
}
