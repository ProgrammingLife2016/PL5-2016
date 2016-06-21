package metadata;

import com.google.inject.internal.util.Lists;
import genome.Genome;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by user on 16/06/16.
 * Used for analysing the meta data of the genomes
 * (e.g. all available metadata types, their possible values
 * and the colours each genome should get based on the metadata type)
 */
public class MetaDataController {

	/**
	 * HashMap with all the meta data.
	 */
    private Map<String, HashSet<String>> allValues = new HashMap<>();
    
    /**
     * HashMap with the colors.
     */
    private Map<String, Color> colorMap = new HashMap<>();
    
    /**
     * All the genomes.
     */
    private List<Genome> genomes;

    /**
     * The preferred colors for the metadata coloring.
     */
    private List<Color> preferredColors = new ArrayList<>();

    /**
     * Whether the color is colorBlindMode active
     */
    private Boolean colorBlindMode = false;

    /**
     * constructor.
     * @param inputGenomes list of the genomes for which the MetaData is relevant
     */
    public MetaDataController(List<Genome> inputGenomes) {
        genomes = inputGenomes;
        initializeHashMap();
        fillHashMap();
        setColorBlindEnabled(false);
    }

    /**
     * Used to initialize all HashMaps (one is used for each metadata type).
     */
    private void initializeHashMap() {
        allValues.put("genomeId", new HashSet<>());
        allValues.put("age", new HashSet<>());
        allValues.put("sex", new HashSet<>());
        allValues.put("hivStatus", new HashSet<>());
        allValues.put("cohort", new HashSet<>());
        allValues.put("dateOfCollection", new HashSet<>());
        allValues.put("geoGraphicDistrict", new HashSet<>());
        allValues.put("specimenType", new HashSet<>());
        allValues.put("smearStatus", new HashSet<>());
        allValues.put("dnaIsolation", new HashSet<>());
        allValues.put("phenoDSTPattern", new HashSet<>());
        allValues.put("capreomycin", new HashSet<>());
        allValues.put("ethambutol", new HashSet<>());
        allValues.put("ethionamide", new HashSet<>());
        allValues.put("isoniazid", new HashSet<>());
        allValues.put("kanamyicin", new HashSet<>());
        allValues.put("pyrazinamide", new HashSet<>());
        allValues.put("ofloxacin", new HashSet<>());
        allValues.put("rifampin", new HashSet<>());
        allValues.put("streptomycin", new HashSet<>());
        allValues.put("digitalSpoligotype", new HashSet<>());
        allValues.put("lineage", new HashSet<>());
        allValues.put("genoDSTPattern", new HashSet<>());
        allValues.put("xdrType", new HashSet<>());
    }

    /**
     * Fill the HashMap.
     */
    private void fillHashMap() {
        for (Genome g : genomes) {
            if (g.getMetadata() != null) {
                allValues.get("age").add(g.getMetadata().getAge());
                allValues.get("sex").add(g.getMetadata().getSex());
                allValues.get("hivStatus").add(g.getMetadata().getHivStatus());
                allValues.get("cohort").add(g.getMetadata().getCohort());
                allValues.get("dateOfCollection").add(g.getMetadata().getDateOfCollection());
                allValues.get("geoGraphicDistrict").add(g.getMetadata().getGeoGraphicDistrict());
                allValues.get("specimenType").add(g.getMetadata().getSpecimenType());
                allValues.get("smearStatus").add(g.getMetadata().getSmearStatus());
                allValues.get("dnaIsolation").add(g.getMetadata().getDnaIsolation());
                allValues.get("phenoDSTPattern").add(g.getMetadata().getPhenoDSTPattern());
                allValues.get("capreomycin").add(g.getMetadata().getCapreomycin());
                allValues.get("ethambutol").add(g.getMetadata().getEthambutol());
                allValues.get("ethionamide").add(g.getMetadata().getEthionamide());
                allValues.get("isoniazid").add(g.getMetadata().getIsoniazid());
                allValues.get("kanamyicin").add(g.getMetadata().getKanamyicin());
                allValues.get("pyrazinamide").add(g.getMetadata().getPyrazinamide());
                allValues.get("ofloxacin").add(g.getMetadata().getOfloxacin());
                allValues.get("rifampin").add(g.getMetadata().getRifampin());
                allValues.get("streptomycin").add(g.getMetadata().getStreptomycin());
                allValues.get("digitalSpoligotype").add(g.getMetadata().getDigitalSpoligotype());
                allValues.get("lineage").add(g.getMetadata().getLineage());
                allValues.get("genoDSTPattern").add(g.getMetadata().getGenoDSTPattern());
                allValues.get("xdrType").add(g.getMetadata().getXdrType());
            }
        }
    }

