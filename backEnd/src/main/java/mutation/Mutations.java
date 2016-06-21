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
                                    createSNP(node, firstEdgeEnd, 
                                    		secondEdgeEnd, edge1);                                	
                                    
                                    firstEdge.setWeight(firstEdge.getWeight() 
                                    		+ secondEdge.getWeight() - 1);
                                    firstEdge.addGenomeToEdge(secondEdge.getColor());
                                    firstEdgeEnd.getOutEdges().get(0).setColor(
                                    		firstEdge.getColor());
                                    firstEdgeEnd.getOutEdges().get(0).setWeight(
                                    		firstEdge.getWeight());
                                    nodes.remove(secondEdgeEnd);
                                    node.getOutEdges().remove(secondEdge);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Create the snp mutation and set its label.
     * @param node The stard strand.
     * @param firstEdgeEnd The first mutated strand.
     * @param secondEdgeEnd The second mutated strand.
     * @param edge
     */
    private void createSNP(RibbonNode node, RibbonNode firstEdgeEnd, 
    		RibbonNode secondEdgeEnd, RibbonEdge edge) {
    			MutationSNP snp = new MutationSNP(
                MutationType.SNP,
                firstEdgeEnd.getGenomes(),
                secondEdgeEnd.getGenomes(),
                node,
                edge.getEnd(),
                firstEdgeEnd,
                secondEdgeEnd);
    	firstEdgeEnd.addMutation(snp);
        firstEdgeEnd.setY(node.getY());
        firstEdgeEnd.setX((node.getX() + edge.getEnd().getX()) / 2);
        firstEdgeEnd.addAnnotations(secondEdgeEnd.getAnnotations());
        StringBuilder label = new StringBuilder();
        label.append(firstEdgeEnd.getStrands().get(0).getSequence());
        label.append("<br>");
        label.append(secondEdgeEnd.getGenomes().toString());
        label.append(" ");
        label.append(secondEdgeEnd.getStrands().get(0).getSequence());
        firstEdgeEnd.setLabel(label.toString());
    }

    /**
     * Check if there is an indel mutation starting from the start strand.
     *
     * @param start   The start strand.
     * @param strands All the strands.
     */
    private void findIndel(RibbonNode node) {
        ArrayList<RibbonEdge> edgesToRemove = new ArrayList<>();
        for (RibbonEdge edge1 : node.getOutEdges()) {
            for (RibbonEdge edge2 : node.getOutEdges()) {
                if (!edge1.equals(edge2)) {
                    RibbonNode end = edge1.getEnd();
                    RibbonNode mutated = edge2.getEnd();
                    for (RibbonEdge edge3 : mutated.getOutEdges()) {
                        if (edge3.getEnd().equals(end)) {
                        	createIndel(node, mutated, end);
                            edgesToRemove.add(edge1);
                            end.getInEdges().remove(edge1);
                            edge2.setWeight(edge2.getWeight() + edge1.getWeight() - 1);
                            edge2.addGenomeToEdge(edge1.getColor());
                            edge2.setSuggested(false);
                            edge3.setWeight(edge2.getWeight() - 1);
                            edge3.addGenomeToEdge(edge1.getColor());
                            edge3.setSuggested(false);
                            
                        }
                    }
                }
            }
        }
        node.getOutEdges().removeAll(edgesToRemove);
    }
    
    private void createIndel(RibbonNode start, RibbonNode mutated, RibbonNode end) {
    	HashSet<String> reference = new HashSet<String>(start.getGenomes());
        reference.retainAll(end.getGenomes());
        HashSet<String> other = new HashSet<String>(mutated.getGenomes());
        other.retainAll(reference);
        reference.removeAll(other);
        mutated.addMutation(new MutationIndel(
                MutationType.INDEL,
                reference,
                other,
                start,
                end,
                new ArrayList<>(Arrays.asList(mutated))));
        mutated.setY(start.getY());
        mutated.setX((start.getX() + end.getX()) / 2);
        StringBuilder label = new StringBuilder();
        label.append(mutated.getStrands().get(0).getSequence());
        label.append("<br>");
        label.append(reference.toString());
        mutated.setLabel(label.toString());
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
