package project4;

import java.io.IOException;

import project4.KruskalAlgorithm.Graph;

/** The Driver class for project 4 part 1 */
public class Driver {
	public static void main(String[] args) {
		// the program takes one  command line argument, the name of the data file
		if (args.length == 0) {
			System.out.println("No arguments provided.");
			return;
		}
		KruskalAlgorithm kruskal = new KruskalAlgorithm(args[0]);
		kruskal.computeMST();
		GUIApp gui = new GUIApp(kruskal); //this will display minimal spanning tree computed by Kruskal
		
	}
}