    /**
     * Fills the colormap, which maps metadata type + value to a color.
     */
    private void fillColormap() {
        System.out.println(colorMap);
        for (String metaDataType : returnMetaDataTypes()) {
            int i = 0;
            for (String value : returnPossibleValues(metaDataType)) {
                colorMap.put(metaDataType + ":" + value, getPreferredColors(i));
                i++;
            }
        }
        colorMap.put("lineage:LIN 1", Color.decode("0xed00c3"));
        colorMap.put("lineage:LIN 2", Color.decode("0x0000ff"));
        colorMap.put("lineage:LIN 3", Color.decode("0x500079"));
        colorMap.put("lineage:LIN 4", Color.decode("0xff0000"));
        colorMap.put("lineage:LIN 5", Color.decode("0x4e2c00"));
        colorMap.put("lineage:LIN 6", Color.decode("0x69ca00"));
        colorMap.put("lineage:LIN 7", Color.decode("0xff7e00"));
        colorMap.put("lineage:LIN animal", Color.decode("0x00ff9c"));
        colorMap.put("lineage:LIN B", Color.decode("0x00ff9c"));
        colorMap.put("lineage:LIN CANETTII", Color.decode("0x00ffff"));
        System.out.println(colorMap);

    }

    /**
     * Returns all types of metadata that are available.
     * @return All available metadata fields
     */
    public List<String> returnMetaDataTypes() {
        return Lists.newArrayList(allValues.keySet());
    }

    /**
     * Returns all possible values of a certain metadata type.
     * @param metaData a string representing the metadata in which one is interested.
     * @return all possible values of this field
     */
    public List<String> returnPossibleValues(String metaData) {
        return Lists.newArrayList(allValues.get(metaData));
    }

    /**
     * Allows setting preferred colors, while returning a random color if all options are exhausted.
     * @param i number expressing the preference
     * @return a color
     */
    private Color getPreferredColors(int i) {
        Random r = new Random();
        if (i < preferredColors.size()) {
            return preferredColors.get(i);
        } else {
            return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
        }
    }

    /**
     * Allows setting preferred colors, while returning a random color if all options are exhausted.
     * @param colorBlindEnabled whether or not color blindness mode is activated
     */
    public void setColorBlindEnabled(Boolean colorBlindEnabled) {
        preferredColors.clear();
        colorBlindMode = colorBlindEnabled;
        if (colorBlindEnabled) {
            preferredColors.add(new Color(100, 75, 0));
            preferredColors.add(new Color(30, 120, 240));
            preferredColors.add(new Color(250, 200, 130));
            preferredColors.add(new Color(240, 170, 50));
            preferredColors.add(new Color(25, 34, 50));
            preferredColors.add(new Color(195, 195, 250));
            preferredColors.add(new Color(10, 60, 110));
        } else {
            preferredColors.add(Color.red);
            preferredColors.add(Color.blue);
            preferredColors.add(Color.green);
            preferredColors.add(Color.orange);
            preferredColors.add(Color.magenta);
            preferredColors.add(Color.black);
            preferredColors.add(Color.cyan);
        }
        fillColormap();
    }

    /**
     * Return the colorBlindMode
     * @return A boolean containing whether the system is on colorBlindMode
     */
    public Boolean getColorBlindMode() {
        return colorBlindMode;
    }

    /**
     * Returns a color for the genome, based on the metadata type.
     * @param genome the genome for which the color is needed
     * @param metaDataType the metadata type from which the color should be calculated
     * @return the color of this genome
     */
    public Color getColor(Genome genome, String metaDataType) {
        if (!genome.hasMetadata()) {
            genome.setColor(Color.gray);
            return Color.GRAY;
        }
        Color color = colorMap.get(metaDataType + ":" 
            + genome.getMetadata().returnField(metaDataType));
        genome.setColor(color);
        return color;
    }

    /**
     * Returns a hashmap mapping metadatetypes to their possible values.
     * @return the hashmap
     */
    public Map<String, String> getMetaDataMap() {
        Map<String, String> out = new HashMap<>();
        for (String id : colorMap.keySet()) {
            String hex = Integer.toHexString(colorMap.get(id).getRGB() & 0xffffff);
            while (hex.length() < 6) {
                hex = "0" + hex;
            }
            out.put(id, hex);
        }
        return out;
    }

    /**
     * Returns a hashmap from (Genome id -> Color).
     * @param metaDataType the metadata type on which the colors should be based
     * @return The hashmap mapping genomes to colors
     */
    public HashMap<String, Color> getAllGenomeColors(String metaDataType) {
        HashMap<String, Color> out = new HashMap<>();
        for (Genome g : genomes) {
            out.put(g.getId(), getColor(g, metaDataType));
        }
        return out;
    }
}
