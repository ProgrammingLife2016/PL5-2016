package mutation;

import ribbonnodes.RibbonNode;

import java.util.ArrayList;
import java.util.HashSet;

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
	public MutationIndel(MutationType mutationType, HashSet<String> reference, 
			HashSet<String> other, RibbonNode start, RibbonNode end, 
			ArrayList<RibbonNode> mutatedStrands) {
		super(mutationType, reference, other, start);
		if (mutationType.getBetween() && !mutationType.equals(MutationType.SNP)) {
			setEndStrand(end);
			setMutatedStrands(mutatedStrands);
		} else {
			throw new UnsupportedOperationException();
		}
	}

}
