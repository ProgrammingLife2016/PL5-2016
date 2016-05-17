package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;

import phylogenetictree.PhylogeneticNode;
import phylogenetictree.PhylogeneticTree;
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class PhylogeneticTreeObject {
		@SuppressWarnings("unused")
		private String id = null;
		 @XmlJavaTypeAdapter(PhylogeneticNodeAdapter.class)
		private PhylogeneticNode phylogeneticTreeRoot;

		public PhylogeneticTreeObject() {
		}

		public PhylogeneticTreeObject(PhylogeneticNode phylogeneticTreeRoot) {
			this.phylogeneticTreeRoot = phylogeneticTreeRoot;
		}

		public PhylogeneticNode getPhylogeneticTreeRoot() {
			return phylogeneticTreeRoot;
		};
}