package cscie97.asn1.knowledge.engine;

public class Predicate {
	private String identifier;
	private long createDate;
	
	public Predicate(String id){
		identifier = id.toLowerCase();
		createDate = System.currentTimeMillis() / 1000L;
	}
	
	/**
	 * Return the predicate identifier.
	 * @return predicate identifier string.
	 */
	public String getIdentifier(){
		return identifier;
	}
	
	/**
	 * Return the predicate creation date.
	 * @return predicate creation date.
	 */
	public long getCreateDate(){
		return createDate;
	}
}
