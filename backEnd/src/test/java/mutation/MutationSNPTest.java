package mutation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ribbonnodes.RibbonNode;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * @author Jeffrey Helgers
 *         Test the MutationSNP class.
 */
public class MutationSNPTest {

	/**
	 * The tested mutation.
	 */
    private MutationSNP mutation;
    
    /**
     * The reference genomeIds.
     */
    private HashSet<String> reference;
    
    /**
     * The other genomeIds.
     */
    private HashSet<String> other;
    
    /**
     * The start node.
     */
    private RibbonNode start;
    
    /**
     * The end node.
     */
    private RibbonNode end;
    
    /**
     * The mutated node from the reference ids.
     */
    private RibbonNode mutateReference;
    
    /**
     * The mutated node from the other ids.
     */
    private RibbonNode mutateOther;

    /**
     * Setup the MutationSNP object.
     */
    @Before
    public void setUp() {
        reference = new HashSet<String>(Arrays.asList("Genome1"));
        other = new HashSet<String>(Arrays.asList("Genome2"));
        start = Mockito.mock(RibbonNode.class);
        end = Mockito.mock(RibbonNode.class);
        mutateReference = Mockito.mock(RibbonNode.class);
        mutateOther = Mockito.mock(RibbonNode.class);
        mutation = new MutationSNP(MutationType.SNP, reference,
                other, start, end, mutateReference, mutateOther);
    }

    /**
     * Test getting the start RibbonNode.
     */
    @Test
    public void testGetStart() {
        assertEquals(mutation.getStartStrand(), start);
    }

    /**
     * Test getting the end RibbonNode.
     */
    @Test
    public void testGetEnd() {
        assertEquals(mutation.getEndStrand(), end);
    }

    /**
     * Test creating a SNP mutation with an inversion.
     * This will throw an exception.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testWrongType1() {
        new MutationSNP(MutationType.INVERSION, reference, other,
                start, end, mutateReference, mutateOther);
    }
}
