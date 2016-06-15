package genome;

import java.util.ArrayList;

/**
 * The Class GFeatureSearchMatch.
 */
public class GFeatureSearchMatch {
	/** The feature. */
	private GenomicFeature feature;
	
	/** The sub string. */
	private String subString;

	/** The strand. */
	private ArrayList<Strand> strands;
	
	

	/**
	 * Instantiates a new g feature search match.
	 */
	public GFeatureSearchMatch() { }
	
	/**
	 * Instantiates a new g feature search match.
	 *
	 * @param strands the strands
	 * @param feature the feature
	 * @param subString the sub string
	 */
	public GFeatureSearchMatch(ArrayList<Strand> strands, GenomicFeature feature, String subString) {
		this.setFeature(feature);
		this.setSubString(subString);
		this.setStrands(strands);
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
	public ArrayList<Strand> getStrands() {
		return strands;
	}

	/**
	 * Sets the strand.
	 *
	 * @param strands the new strand
	 */
	public void setStrands(ArrayList<Strand> strands) {
		this.strands = strands;
	}

	public void addStrand(Strand strand){
		strands.add(strand);
	}
}
