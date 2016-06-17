package ribbonnodes;

import genome.Genome;
import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * A class to test the RibbonEdgeFactory.
 * Created by Matthijs on 7-6-2016.
 */
public class RibbonEdgeFactoryTest {

    /**
     * Test the creation of a node with metadata.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testCreateRibbonEdgeWithMetaData() throws Exception {
        Genome genome = new Genome("1");
        genome.setColor(Color.decode("0xed00c3"));
        HashSet<String> genomeID = new HashSet<>(Arrays.asList("1"));
        RibbonNode node1 = new RibbonNode(0, genomeID);
        RibbonNode node2 = new RibbonNode(1, genomeID);
        RibbonEdge edge = RibbonEdgeFactory.createRibbonEdge(node1, node2, genome);

        assertEquals(edge.getStart(), node1);
        assertEquals(edge.getEnd(), node2);
        assertEquals(edge.getColor(), Color.decode("0xed00c3"));

    }

    /**
     * Test the creation of a node that starts with G.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testCreateRibbonEdgeWithG() throws Exception {
        Genome genome = new Genome("G");
        HashSet<String> genomeID = new HashSet<>(Arrays.asList("1"));
        RibbonNode node1 = new RibbonNode(0, genomeID);
        RibbonNode node2 = new RibbonNode(1, genomeID);
        RibbonEdge edge = RibbonEdgeFactory.createRibbonEdge(node1, node2, genome);


        assertEquals(edge.getStart(), node1);
        assertEquals(edge.getEnd(), node2);
        assertEquals(edge.getColor(), Color.black);

    }

    /**
     * Test the creation of a node that has no metadata (broken data).
     *
     * @throws Exception if fail.
     */
    @Test
    public void testCreateRibbonEdgeBrokenData() throws Exception {
        Genome genome = new Genome("broken");
        HashSet<String> genomeID = new HashSet<>(Arrays.asList("1"));
        RibbonNode node1 = new RibbonNode(0, genomeID);
        RibbonNode node2 = new RibbonNode(1, genomeID);
        RibbonEdge edge = RibbonEdgeFactory.createRibbonEdge(node1, node2, genome);

        assertEquals(edge.getStart(), node1);
        assertEquals(edge.getEnd(), node2);
        assertEquals(edge.getColor(), Color.BLACK);

    }
}