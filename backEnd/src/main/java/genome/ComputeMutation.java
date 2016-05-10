package genome;

import java.util.ArrayList;

public class ComputeMutation {

	private Genome base;
	private Genome other;
	private ArrayList<Node> baseNodes;
	private ArrayList<Node> otherNodes;
	
	public ComputeMutation(Genome base, Genome other) {
		this.base = base;
		this.other = other;
		this.baseNodes = base.getNodes();
		this.otherNodes = other.getNodes();
		System.out.println(baseNodes.size());
		System.out.println(otherNodes.size());
	}
	
	public void t() {
		ArrayList<Node> common = new ArrayList<Node>(baseNodes);
		common.retainAll(otherNodes);
		for (int i = 0; i < common.size(); i++) {
			Node current = common.get(i);
			int basePlace = baseNodes.indexOf(current);
			int otherPlace = otherNodes.indexOf(current);
			getMutation(basePlace, otherPlace);
		}
	}
	
	public boolean getMutation(int basePlace, int otherPlace) {
		if (basePlace < (baseNodes.size() - 1) && otherPlace < (otherNodes.size() - 1)) {
			if (baseNodes.get(basePlace + 1).equals(otherNodes.get(otherPlace + 1))) {
				return false;
			} else {
				return (testInsertion(basePlace, otherPlace) || testDeletion(basePlace, otherPlace));
			}
		}
		return false;
	}
	
	public boolean testInsertion(int basePlace, int otherPlace) {
		if(otherPlace < (otherNodes.size() - 2) &&
				baseNodes.get(basePlace + 1).equals(otherNodes.get(otherPlace + 2))) {
			System.out.println("insertion");
			baseNodes.get(basePlace).setInsertion(true);
			return true;
		}
		return false;
	}
	
	public boolean testDeletion(int basePlace, int otherPlace) {
		if(basePlace < (baseNodes.size() - 2) &&
				baseNodes.get(basePlace + 2).equals(otherNodes.get(otherPlace + 1))) {
			System.out.println("deletion");
			baseNodes.get(basePlace + 1).setDeletion(true);
			return true;
		}
		return false;
	}
}
