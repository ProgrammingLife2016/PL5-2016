package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonInclude;

import phylogenetictree.PhylogeneticTree;
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class PhylogeneticTreeObject {
		@SuppressWarnings("unused")
		private String id = null;
		private PhylogeneticTree phylogeneticTree;

		public PhylogeneticTreeObject() {
		}

		public PhylogeneticTreeObject(PhylogeneticTree phylogeneticTree) {
			this.phylogeneticTree = phylogeneticTree;
		}

		@XmlTransient
		public PhylogeneticTree getPhylogeneticTree() {
			return phylogeneticTree;
		};
}