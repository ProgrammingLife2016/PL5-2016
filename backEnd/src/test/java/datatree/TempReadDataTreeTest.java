package datatree;

import genome.Strand;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.HashMap;

/**
 * @author Jeffrey Helgers
 *         Class that test the reading from the DataTree file.
 */
public class TempReadDataTreeTest {

    private DataTree tree;
    private DataNode node;
    private HashMap<Integer, Strand> strands;

    /**
     * Setting up the test data.
     *
     * @throws Exception If fail.
     */
    @Before
    public void setUp() throws Exception {
        tree = Mockito.mock(DataTree.class);
        node = Mockito.mock(DataNode.class);
        Strand strand = Mockito.mock(Strand.class);
        strands = new HashMap<>();
        strands.put(0, strand);

        Mockito.when(tree.getRoot()).thenReturn(node);
        Mockito.when(node.getNode(0)).thenReturn(node);
    }

    /**
     * Test reading the file.
     */
    @Test
    public void testReadDataTree() {
        TempReadDataTree.readFile(tree, strands, "data/testDataTree.txt");
        Mockito.verify(node).setStrands(Matchers.any());
    }

}
