package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import ribbonnodes.RibbonEdge;

/**
 * The Class RibbonEdgeAdapter.
 */
public class RibbonEdgeAdapter extends XmlAdapter<AdaptedRibbonEdge, RibbonEdge> {

    /* (non-Javadoc)
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
     */
    @Override
    public RibbonEdge unmarshal(AdaptedRibbonEdge adaptedRibbonEdge) throws Exception {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
     */
    @Override
    public AdaptedRibbonEdge marshal(RibbonEdge edge) throws Exception {
    	AdaptedRibbonEdge adaptedEdge = new AdaptedRibbonEdge();
    	adaptedEdge.setEndId(edge.getStart());
    	adaptedEdge.setEndId(edge.getEnd());
    	adaptedEdge.setWeight(edge.getWeight());
        return adaptedEdge;
    }

}
