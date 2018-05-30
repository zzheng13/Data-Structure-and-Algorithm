package project4extraCredit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class test {
	public static void main(String[] args) {
		try {
			loadGraph("USA.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static CityNode[] nodes; // stores a CityNode for each node id
	private static Edge[] adjacencyList; // for each vertex store a linked list of edges;
	// FILL IN: add a HashMap that maps the name of each city to the corresponding integer id
	//private static HashMap hm = new HashMap();//a hashMap whose key is the city, and value is the index(order added into the hashMap)
	
	private static String[] hashTable;//a hashTable to map names of cities to intergers ids.
	
	private static Edge[] allEdge = new Edge[200];//an array to temporarily store all the edges
	private static int many=0;
	private static int num = 0; // length of the CityNode[] nodes
	
	
	public static int hashCode(String s) {
		int i;
		int r = 0;
		char c;
		for (i = 0; i < s.length(); i++){
			c = s.charAt(i);
			r = (int) c + 33*r;
		}
			return Math.abs(r);
	}
	
	public static void doubleHashing(String city,int k, String[] HashTable, int length){
		int hash1 = k % length;
		int hash2 = 17 - (k%17) ;//Common choice: d(k) = q ¨C (k mod q),where q < N, and is prime
		int repeat =1;
		int check = (k%length+repeat*(17-(k%17)))%length;
		while(HashTable[check ]!= null){
			repeat++;
			check = (k%length+repeat*(17-(k%17)))%length;
			System.out.println(check);
			System.out.println("not again!");
		}
		HashTable[check]=city;
		
	}
	
	public static int getCityId(String city, String[] hashTable){
		for(int u =0 ;u < hashTable.length;u++){
			if(hashTable[u].equals(city)){
				return u;
			}
		}
		System.out.println("Sorry city does not exist!");
		return -1;
	}
	
	public static void loadGraph(String filename) throws IOException {	
		// FILL IN CODE
	    try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = "";
			int count = 0;
			int cityK = 0;
			int hash1 =0;
			while((line = br.readLine())!= null){
				line = br.readLine();
				num = Integer.parseInt(line);//number of all the vertices
				nodes = new CityNode[num];
				adjacencyList = new Edge[num];
				hashTable = new String[num];
				line = br.readLine();
				
				
				
				while(!line.equals("ARCS")){
					String[] tokens = line.split(" ");
					cityK = hashCode(tokens[0]);
					hash1 = cityK%num;
					System.out.println("the cityK is: " + cityK);
					System.out.println("the num is: " + num);
					System.out.println("the hash1 is: " + hash1);
					if(hashTable[hash1]==null){
						hashTable[hash1]=tokens[0];
					}else{
						doubleHashing(tokens[0],cityK,hashTable,num);
					}
					CityNode newNode = new CityNode(tokens[0],Double.parseDouble(tokens[1]),Double.parseDouble(tokens[2]));
					nodes[count] = newNode;
					count++;
					line = br.readLine();
				}
				for(int i=0; i< hashTable.length;i++){
					System.out.println("The city is: " + hashTable[i]);
				}
				line = br.readLine();
				
				
				
				
				while(line != null){
					String[] digits = line.split(" ");
					Edge readEdge = new Edge(getCityId(digits[0], hashTable),getCityId(digits[1], hashTable),Integer.parseInt(digits[2]) );
					allEdge[many] = readEdge;
					many++;
					for(int y =0; y< adjacencyList.length;y++){
						adjacencyList[y] = new Edge();
					}
					if(adjacencyList[getCityId(digits[0],hashTable)]==null){
						adjacencyList[getCityId(digits[0],hashTable)]=readEdge;
					}else{
						readEdge.setNext(adjacencyList[getCityId(digits[0],hashTable)]);
						adjacencyList[getCityId(digits[0],hashTable)]=readEdge;
					}
					line = br.readLine();
				}
				
			}
			Arrays.copyOf(allEdge, many);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	   
	}
	
	
}
