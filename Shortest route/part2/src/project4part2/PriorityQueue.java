package project4part2;

import java.util.Arrays;

/** A min-heap implementation of a priority queue. */
public class PriorityQueue {
	private PriorityQueueElement[] heap; // min heap
	private int[] positions; // maps each node id to the index of the minheap
	private int maxsize;
	private int size;
	
	/** Constructor. Takes the size of the heap array as a parameter */
	public PriorityQueue(int max) {//max is the size of min heap, in this case: 21
		maxsize = max;
		heap = new PriorityQueueElement[maxsize];
		size = 0;
		for(int r= 0; r< maxsize;r++){
			heap[r] = new PriorityQueueElement();
		}
		heap[0].setPriority(Integer.MIN_VALUE);
		positions = new int[max-1];
		for(int u =0; u< max-1;u++){
			positions[u]=u+1;
		}
	}
	
	public int getPositions(int cost){
		return positions[cost];
	}
	// FILL IN: add getters/setters as needed
	
	/**
	 * Insert (priority, nodeId) element into priority queue
	 */
	
	/** Return the index of the left child */
	private int leftChild(int pos) {
		return 2 * pos;
	}

	/** Return the index of the right child */
	private int rightChild(int pos) {
		return 2 * pos + 1;
	}

	/** Return the index of the parent */
	private int parent(int pos) {
		return pos / 2;
	}

	/** Returns true if the node in a given position is a leaf */
	private boolean isLeaf(int pos) {
		return ((pos > size / 2) && (pos <= size));
	}

	/** Swap given elements: one at index pos1, another at index pos2 */
	private void swap(int pos1, int pos2) {
		PriorityQueueElement tmp;
		tmp = heap[pos1];
		heap[pos1] = heap[pos2];
		heap[pos2] = tmp;
		//change the positions[]
		positions[heap[pos2].getnodeId()]= pos2;
		positions[heap[pos1].getnodeId()]=pos1;
		
	}

	
	public void insert(int nodeId, int priority) {
		PriorityQueueElement elem = new PriorityQueueElement(priority, nodeId);
		size++;
		heap[size] = elem;
		
		int current = size;
		while (heap[current].compareTo(heap[parent(current)])<0) {
			swap(current, parent(current));
			current = parent(current);
		}
	}
	public int getLen(){
		return size;
	}
	/**
	 * Remove the minimum element from the min heap (the element on top) and
	 * return its value.
	 */
	public int removeMin() {
		swap(1, size); // swap the end of the heap into the root
		size--;  	   // removed the end of the heap   
		// fix the heap property - push down as needed
		if (size != 0)
			pushdown(1);
		return heap[size + 1].getnodeId();
	}
	
	// a helper method to get the heap index according to the nodeId	
	private void pushdown(int position) {
		int smallestchild;
		while (!isLeaf(position)) {
		        smallestchild = leftChild(position); // set the smallest child to left child
			if ((smallestchild < size) && (heap[smallestchild].compareTo(heap[smallestchild + 1]))>0)
				smallestchild = smallestchild + 1; // right child was smaller, so smallest child = right child

			// the value of the smallest child is less than value of current,
			// the heap is already valid
			if (heap[position].compareTo( heap[smallestchild])<=0) 
				return;
			swap(position, smallestchild);
			position = smallestchild;
		}
	}
	
	
	
	
	private void bubbleUp(int current){
		//int current=length;
	    while( heap[current].compareTo(heap[parent(current)])<0){
	    	swap(current,parent(current));
	    	current = parent(current);
	    }
	
	}

	/**
	 * Take a nodeId and a new priority for this nodeId. 
	 * new priority will be <= old priority. Update the min heap
	 * accordingly.
	 */
	public void reduceKey(int nodeId, int newPriority) {
		// FILL IN CODE
		if(newPriority <= heap[positions[nodeId]].getPriority()){
			heap[positions[nodeId]].setPriority(newPriority);
			bubbleUp(positions[nodeId]);
		}
	}
	
	// FILL IN: Add other helper methods as needed 
	// (such as "bubble down", etc.) */
	
	public boolean checkVisited(int id){
		if(positions[id]>size){
			return true;
		}else{
			return false;
		}
	}
	public void printPos(){
		for(int u =0; u< positions.length;u++){
			System.out.println(positions[u]);
		}
	}
	public void printHeap(){
		System.out.println("the lenght in printHeap: " + size);
		for(int u =1; u< size+1;u++){
			System.out.println(heap[u].getnodeId()+": "+ heap[u].getPriority());
		}
	}
}

