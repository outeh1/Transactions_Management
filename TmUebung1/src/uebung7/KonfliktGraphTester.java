package uebung7;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uebung7.Kommando.Typ;

public class KonfliktGraphTester extends AbstractKonfliktGraphTester {

	private TransGraph Graph;
	private Map<String, Set<Integer>> writeSet;
	private Map<String, Set<Integer>> readSet;
	private Set<Integer> finished;
	private boolean AllesKlar = true;
	DatenelementKommando c = null;

	public KonfliktGraphTester() {
		this.Graph = new TransGraph();
		this.writeSet = new HashMap<String, Set<Integer>>();
		this.readSet = new HashMap<String, Set<Integer>>();
		this.finished = new HashSet<Integer>();
	}

	@Override
	public void process(Kommando k, int i) {
		AllesKlar = true;

		try {
			this.c = (DatenelementKommando) k;
		} catch (Exception e) {

		}

		if (k.getTyp() == Typ.COMMIT) {
			this.commandExecute(k, i);
			this.finished.add(i);
			return;
		}

		else if (k.getTyp() == Typ.READ) {
			putIntoWrites(c);
			putIntoReads(c);
			readSet.get(c.getX()).add(i);
			for (Integer oldWrite : this.writeSet.get(c.getX())) {
				if (oldWrite != i) {
					AllesKlar = Graph.Transput(oldWrite, i);
				}
			}
		}

		else if (k.getTyp() == Typ.WRITE) {
			putIntoWrites(c);
			writeSet.get(c.getX()).add(i);

			// (w,i), für alle w aus write(x)
			for (Integer oldWrite : this.writeSet.get(c.getX())) {
				if (oldWrite != i) {
					AllesKlar = Graph.Transput(oldWrite, i);
				}
			}
			// (r,i), wenn r!=i und r aus read(x)
			for (Integer oldRead : this.readSet.get(c.getX())) {
				if (oldRead != i) {
					if (oldRead != i) {
						AllesKlar = Graph.Transput(oldRead, i);
					}
				}
			}
		}
		if (AllesKlar) {
			// Keine Zyklen
			this.commandExecute(k, i);
		} else {
			// Wenn Zyklen da sind
			this.commandIngnore(k, i);
//			this.removeAbortedTransitions(i);

		}

	}

	private void putIntoReads(DatenelementKommando k) {
		if (!readSet.containsKey(c.getX())) {
			readSet.put(c.getX(), new HashSet<Integer>());
		}
	}

	private void putIntoWrites(DatenelementKommando k) {
		if (!writeSet.containsKey(k.getX())) {
			writeSet.put(k.getX(), new HashSet<Integer>());
		}
	}
}
