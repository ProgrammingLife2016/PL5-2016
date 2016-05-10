package mutation;

import java.util.ArrayList;

import genome.Genome;
import genome.Node;

/**
 * 
 * @author Jeffrey Helgers
 * This class computes the mutations between two genomes.
 */
public class ComputeMutation {

	private Genome base;
	private Genome other;
	private ArrayList<Node> baseNodes;
	private ArrayList<Node> otherNodes;
	private ArrayList<Node> common;
	private AllMutations mutations;
	
	/**
	 * Constructor to create mutations.
	 * @param base Reference genome.
	 * @param other Compared genome.
	 */
	public ComputeMutation(Genome base, Genome other, AllMutations mutations) {
		this.base = base;
		this.other = other;
		baseNodes = base.getNodes();
		otherNodes = other.getNodes();
		common = new ArrayList<Node>(baseNodes);
		common.retainAll(otherNodes);
		this.mutations = mutations;
	}
	
	/**
	 * Computes all the mutations between the two genomes.
	 */
	public void allMutations() {
		for (int i = 0; i < common.size(); i++) {
			Node current = common.get(i);
			int basePlace = baseNodes.indexOf(current);
			int otherPlace = otherNodes.indexOf(current);
			getMutation(basePlace, otherPlace);
		}
	}
	
	/**
	 * First tests if the next genome in both the genomes is the same.
	 * If not, then there can be a mutation.
	 * @param basePlace Starting position at the base genome.
	 * @param otherPlace Starting position at the other genome.
	 * @return
	 */
	public boolean getMutation(int basePlace, int otherPlace) {
		if (basePlace < (baseNodes.size() - 1) && otherPlace < (otherNodes.size() - 1)) {
			if (baseNodes.get(basePlace + 1).equals(otherNodes.get(otherPlace + 1))) {
				return false;
			} else {
				return (testInsertion(basePlace)
						|| testDeletion(basePlace, otherPlace));
			}
		}
		return false;
	}
	
	/**
	 * Checks if an insertion appeared.
	 * @param basePlace Starting position at the base genome.
	 * @return
	 */
	public boolean testInsertion(int basePlace) {
		if (common.contains(baseNodes.get(basePlace + 1))) {
			System.out.println("insertion");
			baseNodes.get(basePlace).setInsertion(true);
			Insertion in = new Insertion(base, other, 
					baseNodes.get(basePlace), baseNodes.get(basePlace + 1));
			mutations.addMutation(base.getId(), in);
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if a deletion appeared.
	 * @param basePlace Starting position at the base genome.
	 * @param otherPlace Starting position at the other genome.
	 * @return
	 */
	public boolean testDeletion(int basePlace, int otherPlace) {
		if (common.contains(otherNodes.get(otherPlace + 1))) {
			basePlace++;
			ArrayList<Node> deleted = new ArrayList<>();
			while (!baseNodes.get(basePlace).equals(otherNodes.get(otherPlace + 1))) {
				System.out.println("deletion");
				deleted.add(baseNodes.get(basePlace));
				basePlace++;
			}
			Deletion de = new Deletion(base, other, deleted);
			mutations.addMutation(base.getId(), de);
			return true;
		}
		return false;
	}
}
