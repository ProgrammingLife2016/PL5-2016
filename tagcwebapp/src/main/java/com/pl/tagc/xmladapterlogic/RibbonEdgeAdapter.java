package com.pl.tagc.xmladapterlogic;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import ribbonnodes.RibbonEdge;

public class RibbonEdgeAdapter extends XmlAdapter<AdaptedRibbonEdge, RibbonEdge> {

    @Override
    public RibbonEdge unmarshal(AdaptedRibbonEdge adaptedRibbonEdge) throws Exception {
        return null;
    }

    @Override
    public AdaptedRibbonEdge marshal(RibbonEdge edge) throws Exception {
    	AdaptedRibbonEdge adaptedEdge = new AdaptedRibbonEdge();
    	adaptedEdge.setEndId(edge.getStart());
    	adaptedEdge.setEndId(edge.getEnd());
    	adaptedEdge.setWeight(edge.getWeight());
    	//adaptedEdge.setEdges(Edge.getEdges());
        return adaptedEdge;
    }

}
