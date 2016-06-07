package genome;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Class StrandAnnotator. A utility class which annotates strands.
 */
public final class StrandAnnotator {

	private StrandAnnotator() { };
	
	/**
	 * Annotate.
	 *
	 * @param genomeId
	 *            the genome id
	 * @param genomeGraph
	 *            the genome graph
	 * @param annotations
	 *            the annotations
	 */
	public static void annotate(String genomeId, GenomeGraph genomeGraph,
			List<GenomicFeature> annotations) {
		Genome genome = genomeGraph.getGenome(genomeId);
		assert (genome != null);
		ArrayList<Strand> strands = genome.getStrands();
		annotations.sort((GenomicFeature o1, GenomicFeature o2) -> new Integer(o1.getStart())
				.compareTo(o2.getStart()));
		Iterator<GenomicFeature> gfIterator = annotations.iterator();
		GenomicFeature genomicFeature = gfIterator.next();

		for (int i = 0; i < strands.size() - 1; i++) {
			int startOfStrand = strands.get(i).getReferenceCoordinate();
			int startOfNextStrand = strands.get(i + 1).getReferenceCoordinate();
			while (genomicFeature.overlaps(startOfStrand, startOfNextStrand)) {
				strands.get(i).addGenomicFeature(genomicFeature);
				if (genomicFeature.endsBetween(startOfStrand, startOfNextStrand)) {
					if (gfIterator.hasNext()) {
						genomicFeature = gfIterator.next();
					} else {
						break;
					}
				}
			}
		}

		if (gfIterator.hasNext()) {
			Strand lastStrand = strands.get(strands.size() - 1);
			assert (lastStrand.getReferenceCoordinate() <= genomicFeature.getStart());
			while (gfIterator.hasNext()) {
				lastStrand.addGenomicFeature(genomicFeature);
				genomicFeature = gfIterator.next();
			}
		}
	}

}
