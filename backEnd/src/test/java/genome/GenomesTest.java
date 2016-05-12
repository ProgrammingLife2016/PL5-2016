package genome;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;


/**
 * @author Jeffrey Helgers.
 */
public class GenomesTest {

    private Genome genome;

    /**
     * Setting up the genome to test with.
     */
    @Before
    public void setUp() {
        genome = new Genome("test");
    }

    /**
     * Tests adding nodes to the genome.
     */
    @Test
    public void testAddNode() {
        String[] genomes = {"ref1", "ref2"};
        Strand strand = new Strand(1, "AA", genomes, "ref1", 0);
        ArrayList<Strand> res = new ArrayList<Strand>();
        assertEquals(genome.getStrands(), res);
        res.add(strand);
        genome.addNode(strand);
        assertEquals(genome.getStrands(), res);
    }
}

