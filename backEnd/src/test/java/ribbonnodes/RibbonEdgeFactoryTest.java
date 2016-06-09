package ribbonnodes;

import genome.Genome;
import genome.GenomeMetadata;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * A class to test the RibbonEdgeFactory.
 * Created by Matthijs on 7-6-2016.
 */
public class RibbonEdgeFactoryTest {

    /**
     * Test the creation of a node with metadata.
     * @throws Exception if fail.
     */
    @Test
    public void testCreateRibbonEdgeWithMetaData() throws Exception {
        GenomeMetadata metadata = new GenomeMetadata("1", "LIN 1");
        Genome genome = new Genome("1");
        genome.setMetadata(metadata);
        RibbonEdge edge = RibbonEdgeFactory.createRibbonEdge(0, 1, genome);

        assertEquals(edge.getStart(),0);
        assertEquals(edge.getEnd(),1);
        assertEquals(edge.getColor(), Color.decode("0xed00c3"));

    }

    /**
     * Test the creation of a node that starts with G.
     * @throws Exception if fail.
     */
    @Test
    public void testCreateRibbonEdgeWithG() throws Exception {
        Genome genome = new Genome("G");
        RibbonEdge edge = RibbonEdgeFactory.createRibbonEdge(0, 1, genome);

        assertEquals(edge.getStart(),0);
        assertEquals(edge.getEnd(),1);
        assertEquals(edge.getColor(), Color.decode("0xff0000"));

    }

    /**
     * Test the creation of a node that has no metadata (broken data).
     * @throws Exception if fail.
     */
    @Test
    public void testCreateRibbonEdgeBrokenData() throws Exception {
        Genome genome = new Genome("broken");
        RibbonEdge edge = RibbonEdgeFactory.createRibbonEdge(0, 1, genome);

        assertEquals(edge.getStart(),0);
        assertEquals(edge.getEnd(),1);
        assertEquals(edge.getColor(), new Color(100,100,100));

    }
}