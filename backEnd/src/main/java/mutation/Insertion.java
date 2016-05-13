package mutation;

import genome.Genome;
import genome.Strand;

/**
 * 
 * @author Jeffrey Helgers.
 * This class creates inserion mutations.
 */
public class Insertion extends Mutation {

	private Strand start;
	private Strand end;
	
	/**
	 * Constructor to create an insertion. 
	 * @param reference The reference genome.
	 * @param other The other genome.
	 * @param start The Strand which is followed by an insertion.
	 * @param end The Strand that ends the insertion.
	 */
	public Insertion(Genome reference, Genome other, Strand start, Strand end) {
		super(reference, other);
		this.start = start;
		this.end = end;
	}

	/**
	 * Get the start Strand.
	 * @return Start Strand.
	 */
	public Strand getStart() {
		return start;
	}
	
	/**
	 * Get the end Strand.
	 * @return end Strand.
	 */
	public Strand getEnd() {
		return end;
	}
}
