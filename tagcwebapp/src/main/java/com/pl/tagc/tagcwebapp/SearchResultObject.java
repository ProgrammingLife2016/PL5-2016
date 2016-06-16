package com.pl.tagc.tagcwebapp;

import genome.GFeatureSearchMatch;

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
    private ArrayList<GFeatureSearchMatch> gFeatureSearchMatches;

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
    public ArrayList<GFeatureSearchMatch> getgFeatureSearchMatches() {
        return gFeatureSearchMatches;
    }

    /**
     * Sets the g feature search matches.
     *
     * @param gFeatureSearchMatches the new g feature search matches
     */
    public void setgFeatureSearchMatches(ArrayList<GFeatureSearchMatch> gFeatureSearchMatches) {
        this.gFeatureSearchMatches = gFeatureSearchMatches;
    }

}
