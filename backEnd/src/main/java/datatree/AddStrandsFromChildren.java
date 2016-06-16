package datatree;

import genome.Strand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

/**
 * @author Jeffrey Helgers.
 *         This class computes the Strands that will be added to their parent.
 *         Makes use of a RecursiveAction to speed the process up.
 */
public class AddStrandsFromChildren extends RecursiveAction {

    /**
     * The node on which the computation is made.
     */
    private DataNode currentNode;

    /**
     * Constructor to create a AddStrandsFromChildren object.
     *
     * @param dataNode The node where the Strands will be added to.
     */
    public AddStrandsFromChildren(DataNode dataNode) {
        currentNode = dataNode;
    }


    @Override
    protected void compute() {
        ArrayList<DataNode> children = currentNode.getChildren();
        if (children.size() != 0) {
            AddStrandsFromChildren left =
                    new AddStrandsFromChildren(currentNode.getChildren().get(0));
            AddStrandsFromChildren right =
                    new AddStrandsFromChildren(currentNode.getChildren().get(1));
            left.fork();
            right.fork();
            left.join();
            right.join();
            ArrayList<Strand> strands1 = children.get(0).getStrands();
            ArrayList<Strand> strands2 = children.get(1).getStrands();
            ArrayList<Strand> current = new ArrayList<>(strands1);
            current.retainAll(strands2);
            currentNode.setStrands(current);
            strands1.removeAll(current);
            strands2.removeAll(current);
            currentNode.setChildren(new ArrayList<>(Arrays.asList(
                    left.currentNode, right.currentNode)));
        }
    }


}
