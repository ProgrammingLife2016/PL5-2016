package genomefeature;

import java.util.ArrayList;

import genome.GenomeGraph;
import strand.Strand;

/**
 * The Class GraphSearcher. This class is a utility class that is responsible for searching the
 * genome data structures contained in the GenomeGraph class.
 */
public final class GraphSearcher {

	/**
	 * Instantiates a new graph searcher.
	 */
	private GraphSearcher() {
	}

	/**
	 * Search.
	 *
	 * @param searchString the search string
	 * @param searchType   the search type
	 * @param genomeGraph  the genome graph
	 * @return the search result which contains matches for the search string and search type
	 */
	public static GenomeSearchResult search(String searchString, SearchType searchType,
									   GenomeGraph genomeGraph) {
		GenomeSearchResult searchResult = new GenomeSearchResult();
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
	private static void searchGenomicFeatures(GenomeSearchResult searchResult,
											  String searchString,
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
							searchResult.addGFeatureStrandSearchMatch(strand, feature, subString);
						}

					}

				}
			}
			
		}
	}

	/**
	 * The Enum SearchType. The types of searches used to differentiate between different types
	 * of searches that will be handled by different search functions in this class.
	 */
	public enum SearchType {
		GenomicFeatureSearch,
		MetaDataSearch,
		MutationSearch,
		FullSearch
	}
}
