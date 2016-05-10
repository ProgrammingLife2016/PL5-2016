package genome;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class TestMain {

	public static void main(String[] args) {
		DataContainer dc = DataContainer.DC;
		Collection<Genome> genomes = dc.getGenomes().values();
		Iterator<Genome> i = genomes.iterator();
		Genome base = i.next();
		while (i.hasNext()) {
			Genome other = i.next();
			ComputeMutation cm = new ComputeMutation(base, other);
			cm.t();
		}
	}

}
