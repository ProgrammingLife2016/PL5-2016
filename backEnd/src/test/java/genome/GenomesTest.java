package genome;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;


/**
 * Class to test the genomes.
 *
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
        HashSet<String> genomeSet = new HashSet<String>(Arrays.asList(genomes));
        Strand strand = new Strand(1, "AA", genomeSet, "ref1", 0);
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
        HashSet<String> genomeSet = new HashSet<String>(Arrays.asList(genomes));
        Strand strand = new Strand(1, "AA", genomeSet, "ref1", 0);
        ArrayList<Strand> res = new ArrayList<Strand>();
        assertEquals(genome.getStrands(), res);
        res.add(strand);
        genome.addStrand(strand);
        assertEquals(genome.getStrands(), res);
    }

    /**
     * Test the setter of id.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testSetId() throws Exception {
        assertEquals("GenomeIDTest", genome.getId());
        genome.setId("testId");
        assertEquals("testId", genome.getId());

    }

    /**
     * Test setting the x coordinates of the strands in the genome.
     */
    @Test
    public void testSetX() {
        Strand s = new Strand(0);
        s.setSequence("A");
        genome.setStrands(new ArrayList<>(Arrays.asList(s, new Strand(1))));
        genome.resetStrandX();
        assertEquals(genome.getStrands().get(1).getX(), 0);
        genome.setStrandsX();
        assertEquals(genome.getStrands().get(1).getX(), 1);
    }
}

