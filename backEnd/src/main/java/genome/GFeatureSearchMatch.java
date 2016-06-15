package genome;

/**
 * The Class GFeatureSearchMatch. This class represents a genomic feature search match for a 
 * given string. It contains a substring of the search string used to search the genomic features,
 * the genomic feature which has a name that contains the substring and the strand that 
 * contains the feature. 
 */
public class GFeatureSearchMatch {
	/** The feature. */
	private GenomicFeature feature;
	
	/** The sub string. */
	private String subString;

	/** The strand. */
	private Strand strand;
	
	

	/**
	 * Instantiates a new genomic feature search match.
	 */
	public GFeatureSearchMatch() { }
	
	/**
	 * Instantiates a new genomic feature search match.
	 *
	 * @param strand the strand containing the genomic feature.
	 * @param feature the genomic feature.
	 * @param subString he substring that matched a substring in the name of the feature.
	 */
	public GFeatureSearchMatch(Strand strand, GenomicFeature feature, String subString) {
		this.setFeature(feature);
		this.setSubString(subString);
		this.setStrand(strand);
	}
	/**
	 * Gets the sub string.
	 *
	 * @return the sub string
	 */
	public String getSubString() {
		return subString;
	}

	/**
	 * Sets the sub string.
	 *
	 * @param subString the new sub string
	 */
	public void setSubString(String subString) {
		this.subString = subString;
	}
	

	/**
	 * Gets the feature.
	 *
	 * @return the feature
	 */
	public GenomicFeature getFeature() {
		return feature;
	}

	/**
	 * Sets the feature.
	 *
	 * @param feature the new feature
	 */
	public void setFeature(GenomicFeature feature) {
		this.feature = feature;
	}

	/**
	 * Gets the strand.
	 *
	 * @return the strand
	 */
	public Strand getStrand() {
		return strand;
	}

	/**
	 * Sets the strand.
	 *
	 * @param strand the new strand
	 */
	public void setStrand(Strand strand) {
		this.strand = strand;
	}
}
