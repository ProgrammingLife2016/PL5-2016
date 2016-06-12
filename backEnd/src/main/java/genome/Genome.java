package genome;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc

/**
 * The Class Genome.
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
     * Constructor to create a new genome.
     *
     * @param id The genome name.
     */
    public Genome(String id) {
        this.id = id;
        strands = new ArrayList<Strand>();
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
     * Sets the strands.
     *
     * @param strands the new strands
     */
    public void setStrands(ArrayList<Strand> strands) {
        this.strands = strands;

    }

    public void resetStrandX() {
        for (Strand strand : strands) {
            strand.setX(0);
        }
    }

    public void setStrandsX() {
        for (int i = 1; i < strands.size(); i++) {
            Strand prevStrand = strands.get(i - 1);
            int newX = prevStrand.getX() + prevStrand.getSequence().length();
            if (newX > strands.get(i).getX()) {
                strands.get(i).setX(newX);
            }

        }
    }

}
