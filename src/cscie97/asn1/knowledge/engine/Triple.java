package cscie97.asn1.knowledge.engine;

public class Triple {
	private String identifier;
	private long createDate;
	
	private Node subject;
	private Node object;
	private Predicate predicate;
	private static final int BIG_PRIME = 9987001;

	public Triple(Node subjectNode, Predicate predicateNode, Node objectNode){
		subject = subjectNode;
		object = objectNode;
		predicate = predicateNode;
		identifier = subject.getIdentifier() + " " + predicate.getIdentifier() + " " +
					 object.getIdentifier();
		createDate = System.currentTimeMillis() / 1000L;
	}
	
	/**
	 * Return the triple identifier.
	 * @return triple identifier string.
	 */
	public String getIdentifier(){
		return identifier;
	}
	
	 /**
     * Private non mutable association to the associated Subject instance.
     * @return Node subject
     */
    public Node getSubject() {
        return subject;
    }

    /**
     * Private non mutable association to the associated Predicate instance.
     * @return Predicate object
     */
    public Predicate getPredicate() {
        return predicate;
    }

    /**
     * Private non mutable association to the associated Object instance.
     * @return Object object
     */
    public Node getObject() {
        return object;
    }
	
	/**
	 * Return the creation date of triple.
	 * @return triple creation date.
	 */
	public long getCreateDate(){
		return createDate;
	}
	
	@Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Triple)) { return false; }
        Triple that = (Triple) object;
        return this.identifier == that.identifier;
    }

    @Override
    public int hashCode()
    {
        return (int)createDate * BIG_PRIME;
    }
}
