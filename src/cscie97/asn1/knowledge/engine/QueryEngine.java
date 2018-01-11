package cscie97.asn1.knowledge.engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

public class QueryEngine {
	/**
	 * Public method for executing a single query on the knowledge graph. 
	 * Checks for non null and well formed query string.
	 * @param query Query to execute.
	 * @throws QueryEngineException on error related to malformed queries.
	 *
	 */
	public void executeQuery(String query) throws QueryEngineException{
		try{
			String[] parts = query.split(Pattern.quote(" "));
			
			//If query is not fully formed raise the exception.
			if(parts.length != 3) throw new QueryEngineException(query);
	    	String subject = parts[0];
	    	subject.trim();
	    	String predicate = parts[1];
	    	predicate.trim();
	    	String objectStr = parts[2];
	    	objectStr.trim();
	    	String[] objectStrParts = objectStr.split(Pattern.quote("."));
	    	String object = objectStrParts[0];
	    	
	    	KnowledgeGraph graphInstance = KnowledgeGraph.getInstance();
	    	Set<Triple> tripleSet = graphInstance.executeQuery(subject, predicate, object);
	    	System.out.println(query);
	    	if(tripleSet != null){ 
		    	for(Triple triple : tripleSet){
		    		System.out.println(triple.getIdentifier() + ".");
		    	}
	    	}else{
	    		System.out.println("<null>");
	    	}
	    	
		}catch (QueryEngineException e) {
			System.out.println("Query is malformed: " + e.getMessage());
			e.printStackTrace();
        }
	}
	
	/**
	 * Public method for executing a set of queries read from a file. Checks for valid file name. 
	 * Delegates to executeQuery for processing individual queries.
	 * @param fileName input file name containing queries.
	 * @throws QueryEngineException on error related to opening or reading file.
	 *
	 */
	public void executeQueryFile(String fileName) throws QueryEngineException{
		 try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	            	if(line.equals("")) continue;
	            	executeQuery(line);
	            }
	        } catch (FileNotFoundException e) {
	        	System.out.println("File not found: " + e);
	        	e.printStackTrace();
	        }catch (IOException e) {
	        	System.out.println("Error reading input query file: " + e);
	        	e.printStackTrace();
	        }
	}
}
