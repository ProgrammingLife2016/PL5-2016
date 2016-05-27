package mutation;

import java.util.ArrayList;

import genome.Genome;
import genome.Strand;

/**
 * 
 * @author Jeffrey Helgers.
 * Class to create a mutation which is not located between two genomes.
 */
public class MutationOther extends AbstractMutation {

	/**
	 * Constructor to create the mutation.
	 * Create a mutation.
	 * @param mutationType		The type of mutation.
	 * @param reference			The reference Genome.
	 * @param other				The other Genome.
	 * @param start				The start Strand.
	 * @param mutatedStrands	The mutated Strands.
	 */
	public MutationOther(MutationType mutationType, ArrayList<String> reference,
			ArrayList<String> other, Strand start, ArrayList<Strand> mutatedStrands) {
		super(mutationType, reference, other, start);
		if (!mutationType.getBetween()) {
			setMutatedStrands(mutatedStrands);
		} else {
			throw new UnsupportedOperationException();
		}
	}

}
