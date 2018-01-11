package cscie97.asn1.knowledge.engine;

public class Node {
	private String identifier;
	private long createDate;

	public Node(String id) {
		identifier = id.toLowerCase();
		createDate = System.currentTimeMillis() / 1000L;
	}

	/**
	 * Return the node identifier.
	 * @return node identifier string.
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Return the creation date of node.
	 * @return node created date.
	 */
	public long getCreateDate() {
		return createDate;
	}
}
