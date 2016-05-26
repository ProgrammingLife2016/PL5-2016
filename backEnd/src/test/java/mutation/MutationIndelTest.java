package mutation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import genome.Genome;
import genome.Strand;

/**
 * 
 * @author Jeffrey Helgers.
 * Test the MutationIndel class.
 */
public class MutationIndelTest {

	private MutationIndel mutation;
	private Genome reference;
	private Genome other;
	private Strand start;
	private Strand end;
	private ArrayList<Strand> mutatedStrands;
	
	/**
	 * Setup the MutationIndel object.
	 */
	@Before
	public void setUp() {
		reference = Mockito.mock(Genome.class);
		other = Mockito.mock(Genome.class);
		Strand start = Mockito.mock(Strand.class);
		Strand end = Mockito.mock(Strand.class);
		Strand mutate = Mockito.mock(Strand.class);
		mutatedStrands = new ArrayList<>();
		mutatedStrands.add(mutate);
		mutation = new MutationIndel(MutationType.DELETION, 
				reference, other, start, end, mutatedStrands);
	}

	/**
	 * Test getting the reference genome.
	 */
	@Test
	public void testGetReference() {
		assertEquals(mutation.getReferenceGenome(), reference);
	}
	
	/**
	 * Test getting the other genome.
	 */
	@Test
	public void testGetOther() {
		assertEquals(mutation.getOtherGenome(), other);
	}
	
	/**
	 * Test creating an Indel mutation with a translocation.
	 * This will throw an exception.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testWrongType() {
		new MutationIndel(MutationType.TRANSLOCATION, reference, 
				other, start, end, mutatedStrands);
	}

}