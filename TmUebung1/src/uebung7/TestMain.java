package uebung7;

public class TestMain {

	public static void main(String[] args) {
		TransGraph TestGraph = new TransGraph();
		TestGraph.Transput(1, 2);
		TestGraph.Transput(2, 3);
		TestGraph.Transput(3, 4);
		System.out.println(TestGraph.Transput(4, 1));
		System.out.println(TestGraph);

		TransGraph Test2Graph = new TransGraph();
		Test2Graph.Transput(1, 2);
		Test2Graph.Transput(2, 4);
		Test2Graph.Transput(3, 4);
		System.out.println(Test2Graph.Transput(2, 1));
		System.out.println(Test2Graph);
	}

}
