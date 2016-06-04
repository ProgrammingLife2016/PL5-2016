package genome;

public class GenomicFeature {
	
private int start;
private int end;
private String displayName;


public GenomicFeature(int start, int end, String displayName) {
		this.start = start;
		this.end = end;
		this.displayName = displayName;
	}

public int getStart() {
	return start;
}
public int getEnd() {
	return end;
}
public String getDisplayName() {
	return displayName;
}
public String toString()
{
	return "start: " + start +", end: " + end + ", displayName: " + displayName + "\n";
}
}
