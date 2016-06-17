package genome;

import java.util.ArrayList;

/**
 * The Class GFeatureSearchMatch. This class represents a genomic feature search match for a
 * given string. It contains a substring of the search string used to search the genomic features,
 * the genomic feature which has a name that contains the substring and the strand that
 * contains the feature.
 */
public class GenomeFeatureSearchMatch {
	
	/**
	 * The feature.
	 */
	private GenomicFeature feature;

	/** 
	 * The strand. 
	 */
	private ArrayList<Strand> strands;
	
	/**
	 * The sub string.
	 */
	private String subString;

	/**
	 * Instantiates a new g feature search match.
	 */
	public GenomeFeatureSearchMatch() { }
	
	/**
	 * Instantiates a new g feature search match.
	 *
	 * @param strands the strands
	 * @param feature the feature
	 * @param subString the sub string
	 */
	public GenomeFeatureSearchMatch(ArrayList<Strand> strands,
							   GenomicFeature feature,
							   String subString) {
		this.setFeature(feature);
		this.setSubString(subString);
		this.setStrands(strands);
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

	/**
	 * Add a strand to this search match.
	 *
	 * @param strand strand to add.
	 */
	public void addStrand(Strand strand) {
		strands.add(strand);
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


}
