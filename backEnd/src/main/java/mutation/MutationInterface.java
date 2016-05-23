package mutation;

import java.util.ArrayList;

import genome.Genome;
import genome.Strand;

/**
 * 
 * @author Jefrrey Helgers
 * Interface for mutations, the frontend can use this interface.
 */
public interface MutationInterface {
	
	/**
	 * Get the reference genome.
	 * @return The reference.
	 */
	Genome getReferenceGenome();
	
	/**
	 * Get the other genome.
	 * @return The other genome.
	 */
	Genome getOtherGenome();
	
	/**
	 * Get the start Strand from where the mutation starts.
	 * @return The start Strand.
	 */
	Strand getStartStrand();
	
	/**
	 * Get the end Strand, if this mutation has one.
	 * @return The end Strand.
	 */
	Strand getEndStrand();
	
	/**
	 * Get the type of the mutation, an enum.
	 * @return The type.
	 */
	MutationType getType();
	
	/**
	 * Get the Strands that are mutated by this mutation.
	 * @return Mutated Strands.
	 */
	ArrayList<Strand> getMutatedStrands();
	
}
