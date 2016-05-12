package datatree;

import abstracttree.TreeStructure;
import genome.Genome;
import genome.Strand;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Matthijs on 12-5-2016.
 */
public class DataTree extends TreeStructure<DataNode> {


    public DataTree() {
    }

    public DataTree(DataNode root) {
        super(root);
    }

    /**
     * Get genomeLeaf through depth first search.
     *
     * @param genomeName The name of the node.
     * @return The node, null if it is not contained in this graph.
     */
    public DataNode getGenomeLeaf(final String genomeName) {

        if (root.getChildren().size() != 0) {
            for (DataNode child : root.getChildren()) {
                if (child.getGenomes().size() == 1
                        && child.getGenomes().get(0).equals(genomeName)) {
                    return child;
                }

                DataTree subTree = new DataTree(child);
                DataNode node = subTree.getGenomeLeaf(genomeName);
                if (node != null) {
                    return node;
                }
            }
        }
        return null;
    }

    public void addStrands(ArrayList<Genome> genomes) {

        ArrayList<DataNode> currentLeaves = new ArrayList<>();
        ArrayList<DataNode> nextLeaves = new ArrayList<>();
        ArrayList<DataNode> done = new ArrayList<>();

        //add all strands to the leafs.
        for (Genome genome : genomes) {
            DataNode leaf = getGenomeLeaf(genome.getId());
            leaf.setStrands(genome.getStrands());
            currentLeaves.add(leaf);
        }

        while (currentLeaves.size() != 1) {
            for (DataNode leaf : currentLeaves) {
                if (!done.contains(leaf)) {
                    DataNode parent = leaf.getParent();
                    DataNode child1 = leaf.getChildren().get(0);
                    DataNode child2 = leaf.getChildren().get(1);

                    nextLeaves.add(parent);
                    done.add(child1);
                    done.add(child2);

                    ArrayList<Strand> parentStrands = child1.getStrands();
                    parentStrands.retainAll(child2.getStrands());
                    parent.setStrands(parentStrands);

                    child1.getStrands().removeAll(parentStrands);
                    child2.getStrands().removeAll(parentStrands);
                }
            }
            done = new ArrayList<>();
            currentLeaves = nextLeaves;
            nextLeaves = new ArrayList<>();
        }


    }

    public ArrayList<DataNode> getDataNodes(int xMin, int xMax, ArrayList<String> genomes, int level){
       return filterNodes(xMin,xMax, getDataNodesForGenomes(genomes,level),genomes);

    }

    public ArrayList<DataNode> filterNodes(int xMin, int xMax, Set<DataNode> nodes, ArrayList<String> genomes){
        ArrayList<DataNode> result = new ArrayList<>();
        result.addAll(nodes);
        result=(ArrayList<DataNode>)result.clone();
        for(DataNode node:result){
            node.setGenomes(genomes);
            ArrayList<Strand> newStrands= new ArrayList<>();
            for(Strand strand: node.getStrands()){
                if(strand.getId()>xMax+10||strand.getId()<xMin-10){
                    newStrands.add(strand);
                }
            }
            node.setStrands(newStrands);
        }
        return result;


    }

    public Set<DataNode> getDataNodesForGenomes(ArrayList<String> genomes, int level) {
        Set<DataNode> result = new HashSet<>();
        for(String genome:genomes){
            result.addAll(getDataNodesForGenome(genome,level));
        }
        return result;

    }

    public Set<DataNode> getDataNodesForGenome(String genomeId, int level) {
        Set<DataNode> result = new HashSet<>();
        DataNode currentNode = root;
        while (currentNode.getChildren().size() > 1 && currentNode.getLevel() < level) {
            result.add(currentNode);
            currentNode = currentNode.getChildWithGenome(genomeId);
        }
        return result;


    }


}
