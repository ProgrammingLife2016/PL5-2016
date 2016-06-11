package mutation;

import genome.Strand;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Jefrrey Helgers
 *         Abstract class for mutations, the frontend can use this interface.
 */
public abstract class AbstractMutation {
	
	/**
	 * The genomes in the reference.
	 */
	private HashSet<String> reference;
	
	/**
	 * The genomes in the other.
	 */
	private HashSet<String> other;
	
	/**
	 * The start strand.
	 */
	private Strand start;
	
	/**
	 * The mutation type.
	 */
	private MutationType mutationType;
	
	/**
	 * The strands that are mutated.
	 */
	private ArrayList<Strand> mutatedStrands;
	
	/**
	 * The end strand if there is any.
	 */
	private Strand end;
	
	/**
	 * Create a mutation.
	 * @param mutationType		The type of mutation.
	 * @param reference2			The reference Genome.
	 * @param other2				The other Genome.
	 * @param start				The start Strand.
	 */
	public AbstractMutation(MutationType mutationType, HashSet<String> reference2, 
			HashSet<String> other2, Strand start) {
		this.mutationType = mutationType;
		this.reference = reference2;
		this.other = other2;
		this.start = start;
		this.mutatedStrands = new ArrayList<>();
	}
	
	
	/**
	 * Get the reference genome.
	 * @return The reference.
	 */
	public HashSet<String> getReferenceGenomes() {
		return reference;
	}
	
	/**
	 * Get the other genome.
	 * @return The other genome.
	 */
	public HashSet<String> getOtherGenomes() {
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
	
	/**
	 * Get the mutation as a string.
	 * @return The resulting string.
	 */
	public String toString() {
		return mutationType.toString();
	}
}
