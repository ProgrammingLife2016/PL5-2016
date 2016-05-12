package abstracttree;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;

/**
 * Created by Matthijs on 12-5-2016.
 */
public class AbstractTreeNodeTest {

    private AbstractTreeNode node;

    @Before
    public void setUp() throws Exception {
        node = Mockito.mock(AbstractTreeNode.class, Mockito.CALLS_REAL_METHODS);
        node.parent=node;
        node.id=5;
        node.children=new ArrayList();
        node.children.add(node);
    }


    @Test
    public void testGetChildren() throws Exception {
        assertEquals(node.getChildren(),node.children);
    }

    @Test
    public void testAddChild() throws Exception {
        assertEquals(node.children.size(),1);
        node.addChild(node);
        assertEquals(node.children.size(),2);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals(node.getId(),5);
    }

    @Test
    public void testGetParent() throws Exception {
        assertEquals(node.getParent(),node);
    }
}