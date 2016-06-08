package genome;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class StrandAnnotator. A utility class which annotates strands.
 */
public final class StrandAnnotator {

	private StrandAnnotator() {
	};

	/**
	 * Annotate.
	 *
	 * @param strands            the strands
	 * @param annotations            the annotations
	 */
	public static void annotate(ArrayList<Strand> strands, List<GenomicFeature> annotations) {
		annotations.sort((GenomicFeature o1, GenomicFeature o2) -> new Integer(o1.getStart())
				.compareTo(o2.getStart()));
		LinkedHashSet<GenomicFeature> hashSet = (LinkedHashSet<GenomicFeature>) annotations.stream()
                .collect(Collectors.toCollection(LinkedHashSet::new));
		Iterator<GenomicFeature> gfIterator = hashSet.iterator();
		GenomicFeature genomicFeature = gfIterator.next();

		for (int i = 0; i < strands.size() - 1; i++) {
			int startOfStrand = strands.get(i).getReferenceCoordinate();
			int startOfNextStrand = strands.get(i + 1).getReferenceCoordinate();
			if (genomicFeature.overlaps(startOfStrand, startOfNextStrand)) {
				while (genomicFeature.overlaps(startOfStrand, startOfNextStrand)) {
					strands.get(i).addGenomicFeature(genomicFeature);
					if (genomicFeature.endsBetween(startOfStrand, startOfNextStrand)) {
						gfIterator.remove();
					} 
					if (gfIterator.hasNext()) {
						genomicFeature = gfIterator.next();
					} else {
						break;
					}
				}
				if(!hashSet.isEmpty()) {
					gfIterator = hashSet.iterator();
					genomicFeature = gfIterator.next();
				} else {
					break;
				}
			}
			
		}

		if (gfIterator.hasNext()) {
			Strand lastStrand = strands.get(strands.size() - 1);
			assert (lastStrand.getReferenceCoordinate() <= genomicFeature.getEnd());
			while (gfIterator.hasNext()) {
				lastStrand.addGenomicFeature(genomicFeature);
				genomicFeature = gfIterator.next();
			}
		}
	}

}
