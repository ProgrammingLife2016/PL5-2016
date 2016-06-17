package mutation;

import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

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

    /**
     * Constructor to set up the ribbons on which they are computed.
     *
     * @param nodes The nodes in the graph.
     */
    public Mutations(ArrayList<RibbonNode> nodes) {
        this.nodes = nodes;
    }

    /**
     * Compute all the mutations in the graph.
     * From all the start Strands.
     */
    public void computeAllMutations() {
        for (RibbonNode node : nodes) {
            findIndel(node);
            findSNP(node);
        }
    }

    /**
     * Check if there is a SNP mutation starting from the start strand.
     *
     * @param start   The start strand.
     * @param strands All the strands.
     */
    private void findSNP(RibbonNode node) {
        for (int i = 0; i < node.getOutEdges().size() - 1; i++) {
            RibbonNode firstEdgeEnd = node.getOutEdges().get(i).getEnd();
            if (firstEdgeEnd.getStrands().get(0).getSequence().length() == 1) {
                for (int j = i + 1; j < node.getOutEdges().size(); j++) {
                    RibbonNode secondEdgeEnd = node.getOutEdges().get(j).getEnd();
                    if (secondEdgeEnd.getStrands().get(0).getSequence().length() == 1) {
                        for (RibbonEdge edge1 : firstEdgeEnd.getOutEdges()) {
                            for (RibbonEdge edge2 : secondEdgeEnd.getOutEdges()) {
                                if (edge1.getEnd().getId() == edge2.getEnd().getId()) {
                                    node.addMutation(new MutationSNP(
                                            MutationType.SNP,
                                            firstEdgeEnd.getGenomes(),
                                            secondEdgeEnd.getGenomes(),
                                            node,
                                            edge1.getEnd(),
                                            firstEdgeEnd,
                                            secondEdgeEnd));
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
                            other.removeAll(reference);
                            reference.removeAll(other);
                            node.addMutation(new MutationIndel(
                                    MutationType.INDEL,
                                    reference,
                                    other,
                                    node,
                                    end,
                                    new ArrayList<>(Arrays.asList(mutated))));
                        }
                    }
                }
            }
        }
    }
}
