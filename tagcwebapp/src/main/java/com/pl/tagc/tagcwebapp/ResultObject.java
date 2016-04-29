package com.pl.tagc.tagcwebapp;

import genome.Node;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ResultObject {
	@SuppressWarnings("unused")
	private String id = "test";
	private List<Node> cList;

	public ResultObject() {
	}

	public ResultObject(CopyOnWriteArrayList<Node> cList) {
		this.cList = cList;
	}

	public List<Node> getcList() {
		return cList;
	};
}
