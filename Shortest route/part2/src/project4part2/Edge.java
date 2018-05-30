package project4part2;
/**
 * Edge class represents an edge in the adjacency list of the graph. Implements
 * Comparable. Compares Edges based on the cost.
 */
class Edge implements Comparable<Edge> {
	// FILL IN CODE: add variables and a constructor
	// FILL IN: getters and setters as needed
	private int id1; //source vertex
	private int id2; //destination vertex 
	private int cost;
	private Edge next;
		
		
	Edge(){
			
	}
		
		
	Edge(int id1, int id2, int cost){
		// FILL IN CODE
		this.id1 = id1;
		this.id2 = id2;
		this.cost = cost;
		this.next = null;
	}
		
	// FILL IN CODE: add getters and setters as needed
	public void setNext(Edge newEdge){
		this.next = newEdge;
	}
	
	public int getId1(){
		return this.id1;
	}
	
	public int getId2(){
		return this.id2;
	}
	
	public boolean hasNext(){
		if(this.next == null){
			return false;
		}else{
			return true;
		}
	}
	
	public int getCost(){
		return this.cost;
	}
	public Edge getNext(){
		return this.next;
	}
	/** Compare edges based on their cost */
	@Override
	public int compareTo(Edge o) {
		// FILL IN CODE
		if(this.cost < o.cost){
			return -1;
		}else if(this.cost == o.cost){
			return 0;
		}else{
			return 1;
		}
	}
		
}
