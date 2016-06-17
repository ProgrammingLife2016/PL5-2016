/**
 * Gives the package Info.
 */
@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(value = PhylogeneticNodeAdapter.class, type = PhylogeneticNode.class),
        @XmlJavaTypeAdapter(value = RibbonNodeAdapter.class, type = RibbonNode.class),
        @XmlJavaTypeAdapter(value = StrandAdapter.class, type = Strand.class),
        @XmlJavaTypeAdapter(value = RibbonEdgeAdapter.class, type = RibbonEdge.class),
        @XmlJavaTypeAdapter(value = SearchMatchAdapter.class, type = GenomeFeatureSearchMatch.class)
})
package com.pl.tagc.tagcwebapp;

import phylogenetictree.PhylogeneticNode;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;
import strand.Strand;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import genomefeature.GenomeFeatureSearchMatch;
 
