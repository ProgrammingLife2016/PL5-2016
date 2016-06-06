package genome;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
// TODO: Auto-generated Javadoc

/**
 * The Class GfBranch.
 */
public class GfBranch {

/** The branch. */
ArrayDeque<ArrayDeque<Strand>> branchDs;

public GfBranch(){
	ArrayDeque<Strand> deque = new ArrayDeque<Strand>();
	branchDs = new ArrayDeque<ArrayDeque<Strand>>();
	branchDs.addLast(deque);
}
/**
 * Gets the last.
 *
 * @return the last
 */
public Strand getLast() {
	// TODO Auto-generated method stub
	return branchDs.getLast().getLast();
}

/**
 * Adds the last.
 *
 * @param strand the strand
 */
public void addLast(Strand strand) {
	branchDs.getLast().addLast(strand);
}
/**
 * Gets the concatenated lists as array list.
 *
 * @return the concatenated lists as array list
 */
public ArrayList<Strand> getBranchAsArrayList() {
	ArrayList<Strand> list = new ArrayList<Strand>();
	for (ArrayDeque<Strand> deque : branchDs) {
		list.addAll(deque);
	}
	return list;
}

/**
 * Prepend.
 *
 * @param branch the branch
 */
public void prepend(GfBranch branch) {
	Iterator<ArrayDeque<Strand>> reverseIterator = branch.getArrayDeques().descendingIterator();
	while(reverseIterator.hasNext()) {
		branchDs.addFirst(reverseIterator.next());
	}
}

/**
 * Gets the array deques.
 *
 * @return the array deques
 */
private ArrayDeque<ArrayDeque<Strand>> getArrayDeques() {
	// TODO Auto-generated method stub
	return branchDs;
}

public String toString()
{
	return getBranchAsArrayList().toString();
}
}
