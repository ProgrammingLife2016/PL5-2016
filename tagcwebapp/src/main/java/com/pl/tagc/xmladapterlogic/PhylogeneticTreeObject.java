package com.pl.tagc.xmladapterlogic;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;

import phylogenetictree.PhylogeneticNode;
import phylogenetictree.PhylogeneticTree;
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(propOrder={"name", "children"})
public class PhylogeneticTreeObject {
		@SuppressWarnings("unused")
		private String name = "root";
		private ArrayList<PhylogeneticNode> children;
		public PhylogeneticTreeObject() {
		}

		public PhylogeneticTreeObject(PhylogeneticNode phylogeneticTreeRoot) {
			this.children = phylogeneticTreeRoot.getChildren();
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public ArrayList<PhylogeneticNode> getChildren() {
			return children;
		}

		public void setChildren(ArrayList<PhylogeneticNode> children) {
			this.children = children;
		};
}