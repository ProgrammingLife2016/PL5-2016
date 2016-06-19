package strand;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import genomefeature.GenomicFeature;

/**
 * The Class StrandAnnotator. A utility class which annotates strands.
 */
public final class StrandAnnotator {

	/**
	 * Create a StrandAnnotator.
	 * This is a private constructor because this should not be possible.
	 */
    private StrandAnnotator() {
    }

    /**
     * Annotate the strands with the features.
     *
     * @param strands     the strands
     * @param featureList the annotations
     */
    public static void annotate(ArrayList<Strand> strands, List<GenomicFeature> featureList) {
        featureList.sort((GenomicFeature o1, GenomicFeature o2) -> new Integer(o1.getStart())
                .compareTo(o2.getStart()));
        GenomicFeature firstFeature = featureList.get(0);
        LinkedHashSet<GenomicFeature> features = (LinkedHashSet<GenomicFeature>) featureList
                .stream().collect(Collectors.toCollection(LinkedHashSet::new));
        Iterator<Strand> strandIterator = strands.iterator();

        while (strandIterator.hasNext() && !features.isEmpty()) {
            Strand strand = strandIterator.next();
            int startOfStrand = strand.getStartCoordinate();
            int endOfStrand = strand.getEndCoordinate();
            if (firstFeature.overlaps(startOfStrand, endOfStrand)) {
                annotateStrand(strand, features);
                if (!features.isEmpty()) {
                    firstFeature = features.iterator().next();
                }
            }
        }
        assert features.isEmpty();
    }

    /**
     * Annotate a strand with the features.
     * 
     * @param strand   Annotated strand.
     * @param features The features.
     */
    private static void annotateStrand(Strand strand, LinkedHashSet<GenomicFeature> features) {
        int startOfStrand = strand.getStartCoordinate();
        int endOfStrand = strand.getEndCoordinate();
        Iterator<GenomicFeature> gfIterator = features.iterator();

        while (gfIterator.hasNext()) {
            GenomicFeature feature = gfIterator.next();
            if (feature.overlaps(startOfStrand, endOfStrand)) {
                strand.addGenomicFeature(feature);
                if (feature.endsBetween(startOfStrand, endOfStrand)) {
                    gfIterator.remove();
                }
            } else {
                break;
            }
        }
    }

}
