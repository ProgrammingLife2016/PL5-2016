package controller;

import datatree.DataNode;
import datatree.DataTree;
import datatree.TempReadWriteTree;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import parser.Parser;
import phylogenetictree.PhylogeneticTree;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Matthijs on 8-6-2016.
 */
public class RibbonControllerTest {

    private RibbonController controller;

    @Before
    public void setUp() throws Exception {
        GenomeGraph graph = Mockito.mock(GenomeGraph.class);
        DataTree tree = Mockito.mock(DataTree.class);
        controller = new RibbonController(graph, tree);

    }

    @Test
    public void testGetRibbonNodes() throws Exception {
        GenomeGraph genomeGraph = Parser.parse("data/TB10.gfa");
        genomeGraph.generateGenomes();
        genomeGraph.findStartAndCalculateX();
        ArrayList<String> actGen = new ArrayList<>();

        for(String genId:genomeGraph.getGenomes().keySet()){
            if(actGen.size()<2){
                actGen.add(genId);
            }
        }

        genomeGraph.setGenomesAsActive(actGen);
        PhylogeneticTree phylogeneticTree= new PhylogeneticTree();
        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk",
                new ArrayList<>(genomeGraph.getGenomes().keySet()));
        DataTree dataTree = new DataTree(new DataNode(phylogeneticTree.getRoot(),
                null, 0));
        dataTree.setMinStrandsToReturn(genomeGraph.getStrandNodes().size() / 8);


        dataTree.addStrands(new ArrayList<>(genomeGraph.getGenomes().values()));


        RibbonController ribbonController = new RibbonController(genomeGraph, dataTree);

        ribbonController.getRibbonNodes(0,100000000, 2);
    }

    @Test
    public void testCollapseRibbons() throws Exception {
        ArrayList<String> genomes = new ArrayList<>();
        genomes.add("1");
        ArrayList<RibbonNode> nodes = new ArrayList<>();
        RibbonNode node1 = new RibbonNode(0, genomes);
        RibbonNode node2 = new RibbonNode(1, genomes);
        RibbonNode node3 = new RibbonNode(2, genomes);
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        RibbonEdge edge1 = new RibbonEdge(0,1);
        RibbonEdge edge2 = new RibbonEdge(1,2);
        node1.addEdge(edge1);
        node2.addEdge(edge1);
        node2.addEdge(edge2);
        node3.addEdge(edge2);
        assertEquals(3, nodes.size());
        controller.collapseRibbons(nodes);
        assertEquals(1,nodes.size());
    }

    @Test
    public void testGetNodeWithId() throws Exception {

    }

    @Test
    public void testSpreadYCoordinates() throws Exception {

    }

    @Test
    public void testAddEdges() throws Exception {

    }

    @Test
    public void testAddEdgeReturnEnd() throws Exception {

    }

    @Test
    public void testFindNextNodeWithGenome() throws Exception {

    }

    @Test
    public void testAddMutationLabels() throws Exception {

    }
}