package database;

public class GenomeMetadata {

	private String lineage;
	private String genomeId;
	
	public GenomeMetadata(String genomeId, String lineage) {
		this.genomeId = genomeId;
		this.lineage = lineage;
	}

	public String getLineage() {
		// TODO Auto-generated method stub
		return lineage;
	}

	public String getGenomeId() {
		return genomeId;
	}

}
