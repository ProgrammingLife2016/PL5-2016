package genome;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class GenomeGraph.
 */
public class LightGraph {

	/** The nodes. */
	private ArrayList<Integer> nodes = new ArrayList<Integer>();
	
	/** The edges. */
	private HashMap<Integer, Integer> edges = new HashMap<Integer, Integer>();

	/**
	 * Instantiates a new genome graph.
	 */
	public LightGraph() {
	}

	/**
	 * Adds the node.
	 *
	 * @param i the i
	 */
	public void addNode(int i) {
		nodes.add(i);

	}

	/**
	 * Adds the edge.
	 *
	 * @param start the start
	 * @param end the end
	 */
	public void addEdge(int start, int end) {
		edges.put(start, end);

	}

}
