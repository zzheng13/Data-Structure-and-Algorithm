package project2;

import java.lang.reflect.Array;
/** Class SuffixTree. Has methods to build a suffix tree for a given string and use it to find suffixes, substrings etc.  
 *  You may add additional helper methods (in fact, it is recommended), but may not change the signatures of the given methods. */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class SuffixTree {
	private Node root; // the root of the suffix tree
	private final static int ASCII = 97; // offset of letter 'a' in the ASCII table
	private int u=-1;
	/** Constructor of SuffixTree. The root contains an empty string and index = -1 */
	public SuffixTree() {
		root = new Node("", -1);
	}

	/** Create a SuffixTree for a given string s.
	 *  Iterate backwards, and call insert method to insert each suffix into the tree. */
	public SuffixTree(String s) {
		String newS = s + "$"; // add $ to the end
		// Iterate backwards and insert into the suffix tree
		// FILL IN CODE
		insert("",-1);//first insert the root
		int index = newS.length()-1;
		while(index!=-1){
			insert(newS.substring(index),index);//insert each suffix backwards
			index = index - 1;
		}

	}

	// ------------------ Inner class: A node of the SuffixTree ------------------------
	private class Node {
		private String string; // a string stored at the node
		private Node[] children; // an array of children
		private int index; // an index in the original string where this suffix starts

		/** Constructor for Node */
		public Node(String string, int index) {
			children = new Node[27]; // 26 letters + "$"
			this.string = string;
			this.index = index;
		}
	}  // ------------------------------------------------------------------------------

	/** Insert a new suffix (that starts at index ind in the string) into the suffix tree */
	public void insert(String word, int ind) {
		root = insert(word.toLowerCase(), ind, root);
	}

	/**
	 * Insert a new suffix that starts at index = ind, into the suffix tree with
	 * the given root.
	 */
	private Node insert(String word, int ind, Node tree) {
		if (tree == null) {
			System.out.println("Created a new node with " + word);
			Node temp = new Node(word, ind);
			return temp;
		}
		// FILL IN CODE - you are required to use the algorithm we discussed in class
		int childInd = getChildIndex(word);
		if (tree.children[childInd]==null){//if the index of the first letter of the word to insert in the array is empty
			Node wordNode = new Node(word,ind);
			tree.children[childInd]=wordNode;
			}
			else{
				String common = commonPrefix(word,tree.children[childInd].string);
				int numcommon = common.length();
				String newWord = word.substring(numcommon);//the word left after subtract the common prefix. 
				if(common.equals(tree.children[childInd].string)){
					insert(newWord,ind,tree.children[childInd]);
					
				}else{
					Node temp = tree.children[childInd];
					String neworigword = temp.string.substring(numcommon);//the original word from the index left after subtract the common prefix.
					Node newwordNode = new Node(common,-1);
					tree.children [childInd]=newwordNode;
					insert(neworigword,temp.index,newwordNode);//recursion
					insert(newWord,ind,newwordNode);	
				}
				
		}
		//!!!!!comments:return the whole tree instead of the subtree.
		return tree; // don't forget to change it
	}

	/** A helper method: returns the longest common prefix of word1 and word2.
	 *  Example: if word1 = "banana" and word2 = "band", the  longest common prefix is "ban". */
		private String commonPrefix(String word1, String word2) {
			// FILL IN CODE
			String a = word1.toLowerCase();
			String b = word2.toLowerCase();
		    int minLength = Math.min(a.length(), b.length());
		    for (int i = 0; i < minLength; i++) {
		        if (a.charAt(i) != b.charAt(i)) {
		            return a.substring(0, i);
		        }
		    }
		    return a.substring(0, minLength);// don't forget to change it
		
	}

	
	/** Return a suffix tree as a string in human readable form (using preorder traversal and indentations). For the root, 
	 * print "Root" instead of an empty string. See project description for details. */
	public String toString() {
		return toString(root, 0);
	}

	/**
	 * A private recursive method that returns the string representation of the suffix tree with the given root
	 * with i indentations. If i = 0, the value at the root should not be indented. 
	 * If i = 1, there should be one space printed before the value at the root.
	 * If i = 2, there should be two spaces etc..
	 * Hint: Use StringBuilder or StringBuffer (using "+" for concatenation is very slow).
	 */
	private String toString(Node tree, int i) {
		StringBuilder blr = new StringBuilder();
		int k=0, count =0;
		if (tree == null){
			return "";
		}
		if(tree.string.equals("")){//.equals
			blr.append("Root");//Root
			blr.append(System.lineSeparator());
		}else{
			count = i;
			while(count!=0){
				count--;
				blr.append(" ");
			}//append i numbers of white spaces
			blr.append(tree.string);
			if (tree.index != -1){
				blr.append(": ");
				blr.append(tree.index);
			}
			blr.append(System.lineSeparator());
		}
		//in the else statement
		//for loop itrate over all children
			for (k = 0; k < tree.children.length; k++){
				if(tree.children[k]!=null){
					blr.append(toString(tree.children[k], i+1));
				}
			}
		
		return blr.toString();
	}
	
	
	
	public void testToString(){//hardcode a suffix tree to test other functions
		Node first = new Node("",-1);
		first.children[0]= new Node("a",-1);
		first.children[1]= new Node("banana$",0);
		first.children[13]= new Node("na",-1);
		first.children[26]=new Node("$",6);
		first.children[13].children[13]=new Node("na$",2);
		first.children[13].children[26]=new Node("$",4);
		first.children[0].children[13] = new Node("na",-1);
		first.children[0].children[26] = new Node("$",5);
		first.children[0].children[13].children[13]= new Node("na$",1);
		first.children[0].children[13].children[26]= new Node("$",3);
		
		System.out.println(toString(first,0));
		

		
	}
	/** Return an index in the array of children that corresponds to the first letter of the given word. If 
	 * the first letter is 'a', the method returns 0; if the first letter is 'b', the method returns 1 etc.
	 * '$' is handled separately: the index of the '$' child is 26 (the last one in the array).
	 * @param word
	 * @return
	 */
	public int getChildIndex(String word) {
		int childIndex = word.charAt(0) - ASCII;
		if (word.charAt(0) == '$')
			childIndex = 26;
		return childIndex;
	}

	
	/**
	 * Return true if a string represented by a given suffix tree contains a given
	 * word. Return false otherwise.
	 */
	public boolean containsSubstring(String word) {

		return containsSubstring(word, root);
	}
	
	/**
	 * Return true if a string represented by the suffix tree with the given root,
	 * contains a given word. Return false otherwise. 
	 * Should be recursive and make use of the suffix tree.
	 */
	private void testsamestring(){//my own helper methods to help distinguish the situation when the word after subtracting the common string is the same with
		//the string of the tree, for instance the 'aa' or 'nana'.
		u = 0;
		
		
	}
	private boolean containsSubstring(String word, Node tree) {
		// FILL IN CODE
		boolean what = false;
		if(tree == null){
			return false;
		}else if(word.equals("")){
				return false;
			
		}else{
			if(tree.string.equals(word)){
				if(u == -1){
					return true;
					
				}
			}
		
				int wordInd = getChildIndex(word);
				if(tree.children[wordInd]==null){
					return false;
				}else{
					if(tree.children[wordInd].string.length()>=word.length()){
						what = tree.children[wordInd].string.contains(word);
					}else{
						boolean empty = true;
						for(int k =0; k < tree.children[wordInd].children.length;k++){
							if(tree.children[wordInd].children[k]!=null){
								empty = false;
							}
						}
						if(empty == true){
							return false;
						}else{
							String common = commonPrefix(word, tree.children[wordInd].string);
							int numcommon = common.length();
							String newWord = word.substring(numcommon);
							if(newWord.equals(tree.children[wordInd].string)){
								testsamestring();
								what = containsSubstring(newWord,tree.children[wordInd]);
							}else{
								what = containsSubstring(newWord,tree.children[wordInd]);
							}
							
						}
					}
					}
				}
	
			
		return what;
		}


		
	
	
	/**
	 * Check if a string represented by a given suffix tree contains a given
	 * word, and if yes, return the list of indices where each occurrence of word starts. 
	 * Should be sorted in ascending order.
	 * Example: if the suffix tree is built for the word "banana" and we call this method on "ana",
	 * the method should return [1, 3]. 
	 */
	public List<Integer> getSubstringIndices(String word) {

		List<Integer> indicesOccurrences = getSubstringIndices(word, root);
		return indicesOccurrences;
	}
	
	private List<Integer> getSubstringIndices(String word, Node tree) {
		List<Integer> indices = new ArrayList<Integer>();
		// FILL IN CODE
		if(tree == null){//if the tree itself is empty
			return indices;
		}else if(word.equals("")){//if the word is ""
			return indices;
		}else{//if both the tree and the word are valid
			if(containsSubstring(word,tree)==true){//if the sufix tree does contains the word
				if(tree.string.equals(word)){//if the string of the root of the tree the the word
					if(u==-1){
						indices.add(tree.index);
					}
					
				}
					int wordInd = getChildIndex(word);
					if(tree.children[wordInd]==null){//if index of the first letter of the word in the list is empty
					}else{//else
						String common = commonPrefix(word, tree.children[wordInd].string);
						
						if(!tree.children[wordInd].string.contains(common)){
						}else{//if the index of the first letter in the tree has same substring with word
								if(tree.children[wordInd].string.length()>=word.length()){
									
						

									if(tree.children[wordInd].string.contains(word)){//if string contains word
										boolean empty=true;
										for(int l =0; l< tree.children[wordInd].children.length;l++){
											if(tree.children[wordInd].children[l]!=null){
												empty = false;
											}
										}
										if(empty == true){//if the index of the first letter in the list only has string
											indices.add(tree.children[wordInd].index);
										}else{//
											testsamestring();
											indices.addAll(getAllleaves(tree.children[wordInd]));
										}
										
									}else{
									}
								}else {
									int numcommon = common.length();
									String newWord = word.substring(numcommon);
									indices.addAll(getSubstringIndices( newWord, tree.children[wordInd]));
									}
							}
						}
					
					
				
			}
		}
		Collections.sort(indices);//sort the indices
		return indices; 
	}
	

	
	/**
	 * Return the number of occurrences of a given word in the string, represented by the suffix tree
	 */
	public List<Integer> getAllleaves(Node tree){
		List<Integer> indices = new ArrayList<Integer>();
		if(tree == null){
			
		}else{
			for (int k =0; k< tree.children.length;k++){
				if(tree.children[k]!=null){
					if(tree.children[k].index!=-1){
						indices.add(tree.children[k].index);
					}else{
						indices.addAll(getAllleaves(tree.children[k]));
					}
				}
				
			}
		}
		return indices;
		
	}
	public void testLeaf(){
		System.out.println(getAllleaves(root));
	}
	
	public int numOccurrences(String word) {
		// FILL IN CODE
		// Hint: you can call getSubstringIndices method you wrote above
		int num = getSubstringIndices(word).size();
		return num; // // don't forget to change
	}
	
	
	/** If the suffix tree contains a given suffix, return the index where it starts in the original string, 
	 * otherwise return -1.  */
	public int containsSuffix(String suffix) {
		return containsSuffix(suffix, root);
		
	}

	/** If a given suffix tree contains a given suffix, return its' index, otherwise return -1. 
	 * Should be recursive and make use of the suffix tree. */
	private int containsSuffix(String suffix, Node tree) {
		// FILL IN CODE
		int what;
				if(tree == null){
					return -1;
				}else if(suffix.equals("")){
					return -1;
				}else{
					if(tree.string.equals(suffix)){
						return tree.index;
					}else{
						int wordInd = getChildIndex(suffix);
						if(tree.children[wordInd]==null){
							return -1;
						}else{
							String common = commonPrefix(suffix, tree.children[wordInd].string);
							if(!common.equals(tree.children[wordInd].string)){
								return -1;
							}else{
								boolean empty=true;
								for(int k=0;k<tree.children[wordInd].children.length;k++){
									if(tree.children[wordInd].children[k]!=null){
										empty = false;
									}
								}
								if(empty == true){
									return tree.children[wordInd].index;
								}else{
								int numcommon = common.length();
								String newWord = suffix.substring(numcommon);
								what = containsSuffix(newWord,tree.children[wordInd]);
								}
							}
						}
						
					}
				
				}
				return what;
		
	}
}
