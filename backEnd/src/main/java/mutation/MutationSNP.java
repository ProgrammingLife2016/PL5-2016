package mutation;

import genome.Strand;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * 
 * @author Jeffrey Helgers.
 * Class to create a SNP mutation.
 */
public class MutationSNP extends AbstractMutation {

	/**
	 * Constructor to create the mutation.
	 * @param mutationType		The type of mutation.
	 * @param hashSet			The reference Genome.
	 * @param hashSet2				The other Genome.
	 * @param start				The start Strand.
	 * @param end				The end Strand.
	 * @param fromReference		The changed Strand form the reference Genome.
	 * @param fromOther			The changed Strand form the other Genome.
	 */
	public MutationSNP(MutationType mutationType, HashSet<String> hashSet, 
			HashSet<String> hashSet2, Strand start, Strand end, 
			Strand fromReference, Strand fromOther) {
		super(mutationType, hashSet, hashSet2, start);
		if (mutationType.equals(MutationType.SNP)) {
			ArrayList<Strand> mutatedStrands = new ArrayList<>();
			mutatedStrands.add(fromReference);
			mutatedStrands.add(fromOther);
			setMutatedStrands(mutatedStrands);
			setEndStrand(end);
		} else {
	        throw new UnsupportedOperationException();
		}
	}
}
