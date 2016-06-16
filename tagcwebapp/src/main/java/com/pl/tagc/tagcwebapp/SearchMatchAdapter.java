package com.pl.tagc.tagcwebapp;

import genome.GFeatureSearchMatch;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The Class RibbonNodeAdapter.
 */
public class SearchMatchAdapter extends XmlAdapter<AdaptedSearchMatch, GFeatureSearchMatch> {


	@Override
	public AdaptedSearchMatch marshal(GFeatureSearchMatch gMatch) throws Exception {
		AdaptedSearchMatch match = new AdaptedSearchMatch();
		match.setFeature(gMatch.getFeature());
		match.setStrands(gMatch.getStrands());
		return match;
	}

    @Override
    public GFeatureSearchMatch unmarshal(AdaptedSearchMatch arg0) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
