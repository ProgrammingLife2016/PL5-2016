package mutation;

import genome.Genome;

/**
 * 
 * @author Jeffrey Helgers
 * Abstract class the instantiates the mutations.
 */
public abstract class Mutation {

	private Genome reference;
	private Genome other;
	
	/**
	 * Constructor to create a mutation.
	 * @param reference The reference genome.
	 * @param other The other genome.
	 */
	public Mutation(Genome reference, Genome other) {
		this.reference = reference;
	}
	
	/**
	 * Get the reference genome.
	 * @return The reference.
	 */
	public Genome getReference() {
		return reference;
	}
	
	/**
	 * Get the other genome.
	 * @return The other.
	 */
	public Genome getOther() {
		return other;
	}
}
