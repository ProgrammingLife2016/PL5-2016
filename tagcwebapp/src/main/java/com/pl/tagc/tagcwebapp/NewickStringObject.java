package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class NewickStringObject {
	
	private String newickString;

	public NewickStringObject() {
	}

	public NewickStringObject(String newickString) {
		this.newickString = newickString;
	}

	public String getNewickString() {
		return newickString;
	};
}