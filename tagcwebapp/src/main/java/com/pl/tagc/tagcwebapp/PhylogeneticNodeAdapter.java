package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import phylogenetictree.PhylogeneticNode;

/**
 * The Class PhylogeneticNodeAdapter.
 */
public class PhylogeneticNodeAdapter extends XmlAdapter<AdaptedPhylogeneticNode, PhylogeneticNode> {

	@Override
	public PhylogeneticNode unmarshal(AdaptedPhylogeneticNode adaptedPhylogeneticNode)
			throws Exception {
		return null;
	}

	@Override
	public AdaptedPhylogeneticNode marshal(PhylogeneticNode node) throws Exception {
		AdaptedPhylogeneticNode adaptedNode = new AdaptedPhylogeneticNode();
		adaptedNode.setNameLabel(node.getNameLabel());
		adaptedNode.setDistance(node.getDistance());
		adaptedNode.setChildren(node.getChildren());
		adaptedNode.setId(node.getId());
		boolean isFirstChild = true;
		if (node.getParent() != null) {
			isFirstChild = node.getParent().getChildren().get(0).equals(node);
		}
		if (isFirstChild) {
			adaptedNode.setOriginalChildOrder(1);
		} else {
			adaptedNode.setOriginalChildOrder(2);
		}

		return adaptedNode;
	}
}
