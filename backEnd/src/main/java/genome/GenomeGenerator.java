package genome;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * The Class GenomeGenerator. A utility class that uses the topological ordering of the graph
 * parsed in from the .gfa file to generate the genomes.
 */
public final class GenomeGenerator {
	
	/**
	 * Constructor to create an empty GenomeGenerator.
	 * This should not be possible thats why this constructor is private.
	 */
    private GenomeGenerator() {
    }

    /**
     * Generate genomes, using all the strands that are read from the gfa file.
     *
     * @param genomeIds   the genome ids for which to generate genomes
     * @param genomeGraph the genome graph containing the data from which to generate the genomes
     * @return the hash map of genomes
     */
    public static HashMap<String, Genome> generateGenomes(String[] genomeIds,
                                                          GenomeGraph genomeGraph) {
        HashSet<String> genomeIdSet = new HashSet<String>(Arrays.asList(genomeIds));
        HashMap<String, Genome> genomes = new HashMap<String, Genome>();
        Iterator<Strand> strandIterator = genomeGraph.getStrands().values().iterator();

        while (strandIterator.hasNext() && !genomeIdSet.isEmpty()) {
            Strand strand = strandIterator.next();
            Iterator<String> genomeIdIterator = strand.getGenomes().iterator();
            while (genomeIdIterator.hasNext() && !genomeIdSet.isEmpty()) {
                String genomeId = genomeIdIterator.next();
                if (genomeIdSet.remove(genomeId)) {
                    Genome genome = deriveGenome(strand, genomeId);
                    genomes.put(genomeId, genome);
                } else {
                    assert genomes.containsKey(genomeId);
                }
            }
        }
        return genomes;
    }

    /**
     * Derive genome.
     *
     * @param startingStrand the starting strand from which to generate the genome using the edges
     *                       connecting the strands and using the topological nature
     *                       of the graph they represent.
     * @param genomeId       the genome id of the genome to be generated
     * @return the generated genome
     */
    private static Genome deriveGenome(Strand startingStrand, String genomeId) {

        ArrayDeque<Strand> genomeStrands = new ArrayDeque<Strand>();
        Strand strand = startingStrand;

        while (strand != null) {
            genomeStrands.addLast(strand);
            strand = getStrandWithLowestId(strand.getNextStrandsWith(genomeId));
        }

        strand = getStrandWithHighestId(genomeStrands.peekFirst().getPreviousStrandsWith(genomeId));

        while (strand != null) {
            genomeStrands.addFirst(strand);
            strand = getStrandWithHighestId(strand.getPreviousStrandsWith(genomeId));
        }

        Genome genome = new Genome(genomeId);
        genome.setStrands(new ArrayList<Strand>(genomeStrands));
        return genome;
    }

    /**
     * Gets the strand with highest id.
     *
     * @param strands the strands
     * @return the strand with highest id
     */
    private static Strand getStrandWithHighestId(List<Strand> strands) {
        return strands.stream().max(Comparator.comparing(Strand::getId)).orElse(null);
    }

    /**
     * Gets the strand with lowest id.
     *
     * @param strands the strands
     * @return the strand with lowest id
     */
    private static Strand getStrandWithLowestId(List<Strand> strands) {
        return strands.stream().min(Comparator.comparing(Strand::getId)).orElse(null);
    }

}
