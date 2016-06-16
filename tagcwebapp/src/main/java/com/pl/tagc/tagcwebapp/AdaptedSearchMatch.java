package com.pl.tagc.tagcwebapp;

import genome.GenomicFeature;
import genome.Strand;

import java.util.ArrayList;

/**
 * The Class GFeatureSearchMatch.
 */
public class AdaptedSearchMatch {
	/**
	 * The feature.
	 */
	private GenomicFeature feature;

	/** The strand. */
	private ArrayList<Strand> strands;

	/**
	 * The sub string.
	 */
	private String subString;

	/**
	 * Instantiates a new adapted search match.
	 */
	public AdaptedSearchMatch() { }
	
	/**
	 * Instantiates a new g feature search match.
	 *
	 * @param strands the strands
	 * @param feature the feature
	 * @param subString the sub string
	 */
	@SuppressWarnings("CPD-START")
	public AdaptedSearchMatch(ArrayList<Strand> strands, GenomicFeature feature, String subString) {
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
	@SuppressWarnings("CPD-END")
	public void setFeature(GenomicFeature feature) {
		this.feature = feature;
	}


}
