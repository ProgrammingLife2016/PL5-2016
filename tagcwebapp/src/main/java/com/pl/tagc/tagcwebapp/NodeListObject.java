package com.pl.tagc.tagcwebapp;

import genome.Strand;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class NodeListObject {
	@SuppressWarnings("unused")
	private String id = "test";
	private List<RibbonNode> cList;

	public NodeListObject() {
	}

	public NodeListObject(CopyOnWriteArrayList<RibbonNode> cList) {
		this.cList = cList;
	}

	public List<RibbonNode> getcList() {
		return cList;
	};
}