package cscie97.asn1.test;

import cscie97.asn1.knowledge.engine.ImportException;
import cscie97.asn1.knowledge.engine.Importer;
import cscie97.asn1.knowledge.engine.QueryEngine;
import cscie97.asn1.knowledge.engine.QueryEngineException;

public class TestDriver {

	public static void main(String[] args) {
		
		Importer fileImporter = new Importer();
		
		//Get the current directory and create the path for input files.
		String currentDirectory;
		currentDirectory = System.getProperty("user.dir");
		String inputTripleFile = currentDirectory + "\\cscie97\\asn1\\test\\" + args[0];
		String queryTripleFile = currentDirectory + "\\cscie97\\asn1\\test\\" + args[1];
		
		try{
			fileImporter.importTripleFile(inputTripleFile);
		}
		catch(ImportException e){
			e.printStackTrace();
		}
		
		QueryEngine queryEngine = new QueryEngine();
		try{
			queryEngine.executeQueryFile(queryTripleFile);
		}
		catch(QueryEngineException e){
			e.printStackTrace();
		}
	}

}
