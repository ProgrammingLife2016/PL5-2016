package mutation;

import genome.Genome;
import genome.Node;

/**
 * 
 * @author Jeffrey Helgers.
 * This class creates inserion mutations.
 */
public class Insertion extends Mutation {

	private Node start;
	private Node end;
	
	/**
	 * Constructor to create an insertion. 
	 * @param reference The reference genome.
	 * @param other The other genome.
	 * @param start The node which is followed by an insertion.
	 * @param end The node that ends the insertion.
	 */
	public Insertion(Genome reference, Genome other, Node start, Node end) {
		super(reference, other);
		this.start = start;
		this.end = end;
	}

	/**
	 * Get the start node.
	 * @return Start node.
	 */
	public Node getStart() {
		return start;
	}
	
	/**
	 * Get the end node.
	 * @return end node.
	 */
	public Node getEnd() {
		return end;
	}
}
