/**
 * 
 */
package uebung7;

/**
 * @author ck
 *
 */
public class KonfliktGraphTesterExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Kommando rA = new ReadKommando("A");
		Kommando wA = new WriteKommando("A");
		Kommando rB = new ReadKommando("B");
		Kommando wB = new WriteKommando("B");
		Kommando commit = Kommando.COMMIT;

//		AbstractKonfliktGraphTester kgt = new KonfliktGraphTester();
//		kgt.process(rA, 1);
//		kgt.process(wA, 1);
//		kgt.process(rB, 1);
//		kgt.process(wB, 1);
//		kgt.process(commit, 1);

//		AbstractKonfliktGraphTester kgt = new KonfliktGraphTester();
//		kgt.process(rA, 1);
//		kgt.process(wA, 1);
//		kgt.process(rA, 3);
//		kgt.process(rB, 1);
//		kgt.process(wB, 1);
//		kgt.process(rA, 2);
//		kgt.process(wA, 2);
//		kgt.process(wB, 3);
//		kgt.process(rB, 2);
//		kgt.process(wB, 2);
//		kgt.process(commit, 1);
//		kgt.process(commit, 2);
//		kgt.process(commit, 3);

		AbstractKonfliktGraphTester kgt = new KonfliktGraphTester();
		kgt.process(rA, 3);
		kgt.process(rA, 1);
		kgt.process(wA, 1);
		kgt.process(rB, 1);
		kgt.process(wB, 1);
		kgt.process(rA, 2);
		kgt.process(wA, 2);
		kgt.process(rB, 2);
		kgt.process(wB, 2);
		kgt.process(wB, 3);
		kgt.process(commit, 1);
		kgt.process(commit, 2);
		kgt.process(commit, 3);

	}

}
