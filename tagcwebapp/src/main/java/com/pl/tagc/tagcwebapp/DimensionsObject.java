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
	
	/** The height. */
	private double height;

	/**
	 * Instantiates a new dimensions object.
	 */
	public DimensionsObject() {
	}

	/**
	 * Instantiates a new dimensions object.
	 *
	 * @param width the width
	 * @param height the height
	 */
	public DimensionsObject(double width, double height) {
		this.setWidth(width);
		this.setHeight(height);
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(double height) {
		this.height = height;
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