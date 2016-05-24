package genome;

import java.util.ArrayList;

/**
 * @author Jeffrey Helgers
 */
public class Genome {

    private ArrayList<Strand> strands;
    private String id;

    /**
     * Constructor to create a new genome.
     * @param id The genome name.
     */
    public Genome(String id) {
        this.id = id;
        strands = new ArrayList<>();
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
        return strands;
    }

    /**
     * Get the id.
     * @return Id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id.
     * @param id Id.
     */
    public void setId(String id) {
        this.id = id;
    }


}
