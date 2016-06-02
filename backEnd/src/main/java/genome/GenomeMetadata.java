package genome;

/**
 * The Class GenomeMetadata.
 */
public class GenomeMetadata {

	/** The lineage. */
	private String lineage;
	
	/** The genome id. */
	private String genomeId;
	
	/**
	 * Instantiates a new genome metadata.
	 *
	 * @param genomeId the genome id
	 * @param lineage the lineage
	 */
	public GenomeMetadata(String genomeId, String lineage) {
		this.genomeId = genomeId;
		this.lineage = lineage;
	}

	/**
	 * Gets the lineage.
	 *
	 * @return the lineage
	 */
	public String getLineage() {
		return lineage;
	}

	/**
	 * Gets the genome id.
	 *
	 * @return the genome id
	 */
	public String getGenomeId() {
		return genomeId;
	}

}
