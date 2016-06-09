package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * The Class NodeListObject.
 */
@XmlRootElement
public class ArrayListObject {
	
	/** The id. */
	@SuppressWarnings("unused")
	private String id = "test";
	
	/** The c list. */
	private List<String> list;

	/**
	 * Instantiates a new node list object.
	 */
	public ArrayListObject() {
	}

	/**
	 * Instantiates a new node list object.
	 *
	 * @param list the c list
	 */
	public ArrayListObject(List<String> list) {
		this.list = list;
	}

	/**
	 * Gets the c list.
	 *
	 * @return the c list
	 */
	public List<String> getlist() {
		return list;
	}

	/**
	 * Sets the c list.
	 *
	 * @param list the new c list
	 */
	public void setlist(List<String> list) {
		this.list = list;
	};
}