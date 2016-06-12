package com.pl.tagc.tagcwebapp;

import genome.GenomicFeature;
import genome.Strand;

/**
 * The Class GFeatureSearchMatch.
 */
public class AdaptedSearchMatch {
	/** The feature. */
	private GenomicFeature feature;
	
	/** The sub string. */
	private String subString;

	/** The strand. */
	private Strand strand;	

	/**
	 * Instantiates a new adapted search match.
	 */
	public AdaptedSearchMatch() { }
	
	/**
	 * Instantiates a new g feature search match.
	 *
	 * @param strand the strand
	 * @param feature the feature
	 * @param subString the sub string
	 */
	@SuppressWarnings("CPD-START")
	public AdaptedSearchMatch(Strand strand, GenomicFeature feature, String subString) {
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
	@SuppressWarnings("CPD-END")
	public void setStrand(Strand strand) {
		this.strand = strand;
	}
}
