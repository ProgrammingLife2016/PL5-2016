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
 * The Class ConvergenceIndicator.
 */
public final class ConvergenceIndicator {

	/**
	 * Instantiates a new convergence indicator.
	 */
	private ConvergenceIndicator() { }
	
	/**
	 * Compute convergence levels.
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
	 * Gets the convergence map.
	 *
	 * @param genomeIDs the genome i ds
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
			if (outlierness > 1.5) {
				convergenceMap.put(genomeId, outlierness);
			}
		}
		return convergenceMap;
	}

	/**
	 * Gets the average patristic distances.
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