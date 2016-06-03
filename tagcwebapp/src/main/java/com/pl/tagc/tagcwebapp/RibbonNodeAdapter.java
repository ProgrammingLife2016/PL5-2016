package com.pl.tagc.tagcwebapp;

import ribbonnodes.RibbonNode;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The Class RibbonNodeAdapter.
 */
public class RibbonNodeAdapter extends XmlAdapter<AdaptedRibbonNode, RibbonNode> {

    /* (non-Javadoc)
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
     */
    @Override
    public RibbonNode unmarshal(AdaptedRibbonNode adaptedRibbonNode) throws Exception {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
     */
    @Override
    public AdaptedRibbonNode marshal(RibbonNode node) throws Exception {
    	AdaptedRibbonNode adaptedNode = new AdaptedRibbonNode();
    	adaptedNode.setLabel(node.getLabel() + "Node: " +node.getId());
    	adaptedNode.setId(node.getId());
    	adaptedNode.setGenomes(node.getGenomes());
        adaptedNode.setEdges(node.getOutEdges());
        adaptedNode.setX(node.getX());
        adaptedNode.setY(node.getY());
        return adaptedNode;
    }

}
