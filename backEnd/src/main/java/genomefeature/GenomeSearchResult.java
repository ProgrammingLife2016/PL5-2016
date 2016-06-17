package genomefeature;

import strand.Strand;

import java.util.ArrayList;
import java.util.Arrays;

import genomefeature.GraphSearcher.SearchType;

/**
 * Contains the search result that contains the selected search feature.
 */
public class GenomeSearchResult {

    /**
     * The search type.
     */
    private SearchType searchType;

    /**
     * The g feature search matches.
     */
    private ArrayList<GenomeFeatureSearchMatch> gFeatureSearchMatches =
            new ArrayList<GenomeFeatureSearchMatch>();

	/**
	 * Adds the g feature search match.
	 * @param strand The strand to add to the feature
	 * @param feature The genomic feature to match.
	 * @param subString The substring that was searched.
	 */
	public void addGFeatureStrandSearchMatch(Strand strand,
											 GenomicFeature feature,
											 String subString) {
		boolean contains = false;
		for (GenomeFeatureSearchMatch match : gFeatureSearchMatches) {
			if (match.getFeature() == feature) {
				match.addStrand(strand);
				contains = true;
				break;
			}
		}
		if (!contains) {
			GenomeFeatureSearchMatch searchMatch = new GenomeFeatureSearchMatch(
					new ArrayList<>(Arrays.asList(strand)),
					feature,
					subString);
			gFeatureSearchMatches.add(searchMatch);
		}

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
     * Sets the search type.
     *
     * @param searchType the new search type
     */
    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    /**
     * Gets the g feature search matches.
     *
     * @return the g feature search matches
     */
    public ArrayList<GenomeFeatureSearchMatch> getgFeatureSearchMatches() {
        return gFeatureSearchMatches;
    }

    /**
     * Sets the g feature search matches.
     *
     * @param gFeatureSearchMatches the new g feature search matches
     */
    public void setgFeatureSearchMatches(ArrayList<GenomeFeatureSearchMatch> gFeatureSearchMatches) {
        this.gFeatureSearchMatches = gFeatureSearchMatches;
    }
}
