package project4part2;

/** The Driver class for project 4 part 2 */
public class Driver {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("No arguments");
			return;
		}
		PrimAlgorithm algo = new PrimAlgorithm(args[0]);
		algo.computeMST(0); // compute MST that starts at vertex 0
		GUIApp app = new GUIApp(algo);

	}
}
