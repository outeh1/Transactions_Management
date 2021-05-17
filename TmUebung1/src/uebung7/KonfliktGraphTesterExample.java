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

		AbstractKonfliktGraphTester kgt1 = new KonfliktGraphTester();
		kgt1.process(rA, 1);
		kgt1.process(wA, 1);
		kgt1.process(rB, 1);
		kgt1.process(wB, 1);
		kgt1.process(commit, 1);

		AbstractKonfliktGraphTester kgt2 = new KonfliktGraphTester();
		kgt2.process(rA, 1);
		kgt2.process(wA, 1);
		kgt2.process(rA, 3);
		kgt2.process(rB, 1);
		kgt2.process(wB, 1);
		kgt2.process(rA, 2);
		kgt2.process(wA, 2);
		kgt2.process(wB, 3);
		kgt2.process(rB, 2);
		kgt2.process(wB, 2);
		kgt2.process(commit, 1);
		kgt2.process(commit, 2);
		kgt2.process(commit, 3);

		AbstractKonfliktGraphTester kgt3 = new KonfliktGraphTester();
		kgt3.process(rA, 3);
		kgt3.process(rA, 1);
		kgt3.process(wA, 1);
		kgt3.process(rB, 1);
		kgt3.process(wB, 1);
		kgt3.process(rA, 2);
		kgt3.process(wA, 2);
		kgt3.process(rB, 2);
		kgt3.process(wB, 2);
		kgt3.process(wB, 3);
		kgt3.process(commit, 1);
		kgt3.process(commit, 2);
		kgt3.process(commit, 3);

	}

}
