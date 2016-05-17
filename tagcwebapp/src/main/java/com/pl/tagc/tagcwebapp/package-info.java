@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(value = PhylogeneticNodeAdapter.class, type = PhylogeneticNode.class)
})
package com.pl.tagc.tagcwebapp;
 
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import phylogenetictree.PhylogeneticNode;
 
