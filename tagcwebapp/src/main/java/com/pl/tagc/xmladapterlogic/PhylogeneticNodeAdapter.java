package com.pl.tagc.xmladapterlogic;

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
    	boolean isFirstChild = true;
    	if(node.getParent() != null)
    	{
    		isFirstChild = node.getParent().getChildren().get(0).equals(node);
    	}    	
    	adaptedNode.setOriginalChildOrder(isFirstChild ? 1 : 2);
        return adaptedNode;
    }

}
