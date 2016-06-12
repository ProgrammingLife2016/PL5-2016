package ribbonnodes;

import genome.Genome;

import java.awt.Color;
import java.util.HashMap;

/**
 * A factory to create Ribbon Edges.
 * Created by Matthijs on 7-6-2016.
 */
public abstract class RibbonEdgeFactory {


    /**
     * Static mapping of the color associated with the lineages.
     */
    private static HashMap<String, Color> colorMap = new HashMap<String, Color>();

    static {
        colorMap.put("LIN 1", Color.decode("0xed00c3"));
        colorMap.put("LIN 2", Color.decode("0x0000ff"));
        colorMap.put("LIN 3", Color.decode("0x500079"));
        colorMap.put("LIN 4", Color.decode("0xff0000"));
        colorMap.put("LIN 5", Color.decode("0x4e2c00"));
        colorMap.put("LIN 6", Color.decode("0x69ca00"));
        colorMap.put("LIN 7", Color.decode("0xff7e00"));
        colorMap.put("LIN animal", Color.decode("0x00ff9c"));
        colorMap.put("LIN B", Color.decode("0x00ff9c"));
        colorMap.put("LIN CANETTII", Color.decode("0x00ffff"));
    }


    /**
     * Create and return a Ribbonedge.
     *
     * @param genome The genome to color the edge for.
     * @return The Created edge.
     */
    public static RibbonEdge createRibbonEdge(RibbonNode start, RibbonNode end, Genome genome) {
        RibbonEdge edge = new RibbonEdge(start, end);
        edge.setColor(getColorForGenome(genome));
        return edge;
    }


    /**
     * Gets the color for the genome as specified by http://www.abeel.be/wiki/Lineage_colors.
     * Because genomes with an identifier starting with G are not specified in the metadata
     * they get treated as a special case in this method.
     *
     * @param genome the genome
     * @return the color for the genome
     */
    private static Color getColorForGenome(Genome genome) {
        Color result;
        if (genome.hasMetadata()) {
            result = colorMap.get(genome.getMetadata().getLineage());
        } else {
            if (genome.getId().startsWith("G")) {
                result = Color.decode("0xff0000");
            } else {
                result = new Color(100, 100, 100);
            }
        }
        return result;
    }
}
