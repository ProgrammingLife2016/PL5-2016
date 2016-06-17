package parser;

import genome.GenomeGraph;
import genome.GenomicFeature;
import genome.Strand;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import datatree.DataNode;
import datatree.DataTree;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * @author Jeffrey Helgers.
 */
public class ParserTest {

    /**
     * Reads the data and checks if the id's do match.
     */
    @Test
    public void test() {
        GenomeGraph genomeGraph = Parser.parse("data/TB10.gfa");
        assertEquals(genomeGraph.getStrands().get(1).getId(), 1);
    }

    /**
     * Test the annotationParser.
     */
    @Test
    public void testParseAnnotations() {
        GenomicFeature feature = Parser.parseAnnotations("data/annotationsTest.gff").get(0);
        assertEquals(feature.getStart(), 2627279);
        assertEquals(feature.getEnd(), 2632941);
        assertEquals(feature.getDisplayName(),
                "DS9 5662 bp (4 orfs 2 IGs)  (electronically transferred)");
    }
    
    /**
     * Test reading the file.
     */
    @Test
    public void testReadDataTree() {
    	DataTree tree = Mockito.mock(DataTree.class);
        DataNode node = Mockito.mock(DataNode.class);
        Strand strand = Mockito.mock(Strand.class);
        HashMap<Integer, Strand> strands = new HashMap<>();
        strands.put(0, strand);

        Mockito.when(tree.getRoot()).thenReturn(node);
        Mockito.when(node.getNode(0)).thenReturn(node);
        Parser.readDataTree(tree, strands, "data/testDataTree.txt");
        Mockito.verify(node).setStrands(Matchers.any());
    }
}
