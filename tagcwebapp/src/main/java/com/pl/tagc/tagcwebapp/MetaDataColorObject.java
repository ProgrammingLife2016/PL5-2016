package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.XmlRootElement;
import java.awt.*;
import java.util.HashSet;
import java.util.Map;

/**
 * The Class NodeListObject.
 */
@XmlRootElement
public class MetaDataColorObject {

	/** The id. */
	@SuppressWarnings("unused")
	private String id = "test";

	/** The c list. */
	private Map<String, Color> list;

	/**
	 * Instantiates a new node list object.
	 */
	public MetaDataColorObject() {
	}

	/**
	 * Instantiates a new node list object.
	 *
	 * @param list the c list
	 */
	public MetaDataColorObject(Map<String, Color> list) {
		this.list = list;
	}

	/**
	 * Gets the c list.
	 *
	 * @return the c list
	 */
	public Map<String, Color> getlist() {
		return list;
	}

	/**
	 * Sets the c list.
	 *
	 * @param list the new c list
	 */
	public void setlist(Map<String, Color> list) {
		this.list = list;
	};
}