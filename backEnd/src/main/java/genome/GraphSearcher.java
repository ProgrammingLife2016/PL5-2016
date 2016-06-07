package genome;

// TODO: Auto-generated Javadoc

/**
 * The Class GraphSearcher.
 */
public final class GraphSearcher {

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
	 * @param searchString
	 *            the search string
	 * @param searchType
	 *            the search type
	 * @return the g search result
	 */
	public static GSearchResult search(String searchString, SearchType searchType) {
		GSearchResult searchResult = new GSearchResult();
		return searchResult;
	}
}
