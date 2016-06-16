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
 *         Test the MutationOther class.
 */
public class MutationOtherTest {

    private MutationOther mutation;
    private MutationType type;
    private HashSet<String> reference;
    private HashSet<String> other;
    private RibbonNode start;
    private ArrayList<RibbonNode> mutatedRibbonNodes;

    /**
     * Setup the MutationOther object.
     */
    @Before
    public void setUp() {
        reference = new HashSet<String>(Arrays.asList("Genome1"));
        other = new HashSet<String>(Arrays.asList("Genome2"));
        start = Mockito.mock(RibbonNode.class);
        RibbonNode mutate = Mockito.mock(RibbonNode.class);
        mutatedRibbonNodes = new ArrayList<>();
        mutatedRibbonNodes.add(mutate);
        type = MutationType.INTERSPERSEDDUPLICATION;
        mutation = new MutationOther(type, reference, other, start, mutatedRibbonNodes);
    }

    /**
     * Test getting the mutated RibbonNodes.
     */
    @Test
    public void testGetMutatedRibbonNodes() {
        assertEquals(mutation.getMutatedStrands(), mutatedRibbonNodes);
    }

    /**
     * Test getting the end RibbonNode.
     * This RibbonNode does not appear in the other mutations, so it throws an exception.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testGetEnd() {
        mutation.getEndStrand();
    }

    /**
     * Test getting the mutation type.
     */
    @Test
    public void testGetMutationType() {
        assertEquals(mutation.getMutationType(), type);
    }

    /**
     * Test creating a MutationOther with a insertion.
     * This will throw an exception.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testWrongType() {
        new MutationOther(MutationType.INDEL, reference,
                other, start, mutatedRibbonNodes);
    }

}