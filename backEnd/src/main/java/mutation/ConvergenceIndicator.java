package mutation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import datatree.DataTree;

/**
 * The Class ConvergenceIndicator.
 */
public class ConvergenceIndicator {

	/**
	 * Compute convergence levels.
	 *
	 * @param mutation the mutation
	 * @param tree the tree
	 * @return the map
	 */
	public Map<String, Double> computeConvergenceLevels(AbstractMutation mutation, DataTree tree) {

		Map<String, Integer> avgDistanceMap = new HashMap<String, Integer>();
		List<String> genomeIDs = new ArrayList<String>(mutation.getOtherGenomes());
		List<Integer> distanceSums = new ArrayList<Integer>(genomeIDs.size());
		for (int i = 0; i < genomeIDs.size(); i++) {
			for (int j = i + 1; j < genomeIDs.size(); j++) {
				int d = tree.getPatristicDistance(genomeIDs.get(i), genomeIDs.get(j));
				distanceSums.add(i, distanceSums.get(i) + d);
				distanceSums.add(j, distanceSums.get(j) + d);
			}
		}
		int n = genomeIDs.size();
		Stream<Integer> streamOfSums = distanceSums.stream();
		double[] averageDistanceArray = streamOfSums.mapToDouble(i -> (double) i / n).toArray();
		DescriptiveStatistics stats = new DescriptiveStatistics(averageDistanceArray);
		Double standardDeviation = stats.getStandardDeviation();
		Double meanAvgDistance = stats.getMean();
		Map<String, Double> resultMap = new HashMap<String, Double>();

		for (String genomeId : mutation.getOtherGenomes()) {
			int avgDistance = avgDistanceMap.get(genomeId);
			double outlierness = Math.abs(avgDistance - meanAvgDistance) / standardDeviation;
			resultMap.put(genomeId, outlierness);
		}

		return resultMap;
	}

}