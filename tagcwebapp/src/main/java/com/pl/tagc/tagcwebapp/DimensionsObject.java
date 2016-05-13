package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * The Class DimensionsObject.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class DimensionsObject {

	/** The width. */
	private double width;


	/**
	 * Instantiates a new dimensions object.
	 */
	public DimensionsObject() {
	}

	/**
	 * Instantiates a new dimensions object.
	 *
	 * @param width the width
	 */
	public DimensionsObject(double width) {
		this.setWidth(width);
	}



	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(double width) {
		this.width = width;
	}

}