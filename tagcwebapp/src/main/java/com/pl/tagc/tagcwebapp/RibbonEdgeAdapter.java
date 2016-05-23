package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import ribbonnodes.RibbonEdge;

import java.awt.*;

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
    	adaptedEdge.setStartId(edge.getStart());
    	adaptedEdge.setEndId(edge.getEnd());
        adaptedEdge.setWeight(edge.getWeight());
        adaptedEdge.setColor(parseColor(edge.getColor()));
        return adaptedEdge;
    }

    /**
     * Returns the hexadecimal string of a color
     * @param color The color object
     * @return A string containing the hexadecimal of the color
     */
    private String parseColor(Color color) {
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        while (hex.length() < 6) {
            hex = "0" + hex;
        }
        return hex;
    }

}
