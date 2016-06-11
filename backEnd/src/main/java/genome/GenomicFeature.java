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
	 */
	public GenomicFeature() { } 
	
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
		if (start > end) {
			throw new IllegalArgumentException("Illegal bounds, start is greater than end");
		}
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
	 * Checks if this feature overlaps the space between the reference
	 * coordinates of strand1 and strand2 given that they use the same reference
	 * genome as reference coordinate.
	 *
	 * @param start the start
	 * @param end the end
	 * @return true, if successful
	 */
	public boolean overlaps(int start, int end) {

		return !((this.end < start) || (this.start > end));
	}

	/**
	 * Ends between.
	 *
	 * @param start the start
	 * @param end the end
	 * @return true, if successful
	 */
	public boolean endsBetween(int start, int end) {
		return (this.end >= start) && (this.end <= end);
	}
	
	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	public void setStart(int start) {
		this.start = start;
	}
	
	/**
	 * Sets the end.
	 *
	 * @param end the new end
	 */
	public void setEnd(int end) {
		this.end = end;
	}
	
	/**
	 * Sets the display name.
	 *
	 * @param displayName the new display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
