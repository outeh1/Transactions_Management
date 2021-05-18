package uebung7;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uebung7.Kommando.Typ;

public class KonfliktGraphTester extends AbstractKonfliktGraphTester {

	private TransGraph Graph;
	private Map<String, Set<Integer>> Writes;
	private Map<String, Set<Integer>> Reads;
	private Set<Integer> finished;

	public KonfliktGraphTester() {
		this.Graph = new TransGraph();
		this.Writes = new HashMap<String, Set<Integer>>();
		this.Reads = new HashMap<String, Set<Integer>>();
		this.finished = new HashSet<Integer>();
	}

	@Override
	public void process(Kommando k, int i) {

		DatenelementKommando c;
		if (k.getTyp() == Typ.COMMIT) {
			this.commandExecute(k, i);
			this.finished.add(i);
			return;
		}

		else if (k.getTyp() == Typ.READ) {
			c = (DatenelementKommando) k;
			putIntoWrites(c);
			putIntoReads(c);
			Reads.get(c.getX()).add(i);
			for (Integer oldWrite : this.Writes.get(c.getX())) {
				if (oldWrite != i) {
					if (!Graph.Transput(oldWrite, i)) {
						this.commandIngnore(k, i);
						return;
					}
				}
			}
		}

		else if (k.getTyp() == Typ.WRITE) {
			c = (DatenelementKommando) k;
			putIntoWrites(c);
			Writes.get(c.getX()).add(i);

			// (w,i), für alle w aus write(x)
			for (Integer oldWrite : this.Writes.get(c.getX())) {
				if (oldWrite != i) {
					if (!Graph.Transput(oldWrite, i)) {
						this.commandIngnore(k, i);
						return;
					}
				}
			}
			// (r,i), wenn r!=i und r aus read(x)
			for (Integer oldRead : this.Reads.get(c.getX())) {
				if (oldRead != i) {
					if (oldRead != i) {
						if (!Graph.Transput(oldRead, i)) {
							this.commandIngnore(k, i);
							return;
						}
					}
				}
			}
		}

		// Keine Zyklen
		this.commandExecute(k, i);

//			this.removeAbortedTransitions(i); fehlt noch bei allen ignore commands

	}

	private void putIntoReads(DatenelementKommando c) {
		if (!Reads.containsKey(c.getX())) {
			Reads.put(c.getX(), new HashSet<Integer>());
		}
	}

	private void putIntoWrites(DatenelementKommando c) {
		if (!Writes.containsKey(c.getX())) {
			Writes.put(c.getX(), new HashSet<Integer>());
		}
	}
}
