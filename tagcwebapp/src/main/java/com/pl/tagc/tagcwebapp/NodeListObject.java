package com.pl.tagc.tagcwebapp;
import ribbonnodes.RibbonNode;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class NodeListObject.
 */
@XmlRootElement
public class NodeListObject {
	
	/** The id. */
	@SuppressWarnings("unused")
	private String id = "test";
	
	/** The c list. */
	private List<RibbonNode> cList;

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
	public NodeListObject(CopyOnWriteArrayList<RibbonNode> cList) {
		this.cList = cList;
	}

	/**
	 * Gets the c list.
	 *
	 * @return the c list
	 */
	public List<RibbonNode> getcList() {
		return cList;
	}

	/**
	 * Sets the c list.
	 *
	 * @param cList the new c list
	 */
	public void setcList(List<RibbonNode> cList) {
		this.cList = cList;
	};
}