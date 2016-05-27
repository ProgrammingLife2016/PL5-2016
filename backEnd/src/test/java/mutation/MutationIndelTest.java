package mutation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

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
	private ArrayList<String> reference;
	private ArrayList<String> other;
	private Strand start;
	private Strand end;
	private ArrayList<Strand> mutatedStrands;
	
	/**
	 * Setup the MutationIndel object.
	 */
	@Before
	public void setUp() {
		reference = new ArrayList<>(Arrays.asList("Genome1"));
		other = new ArrayList<>(Arrays.asList("Genome2"));
		start = Mockito.mock(Strand.class);
		end = Mockito.mock(Strand.class);
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
		assertEquals(mutation.getOtherGenomes(), other);
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