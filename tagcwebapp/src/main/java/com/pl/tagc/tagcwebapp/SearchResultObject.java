package com.pl.tagc.tagcwebapp;

import genome.GenomeFeatureSearchMatch;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * The Class SearchResultObject.
 */
@XmlRootElement
public class SearchResultObject {


    /**
     * The g feature search matches.
     */
    private ArrayList<GenomeFeatureSearchMatch> gFeatureSearchMatches;

    /**
     * Instantiates a new search result object.
     */
    public SearchResultObject() {
    }


    /**
     * Gets the g feature search matches.
     *
     * @return the g feature search matches
     */
    public ArrayList<GenomeFeatureSearchMatch> getgFeatureSearchMatches() {
        return gFeatureSearchMatches;
    }

    /**
     * Sets the g feature search matches.
     *
     * @param gFeatureSearchMatches the new g feature search matches
     */
    public void setgFeatureSearchMatches(ArrayList<GenomeFeatureSearchMatch> gFeatureSearchMatches) {
        this.gFeatureSearchMatches = gFeatureSearchMatches;
    }

}
