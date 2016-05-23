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
        genome = new Genome("GenomeIDTest");
    }

    /**
     * Tests adding nodes to the genome.
     */
    @Test
    public void test() {
        ArrayList<Strand> res = new ArrayList<Strand>();
        assertEquals(genome.getStrands(), res);
        String[] genomes = {"ref1", "ref2"};
        Strand strand = new Strand(1, "AA", genomes, "ref1", 0);
        res.add(strand);
        genome.addStrand(strand);
        assertEquals(genome.getStrands(), res);
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
        genome.addStrand(strand);
        assertEquals(genome.getStrands(), res);
    }

    /**
     * Test the getter of id.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetId() throws Exception {
        assertEquals(genome.getId(), "GenomeIDTest");
    }

    /**
     * Test the setter of id.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testSetId() throws Exception {
        assertEquals(genome.getId(), "GenomeIDTest");
        genome.setId("testId");
        assertEquals(genome.getId(), "testId");

    }
}

