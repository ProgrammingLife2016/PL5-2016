package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import phylogenetictree.PhylogeneticTree;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class PhylogeneticTreeObject {
		@SuppressWarnings("unused")
		private String id = "test";
		private PhylogeneticTree phylogeneticTree;

		public PhylogeneticTreeObject() {
		}

		public PhylogeneticTreeObject(PhylogeneticTree phylogeneticTree) {
			this.phylogeneticTree = phylogeneticTree;
		}

		public PhylogeneticTree getPhylogeneticTree() {
			return phylogeneticTree;
		};
}
