package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import strand.Strand;

/**
 * The Class RibbonNodeAdapter.
 */
public class StrandAdapter extends XmlAdapter<AdaptedStrand, Strand> {


    @Override
    public Strand unmarshal(AdaptedStrand adaptedStrand) throws Exception {
        return null;
    }

    @Override
    public AdaptedStrand marshal(Strand node) throws Exception {
        AdaptedStrand adaptedNode = new AdaptedStrand();
        adaptedNode.setReferenceCoordinate(node.getStartCoordinate());
        adaptedNode.setId(node.getId());
        adaptedNode.setGenomes(node.getGenomes());
        adaptedNode.setX(node.getX());
        return adaptedNode;
    }

}
