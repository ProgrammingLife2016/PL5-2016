package mutation;

import genome.Strand;

import java.util.ArrayList;

/**
 * 
 * @author Jefrrey Helgers
 * Interface for mutations, the frontend can use this interface.
 */
public abstract class AbstractMutation {
	
	private ArrayList<String> reference;
	private ArrayList<String> other;
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
	 */
	public AbstractMutation(MutationType mutationType, ArrayList<String> reference, 
			ArrayList<String> other, Strand start) {
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
	public ArrayList<String> getReferenceGenome() {
		return reference;
	}
	
	/**
	 * Get the other genome.
	 * @return The other genome.
	 */
	public ArrayList<String> getOtherGenomes() {
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
	 * @param end The new end Strand.
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
	
	public String toString() {
		return mutationType.toString();
	}
	
}
