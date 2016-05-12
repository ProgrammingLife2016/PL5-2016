package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * The Class NewickStringObject.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class NewickStringObject {

	/** The newick string. */
	private String newickString;

	/**
	 * Instantiates a new newick string object.
	 */
	public NewickStringObject() {
	}

	/**
	 * Instantiates a new newick string object.
	 *
	 * @param newickString
	 *            the newick string
	 */
	public NewickStringObject(String newickString) {
		this.newickString = newickString;
	}

	/**
	 * Gets the newick string.
	 *
	 * @return the newick string
	 */
	public String getNewickString() {
		return newickString;
	};
}