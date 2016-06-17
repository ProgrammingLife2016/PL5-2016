package genomefeature;

/**
 * The Class GenomicFeature. This class represents a genomic feature.
 */
public class GenomicFeature {

    /**
     * The start coordinate of the genomic feature.
     */
    private int start;

    /**
     * The end coordinate of the genomic feature.
     */
    private int end;

    /**
     * The display name of the genomic feature.
     */
    private String displayName;

    /**
     * Instantiates a new g feature match.
     */
    public GenomicFeature() { }

    /**
     * Instantiates a new genomic feature.
     *
     * @param start       the start coordinate
     * @param end         the end coordinate
     * @param displayName the display name
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
     * Gets the start coordinate.
     *
     * @return the start coordinate
     */
    public int getStart() {
        return start;
    }

    /**
     * Sets the start coordinate.
     *
     * @param start the new start coordinate.
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * Gets the end coordinate.
     *
     * @return the end coordinate
     */
    public int getEnd() {
        return end;
    }

    /**
     * Sets the end coordinate.
     *
     * @param end the new end coordinate.
     */
    public void setEnd(int end) {
        this.end = end;
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
     * Sets the display name.
     *
     * @param displayName the new display name.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
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
     * @param start the start coordinate
     * @param end   the end coordinate
     * @return true, if successful
     */
    public boolean overlaps(int start, int end) {
        return !(this.end < start || this.start > end);
    }

    /**
     * Checks if this feature ends between the start and end coordinates given as arguments to the
     * function.
     *
     * @param start the start coordinate used for the check
     * @param end   the end coordinate used for the check
     * @return true, if successful
     */
    public boolean endsBetween(int start, int end) {
        return this.end >= start && this.end <= end;
    }
}
