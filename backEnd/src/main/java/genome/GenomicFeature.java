package genome;

/**
 * The Class GenomicFeature.
 */
public class GenomicFeature {

	/** The start. */
	private int start;

	/** The end. */
	private int end;

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new genomic feature.
	 *
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @param displayName
	 *            the display name
	 */
	public GenomicFeature(int start, int end, String displayName) {
		this.start = start;
		this.end = end;
		this.displayName = displayName;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 *
	 * @return the string
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "start: " + start + ", end: " + end + ", displayName: " + displayName + "\n";
	}

	/**
	 * Starts between.
	 *
	 * @param strand
	 *            the strand
	 * @param strand2
	 *            the strand2
	 * @return true, if successful
	 */
	public boolean startsBetween(Strand strand, Strand strand2) {
		return strand.getReferenceCoordinate() <= start
				&& strand2.getReferenceCoordinate() >= start;
	}
}
