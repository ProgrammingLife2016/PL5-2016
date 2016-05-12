package datatree;

import abstracttree.AbstractTreeNode;
import genome.Strand;
import phylogenetictree.PhylogeneticNode;

import java.util.ArrayList;

/**
 * Created by Matthijs on 12-5-2016.
 */
public class DataNode extends AbstractTreeNode<DataNode> {

    private ArrayList<Strand> strands;
    private ArrayList<String> genomes;
    private int level;

    /**
     * creates a data Node.
     *
     * @param parent The parent of this node, null if root.
     */
    public DataNode(DataNode parent, int childNumber) {
        super(parent, childNumber);
        strands = new ArrayList<>();
        genomes = new ArrayList<>();
        if(parent==null){
            level=0;
        }
        else{
            level=parent.getLevel()+1;
        }

    }

    /**
     * Adapt DataNode from a phylogeneticNode, recursively.
     *
     * @param phyloNode The phylo to adapt.
     */
    public DataNode(PhylogeneticNode phyloNode, DataNode parent, int childNumber) {
        super(parent, childNumber);
        strands = new ArrayList<>();
        this.genomes = phyloNode.getGenomes();

        if(parent==null){
            level=0;
        }
        else{
            level=parent.getLevel()+1;
        }

        for (int i = 0; i < phyloNode.getChildren().size(); i++) {
            this.addChild(new DataNode(phyloNode.getChildren().get(i), this, i));
        }
    }

    public DataNode getChildWithGenome(String genome){
        for(DataNode child:children){
            if(child.getGenomes().contains(genome)){
                return child;
            }
        }
        return null;
    }

    public ArrayList<Strand> getStrands() {
        return strands;
    }


    public void setStrands(ArrayList<Strand> strands) {
        this.strands = strands;
    }

    public ArrayList<String> getGenomes() {
        return genomes;
    }



    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setGenomes(ArrayList<String> genomes) {
        this.genomes = genomes;
    }
}
