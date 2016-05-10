package mutation;

import genome.DataContainer;

public class TestMain {

	public static void main(String[] args) {
				
		DataContainer dc = DataContainer.DC;
		double start = System.currentTimeMillis();
		AllMutations am = new AllMutations(dc);
		double end = System.currentTimeMillis();
		System.out.println(end - start);
		for (String k : am.getMutations().keySet()) {
			System.out.println(am.getGenomeMutations(k).size());
		}
	}
	
}
