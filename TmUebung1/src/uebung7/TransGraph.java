package uebung7;

import java.util.HashMap;
import java.util.HashSet;

public class TransGraph {
	private HashMap<Integer, HashSet<Integer>> Order = new HashMap<Integer, HashSet<Integer>>();
	private HashMap<Integer, HashSet<Integer>> TempOrder = new HashMap<Integer, HashSet<Integer>>();

	public TransGraph() {
		super();
	}

	/**
	 * Packt etwas in den TransGraph
	 * 
	 * @param tFrom
	 * @param tTo
	 * @return true wenn es Erfolgreich hinzugefügt wurde, sonst false
	 */
	public boolean Transput(Integer tFrom, Integer tTo) {
		if (tFrom == tTo) {
			return false;
		}
		HashSet<Integer> newTos = new HashSet<Integer>();
		newTos.add(tTo);
		if (TempOrder.putIfAbsent(tFrom, newTos) != null) {
			HashSet<Integer> oldTos = TempOrder.get(tFrom);
			if (newTos.contains(tFrom)) {
				TempOrder = new HashMap<Integer, HashSet<Integer>>();
				TempOrder.putAll(Order);
				return false;
			} else {
				newTos.addAll(oldTos);
				TempOrder.put(tFrom, newTos);
			}
		} else {
			HashSet<Integer> TransTos = Order.get(tTo);
			if (TransTos == null) {
				Order.putAll(TempOrder);
				return true;
			} else {
				for (Integer TransTo : TransTos) {
					if (Transput(tFrom, TransTo) == false) {
						TempOrder = new HashMap<Integer, HashSet<Integer>>();
						TempOrder.putAll(Order);
						return false;
					}
				}
				return true;
			}
		}
		// Dieses Return sollte nie erreicht werden
		HashSet<Integer> TransTos = Order.get(tTo);
		if (TransTos == null) {
			Order.putAll(TempOrder);
			return true;
		} else {
			for (Integer TransTo : TransTos) {
				if (Transput(tFrom, TransTo) == false) {
					TempOrder = new HashMap<Integer, HashSet<Integer>>();
					TempOrder.putAll(Order);
					return false;
				}
			}
			return true;
		}
	}

	public String toString() {
		return Order.toString();
	}
}

//private HashSet<IntTuple> Graph = new HashSet<IntTuple>();
//
//public boolean TransPut(IntTuple newRel) {
//	Graph.add(newRel);
//		ArrayList<Integer> newTos = getAllTo();
//		if (newTos.isEmpty()) {
//			return true;
//		} else
//			for (Integer newTo : newTos) {
//				TransPut(newnewRel.getFrom(), newTo);
//			}
//
//	
//	return false;
//}
//
//private ArrayList<Integer> getAllTo() {
//	// TODO Auto-generated method stub
//	return null;
//}