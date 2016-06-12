package com.pl.tagc.tagcwebapp;

import ribbonnodes.RibbonEdge;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.awt.Color;

/**
 * The Class RibbonEdgeAdapter.
 */
public class RibbonEdgeAdapter extends XmlAdapter<AdaptedRibbonEdge, RibbonEdge> {

    @Override
    public RibbonEdge unmarshal(AdaptedRibbonEdge adaptedRibbonEdge) throws Exception {
        return null;
    }

    @Override
    public AdaptedRibbonEdge marshal(RibbonEdge edge) throws Exception {
    	AdaptedRibbonEdge adaptedEdge = new AdaptedRibbonEdge();
    	adaptedEdge.setStartId(edge.getStartId());
    	adaptedEdge.setEndId(edge.getEndId());
        adaptedEdge.setWeight(edge.getWeight());
        adaptedEdge.setColor(parseColor(edge.getColor()));
        return adaptedEdge;
    }

    /**
     * Returns the hexadecimal string of a color.
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
