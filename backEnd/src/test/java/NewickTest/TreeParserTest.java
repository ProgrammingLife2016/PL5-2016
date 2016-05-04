package NewickTest;

import org.junit.*;

import phylogeneticTree.*;


/**
 * Created by Matthijs on 4-5-2016.
 * Test tree: https://en.wikipedia.org/wiki/File:NewickExample.svg
 */
public class TreeParserTest {

    private PhylogeneticTree tree;

    @Before
    public void setUp() throws Exception {
        tree= new PhylogeneticTree();
        tree.parseTree("src/testFile");
    }

    @Test
    public void testRootSize() throws Exception {
        assert (tree.getRoot().getChildren().size()==3);
    }

    @Test
    public void testPathEnd() throws Exception{
        Assert.assertEquals(tree.getNode("D").getDistance(),0.4,0.001);
    }

    @Test
    public void testPathMiddle() throws Exception{
        Assert.assertEquals(tree.getNode("").getDistance(),0.0,0.001);
    }
}
