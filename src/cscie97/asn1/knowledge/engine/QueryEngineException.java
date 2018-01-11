package cscie97.asn1.knowledge.engine;

@SuppressWarnings("serial")
public class QueryEngineException extends Exception {
	public QueryEngineException(String msg)
    {
        super("QueryEngineException thrown: " + msg);
    }
}
