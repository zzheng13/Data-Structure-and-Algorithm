package project4;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import project4.Edge;

public class KruskalAlgorithm {
	private Graph graph; // stores the graph of CityNode-s and edges connecting them
	private List<Edge> edgesMST = new ArrayList<>(); // edges that belong to MinimalSpanningTree
	private Edge[] allEdge = new Edge[200];//an array to temporarily store all the edges
	private int many=0;
	private int num = 0; // length of the CityNode[] nodes
	public KruskalAlgorithm(String filename) {
		graph = new Graph();
		try {
			graph.loadGraph(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("The file is not found!");
		}
	}
	
	
	/**
	 * Inner class Graph.
	 * A class that represents a graph where nodes are cities (of type CityNode)
	 * and edges connect them and the cost of each edge is the distance between
	 * the cities.
	 */
	public class Graph {

		private CityNode[] nodes; // stores a CityNode for each node id
		private Edge[] adjacencyList; // for each vertex store a linked list of edges;
		// FILL IN: add a HashMap that maps the name of each city to the corresponding integer id
		private HashMap hm = new HashMap();//a hashMap whose key is the city, and value is the index(order added into the hashMap)
		
		/**
		 * Read graph info from the given file, and create nodes and edges of
		 * the graph.
		 * Must catch I/O exceptions inside the method.
		 * @param filename
		 * @throws IOException 
		 */
		public void loadGraph(String filename) throws IOException {	
			// FILL IN CODE
		    try {
				BufferedReader br = new BufferedReader(new FileReader(filename));
				String line = "";
				int count = 0;
				while((line = br.readLine())!= null){
					line = br.readLine();
					num = Integer.parseInt(line);//number of all the vertices
					nodes = new CityNode[num];
					adjacencyList = new Edge[num];
					for(int y =0; y< adjacencyList.length;y++){
						adjacencyList[y] = new Edge();
					}
					
					
					line = br.readLine();
					while(!line.equals("ARCS")){
						String[] tokens = line.split(" ");
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
						many++;
						if(adjacencyList[(int)hm.get(digits[0])]==null){
							adjacencyList[(int)hm.get(digits[0])]=readEdge;
						}else{
							readEdge.setNext(adjacencyList[(int)hm.get(digits[0])]);
							adjacencyList[(int)hm.get(digits[0])]=readEdge;
						}
						line = br.readLine();
					}
				}
				int y =0, ya =0;
				Edge temp;
				//get an array of all the edges from adjacencyList
				while(y< graph.nodes.length){
					temp = graph.adjacencyList[y];
					while(temp.hasNext()){
						allEdge[ya]=temp;
						ya++;
						temp = temp.getNext();
						
					}
					y++;
				}
				
				allEdge = Arrays.copyOf(allEdge, many);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		   
		}

		
	} // inner class Graph finished ------------------
	
	public static void quickSort(Edge arr[]) {
		quickSort(arr, 0, arr.length - 1);
	}

	public static void quickSort(Edge arr[], int low, int high) {
		int pivot;
		if (low < high) {
			pivot = partition(arr, low, high);
			quickSort(arr, low, pivot - 1);
			quickSort(arr, pivot + 1, high);
		}
	}

	static int partition(Edge arr[], int low, int high) {
		Edge pivot;
		Edge tmp;
		int max = high;
		int mid = (low + high) / 2;
		tmp = arr[mid];
		arr[mid] = arr[high];
		arr[high] = tmp;
		pivot = arr[high];
		low--;
		do {
			while (arr[++low].getCost() < pivot.getCost())
				;
			while ((low < high) && (arr[--high].getCost() > pivot.getCost()))
				;
			tmp = arr[low];
			arr[low] = arr[high];
			arr[high] = tmp;
		} while (low < high);
		tmp = arr[low];
		arr[low] = arr[max];
		arr[max] = tmp;
		return low;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Compute minimum spanning tree for this graph. Add edges of Minimal Spanning Tree to
	 * edgesMST list. Should use DisjointSet class.
	 */
	public void computeMST() {
		// FILL IN CODE
		int[] temp = new int[num];
		int x1=0,y1=0;
		DisjointSets rith = new DisjointSets();
		rith.createSets(num);
		//sort the array allEdge
		quickSort(allEdge);
		//test if all edges are added in allEdge[]
		for(int p=0;p<many;p++){
			System.out.println("The cost of each Edge after sorting is: " + allEdge[p].getCost());
			System.out.println("The p is: " + p);
		}
		//check every edge in allEdge[], and then add the shortest path into edgesMST
		for(int p=0;p<many;p++){
			x1 = allEdge[p].getId1();
			y1 = allEdge[p].getId2();
			if(rith.find(x1)!= rith.find(y1)){
				edgesMST.add(allEdge[p]);
				rith.union(rith.find(x1), rith.find(y1));
			}
		}
		for(int u =0; u< edgesMST.size();u++){
			System.out.println("The edge in edgesMST: ");
			System.out.println(edgesMST.get(u).getId1()+", "+ edgesMST.get(u).getId2());
		}
	}


	// -------------------- methods needed for GUIApp-------------------
	/** Used in GUIApp to display the MST. Returns a 2D Array, where each element represents
	 * an edge and is an array of two Points (where this edge starts and where it is going). */
	public Point[][] getMSTEdges() {
		Point[][] edges = new Point[edgesMST.size()][2];
		// FILL IN CODE
		for(int i=0;i<edgesMST.size();i++){
			int city1Num = edgesMST.get(i).getId1();
			int city2Num = edgesMST.get(i).getId2();
			Point p1 = graph.nodes[city1Num].getLocation();
			Point p2 = graph.nodes[city2Num].getLocation();
			edges[i][0]=p1;
			edges[i][1]=p2;
		}
		
		return edges;
	}

	/** Used in GUIApp to display the edges of the graph. Returns a 2D array that contains edge info
	 *  that is easy for GUI to display. For each edge, we store an array of two Points, v1 and v2. 
	 *  v1 is the source vertex for this edge, v2 is the destination vertex. 
	 *  This info can be obtained from the adjacency list. */
	public Point[][] getEdges() {
		//Point[][] edges2D = null; // change!
		
		// FILL IN CODE
		Point[][] edges2D = new Point[allEdge.length][2];
		for(int h =0; h<allEdge.length;h++){
			for(int j =0; j< 2;j++){
				edges2D[h][j]= new Point();
			}
		}
		int city1Num =0, city2Num = 0;
		Point p1,p2;
		Edge temp;
		int y=0, number =0;
		
		
		while(y< graph.nodes.length){
			temp = graph.adjacencyList[y];
			while(temp.hasNext()){
				city1Num = temp.getId1();
				city2Num = temp.getId2();
				p1 = graph.nodes[city1Num].getLocation();
				p2 = graph.nodes[city2Num].getLocation();
				edges2D[number][0]= p1;
				edges2D[number][1]= p2;
				number++;
				System.out.println("temp: " + temp.getCost());
				temp = temp.getNext();
				
			}
			y++;
		}
		return edges2D;

	

	}

	/** Used in GUIApp to display the nodes of the graph. Return a list of Points that
	 *  correspond to nodes of the graph. */
	public Point[] getNodes() {
		Point[] nodes = new Point[graph.nodes.length];
		// FILL IN CODE
		Point p;
		for(int t=0; t < graph.nodes.length;t++){
			p = graph.nodes[t].getLocation();
			nodes[t] = p;
		}
		
		return nodes;
	}

	/** Used in GUIApp to display the names of the cities. Return the list that
	 * contains the "names" of city nodes. */
	public String[] getCities() {
		String[] labels = new String[graph.nodes.length];
		// FILL IN CODE
		String p;
		for(int t=0; t < graph.nodes.length;t++){
			p = graph.nodes[t].getCity();
			labels[t] = p;
		}
		return labels;

	}
	

}