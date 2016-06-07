package ribbonnodes;

import genome.Strand;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A class to test the ribbonNodeFactory.
 * Created by Matthijs on 7-6-2016.
 */
public class RibbonNodeFactoryTest {

    @Test
    public void testMakeRibbonNodeFromStrand() throws Exception {
        String[] genomes = {"1","4"};
        Strand strand = new Strand(0,"asdf",genomes,"1",0);
        strand.setX(5);

        RibbonNode node = RibbonNodeFactory.makeRibbonNodeFromStrand(5,strand);
        assertEquals(node.getId(),5);
        assertEquals(2,node.getGenomes().size());
        assertEquals(node.getGenomes().get(0),"1");
        assertEquals(node.getGenomes().get(1),"4");
        assertEquals(node.getStrands().size(),1);
        assertEquals(node.getStrands().get(0),strand);
        assertEquals(node.getX(),5);

    }
}