package project2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/** The driver class for GeneralizedSuffixTree */
public class GeneralizedSuffixTreeDriver {
	public static void main(String[] args) {
//		testLongestPalindrome("banana");
//		testLCS("inputStringsForGeneralizedSuffixTree", "lcsResults");
		
		
//		ArrayList<String> word = new ArrayList<String>();
//		ArrayList<String> mark = new ArrayList<String>();
//		word.add("aba");
//		word.add("bab");
//		mark.add("$");
//		mark.add("#");
//		GeneralizedSuffixTree test = new GeneralizedSuffixTree(word,mark);
//		System.out.println(test.toString());
		
		try {
			testRandW("inputStringsForGeneralizedSuffixTree");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * A method that uses a generalized suffix tree to find the longest common
	 * palindrome in a given string
	 */
	public static void testLongestPalindrome(String s) {
		ArrayList<String> arr = new ArrayList<String>();
		arr.add(s);
		String reverse = "";
		for (int i = s.length() - 1; i >= 0; i--)
			reverse += s.charAt(i);
		arr.add(reverse);

		ArrayList<String> markers = new ArrayList<String>();
		markers.add("#");
		markers.add("$");

		GeneralizedSuffixTree tree = new GeneralizedSuffixTree(arr, markers);
		ArrayList<String> lcs = tree.findLCS();
		System.out.println("Longest palindrome(s): " + lcs);
	}

	/**
	 * Read a list of strings and a list of markers from the file. Build a
	 * generalized suffix tree. Invoke findLCS method. Print output to the file.
	 * Repeat: Read another set of strings&markers, build a tree, call findLCS
	 * method etc.
	 */
	
	private static void stringToFile( String text, String fileName )
	 {
	 try
	 {
	    File file = new File( fileName );

	    // if file doesnt exists, then create it 
	    if ( ! file.exists( ) )
	    {
	        file.createNewFile( );
	    }

	    FileWriter fw = new FileWriter( file.getAbsoluteFile( ) );
	    BufferedWriter bw = new BufferedWriter( fw );
	    bw.write( text );
	    bw.close( );
	 }
	 catch( IOException e )
	 {
	 System.out.println("Error: " + e);
	 e.printStackTrace( );
	 }
	} 
	
	public static void testRandW(String inputFile) throws IOException{// my own function for  
		//reading the file and combine the words and markers. And then build a generalized
		//suffix tree from the combined words, write the tree into new files.
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
	 try {
	        String line = br.readLine();
	        int k =0;
	        String needname = "";
	        while(line != null){
	        	StringBuilder sb = new StringBuilder();
		        while (line!=null&&!line.trim().isEmpty()) {
		        	ArrayList<String> one = new ArrayList<String>();
		    		ArrayList<String> two = new ArrayList<String>();
		    		k++;
		    		needname = Integer.toString(k);
		        	String[] one1 = line.split(", ");
		        	for(String i:one1){
		        		one.add(i);
		        	}
		        	sb.append("\n");
		        	line = br.readLine();
		        	String[] two1 = line.split(", ");
		        	for(String i:two1){
		        		two.add(i);
		        	}
		        	GeneralizedSuffixTree test = new GeneralizedSuffixTree(one, two);
		        	sb.append(test.toString());
		        	sb.append("\n");
		        	line = br.readLine();
		        }
		        System.out.println("sb.tostring: "+ sb.toString());
		        stringToFile( sb.toString(), needname+"Results");
	        
	        line = br.readLine();
	        }    
	    } finally {
	        br.close();
	    }
	 }
	
	
	
	
	
	public static void testLCS(String intputFile, String outputFile) {
		// FILL IN CODE
		// Repeat until the end of file:

		// Read a line with strings, strings are separated by comma space

		// read a line with markers (separated by comma space)

		// Build a generalized suffix tree

		// call findLCS

		// output results to the file

	}

}
