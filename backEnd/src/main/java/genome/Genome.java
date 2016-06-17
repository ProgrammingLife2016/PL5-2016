package genome;

import metadata.GenomeMetadata;

import java.awt.Color;
import java.util.ArrayList;


/**
 * The Class Genome.
 * Creates genomes that contain all the strands they pass through.
 *
 * @author Jeffrey Helgers
 */
public class Genome {

    /**
     * The strands contained in this Genome, parsed starting at the lowest id.
     */
    private ArrayList<Strand> strands;

    /**
     * The id String of this genome.
     */
    private String id;

    /**
     * The meta data of this genome.
     */
    private GenomeMetadata metadata;

    /**
     * The genome color for the current metadata.
     */
    private Color color;

    /**
     * Constructor to create a new genome.
     * When a genome is created there are no strands in this genome.
     *
     * @param id The genome name.
     */
    public Genome(String id) {
        this.id = id;
        strands = new ArrayList<Strand>();
        color = Color.black;
    }

    /**
     * Add a strand to the genome.
     *
     * @param strand The added strand.
     */
    public void addStrand(Strand strand) {
        strands.add(strand);
    }

    /**
     * Get all the strands from the genome.
     *
     * @return The strands the genome passes through.
     */
    public ArrayList<Strand> getStrands() {
        return new ArrayList<Strand>(strands);
    }

    /**
     * Sets the strands.
     *
     * @param strands the new strands
     */
    public void setStrands(ArrayList<Strand> strands) {
        this.strands = strands;

    }

    /**
     * Get the id.
     *
     * @return Id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id.
     *
     * @param id Id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the metadata.
     *
     * @return the metadata
     */
    public GenomeMetadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata.
     *
     * @param metadata the metadata to set
     */
    public void setMetadata(GenomeMetadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Checks for metadata.
     *
     * @return true, if successful
     */
    public boolean hasMetadata() {
        return this.metadata != null;
    }

    /**
     * Reset all x's of this genome to 0, prior to recalculating.
     */
    public void resetStrandX() {
        for (Strand strand : strands) {
            strand.setX(0);
        }
    }

    /**
     * Caculate and set the x's of all strands in this genome.
     */
    public void setStrandsX() {
        for (int i = 1; i < strands.size(); i++) {
            Strand prevStrand = strands.get(i - 1);
            int newX = prevStrand.getX() + prevStrand.getSequence().length();
            if (newX > strands.get(i).getX()) {
                strands.get(i).setX(newX);
            }

        }
    }

    /**
     * Get the color for the current metadata.
     *
     * @return color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set the color for the current metadata.
     *
     * @param color the color.
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
