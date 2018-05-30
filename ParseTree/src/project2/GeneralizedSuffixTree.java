package project2;

/** A class that stores a generalizes suffix tree for a given list of strings and a given  list of terminal markers. */

import java.util.ArrayList;


public class GeneralizedSuffixTree {
	private final static int ASCII = 97;
	private Node root; // the root of the generalized suffix tree
	private ArrayList<String> markers = null; // a list of markers to use after each string
	private ArrayList<String> strings = null; // a list of strings for this generalized suffix tree  - you do not have to use it
	private String concatString = ""; // The string that is "all strings concatenated into one"
	int markersSize =0;
	/** Create a GeneralizedSuffixTree for a given list of strings, with the given terminal markers.
	 *  Size of the markers array should be the same as the strings array */
	public GeneralizedSuffixTree(ArrayList<String> strings, ArrayList<String> markers) {
		// FILL IN CODE
		// Concatenate all strings into one, separated by markers given in the array
		// Iterate backwards over the suffixes of the concatString and call insert on each suffix
		this.markers = markers;
		this.strings = strings;
		System.out.println("the strings is: " + strings.toString());
		System.out.println("the markers is: " + markers.toString());
		StringBuilder blr = new StringBuilder();
		if(strings.size()==markers.size()){
			markersSize = markers.size();
			int num = strings.size();
			for(int k =0;k< num;k++){
				blr.append(concatString.concat(strings.get(k)));
				blr.append(concatString.concat(markers.get(k)));
				
			}
			String newS = blr.toString();
			
			// Iterate backwards and insert into the suffix tree
			// FILL IN CODE
			insert("",-1);
			int index = newS.length()-1;
			while(index!=-1){
				insert(newS.substring(index),index);
				index = index - 1;
			}

		

		}else{
			System.out.println("The length of the strings is: "+ strings.size());
			System.out.println("The length of the markers is: "+ markers.size());
			System.out.println("The length of the two ArrayList does not match!");
		}
		
	}

	// ------------------ A node of the GeneralizedSuffixTree ------------------------
	private class Node {
		private String string;
		private Node[] children;
		private int index;
		private int numMarkers;
		// private int depth = 0; // you are not required to use it, but might find useful
		
		public Node(String string, int index, int numMarkers) {
			children = new Node[numMarkers + 26]; // 26 letters + however many markers we have
			this.string = string;
			this.index = index;
		}

	} // -----------------------------------------------------------------------

	/** Insert a given suffix into the tree (the suffix starts at index = ind in the concatString */
	public void insert(String word, int ind) {
		root = insert(word.toLowerCase(), ind, root);
	}

	private Node insert(String word, int ind, Node tree) {
		System.out.println("Inserting suffix " + word);
		if (tree == null) {
			Node temp = new Node(word, ind, markers.size());
			return temp;
		}
		// FILL IN CODE - you are required to use the algorithm we discussed in class
		int childInd = getChildIndex(word);
//		!!!!!comments:get the first letter of the given word to decide where should the word be put in.
		if (tree.children[childInd]==null){//if the index of the first letter of the word to insert in the array is empty
			Node wordNode = new Node(word,ind, markers.size());
			tree.children[childInd]=wordNode;
			}
			else{
				String common = commonPrefix(word,tree.children[childInd].string);
				int numcommon = common.length();
				String newWord = word.substring(numcommon);//the word left after subtract the common prefix. 
				
				//System.out.println("!!!!!!!!!!!!!!!!!!!tree.children[childInd].string:"+tree.children[childInd].string);
				if(common.equals(tree.children[childInd].string)){
					insert(newWord,ind,tree.children[childInd]);
					
				}else{
					Node temp = tree.children[childInd];
					String neworigword = temp.string.substring(numcommon);//the original word from the index left after subtract the common prefix.
					Node newwordNode = new Node(common,-1, markers.size());
					tree.children [childInd]=newwordNode;
					insert(neworigword,temp.index,newwordNode);
					insert(newWord,ind,newwordNode);	
				}
				
		}
		//!!!!!comments:return the whole tree instead of the subtree.
		return tree; // don't forget to change it
	}
	
	
	
	
	/**
	 * Insert a new suffix that starts at index = ind, into the suffix tree with the given root
	 */
	
	
	
	
	/** A helper method: returns the longest common prefix of word1 and word2.
	 *  Example: if word1 = "banana" and word2 = "band", the  longest common prefix is "ban". */
	private String commonPrefix(String word1, String word2) {
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

	
	/** Return a suffix tree as a string in human readable form (using preorder traversal and indentations) */
	public String toString() {
		return toString(root, 0);
	}

	/**
	 * A private recursive method that returns the string representation of the suffix tree with the given root
	 * with i indentations.
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
			}
			blr.append(tree.string);
			if (tree.index != -1){
				blr.append(": ");
				blr.append(tree.index);
			}
			blr.append(System.lineSeparator());
		}
			for (k = 0; k < tree.children.length; k++){
				if(tree.children[k]!=null){
					blr.append(toString(tree.children[k], i+1));
				}
			}
		
		return blr.toString();
	
	}
	
	
	/** Return the index in the children array that corresponds to the first letter of the given word. If 
	 * the first letter is 'a', the method returns 0; if the first letter is 'b', the method returns 1 etc.
	 * Markers are handled separately: the index of the first marker child is 26 (the last one in the array),
	 * the index of the second marker child is 27, etc..
	 * @param word
	 * @return
	 */
	public int getChildIndex(String word) {
		int childIndex = word.charAt(0) - ASCII;
		String s = Character.toString(word.charAt(0));
		int i = markers.indexOf(s);
		if (i != -1 ) // one of the markers
			childIndex = 26 + i;
		return childIndex;
	}


	
	/** Return an ArrayList of common longest substrings of the strings in the generalized suffix tree */
	public ArrayList<String> findLCS() {
		ArrayList<String> lcsStrings = new ArrayList<String>();
		// FILL IN CODE
		
		
		// Note: you will likely write a couple of helper methods for this method
		return lcsStrings;
	}
}
