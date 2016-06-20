package mutation;

import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import datatree.DataTree;

/**
 * The Class AllMutations.
 *
 * @author Jeffrey Helgers.
 *         This class computes all the mutations in the given dataset.
 *         It uses ribbon nodes as comparisons.
 */
public class Mutations {

    /**
     * The genome graph.
     */
    private ArrayList<RibbonNode> nodes;
	private DataTree dataTree;

    /**
     * Constructor to set up the ribbons on which they are computed.
     *
     * @param nodes The nodes in the graph.
     * @param dataTree 
     */
    public Mutations(ArrayList<RibbonNode> nodes, DataTree dataTree) {
        this.nodes = nodes;
        this.dataTree = dataTree;
    }

    /**
     * Compute all the mutations in the graph.
     * From all the start Strands.
     */
    public void computeAllMutations() {
        for (int i = 0; i < nodes.size(); i++) {
            findIndel(nodes.get(i));
            findSNP(nodes.get(i), nodes);
        }
    }

    /**
     * Check if there is a SNP mutation starting from the start strand.
     *
     * @param start   The start strand.
     * @param strands All the strands.
     */
    @SuppressWarnings("checkstyle:methodlength")
    private void findSNP(RibbonNode node, ArrayList<RibbonNode> nodes) {
        for (int i = 0; i < node.getOutEdges().size() - 1; i++) {
        	RibbonEdge firstEdge = node.getOutEdges().get(i);
            RibbonNode firstEdgeEnd = firstEdge.getEnd();
            if (firstEdgeEnd.getStrands().get(0).getSequence().length() == 1) {
                for (int j = i + 1; j < node.getOutEdges().size(); j++) {
                	RibbonEdge secondEdge = node.getOutEdges().get(j);
                    RibbonNode secondEdgeEnd = secondEdge.getEnd();
                    if (secondEdgeEnd.getStrands().get(0).getSequence().length() == 1) {
                        for (RibbonEdge edge1 : firstEdgeEnd.getOutEdges()) {
                            for (RibbonEdge edge2 : secondEdgeEnd.getOutEdges()) {
                                if (edge1.getEnd().getId() == edge2.getEnd().getId()) {
                                    MutationSNP snp = new MutationSNP(
                                            MutationType.SNP,
                                            firstEdgeEnd.getGenomes(),
                                            secondEdgeEnd.getGenomes(),
                                            node,
                                            edge1.getEnd(),
                                            firstEdgeEnd,
                                            secondEdgeEnd);
                                    firstEdgeEnd.addMutation(snp);
                                    secondEdgeEnd.addMutation(snp);
                                    firstEdgeEnd.setY(node.getY());
                                    firstEdgeEnd.setX((node.getX() + edge1.getEnd().getX()) / 2);
                                    nodes.remove(secondEdgeEnd);
                                    node.getOutEdges().remove(secondEdge);
                                    StringBuilder label = new StringBuilder();
                                    label.append(firstEdgeEnd.getStrands().get(0).getSequence());
                                    label.append("<br>");
                                    label.append(secondEdgeEnd.getGenomes().toString());
                                    label.append(" ");
                                    label.append(secondEdgeEnd.getStrands().get(0).getSequence());
                                    firstEdgeEnd.setLabel(label.toString());
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Check if there is an indel mutation starting from the start strand.
     *
     * @param start   The start strand.
     * @param strands All the strands.
     */
    @SuppressWarnings("checkstyle:methodlength")
    private void findIndel(RibbonNode node) {
        for (RibbonEdge edge1 : node.getOutEdges()) {
            for (RibbonEdge edge2 : node.getOutEdges()) {
                if (!edge1.equals(edge2)) {
                    RibbonNode end = edge1.getEnd();
                    RibbonNode mutated = edge2.getEnd();
                    HashSet<String> reference = new HashSet<String>(node.getGenomes());
                    reference.retainAll(end.getGenomes());
                    for (RibbonEdge edge3 : mutated.getOutEdges()) {
                        if (edge3.getEnd().equals(end)) {
                            HashSet<String> other = new HashSet<String>(mutated.getGenomes());
                            other.retainAll(reference);
                            reference.removeAll(other);
                            mutated.addMutation(new MutationIndel(
                                    MutationType.INDEL,
                                    reference,
                                    other,
                                    node,
                                    end,
                                    new ArrayList<>(Arrays.asList(mutated))));
                            mutated.setY(node.getY());
                            mutated.setX((node.getX() + edge3.getEnd().getX()) / 2);
                            StringBuilder label = new StringBuilder();
                            label.append(mutated.getStrands().get(0).getSequence());
                            label.append("<br>");
                            label.append(reference.toString());
                            mutated.setLabel(label.toString());
                        }
                    }
                }
            }
        }
    }

	/**
	 * Detects evolutionary convergence.
	 */
	public void detectConvergence() {
	       for (RibbonNode node : nodes) {
	            for (AbstractMutation mutation: node.getMutations()) {
	            	ConvergenceIndicator.computeConvergenceLevels(mutation, dataTree);
	            }
	        }
	}
}
