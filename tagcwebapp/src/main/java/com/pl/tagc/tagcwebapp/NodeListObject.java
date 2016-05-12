package com.pl.tagc.tagcwebapp;

import genome.Node;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * The Class NodeListObject.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class NodeListObject {
	
	/** The id. */
	@SuppressWarnings("unused")
	private String id = "test";
	
	/** The c list. */
	private List<Node> cList;

	/**
	 * Instantiates a new node list object.
	 */
	public NodeListObject() {
	}

	/**
	 * Instantiates a new node list object.
	 *
	 * @param cList the c list
	 */
	public NodeListObject(CopyOnWriteArrayList<Node> cList) {
		this.cList = cList;
	}

	/**
	 * Gets the c list.
	 *
	 * @return the c list
	 */
	public List<Node> getcList() {
		return cList;
	};
}