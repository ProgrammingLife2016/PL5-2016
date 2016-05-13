package com.pl.tagc.tagcwebapp;

import genome.Strand;

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
	private List<Strand> cList;

	/**
	 * Instantiates a new node list object.
	 */
	public NodeListObject() {
	}


	public NodeListObject(CopyOnWriteArrayList<Strand> cList) {
		this.cList = cList;
	}

	public List<Strand> getcList() {
		return cList;
	};
}