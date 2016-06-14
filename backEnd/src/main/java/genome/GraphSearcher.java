package genome;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc

/**
 * The Class GraphSearcher.
 */
public final class GraphSearcher {

	/**
	 * Instantiates a new graph searcher.
	 */
	private GraphSearcher() {
	}

	/**
	 * The Enum SearchType.
	 */
	public enum SearchType {

		/** The Genomic feature search. */
		GenomicFeatureSearch,

		/** The Meta data search. */
		MetaDataSearch,

		/** The Mutation search. */
		MutationSearch,

		/** The Full search. */
		FullSearch
	}

	/**
	 * Search.
	 *
	 * @param searchString            the search string
	 * @param searchType            the search type
	 * @param genomeGraph the genome graph
	 * @return the g search result
	 */
	public static GSearchResult search(String searchString, SearchType searchType,
			GenomeGraph genomeGraph) {
		GSearchResult searchResult = new GSearchResult();
		searchResult.setSearchType(searchType);
		switch (searchType) {
		case FullSearch:
			break;
		case GenomicFeatureSearch:
			searchGenomicFeatures(searchResult, searchString, genomeGraph);
			break;
		case MetaDataSearch:
			break;
		case MutationSearch:
			break;
		default:
			break;

		}
		return searchResult;
	}

	/**
	 * Search genomic features.
	 *
	 * @param searchResult the search result
	 * @param searchString the search string
	 * @param graph the graph to search
	 */
	private static void searchGenomicFeatures(GSearchResult searchResult, String searchString,
											  GenomeGraph graph) {

		for (Strand strand : graph.getStrands().values()) {
			boolean relevant = false;

			for (String id : graph.getActiveGenomeIds()) {
				if (strand.getGenomes().contains(id)) {
					relevant = true;
					break;
				}
			}

			if (relevant) {
				ArrayList<GenomicFeature> features = strand.getGenomicFeatures();

				for (GenomicFeature feature : features) {
					String displayName = feature.getDisplayName().toLowerCase();
					String[] subStrings = searchString.split("\\s+");

					for (String subString : subStrings) {

						if (displayName.contains(subString.toLowerCase())) {
							GFeatureSearchMatch searchMatch = new GFeatureSearchMatch(strand, feature,
									subString);
							searchResult.addGFeatureSearchMatch(searchMatch);
						}

					}

				}
			}
			
		}
	}
}
