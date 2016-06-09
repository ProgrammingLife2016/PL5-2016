package ribbonnodes;

import genome.Strand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A class to test the ribbonNodeFactory.
 * Created by Matthijs on 7-6-2016.
 */
public class RibbonNodeFactoryTest {

    /**
     * Test if a strand is correctly adapted to a ribbonNode.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testMakeRibbonNodeFromStrand() throws Exception {
        String[] genomes = {"1", "4"};
        Strand strand = new Strand(0, "asdf", genomes, "1", 0);
        strand.setX(5);

        ArrayList<String> activeGenome = new ArrayList<>(Arrays.asList(genomes));

        RibbonNode node = RibbonNodeFactory.makeRibbonNodeFromStrand(5, strand, activeGenome);
        assertEquals(node.getId(), 5);
        assertEquals(2, node.getGenomes().size());
        assertEquals(node.getGenomes().get(0), "1");
        assertEquals(node.getGenomes().get(1), "4");
        assertEquals(node.getStrands().size(), 1);
        assertEquals(node.getStrands().get(0), strand);
        assertEquals(node.getX(), 5);

    }

    /**
     * Test if a ribbon node is correctly split in to nodes containing only one genome.
     *
     * @throws Exception if fail.
     */

    @Test
    public void testMakeRibbonNodesFromSplit() throws Exception {
        String[] genomes = {"1", "2"};
        Strand strand = new Strand(0, "asdf", genomes, "1", 0);
        RibbonNode node = new RibbonNode(0, new ArrayList<>(Arrays.asList("1", "2")));
        node.addStrand(strand);
        ArrayList<RibbonNode> splitNodes = RibbonNodeFactory.makeRibbonNodesFromSplit(node, 0);
        assertEquals(splitNodes.size(), 2);
        assertEquals(splitNodes.get(0).getGenomes().get(0), "1");
        assertEquals(splitNodes.get(1).getGenomes().get(0), "2");
        assertEquals(splitNodes.get(0).getId(), 1);
        assertEquals(splitNodes.get(1).getId(), 2);
        assertEquals(splitNodes.get(0).getStrands().get(0), strand);
        assertEquals(splitNodes.get(1).getStrands().get(0), strand);

    }

    /**
     * Test if two nodes are correctly collapsed in to one.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testCollapseNodes() throws Exception {
        RibbonNode node1 = new RibbonNode(0, new ArrayList<>(Arrays.asList("1")));
        node1.setX(1);
        RibbonNode node2 = new RibbonNode(1, new ArrayList<>(Arrays.asList("1")));
        node2.setId(2);
        String[] genomes = {"1", "2"};
        Strand strand = new Strand(0, "asdf", genomes, "1", 0);
        node2.addStrand(strand);
        RibbonEdge edge = new RibbonEdge(1, 3);
        node2.addEdge(edge);

        RibbonNode collapsedNode = RibbonNodeFactory.collapseNodes(node1, node2);
        assertNotNull(collapsedNode.getOutEdge(0, 3));
        assertEquals(collapsedNode.getStrands().get(0), strand);
        assertEquals(collapsedNode.getX(), node1.getX());

    }
}