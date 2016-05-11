package genome;

import java.util.ArrayList;

/**
 * 
 * @author Jeffrey Helgers
 *
 */
public class Genome {
    private ArrayList<Strand> strands;

    /**
     * Constructor to create a new genome.
     */
    public Genome() {
        strands = new ArrayList<>();
    }

    /**
     * Add a strand to the genome.
     * @param strand The added strand.
     */
    public void addNode(Strand strand) {
        strands.add(strand);
    }

    /**
     * Get all the strands from the genome.
     * @return The strands the genome passes through.
     */
    public ArrayList<Strand> getStrands() {
        return strands;
    }
}
