package project4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class test {
	public static void main(String[] args) {
//		DisjointSets first = new DisjointSets();
//		first.createSets(6);
//		first.union(2, 3);
//		first.union(4, 5);
//		first.union(0, 2);
//		first.union(0, 4);
//		first.printParent();
//		if (args.length == 0) {
//			System.out.println("No arguments provided.");
//			return;
//		}
//		try {
//			loadGraph(args[0]);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		int[] ha = new int[3];
		System.out.println(ha[0]);
	}
	
	public static void loadGraph(String filename) throws IOException {
		// FILL IN CODE
	    try {
	    	CityNode[] nodes; // stores a CityNode for each node id
			Edge[] adjacencyList; // for each vertex store a linked list of edges;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = "";
			int num = 0, count = 0, another = 0;
			HashMap hm = new HashMap();
			while((line = br.readLine())!= null){
				line = br.readLine();
				System.out.println("The line right now is: " + line);
				num = Integer.parseInt(line);
				System.out.println("The number of cities is: " + num);
				nodes = new CityNode[num];
				adjacencyList = new Edge[50];
				line = br.readLine();
				System.out.println("The line to be split is: " + line);
				while(!line.equals("ARCS")){
					String[] tokens = line.split(" ");
					System.out.println("tokens[0]: " + tokens[0]);
					if(!hm.containsKey(tokens[0])){
					    hm.put(tokens[0], count);
					}
					CityNode newNode = new CityNode(tokens[0],Double.parseDouble(tokens[1]),Double.parseDouble(tokens[2]));
					nodes[count] = newNode;
					count++;
					line = br.readLine();
				}
				line = br.readLine();
				while(line != null){
					String[] digits = line.split(" ");
					Edge readEdge = new Edge((int)hm.get(digits[0]),(int)hm.get(digits[1]),Integer.parseInt(digits[2]));
					adjacencyList[another] = readEdge;
					another ++;
					line = br.readLine();
				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	   
	}
}
