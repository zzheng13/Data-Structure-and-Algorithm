package project4part2;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project4part2.CityNode;
import project4part2.Edge;


public class PrimAlgorithm {
	private Graph graph; // stores the graph of CityNode-s and edges connecting
	// them
	private List<Edge> edgesMST = new ArrayList<>(); // edges that belong to
	// MinimalSpanningTree
	private int[] verticesMST;
	private int[][] table;// a table to store the IDs and their cost and path.
	private Edge[] allEdge = new Edge[200];//an array to temporarily store all the edges
	private PriorityQueue heap;
	private int many=0;
	private int num = 0; // length of the CityNode[] nodes
	public PrimAlgorithm(String filename) {
		graph = new Graph();
		try {
			graph.loadGraph(filename);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * A class that represents a graph where nodes are cities (of type CityNode)
	 * and edges connect them and the cost of each edge is the distance between
	 * the cities.
	 */
	public class Graph {

		private CityNode[] nodes;
		private Edge[] adjacencyList; // for each vertex store a linked list of
		// edges;
		private HashMap hm = new HashMap();//a hashMap whose key is the city, and value is the index(order added into the hashMap)
		
		// FILL IN: other instance variables as needed

		/**
		 * Read graph info from the given file, and create nodes and edges of
		 * the graph.
		 * 
		 * @param filename
		 * @throws IOException 
		 * @throws NumberFormatException 
		 */
		public void loadGraph(String filename) throws NumberFormatException, IOException {
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
						Edge readEdge1 = new Edge((int)hm.get(digits[0]),(int)hm.get(digits[1]),Integer.parseInt(digits[2]));
						
						many++;
						if(adjacencyList[(int)hm.get(digits[0])]==null){
							adjacencyList[(int)hm.get(digits[0])]=readEdge1;
						}else{
							readEdge1.setNext(adjacencyList[(int)hm.get(digits[0])]);
							adjacencyList[(int)hm.get(digits[0])]=readEdge1;
						}
						Edge readEdge2 = new Edge((int)hm.get(digits[1]),(int)hm.get(digits[0]),Integer.parseInt(digits[2]));
						many++;
						if(adjacencyList[(int)hm.get(digits[1])]==null){
							adjacencyList[(int)hm.get(digits[1])]=readEdge2;
						}else{
							readEdge2.setNext(adjacencyList[(int)hm.get(digits[1])]);
							adjacencyList[(int)hm.get(digits[1])]=readEdge2;
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

	} // class MapGraph ------------------
	
	
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
	 * Compute minimum spanning tree for this graph using Prim's algorithm,
	 * starting with vertex =  startNode. 
	 * Add edges of MST to edgesMST list.
	 */
	public void createTable(){
		table = new int[num][2];
		heap = new PriorityQueue(num+1);
		for(int d =0 ;d < num;d++){
			if(d == 0){
				table[d][0]=0;
				table[d][1]=-1;
				heap.insert(d, 0);
			}else{
				table[d][0]=Integer.MAX_VALUE;
				table[d][1]=-1;
				heap.insert(d, Integer.MAX_VALUE);
			}
		}
		System.out.println("print table: ");
		for(int y =0;y<num;y++){
			System.out.print(table[y][0]+": ");
			System.out.println(table[y][1]);
	}
	}
	
	public void computeMST(int startNode) {
		// FILL IN CODE
		int removeM =0, count = 0;
		Edge temp;
		verticesMST = new int[num];// an array to store all vertices for MST
		createTable();//create the table for prim's algorithm
		for(int y =0; y < num;y++){// repeat num vertices times
		//while(heap.getLen()!=0){
			System.out.println("printHeap");
			heap.printHeap();
			removeM = heap.removeMin();// this id is no longer in the minheap, thus marked as known
			System.out.println("the vertex removed is______________-: " + removeM);
			verticesMST[count]=removeM;
			count++;
			temp = graph.adjacencyList[removeM];
			while(temp.hasNext()){
				if(heap.checkVisited(temp.getId2())==false){
					if(table[temp.getId2()][0]>temp.getCost()){
						table[temp.getId2()][0]=temp.getCost();
						table[temp.getId2()][1]=removeM;
						heap.reduceKey(temp.getId2(), temp.getCost());
					}
				}
				temp = temp.getNext();
			}
			
		}
		System.out.println("the verticesMST is: ");
		for(int u = 0; u < verticesMST.length;u++){
			System.out.println(verticesMST[u]);
		}
		//convert verticesMST to edgesMST
		for(int y=0;y<verticesMST.length;y++){
			if(verticesMST[y]!=0){
				System.out.println("id1: id2     "+verticesMST[y]+" "+ table[verticesMST[y]][1]);
				Edge newedge = new Edge(verticesMST[y],table[verticesMST[y]][1],table[verticesMST[y]][0]);
				edgesMST.add(newedge);
			}
			
			
		}
		System.out.println("print MST id1: id2: ");
		for(int u =0 ;u < edgesMST.size();u++){
			System.out.print(edgesMST.get(u).getId1()+" ");
			System.out.println(edgesMST.get(u).getId2());
		}
		
	}

	
	
	
	
	
	
	
	
	
	
	// -------------------- Methods needed for GUIApp-------------------
	/**
	 * Used in GUIApp to display the MST. Returns a 2D Array, where each element
	 * represents an edge and is an array of two Points (where this edge starts
	 * and where it is going).
	 */
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

	/**
	 * Used in GUIApp to display the edges of the graph. Returns a 2D array that
	 * contains edge info that is easy for GUI to display. For each edge, we
	 * store an array of two Points, v1 and v2. v1 is the source vertex for this
	 * edge, v2 is the destination vertex. This info can be obtained from the
	 * adjacency list
	 */
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
				temp = temp.getNext();
				
			}
			y++;
		}
		return edges2D;

	

	}

	/**
	 * Used in GUIApp to display the nodes of the graph. Return a list of Points
	 * that correspond to nodes of the graph.
	 */
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

	/**
	 * Used in GUIApp to display the names of the airports. Return the list that
	 * contains the "names" of nodes (corresponding cities)
	 */
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
