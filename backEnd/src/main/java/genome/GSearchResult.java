package genome;

import genome.GraphSearcher.SearchType;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Class GSearchResult.
 */
public class GSearchResult {

	/** The search type. */
	private SearchType searchType;

	/** The g feature search matches. */
	private ArrayList<GFeatureSearchMatch> gFeatureSearchMatches = 
			new ArrayList<GFeatureSearchMatch>();

	/**
	 * Adds the g feature search match.
	 *
	 *            the search match
	 */
	public void addGFeatureStrandSearchMatch(Strand strand, GenomicFeature feature, String subString) {
		boolean contains = false;
		for (GFeatureSearchMatch match : gFeatureSearchMatches) {
			if (match.getFeature() == feature) {
				match.addStrand(strand);
				contains = true;
				break;
			}
		}
		if (!contains) {
			GFeatureSearchMatch searchMatch = new GFeatureSearchMatch(
					new ArrayList<>(Arrays.asList(strand)),
					feature,
					subString);
			gFeatureSearchMatches.add(searchMatch);
		}

	}

	/**
	 * Sets the search type.
	 *
	 * @param searchType
	 *            the new search type
	 */
	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}

	/**
	 * Gets the search type.
	 *
	 * @return the search type
	 */
	public SearchType getSearchType() {
		return searchType;
	}

	/**
	 * Gets the g feature search matches.
	 *
	 * @return the g feature search matches
	 */
	public ArrayList<GFeatureSearchMatch> getgFeatureSearchMatches() {
		return gFeatureSearchMatches;
	}

	/**
	 * Sets the g feature search matches.
	 *
	 * @param gFeatureSearchMatches the new g feature search matches
	 */
	public void setgFeatureSearchMatches(ArrayList<GFeatureSearchMatch> gFeatureSearchMatches) {
		this.gFeatureSearchMatches = gFeatureSearchMatches;
	}
}
