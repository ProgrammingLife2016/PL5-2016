package mutation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import genome.Genome;
import genome.Strand;

/**
 * 
 * @author Jeffrey Helgers
 * Test the MutationSNP class.
 */
public class MutationSNPTest {

	private MutationSNP mutation;
	private Genome reference;
	private Genome other;
	private Strand start;
	private Strand end;
	private Strand mutateReference;
	private Strand mutateOther;
	
	/**
	 * Setup the MutationSNP object.
	 */
	@Before
	public void setUp() {
		reference = Mockito.mock(Genome.class);
		other = Mockito.mock(Genome.class);
		start = Mockito.mock(Strand.class);
		end = Mockito.mock(Strand.class);
		mutateReference = Mockito.mock(Strand.class);
		mutateOther = Mockito.mock(Strand.class);
		mutation = new MutationSNP(MutationType.SNP, reference, 
				other, start, end, mutateReference, mutateOther);
	}

	/**
	 * Test getting the start Strand.
	 */
	@Test
	public void testGetStart() {
		assertEquals(mutation.getStartStrand(), start);
	}
	
	/**
	 * Test getting the end Strand.
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
