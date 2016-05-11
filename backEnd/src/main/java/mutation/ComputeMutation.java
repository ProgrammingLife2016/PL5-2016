package mutation;

import java.util.ArrayList;

import genome.Genome;
import genome.Node;

/**
 * 
 * @author Jeffrey Helgers
 * This class computes the mutations between two genomes.
 */
public final class ComputeMutation {

	private static Genome base;
	private static Genome other;
	private static ArrayList<Node> baseNodes;
	private static ArrayList<Node> otherNodes;
	private static ArrayList<Node> common;
	private static AllMutations mutations;
	
	/**
	 * Not used constructor.
	 */
	private ComputeMutation() {
		
	}
	
	/**
	 * Compute all the mutations betwee two genomes.
	 * @param base1 Reference genome.
	 * @param other1 Compared genome.
	 * @param mutations1 That stores the mutations.
	 */
	public static void compute(Genome base1, Genome other1, AllMutations mutations1) {
		base = base1;
		other = other1;
		baseNodes = base.getNodes();
		otherNodes = other.getNodes();
		common = new ArrayList<Node>(baseNodes);
		common.retainAll(otherNodes);
		mutations = mutations1;
		allMutations();
	}
	
	/**
	 * Computes all the mutations between the two genomes.
	 */
	private static void allMutations() {
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
	private static boolean getMutation(int basePlace, int otherPlace) {
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
	private static boolean testInsertion(int basePlace) {
		if (common.contains(baseNodes.get(basePlace + 1))) {
			System.out.println("insertion");
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
	private static boolean testDeletion(int basePlace, int otherPlace) {
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
