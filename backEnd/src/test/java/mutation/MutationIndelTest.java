package mutation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ribbonnodes.RibbonNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * @author Jeffrey Helgers.
 *         Test the MutationIndel class.
 */
public class MutationIndelTest {

	/**
	 * The tested mutation.
	 */
    private MutationIndel mutation;
    
    /**
     * The reference genomeIds.
     */
    private HashSet<String> reference;
    
    /**
     * The other genomeIds.
     */
    private HashSet<String> other;
    
    /**
     * The strart node.
     */
    private RibbonNode start;
    
    /**
     * The end node.
     */
    private RibbonNode end;
    
    /**
     * The nodes that are mutated.
     */
    private ArrayList<RibbonNode> mutatedRibbonNodes;

    /**
     * Setup the MutationIndel object.
     */
    @Before
    public void setUp() {
        reference = new HashSet<String>(Arrays.asList("Genome1"));
        other = new HashSet<String>(Arrays.asList("Genome2"));
        start = Mockito.mock(RibbonNode.class);
        end = Mockito.mock(RibbonNode.class);
        RibbonNode mutate = Mockito.mock(RibbonNode.class);
        mutatedRibbonNodes = new ArrayList<>();
        mutatedRibbonNodes.add(mutate);
        mutation = new MutationIndel(MutationType.INDEL,
                reference, other, start, end, mutatedRibbonNodes);
    }

    /**
     * Test getting the reference genome.
     */
    @Test
    public void testGetReference() {
        assertEquals(mutation.getReferenceGenomes(), reference);
    }

    /**
     * Test getting the other genome.
     */
    @Test
    public void testGetOther() {
        assertEquals(mutation.getOtherGenomes(), other);
    }

    /**
     * Test creating an Indel mutation with a translocation.
     * This will throw an exception.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testWrongType() {
        new MutationIndel(MutationType.TRANSLOCATION, reference,
                other, start, end, mutatedRibbonNodes);
    }

    /**
     * Test getting the mutation as a String.
     */
    @Test
    public void testToString() {
        assertEquals(mutation.toString(), MutationType.INDEL.toString());
    }

}