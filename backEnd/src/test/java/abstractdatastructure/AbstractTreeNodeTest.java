package abstractdatastructure;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthijs on 12-5-2016.
 */
public class AbstractTreeNodeTest {

    private AbstractTreeNode node;

    /**
     * Set up the tests.
     *
     * @throws Exception if fail.
     */
    @Before
    public void setUp() throws Exception {
        node = Mockito.mock(AbstractTreeNode.class, Mockito.CALLS_REAL_METHODS);
        node.setChildren(new ArrayList());
        node.setParent(node);
        node.setId(5);
        node.getChildren().add(node);
    }

    /**
     * Test getChildren method.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetChildren() throws Exception {
        assertEquals(node.getChildren(), node.getChildren());
    }

    /**
     * Test add child.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testAddChild() throws Exception {
        assertEquals(node.getChildren().size(), 1);
        node.addChild(node);
        assertEquals(node.getChildren().size(), 2);
    }

    /**
     * Test get id.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetId() throws Exception {
        assertEquals(node.getId(), 5);
    }

    /**
     * Test get parent.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetParent() throws Exception {
        assertEquals(node.getParent(), node);
    }
}