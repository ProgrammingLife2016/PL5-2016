package genome;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
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
        return strands;
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
     * @return the metadata
     */
    public GenomeMetadata getMetadata() {
        return metadata;
    }


    /**
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
	 * Annotate strands for genome.
	 *
	 * @param annotations the annotations
	 */
	public void annotate(List<GenomicFeature> annotations) {
		annotations.sort((GenomicFeature o1, GenomicFeature o2) -> new Integer(o1.getStart()).
				compareTo(o2.getStart()));		
		Iterator<GenomicFeature> gfIterator = annotations.iterator();
		GenomicFeature genomicFeature = gfIterator.next();		
		Strand strand = getFirstStrand();
		Strand nextStrand = strand.getNextStrand(this);
		while (nextStrand != null) {
			while (genomicFeature.startsBetween(strand, nextStrand)) {
				strand.addGenomicFeature(genomicFeature);
				if (gfIterator.hasNext()) {
				genomicFeature = gfIterator.next();
				}
				else {
					break;
				}
			}
			strand  = nextStrand;
			nextStrand = nextStrand.getNextStrand(this);
		}
		
		if (gfIterator.hasNext()) {
		Strand lastStrand = strand;
		assert (lastStrand.getReferenceCoordinate() <= genomicFeature.getStart());
			while (gfIterator.hasNext()) {
				lastStrand.addGenomicFeature(genomicFeature);
				genomicFeature = gfIterator.next();
			}
		}
		System.out.println(strands);
	}


	private Strand getFirstStrand() {
		// TODO Auto-generated method stub
		return null;
	}


	public void straightenStrands() {
		LinkedList<Strand> straightStrands = new LinkedList<Strand>();
		while(!strands.isEmpty())
		{
			Strand strand = strands.iterator().next();
			while(strand != null)
			{				
			strands.remove(strand);
			straightStrands.add(strand);
			strand = strand.getNextStrand(this);
			if(strand.equals(straightStrands.peekFirst()))
			{
				
			}
		}
	}
		
	}
	
}
