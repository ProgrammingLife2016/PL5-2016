package mutation;

import java.util.ArrayList;

import genome.Genome;
import genome.Strand;

/**
 * 
 * @author Jeffrey Helgers.
 * Class to create a SNP mutation.
 */
public class MutationSNP extends AbstractMutation {

	/**
	 * Constructor to create the mutation.
	 * @param mutationType		The type of mutation.
	 * @param reference			The reference Genome.
	 * @param other				The other Genome.
	 * @param start				The start Strand.
	 * @param end				The end Strand.
	 * @param fromReference		The changed Strand form the reference Genome.
	 * @param fromOther			The changed Strand form the other Genome.
	 */
	public MutationSNP(MutationType mutationType, ArrayList<String> reference, 
			ArrayList<String> other, Strand start, Strand end, 
			Strand fromReference, Strand fromOther) {
		super(mutationType, reference, other, start);
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
