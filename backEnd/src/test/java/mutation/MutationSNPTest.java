package mutation;

import ribbonnodes.RibbonNode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * 
 * @author Jeffrey Helgers
 * Test the MutationSNP class.
 */
public class MutationSNPTest {

	private MutationSNP mutation;
	private HashSet<String> reference;
	private HashSet<String> other;
	private RibbonNode start;
	private RibbonNode end;
	private RibbonNode mutateReference;
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
