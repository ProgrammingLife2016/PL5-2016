package mutation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import datatree.DataTree;

/**
 * This class is a utility class that computes the convergence levels. It computes and 
 * sets the convergence map for a mutation. This map has key value pairs where the key is the
 * genome for which possible convergent evolution has been detected and a convergence level as
 * value. The higher this value the higher the chance that this mutation was due to convergent
 * evolution for that genome.
 */
public final class ConvergenceIndicator {

	/**
	 * Instantiates a new convergence indicator.
	 */
	private ConvergenceIndicator() { }
	
	/**
	 * Computes the convergence levels for the mutation using the DataTree.
	 *
	 * @param mutation the mutation
	 * @param tree the tree
	 */
	public static void computeConvergenceLevels(AbstractMutation mutation, DataTree tree) {
		try {
			List<String> genomeIDs;
			if (!mutation.getOtherGenomes().isEmpty()) {
				genomeIDs = new ArrayList<String>(mutation.getOtherGenomes());
			} else {
				genomeIDs = new ArrayList<String>(mutation.getReferenceGenomes());
			}

			if (genomeIDs.size() < 2) {
				return;
			}
			double[] averageDistanceArray = getAveragePatristicDistances(genomeIDs, tree);
			Map<String, Double> convergenceMap = getConvergenceMap(genomeIDs, averageDistanceArray);

			mutation.setConvergenceMap(convergenceMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Computes and returns the convergence map of genome ids as keys and convergence levels as 
	 * values. The convergence level is the distance in standard deviations of the average 
	 * patristic distance of the genome compared to the average patristic distances of the other 
	 * genomes.
	 *
	 * @param genomeIDs the genome ids
	 * @param averageDistanceArray the average distance array
	 * @return the convergence map
	 */
	private static Map<String, Double> getConvergenceMap(List<String> genomeIDs,
			double[] averageDistanceArray) {

		Map<String, Double> convergenceMap = new HashMap<String, Double>();
		DescriptiveStatistics stats = new DescriptiveStatistics(averageDistanceArray);
		Double standardDeviation = stats.getStandardDeviation();
		Double meanAvgDistance = stats.getMean();
		
		for (int i = 0; i < genomeIDs.size(); i++) {
			String genomeId = genomeIDs.get(i);
			double avgDistance = averageDistanceArray[i];
			double outlierness = Math.abs(avgDistance - meanAvgDistance) / standardDeviation;
			if (outlierness > 1.9) {
				convergenceMap.put(genomeId, avgDistance);
			}
		}
		return convergenceMap;
	}

	/**
	 * Gets the average patristic distances for each genome to all the other genomes.
	 *
	 * @param genomeIDs the genome i ds
	 * @param tree the tree
	 * @return the average patristic distances
	 */
	private static double[] getAveragePatristicDistances(List<String> genomeIDs, DataTree tree) {

		List<Integer> distanceSums = new ArrayList<Integer>(
				Collections.nCopies(genomeIDs.size(), 0));
		for (int i = 0; i < genomeIDs.size(); i++) {
			for (int j = i + 1; j < genomeIDs.size(); j++) {
				int d = tree.getPatristicDistance(genomeIDs.get(i), genomeIDs.get(j));
				distanceSums.set(i, distanceSums.get(i) + d);
				distanceSums.set(j, distanceSums.get(j) + d);
			}
		}
		
		int n = genomeIDs.size();
		Stream<Integer> streamOfSums = distanceSums.stream();
		double[] averageDistanceArray = streamOfSums.mapToDouble(i -> (double) i / n).toArray();
		return averageDistanceArray;
	}
}