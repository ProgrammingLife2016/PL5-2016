package genome;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

/**
 * A factory for creating Genome objects.
 */
final class GenomeFactory {

	/** The current branchpoints. */
	private static HashMap<GfBranch, List<GfBranch>> currentBranchpoints = new HashMap<GfBranch, List<GfBranch>>();
	private static List<GfBranch> removedBranchpoints = new ArrayList<GfBranch>();
	private static HashMap<GfBranch, List<GfBranch>> newBranchpoints = new HashMap<GfBranch, List<GfBranch>>();
	/** The finished branches. */
	private static List<GfBranch> finishedBranches = new ArrayList<GfBranch>();

	/** The genome id. */
	private static String genomeId;

	/** The main branch. */
	private static GfBranch mainBranch = new GfBranch();

	/** The strands seen. */
	private static HashMap<Strand, GfBranch> strandsSeen = new HashMap<Strand, GfBranch>();

	/**
	 * Instantiates a new genome factory.
	 */
	private GenomeFactory() {
	}

	/**
	 * Derive genome.
	 *
	 * @param strand
	 *            the strand
	 * @param genomeId
	 *            the genome id
	 * @return the genome
	 */
	public static Genome deriveGenome(Strand strand, String genomeId) {

		GenomeFactory.genomeId = genomeId;
		Genome genome = new Genome(genomeId);
		genome.setStrands(computeStrands(strand));
		return genome;
	}

	/**
	 * Compute strands.
	 *
	 * @param strand
	 *            the strand
	 * @return the array list
	 */
	private static ArrayList<Strand> computeStrands(Strand strand) {

		mainBranch.addLast(strand);
		while (!currentBranchpoints.isEmpty() || !finishedBranches.contains(mainBranch)) {
			System.out.println(mainBranch.toString() + " " + currentBranchpoints);
			if (!currentBranchpoints.isEmpty()) {

				for (GfBranch currentBranchpoint : currentBranchpoints.keySet()) {
					List<GfBranch> currentBranches = currentBranchpoints.get(currentBranchpoint);
					for (GfBranch currentBranch : currentBranches) {
						makeStep(currentBranch);
					}

					currentBranches.removeAll(finishedBranches);
					finishedBranches.clear();
					if (currentBranches.size() == 1) {
						currentBranches.get(0).prepend(currentBranchpoint);
						removedBranchpoints.add(currentBranchpoint);
					}
				}
			} else {
				makeStep(mainBranch);
			}
			currentBranchpoints.putAll(newBranchpoints);
			currentBranchpoints.keySet().removeAll(removedBranchpoints);
			newBranchpoints.clear();
			removedBranchpoints.clear();
		}

		return mainBranch.getBranchAsArrayList();
	}

	/**
	 * Make step.
	 *
	 * @param currentBranch
	 *            the current branch
	 */
	private static void makeStep(GfBranch currentBranch) {

		Strand currentStrand = currentBranch.getLast();
		List<Strand> nextStrands = currentStrand.getNextStrandsWith(genomeId);

		if (nextStrands.size() > 1) {
			List<GfBranch> newBranches = new ArrayList<GfBranch>();
			for (Strand nextStrand : nextStrands) {
				GfBranch newBranch = new GfBranch();
				newBranches.add(newBranch);
				concatOrAdd(newBranch, nextStrand);
			}
			newBranches.removeAll(finishedBranches);
			newBranchpoints.put(currentBranch, newBranches);
		} else if (nextStrands.size() == 1) {
			Strand nextStrand = nextStrands.get(0);
			concatOrAdd(currentBranch, nextStrand);
		} else {
			finishedBranches.add(currentBranch);
		}

	}

	/**
	 * Concat or add.
	 *
	 * @param newBranch
	 *            the current branch
	 * @param nextStrand
	 *            the next strand
	 */
	private static void concatOrAdd(GfBranch currentBranch, Strand nextStrand) {
		GfBranch otherBranch = strandsSeen.get(nextStrand);
		if (otherBranch == null) {
			currentBranch.addLast(nextStrand);
			strandsSeen.put(nextStrand, currentBranch);
		} else {
			otherBranch.prepend(currentBranch);
			finishedBranches.add(currentBranch);
			mainBranch = otherBranch;
		}

	}

}
