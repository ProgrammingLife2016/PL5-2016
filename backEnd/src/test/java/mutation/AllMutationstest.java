package mutation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import controller.Controller;
import genome.Strand;
import org.junit.Before;
import org.junit.Test;

import genome.Genome;


/**
 * @author Jeffrey Helgers.
 *         Test the AllMutations and ComputeMutation classes.
 */
public class AllMutationstest {

    private Controller dc;
    private Genome g1;
    private Genome g2;
    private Strand n1;
    private Strand n2;
    private Strand n3;
    private Strand n4;
    private ArrayList<MutationWillBeDeleted> result;

    /**
     * Set up the Strands, gemomes and data container that are used.
     *
     * @throws Exception
     */
    @Before
    public void setUp() {
        dc = new Controller("data/TB10.gfa", "data/340tree.rooted.TKK.nwk");
        g1 = new Genome("ref1");
        g2 = new Genome("ref2");
        n1 = new Strand(1, "A", new String[1], "ref1", 1);
        n2 = new Strand(2, "C", new String[1], "ref2", 1);
        n3 = new Strand(3, "T", new String[1], "ref3", 1);
        n4 = new Strand(4, "G", new String[1], "ref4", 1);
        result = new ArrayList<>();
    }

    /**
     * Tets two genomes with no mutations.
     */
    @Test
    public void testNoMutation() {
        String[] common = {g1.getId(), g2.getId()};
        n1.setGenomes(common);
        n2.setGenomes(common);
        dc.addStrand(n1);
        dc.addStrand(n2);
        AllMutations mutations = new AllMutations(dc);
        result = mutations.getGenomeMutations(g1.getId());
        assertEquals(result.size(), 0);
    }

    /**
     * Test the insertion mutation.
     */
    @Test
    public void testInsertion() {
        String[] common = {g1.getId(), g2.getId()};
        String[] one = {g2.getId()};
        n1.setGenomes(common);
        n2.setGenomes(one);
        n3.setGenomes(one);
        n4.setGenomes(common);
        dc.addStrand(n1);
        dc.addStrand(n2);
        dc.addStrand(n3);
        dc.addStrand(n4);
        AllMutations mutations = new AllMutations(dc);
        result = mutations.getGenomeMutations(g1.getId());
        assertEquals(result.size(), 1);
        assertTrue(result.get(0) instanceof Insertion);
    }

    /**
     * Test the deletion mutation.
     */
    @Test
    public void testDeletion() {
        String[] common = {g1.getId(), g2.getId()};
        String[] one = {g2.getId()};
        n1.setGenomes(common);
        n2.setGenomes(one);
        n3.setGenomes(one);
        n4.setGenomes(common);
        dc.addStrand(n1);
        dc.addStrand(n2);
        dc.addStrand(n3);
        dc.addStrand(n4);
        AllMutations mutations = new AllMutations(dc);
        result = mutations.getGenomeMutations(g2.getId());
        assertEquals(result.size(), 1);
        assertTrue(result.get(0) instanceof Deletion);
    }

    /**
     * Test no mutations, but the genomes are different.
     */
    @Test
    public void testNoMutationDifferent() {
        String[] common = {g1.getId(), g2.getId()};
        String[] one = {g1.getId()};
        String[] two = {g2.getId()};
        n1.setGenomes(common);
        n2.setGenomes(one);
        n3.setGenomes(two);
        n4.setGenomes(common);
        dc.addStrand(n1);
        dc.addStrand(n2);
        dc.addStrand(n3);
        dc.addStrand(n4);
        AllMutations mutations = new AllMutations(dc);
        result = mutations.getGenomeMutations(g2.getId());
        assertEquals(result.size(), 0);
    }
}
