package phylogeneticTree;

import net.sourceforge.olduvai.treejuxtaposer.*;
import net.sourceforge.olduvai.treejuxtaposer.drawer.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Matthijs on 4-5-2016.
 */
public class PhylogeneticTree {



    private PhylogeneticNode root;


    public PhylogeneticTree() {
    }

    public PhylogeneticTree(PhylogeneticNode _root){
        root=_root;
    }

    /**
     * Method that parses a newick tree from a file and stores it in this tree
     * @param fileName the file to parse
     */

    public void parseTree(String fileName){
        Tree tree= new Tree();
        try {
            BufferedReader r = new BufferedReader(new FileReader("src/testFile"));
            TreeParser tp = new TreeParser(r);
            tree = tp.tokenize("");
            this.setRoot(new PhylogeneticNode(tree.getRoot(),0.));


        }
        catch (FileNotFoundException e){
            System.out.println("tree file not found");
        }
    }

    /**
     * Get node with nameLabel name throught -first search.
     * @param name name of node
     * @return The node, null if it is not contained in this graph
     */
    public PhylogeneticNode getNode(String name){

        if(root.getNameLabel().equals(name))
            return root;
        else if(root.getChildren().size()!=0){
            for(PhylogeneticNode child:root.getChildren()){
                if(child.getNameLabel().equals(name))
                    return child;
                PhylogeneticTree subTree= new PhylogeneticTree(child);
                PhylogeneticNode node=subTree.getNode(name);
                if(node!=null)
                    return node;

            }


        }
        return null;
    }


    public void setRoot(PhylogeneticNode node){
        root=node;
    }

    public PhylogeneticNode getRoot() {
        return root;
    }

}
