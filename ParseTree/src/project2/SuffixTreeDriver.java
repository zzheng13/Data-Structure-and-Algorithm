package project2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/** The driver class for SuffixTree */
public class SuffixTreeDriver {
	
	public static void main(String[] args) {
		try {
			processStrings("inputStringsSimple");// call the function processStrings, results in the output files with name "word"+"Results".
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void stringToFile( String text, String fileName )//write the text to a file called fileName
	 {
	 try
	 {
	    File file = new File( fileName );

	    // if file doesn't exists, then create it 
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
	
	
	private static String readFile(String fileName) throws IOException {// my own methods to help build processStrings
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	public static void processStrings(String filename)throws IOException  {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		 try {
		        String line = br.readLine();
		        int k = 0;
		        String needname = "";
		        while(line != null){//when it is not until the last non-empty line of the file
		        	StringBuilder sb = new StringBuilder();
			        while (line!=null&&!line.trim().isEmpty()) {// check if it's an empty line
			        	SuffixTree newtest = new SuffixTree(line);
			        	needname = line;
			        	sb.append(newtest.toString());
			            sb.append("\n");
			            System.out.println("after creating the suffix tree of: "+needname);
			            
			            
			            line = br.readLine();
			            String[] splitted = line.split(", ");
			            for(k =0;k< splitted.length;k++){
			            	sb.append(newtest.containsSuffix(splitted[k]));
			            	sb.append(" ");
			            }
			            sb.append("\n");
			            System.out.println("after checking containsSuffix 1 of: "+needname);
			            
			            
			            line = br.readLine();
			            String[] spli = line.split(", ");
			            for(k =0;k< spli.length;k++){
			            	sb.append(newtest.containsSuffix(spli[k]));
			            	sb.append(" ");
			            }
			            sb.append("\n");
			            System.out.println("after checking containsSuffix 2 of: "+ needname);
			            
			            
			            line = br.readLine();
			            String[] split = line.split(", ");
			            for(k =0;k< split.length;k++){
			            	sb.append(newtest.getSubstringIndices(split[k]));
			            	sb.append(" ");
			            }
			            sb.append("\n");
			            System.out.println("after checking getSubstringIndices 1 of: "+ needname);
			            
			            
			            line = br.readLine();
			            String[] split2 = line.split(", ");
			            for(k =0;k< split2.length;k++){
			            	sb.append(newtest.getSubstringIndices(split2[k]));
			            	sb.append(" ");
			            }
			            sb.append("\n");
			            System.out.println("after checking getSubstringIndices 2 of: " + needname);
			            
			            
			            line = br.readLine();
			            System.out.println("then the line is: "+ line);
			        }
			        System.out.println("sb.tostring: "+ sb.toString());
			        stringToFile( sb.toString(), needname+"Results");
		        line = br.readLine();
		        }    
		    } finally {
		        br.close();
		    }

	}	
	


}

