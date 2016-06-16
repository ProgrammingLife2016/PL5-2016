package genome;

import java.util.ArrayList;
import java.util.HashMap;

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
    public static GSearchResult search(String searchString, SearchType searchType,
                                       GenomeGraph genomeGraph) {
        GSearchResult searchResult = new GSearchResult();
        searchResult.setSearchType(searchType);
        switch (searchType) {
            case FullSearch:
                break;
            case GenomicFeatureSearch:
                searchGenomicFeatures(searchResult, searchString, genomeGraph.getStrands());
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
     * Searches for genomic features for which the name matches the search string.
     *
     * @param searchResult the search result
     * @param searchString the search string
     * @param the          strands in which the features will be searched
     */
    private static void searchGenomicFeatures(GSearchResult searchResult, String searchString,
                                              HashMap<Integer, Strand> strands) {

        for (Strand strand : strands.values()) {
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
