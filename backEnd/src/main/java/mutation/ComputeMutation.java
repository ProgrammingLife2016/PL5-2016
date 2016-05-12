package mutation;

import java.util.ArrayList;

import genome.Genome;
import genome.Strand;

/**
 * 
 * @author Jeffrey Helgers
 * This class computes the mutations between two genomes.
 */
public final class ComputeMutation {

	private static Genome base;
	private static Genome other;
	private static ArrayList<Strand> baseStrands;
	private static ArrayList<Strand> otherStrands;
	private static ArrayList<Strand> common;
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
		baseStrands = base.getStrands();
		otherStrands = other.getStrands();
		common = new ArrayList<Strand>(baseStrands);
		common.retainAll(otherStrands);
		mutations = mutations1;
		allMutations();
	}
	
	/**
	 * Computes all the mutations between the two genomes.
	 */
	private static void allMutations() {
		for (int i = 0; i < common.size(); i++) {
			Strand current = common.get(i);
			int basePlace = baseStrands.indexOf(current);
			int otherPlace = otherStrands.indexOf(current);
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
	private static void getMutation(int basePlace, int otherPlace) {
		if (basePlace < (baseStrands.size() - 1) && otherPlace < (otherStrands.size() - 1)) {
			if (baseStrands.get(basePlace + 1).equals(otherStrands.get(otherPlace + 1))) {
			} else {
				if (!testInsertion(basePlace)) {
					testDeletion(basePlace, otherPlace);
				}
			}
		}
	}
	
	/**
	 * Checks if an insertion appeared.
	 * @param basePlace Starting position at the base genome.
	 * @return
	 */
	private static boolean testInsertion(int basePlace) {
		if (common.contains(baseStrands.get(basePlace + 1))) {
			System.out.println("insertion");
			Insertion in = new Insertion(base, other, 
					baseStrands.get(basePlace), baseStrands.get(basePlace + 1));
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
	private static void testDeletion(int basePlace, int otherPlace) {
		if (common.contains(otherStrands.get(otherPlace + 1))) {
			basePlace++;
			ArrayList<Strand> deleted = new ArrayList<>();
			while (!baseStrands.get(basePlace).equals(otherStrands.get(otherPlace + 1))) {
				System.out.println("deletion");
				deleted.add(baseStrands.get(basePlace));
				basePlace++;
			}
			Deletion de = new Deletion(base, other, deleted);
			mutations.addMutation(base.getId(), de);
		}
	}
}
