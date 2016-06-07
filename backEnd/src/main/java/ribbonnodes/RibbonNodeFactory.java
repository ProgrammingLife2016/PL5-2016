package ribbonnodes;

import genome.Strand;

/**
 * Factory for the making of ribbons derived from strands.
 * Created by Matthijs on 7-6-2016.
 */
public abstract class RibbonNodeFactory {

    /**
     * Create a ribbon node based on a strand.
     * @param id The id of the ribbon node.
     * @param strand The strand to base the ribbon node form.
     * @return The created ribbonNode.
     */
    public static RibbonNode makeRibbonNodeFromStrand(int id, Strand strand) {

        RibbonNode ribbon = new RibbonNode(id, strand.getGenomes());
        ribbon.setX(strand.getX());
        ribbon.addStrand(strand);

        return ribbon;
    }


}
