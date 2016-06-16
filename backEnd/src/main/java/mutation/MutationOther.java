package mutation;

import ribbonnodes.RibbonNode;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Jeffrey Helgers.
 *         Class to create a mutation which is not located between two genomes.
 */
public class MutationOther extends AbstractMutation {

    /**
     * Constructor to create the mutation.
     * Create a mutation.
     *
     * @param mutationType   The type of mutation.
     * @param reference      The reference Genome.
     * @param hashSet        The other Genome.
     * @param start          The start Strand.
     * @param mutatedStrands The mutated Strands.
     */
    public MutationOther(MutationType mutationType, HashSet<String> reference,
                         HashSet<String> hashSet, RibbonNode start,
                         ArrayList<RibbonNode> mutatedStrands) {
        super(mutationType, reference, hashSet, start);
        if (!mutationType.getBetween()) {
            setMutatedStrands(mutatedStrands);
        } else {
            throw new UnsupportedOperationException();
        }
    }

}
