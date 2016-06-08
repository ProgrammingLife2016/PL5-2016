package genome;

/**
 * The Class GFeatureSearchMatch.
 */
public class GFeatureSearchMatch {
	/** The feature. */
	private GenomicFeature feature;
	
	/** The sub string. */
	private String subString;

	/** The strand. */
	private Strand strand;
	
	

	/**
	 * Instantiates a new g feature search match.
	 */
	public GFeatureSearchMatch() { }
	
	/**
	 * Instantiates a new g feature search match.
	 *
	 * @param strand the strand
	 * @param feature the feature
	 * @param subString the sub string
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
