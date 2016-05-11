package mutation;

import java.util.ArrayList;

import genome.Genome;
import genome.Node;

/**
 * 
 * @author Jeffrey Helgers.
 * This class creates deletion mutations.
 */
public class Deletion extends Mutation {

	private ArrayList<Node> missingNodes;
	
	/**
	 * Constructor to create a deletion.
	 * @param reference The reference genome.
	 * @param other The other genome.
	 * @param missingNodes The missing nodes.
	 */
	public Deletion(Genome reference, Genome other, ArrayList<Node> missingNodes) {
		super(reference, other);
		this.missingNodes = missingNodes;
	}
	
	/**
	 * Get the missing nodes.
	 * @return The missing nodes.
	 */
	public ArrayList<Node> getMissingNodes() {
		return missingNodes;
	}

}
