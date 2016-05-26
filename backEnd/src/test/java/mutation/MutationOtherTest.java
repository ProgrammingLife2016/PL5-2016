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
 * Test the MutationOther class.
 */
public class MutationOtherTest {

	private MutationOther mutation;
	private MutationType type;
	private Genome reference;
	private Genome other;
	private Strand start;
	private ArrayList<Strand> mutatedStrands;
	
	/**
	 * Setup the MutationOther object.
	 */
	@Before
	public void setUp() {
		reference = Mockito.mock(Genome.class);
		other = Mockito.mock(Genome.class);
		start = Mockito.mock(Strand.class);
		Strand mutate = Mockito.mock(Strand.class);
		mutatedStrands = new ArrayList<>();
		mutatedStrands.add(mutate);
		type = MutationType.INTERSPERSEDDUPLICATION;
		mutation = new MutationOther(type, reference, other, start, mutatedStrands);
	}

	/**
	 * Test getting the mutated Strands.
	 */
	@Test
	public void testGetMutatedStrands() {
		assertEquals(mutation.getMutatedStrands(), mutatedStrands);
	}
	
	/**
	 * Test getting the end Strand.
	 * This Strand does not appear in the other mutations, so it throws an exception.
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
		new MutationOther(MutationType.INSERTION, reference, 
				other, start, mutatedStrands);
	}

}