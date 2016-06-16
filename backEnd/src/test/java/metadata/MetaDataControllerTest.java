package metadata;

import com.google.inject.internal.util.Lists;
import genome.Genome;
import genome.GenomeGraph;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import parser.Parser;

import java.awt.Color;
import java.util.*;

/**
 * Created by user on 16/06/16.
 */
public class MetaDataControllerTest {

    private static MetaDataController mdc;
    private static List<Genome> currentGenomes;

    /**
     * Create a metaDataController to test on.
     */
    @BeforeClass
    public static void setup() {
        String gfaFile = "data/TB10.gfa";
        GenomeGraph genomeGraph = Parser.parse(gfaFile);
        genomeGraph.loadMetaData(Parser.parseGenomeMetadata("data/metadata.csv"));

        currentGenomes = Lists.newArrayList(genomeGraph.getGenomes().values());
        mdc = new MetaDataController(currentGenomes);
    }

    /**
     * Tests if we can correctly return all the categories of metaData.
     */
    @Test
    public void testReturnMetaDataTypes() {
        List<String> temp = Arrays.asList("genomeId", "lineage", "geoGraphicDistrict",
                "capreomycin", "smearStatus", "isoniazid", "dateOfCollection", "sex",
                "pyrazinamide", "cohort", "dnaIsolation", "kanamyicin", "ethionamide",
                "ofloxacin", "rifampin", "phenoDSTPattern", "ethambutol", "hivStatus",
                "genoDSTPattern", "xdrType", "specimenType", "digitalSpoligotype",
                "age", "streptomycin");
        HashSet<String> metaDataTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnMetaDataTypes());
        Assert.assertTrue(metaDataTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testAgeTypes() {
        List<String> temp = Arrays.asList("33", "44", "36", "37", "26", "27",
                "28", "52", "30", "31");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("age"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testSexTypes() {
        List<String> temp = Arrays.asList("Female", "Male");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("sex"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testHIVStatusTypes() {
        List<String> temp = Arrays.asList("Positive", "Negative");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("hivStatus"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testCohortTypes() {
        List<String> temp = new ArrayList<>();
        temp.add("PROX");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("cohort"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testDateOfCollectionTypes() {
        List<String> temp = Arrays.asList("11-6-2010", "25-6-2010", "12-4-2010",
                "24-3-2010", "7-4-2010", "29-3-2010", "25-3-2010");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("dateOfCollection"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testGeoGraphicDistrictTypes() {
        List<String> temp = Arrays.asList("eThekwini", "uMgungundlovu", "uMzinyathi",
                "Sisonke", "Zululand", "uThungulu");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("geoGraphicDistrict"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testSpecimenTypeTypes() {
        List<String> temp = new ArrayList<>();
        temp.add("sputum");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("specimenType"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testSmearStatusTypeTypes() {
        List<String> temp = Arrays.asList("Negative", "Positive");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("smearStatus"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testDNAIsolationTypeTypes() {
        List<String> temp = new ArrayList<>();
        temp.add("single colony");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("dnaIsolation"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testPhenoDSTPatternTypeTypes() {
        List<String> temp = Arrays.asList("XDR", "MDR");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("phenoDSTPattern"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testCapreomycinTypes() {
        List<String> temp = Arrays.asList("R", "S");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("capreomycin"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testEthambutolTypes() {
        List<String> temp = new ArrayList<>();
        temp.add("R");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("ethambutol"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testEthionamideTypes() {
        List<String> temp = new ArrayList<>();
        temp.add("R");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("ethionamide"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testIsoniazidTypes() {
        List<String> temp = new ArrayList<>();
        temp.add("R");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("ethionamide"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testKanamyicinTypes() {
        List<String> temp = Arrays.asList("R", "S");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("kanamyicin"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testPyrazinamideTypes() {
        List<String> temp = new ArrayList<>();
        temp.add("R");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("pyrazinamide"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testOfloxacinTypes() {
        List<String> temp = new ArrayList<>();
        temp.add("R");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("ofloxacin"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testRifampinTypes() {
        List<String> temp = new ArrayList<>();
        temp.add("R");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("rifampin"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testStreptomycinTypes() {
        List<String> temp = Arrays.asList("R", "S");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("streptomycin"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testDigitalSpoligotypeTypes() {
        List<String> temp = Arrays.asList("Beijing", "S", "T3", "LAM4");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("digitalSpoligotype"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testLineageTypes() {
        List<String> temp = Arrays.asList("LIN 2", "LIN 4");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("lineage"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testGenoDSTPatternTypes() {
        List<String> temp = Arrays.asList("XDR", "MDR");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("genoDSTPattern"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly return all possible values of the metadata type in question.
     */
    @Test
    public void testXDRTypeTypes() {
        List<String> temp = Arrays.asList("non-Tugela Ferry XDR", "n/a", "Tugela Ferry XDR");
        HashSet<String> allowedTypes = new HashSet<>(temp);
        HashSet<String> resultSet = new HashSet<>(mdc.returnPossibleValues("xdrType"));
        Assert.assertTrue(allowedTypes.equals(resultSet));
    }

    /**
     * Tests if we correctly color the genomes based on lineage.
     */
    @Test
    public void testLineageColor() {
        Assert.assertEquals(Color.decode("0xff0000"), mdc.getColor(currentGenomes.get(0),
                "lineage"));
        Assert.assertEquals(Color.decode("0x0000ff"), mdc.getColor(currentGenomes.get(8),
                "lineage"));
    }

    /**
     * Tests if we correctly color the genomes based on lineage.
     */
    @Test
    public void testReturnAllColors() {
        HashMap<String, Color> wanted = new HashMap<>();
        wanted.put("TKK_02_0018", Color.decode("0xff0000"));
        wanted.put("TKK_02_0007", Color.decode("0xff0000"));
        wanted.put("TKK_02_0006", Color.decode("0xff0000"));
        wanted.put("TKK_02_0008", Color.decode("0xff0000"));
        wanted.put("TKK_02_0010", Color.decode("0x0000ff"));
        wanted.put("TKK_02_0001", Color.decode("0x0000ff"));
        wanted.put("TKK_02_0025", Color.decode("0xff0000"));
        wanted.put("TKK_02_0002", Color.decode("0x0000ff"));
        wanted.put("TKK_02_0005", Color.decode("0xff0000"));
        wanted.put("TKK_02_0004", Color.decode("0xff0000"));
        wanted.put("MT_H37RV_BRD_V5.ref", Color.GRAY);
        Assert.assertEquals(wanted, mdc.getAllGenomeColors("lineage"));
    }
}
