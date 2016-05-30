/**
 * Gives the package Info.
 */
@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(value = PhylogeneticNodeAdapter.class, type = PhylogeneticNode.class),
    @XmlJavaTypeAdapter(value = RibbonNodeAdapter.class, type = RibbonNode.class),
    @XmlJavaTypeAdapter(value = RibbonEdgeAdapter.class, type = RibbonEdge.class)
})
package com.pl.tagc.tagcwebapp;
 
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import phylogenetictree.PhylogeneticNode;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;
 
