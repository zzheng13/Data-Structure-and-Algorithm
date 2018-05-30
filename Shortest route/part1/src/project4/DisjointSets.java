package project4;

/** A class that represents the Disjoint Sets data structure. Must use Union by Rank and Path compression. */
public class DisjointSets {
	// array of "parent" indices. Stores either the index of the parent or (-height) for the root
	private int[] parent; 

	public void createSets(int n) {
		// FILL IN CODE
		parent = new int[n];
		for(int i=0; i<parent.length;i++){
			parent[i] = -1;
		}
	}

	/**
	 * Returns the root of the "tree" that x belongs to. Uses path compression
	 * heuristic.
	 */
	public int find(int x) {
		// FILL IN CODE
		if (parent[x] < 0){ // x is the root
			 return x;
		}else {
			 parent[x] = find(parent[x]);
			 return parent[x];
		}
	}

	/** Merges two sets: the one with x and the one with y. Should use Union by Rank heuristic. */
	public void union(int x, int y) {
		// FILL IN CODE
		int rootx, rooty;
		rootx = find(x);
		rooty = find(y);
		if (parent[rootx] < parent[rooty]) {
			parent[rooty] = rootx;
		} else {
			if (parent[rootx] == parent[rooty]){
				parent[rooty]--;
			}
			parent[rootx] = rooty;
		}
	}
	
	public int[] getPcopy(){
		int[] copy = new int[parent.length];
		copy = parent;
		return copy;
	}
	
	public void printParent(){
		for (int i =0; i< parent.length ;i++){
			System.out.println(parent[i]);
		}
	}

}

