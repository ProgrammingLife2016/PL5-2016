package genome;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * A factory for creating Genome objects.
 */
abstract class GenomeFactory {

	/** The current branchpoints. */
	private static LinkedHashMap<GfBranch, GfBranch> branches = new LinkedHashMap<GfBranch, GfBranch>();
	private static HashMap<GfBranch, GfBranch> newBranches = new HashMap<GfBranch, GfBranch>();
	private static HashMap<GfBranch, Integer> subBranchCountMap = new HashMap<GfBranch, Integer>();
	private static Iterator<GfBranch> it;

	/** The genome id. */
	private static String genomeId;

	/** The main branch. */
	private static GfBranch mainBranch = new GfBranch();

	/** The strands seen. */
	private static HashMap<Strand, GfBranch> strandsSeen = new HashMap<Strand, GfBranch>();

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
		branches.put(mainBranch, new GfBranch());
		while (!branches.isEmpty()) {


			//System.out.println(branches);
			it = branches.keySet().iterator();
			while (it.hasNext()) {
				makeStep(it);
			}

			branches.putAll(newBranches);
			newBranches.clear();
			clearMergedBranches();
		}
		return mainBranch.getBranchAsArrayList();
	}

	private static void clearMergedBranches() {
		HashSet<GfBranch> deletedBranches = new HashSet<GfBranch>();
		Iterator<Entry<GfBranch, GfBranch>> it = branches.entrySet().iterator();
		while(it.hasNext()) {
			Entry<GfBranch, GfBranch> entry = it.next();
			
			if (subBranchCountMap.containsKey(entry.getValue()) && 
					subBranchCountMap.get(entry.getValue()) == 1) {
				subBranchCountMap.remove(entry.getValue());
				entry.getKey().prepend(entry.getValue());
				deletedBranches.add(entry.getValue());
			}
		}
		for(GfBranch branch :  deletedBranches) {
			branches.remove(branch);
		}
	}

	/**
	 * Make step.
	 *
	 * @param it
	 *            the current branch
	 */
	private static void makeStep(Iterator<GfBranch> it) {
		GfBranch currentBranch = it.next();
		Strand currentStrand = currentBranch.getLast();
		System.out.println(currentStrand);
		List<Strand> nextStrands = currentStrand.getNextStrandsWith(genomeId);

		if (nextStrands.size() > 1) {
			for (Strand nextStrand : nextStrands) {
				GfBranch otherBranch = strandsSeen.get(nextStrand);
				if (otherBranch == null) {
					GfBranch newBranch = new GfBranch();
					appendStrand(newBranch, nextStrand);
					newBranches.put(newBranch, currentBranch);
					increaseSubBranchCount(currentBranch);
				}
			}
		} else if (nextStrands.size() == 1) {
			Strand nextStrand = nextStrands.get(0);
			GfBranch otherBranch = strandsSeen.get(nextStrand);
			if (otherBranch == null) {
				appendStrand(currentBranch, nextStrand);
			} else {
				otherBranch.prepend(currentBranch);
				decreaseSubBranchCount(branches.get(currentBranch));
				it.remove();
			}
		} else {
			if (branches.size() == 1) {
				mainBranch = branches.remove(branches.keySet().iterator().next());
			}
		}

	}

	private static void decreaseSubBranchCount(GfBranch branch) {
		if(subBranchCountMap.containsKey(branch)) {
			subBranchCountMap.put(branch, subBranchCountMap.get(branch) - 1);
			}
			else
			{
				subBranchCountMap.put(branch,1);
			}
		
	}

	private static void increaseSubBranchCount(GfBranch branch) {
		if(subBranchCountMap.containsKey(branch)) {
		subBranchCountMap.put(branch, subBranchCountMap.get(branch) + 1);
		}
		else
		{
			subBranchCountMap.put(branch,1);
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
	private static void appendStrand(GfBranch currentBranch, Strand nextStrand) {

		currentBranch.addLast(nextStrand);
		strandsSeen.put(nextStrand, currentBranch);

	}

}
