package mutation;

/**
 * 
 * @author Jeffrey Helgers.
 * The mutations that can appear.
 * The field is true when the mutations appears between two Strands.
 * And is false when the mutation start from one Strand.
 */
public enum MutationType {
	SNP(true), 
	DELETION(true), 
	INSERTION(true), 
	TANDEMDUPLICATION(false), 
	INTERSPERSEDDUPLICATION(false), 
	INVERSION(false), 
	TRANSLOCATION(false);
	
	private boolean between;
	
	/**
	 * Create the mutation type.
	 * @param between The between.
	 */
	MutationType(boolean between) {
		this.between = between;
	}
	
	/**
	 * Get the type of mutation.
	 * @return Boolean between.
	 */
	public boolean getBetween() {
		return between;
	}
}
