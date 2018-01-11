package cscie97.asn1.knowledge.engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;


public class Importer {
	/**
	 * Public method for importing triples from N_Triple formatted file into the KnowledgeGraph.
	 * Checks for valid input file name. 
	 * @param fileName name of input file
	 * @throws ImportException on error accessing or processing the input Triple File.
	 *
	 */
	public void importTripleFile(String fileName) throws ImportException{
		 try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	            	try{
		            	if(line.equals("")) continue;
		            	String[] parts = line.split(Pattern.quote(" "));
		            	
		            	//If triple is not fully formed raise the exception.
		            	if(parts.length != 3) throw new ImportException(line);
		            	String subject = parts[0];
		            	
		            	//Trim extra leading and trailing white spaces.
		            	subject.trim();
		            	
		            	//"?" is reserved key word for subject,predicate amd object.
		            	if(subject.equals("?")) continue; 
		            	String predicate = parts[1];
		            	predicate.trim();
		            	if(predicate.equals("?")) continue; 
		            	String objectStr = parts[2];
		            	objectStr.trim();
		            	if(objectStr.equals("?")) continue; 
		            	String[] objectStrParts = objectStr.split(Pattern.quote("."));
		            	String object = objectStrParts[0];
		            	
		            	KnowledgeGraph graphInstance = KnowledgeGraph.getInstance();
		            	graphInstance.importTriple(subject, predicate, object);
	            	}
	            	catch (ImportException e) {
	    				System.out.println("Input is malformed: " + e.getMessage());
	    				e.printStackTrace();
	            	}
	            }
		 }
		 catch (FileNotFoundException e) {
	        	System.out.println("File not found: " + e);
	        	e.printStackTrace();
		 }
		 catch (IOException e) {
	        	System.out.println("File not found: " + e);
	        	e.printStackTrace();
		 }
	}
}
