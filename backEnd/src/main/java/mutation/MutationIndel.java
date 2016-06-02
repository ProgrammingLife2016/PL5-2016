package mutation;

import genome.Strand;

import java.util.ArrayList;

/**
 * 
 * @author Jeffrey Helgers.
 * Class to create an indel mutation.
 */
public class MutationIndel extends AbstractMutation {

	/**
	 * Constructor to create the mutation.
	 * Create a mutation.
	 * @param mutationType		The type of mutation.
	 * @param reference			The reference Genome.
	 * @param other				The other Genome.
	 * @param start				The start Strand.
	 * @param end				The end Strand.
	 * @param mutatedStrands	The mutated Strands.
	 */
	public MutationIndel(MutationType mutationType, ArrayList<String> reference, 
			ArrayList<String> other, Strand start, Strand end, ArrayList<Strand> mutatedStrands) {
		super(mutationType, reference, other, start);
		if (mutationType.getBetween() && !mutationType.equals(MutationType.SNP)) {
			setEndStrand(end);
			setMutatedStrands(mutatedStrands);
		} else {
			throw new UnsupportedOperationException();
		}
	}

}
