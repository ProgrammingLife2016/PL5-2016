package abstractdatastructure;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


/**
 * Created by Matthijs on 12-5-2016.
 */
public class TreeStructureTest {

    private TreeStructure tree;
    private AbstractTreeNode a;
    private AbstractTreeNode b;
    private AbstractTreeNode c;
    private AbstractTreeNode d;
    private AbstractTreeNode testRoot;


    /**
     * Setting up the tree.
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        //Set up the test tree.
        testRoot = Mockito.mock(AbstractTreeNode.class, Mockito.CALLS_REAL_METHODS);
        testRoot.setId(1);
        a = Mockito.mock(AbstractTreeNode.class, Mockito.CALLS_REAL_METHODS);
        a.setId(2);
        b = Mockito.mock(AbstractTreeNode.class, Mockito.CALLS_REAL_METHODS);
        b.setId(3);
        c = Mockito.mock(AbstractTreeNode.class, Mockito.CALLS_REAL_METHODS);
        c.setId(4);
        d = Mockito.mock(AbstractTreeNode.class, Mockito.CALLS_REAL_METHODS);
        d.setId(5);

        ArrayList<AbstractTreeNode> rootChildren = new ArrayList<>();
        rootChildren.add(a);
        rootChildren.add(b);
        ArrayList<AbstractTreeNode> aChildren = new ArrayList<>();
        aChildren.add(c);
        aChildren.add(d);

        testRoot.setChildren(rootChildren);
        a.setChildren(aChildren);

        tree = new TreeStructure(testRoot);


    }

    /**
     * Test getting a specific node.
     * @throws Exception Exception.
     */
    @Test
    public void testGetNode() throws Exception {
        assertEquals(tree.getRoot().getNode(4), c);

    }

    /**
     * Test setting the root.
     * @throws Exception Exception.
     */
    @Test
    public void testSetRoot() throws Exception {
        assertEquals(tree.getRoot().getId(), 1);
        tree.setRoot(c);
        assertEquals(tree.getRoot().getId(), 4);
    }

    /**
     * Test getting the root.
     * @throws Exception Exception.
     */
    @Test
    public void testGetRoot() throws Exception {
        assertEquals(tree.getRoot(), testRoot);
    }
}