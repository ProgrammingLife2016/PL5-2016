package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import ribbonnodes.RibbonNode;

public class RibbonNodeAdapter extends XmlAdapter<AdaptedRibbonNode, RibbonNode> {

    @Override
    public RibbonNode unmarshal(AdaptedRibbonNode adaptedRibbonNode) throws Exception {
        return null;
    }

    @Override
    public AdaptedRibbonNode marshal(RibbonNode node) throws Exception {
    	AdaptedRibbonNode adaptedNode = new AdaptedRibbonNode();
    	adaptedNode.setLabel(node.getLabel());
    	adaptedNode.setId(node.getId());
    	adaptedNode.setGenomes(node.getGenomes());
    	adaptedNode.setEdges(node.getEdges());
        return adaptedNode;
    }

}
