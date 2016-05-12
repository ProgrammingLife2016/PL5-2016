package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class DimensionsObject {

	private double width;

	public DimensionsObject() {
	}

	public DimensionsObject(double width) {
		this.setWidth(width);
	}



	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

}