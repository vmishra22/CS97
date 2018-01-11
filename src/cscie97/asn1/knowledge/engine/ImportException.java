package cscie97.asn1.knowledge.engine;


@SuppressWarnings("serial")
public class ImportException extends Exception {
	public ImportException(String msg)
    {
        super("ImportException thrown: " + msg);
    }
}
