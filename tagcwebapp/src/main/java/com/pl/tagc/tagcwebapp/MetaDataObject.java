package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * The Class NodeListObject.
 */
@XmlRootElement
public class MetaDataObject {

	/** The id. */
	@SuppressWarnings("unused")
	private String id = "test";

	/** The c list. */
	private Map<String, HashSet<String>> list;

	/**
	 * Instantiates a new node list object.
	 */
	public MetaDataObject() {
	}

	/**
	 * Instantiates a new node list object.
	 *
	 * @param list the c list
	 */
	public MetaDataObject(Map<String, HashSet<String>> list) {
		this.list = list;
	}

	/**
	 * Gets the c list.
	 *
	 * @return the c list
	 */
	public Map<String, HashSet<String>> getlist() {
		return list;
	}

	/**
	 * Sets the c list.
	 *
	 * @param list the new c list
	 */
	public void setlist(Map<String, HashSet<String>> list) {
		this.list = list;
	};
}