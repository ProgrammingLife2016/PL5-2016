package mutation;

import java.util.ArrayList;

import genome.Genome;
import genome.Strand;

/**
 * 
 * @author Jefrrey Helgers
 * Interface for mutations, the frontend can use this interface.
 */
public abstract class AbstractMutation {
	
	private Genome reference;
	private Genome other;
	private Strand start;
	private MutationType mutationType;
	private ArrayList<Strand> mutatedStrands;
	private Strand end;
	
	/**
	 * Create a mutation.
	 * @param mutationType		The type of mutation.
	 * @param reference			The reference Genome.
	 * @param other				The other Genome.
	 * @param start				The start Strand.
	 * @param mutatedStrands	The mutated Strands.
	 */
	public AbstractMutation(MutationType mutationType, Genome reference, Genome other,
							Strand start) {
		this.mutationType = mutationType;
		this.reference = reference;
		this.other = other;
		this.start = start;
		this.mutatedStrands = new ArrayList<>();
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
	 * Get the end Strand from where the mutation starts.
	 * @return The end Strand.
	 */
	public Strand getEndStrand() {
		if (mutationType.getBetween()) {
			return end;
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * Set the end Strand from where the mutation starts.
	 * @return The end Strand.
	 */
	public void setEndStrand(Strand end) {
		this.end = end;
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
	
	/**
	 * Set the mutated Strands.
	 * @param mutatedStrands Mutated strands.
	 */
	public void setMutatedStrands(ArrayList<Strand> mutatedStrands) {
		this.mutatedStrands = mutatedStrands;
	}
	
}
