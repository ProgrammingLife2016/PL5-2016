package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import phylogenetictree.PhylogeneticNode;

public class PhylogeneticNodeAdapter extends XmlAdapter<AdaptedPhylogeneticNode, PhylogeneticNode> {

    @Override
    public PhylogeneticNode unmarshal(AdaptedPhylogeneticNode adaptedPhylogeneticNode) throws Exception {
        return null;
    }

    @Override
    public AdaptedPhylogeneticNode marshal(PhylogeneticNode node) throws Exception {
    	AdaptedPhylogeneticNode adaptedNode = new AdaptedPhylogeneticNode();
    	adaptedNode.setNameLabel(node.getNameLabel());
    	adaptedNode.setDistance(node.getDistance());
    	adaptedNode.setChildren(node.getChildren());
        return adaptedNode;
    }

}
