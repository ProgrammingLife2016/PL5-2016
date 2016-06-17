package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import genomefeature.GenomeFeatureSearchMatch;

/**
 * The Class RibbonNodeAdapter.
 */
public class SearchMatchAdapter extends XmlAdapter<AdaptedSearchMatch, GenomeFeatureSearchMatch> {


	@Override
	public AdaptedSearchMatch marshal(GenomeFeatureSearchMatch gMatch) throws Exception {
		AdaptedSearchMatch match = new AdaptedSearchMatch();
		match.setFeature(gMatch.getFeature());
		match.setStrands(gMatch.getStrands());
		return match;
	}

    @Override
    public GenomeFeatureSearchMatch unmarshal(AdaptedSearchMatch arg0) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
