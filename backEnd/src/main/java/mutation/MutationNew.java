package mutation;

import java.util.ArrayList;

import genome.Genome;
import genome.Strand;

/**
 * 
 * @author Jefrrey Helgers
 * Interface for mutations, the frontend can use this interface.
 */
public abstract class MutationNew {
	
	private Genome reference;
	private Genome other;
	private Strand start;
	private Strand end;
	private MutationType mutationType;
	private ArrayList<Strand> mutatedStrands;
	
	/**
	 * Create a mutation.
	 * @param mutationType		The type of mutation.
	 * @param reference			The reference Genome.
	 * @param other				The other Genome.
	 * @param start				The start Strand.
	 * @param end				The end Strand, if any.
	 * @param mutatedStrands	The mutated Strands.
	 */
	public MutationNew(MutationType mutationType, Genome reference, Genome other,
			Strand start, Strand end, ArrayList<Strand> mutatedStrands) {
		this.mutationType = mutationType;
		this.reference = reference;
		this.other = other;
		this.start = start;
		if (mutationType.getBetween()) {
			this.end = end;
		} else {
			String[] temp = {};
			this.end = new Strand(0, "", temp, "", 0);
		}
		this.mutatedStrands = mutatedStrands;
	}
	
	
	/**
	 * Get the reference genome.
	 * @return The reference.
	 */
	public Genome getReferenceGenome() {
		return reference;
	}
	
	/**
	 * Get the other genome.
	 * @return The other genome.
	 */
	public Genome getOtherGenome() {
		return other;
	}
	
	/**
	 * Get the start Strand from where the mutation starts.
	 * @return The start Strand.
	 */
	public Strand getStartStrand() {
		return start;
	}
	
	/**
	 * Get the end Strand, if this mutation has one.
	 * @return The end Strand.
	 */
	public Strand getEndStrand() {
		return end;
	}
	
	/**
	 * Get the type of the mutation, an enum.
	 * @return The type.
	 */
	public MutationType getMutationType() {
		return mutationType;
	}
	
	/**
	 * Get the Strands that are mutated by this mutation.
	 * @return Mutated Strands.
	 */
	public ArrayList<Strand> getMutatedStrands() {
		return mutatedStrands;
	}
	
}
