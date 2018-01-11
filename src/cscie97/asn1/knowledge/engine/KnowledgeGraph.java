package cscie97.asn1.knowledge.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class KnowledgeGraph {
	private static KnowledgeGraph instance = null;
	
	private Map<String, Node> nodeMap = new HashMap<String, Node>();
	private Map<String, Predicate> predicateMap = new HashMap<String, Predicate>();
	private Map<String, Triple> tripleMap = new HashMap<String, Triple>();
	private Map<String, Set<Triple>> queryMapSet = new HashMap<String, Set<Triple>>();
	
	private Set<String> createPermutations(String subject, String predicate, String object){
		Set<String> queryStringSet = new HashSet<String>();
		queryStringSet.add(subject + " " + predicate + " " + object);
		queryStringSet.add(subject + " " + predicate + " " + "?");
		queryStringSet.add(subject + " " + "?" + " " + object);
		queryStringSet.add(subject + " " + "?" + " " + "?");
		queryStringSet.add("?" + " " + predicate + " " + object);
		queryStringSet.add("?" + " " + predicate + " " + "?");
		queryStringSet.add("?" + " " + "?" + " " + object);
		queryStringSet.add("?" + " " + "?" + " " + "?");
		return queryStringSet;
	}
	
	/**
	 * Adds a Triple to theKnowledgeGraph.
	 * @param subject Subject string.
	 * @param predicate Predicate string.
	 * @param object Object string.
	 *
	 */
	public void importTriple(String subject, String predicate, String object){
		Node SubjectNode = getNode(subject);
		Node ObjectNode = getNode(object);
		Predicate PredicateNode = getPredicate(predicate);
		Triple TripleObject = getTriple(SubjectNode, PredicateNode, ObjectNode);
		
		Set<String> queryStrings = createPermutations(SubjectNode.getIdentifier(), PredicateNode.getIdentifier(), ObjectNode.getIdentifier());
		for(String str : queryStrings){
			Set<Triple> tripleSet = null;
			if(queryMapSet.containsKey(str)){
				tripleSet = queryMapSet.get(str);
				tripleSet.add(TripleObject);
			}else{
				tripleSet = new HashSet<Triple>();
				tripleSet.add(TripleObject);
				queryMapSet.put(str, tripleSet);
			}
		}
	}
	
	/**
	 * Get the set of triples matching the query
	 * @param subject Subject string.
	 * @param predicate Predicate string.
	 * @param object Object string.
	 * @return triple set matching the query.
	 */
	public  Set<Triple> executeQuery(String subject, String predicate, String object){
		String queryString = subject.toLowerCase() + " " + predicate.toLowerCase() + " " + object.toLowerCase();
		Set<Triple> tripleSet = null;
		if(queryMapSet.containsKey(queryString)){
			tripleSet = queryMapSet.get(queryString);
		}
		return tripleSet;
	}
	
	/**
	 * Return a reference to the knowledge graph instance.
	 * @return KnowledgeGraph singleton instance.
	 */
	public static KnowledgeGraph getInstance(){
		if(instance == null) {
	         instance = new KnowledgeGraph();
		}
		return instance;
	}
	
	/**
	 * Return a node object matching the identifier.
	 * @param identifier node identifier.
	 * @return Node object matching the identifier.
	 */
	public Node getNode(String identifier){
		Node NodeObject = nodeMap.get(identifier.toLowerCase());
		if(NodeObject == null){
			NodeObject = new Node(identifier);
			nodeMap.put(identifier, NodeObject);
		}
		return NodeObject;
	}
	
	/**
	 * Return a predicate object matching the identifier.
	 * @param identifier node identifier.
	 * @return Predicate object matching the identifier.
	 */
	public Predicate getPredicate(String identifier){
		Predicate PredicateNode = predicateMap.get(identifier.toLowerCase());
		if(PredicateNode == null){
			PredicateNode = new Predicate(identifier);
			predicateMap.put(identifier, PredicateNode);
		}
		return PredicateNode;
	}
	
	/**
	 * Get the triple matching the identifier.
	 * @param subject Subject string.
	 * @param predicate Predicate string.
	 * @param object Object string.
	 * @return Triple object matching subject,predicate and object.
	 */
	public Triple getTriple(Node subject, Predicate predicate, Node object){
		String tripleKeyStr = subject.getIdentifier() + " " + 
				predicate.getIdentifier() + " " + object.getIdentifier();
		Triple TripleObject = tripleMap.get(tripleKeyStr);
		if(TripleObject == null){
			TripleObject = new Triple(subject, predicate, object);
			tripleMap.put(tripleKeyStr, TripleObject);
		}
		return TripleObject;
	}

	public void updateTriple(String subject, String predicate, String object) {
		Node SubjectNode = getNode(subject);
		Node ObjectNode = getNode(object);
		Predicate PredicateNode = getPredicate(predicate);
		Triple newTripleObject = getTriple(SubjectNode, PredicateNode, ObjectNode);
		
		Set<String> queryStrings = createPermutations(SubjectNode.getIdentifier(), PredicateNode.getIdentifier(), ObjectNode.getIdentifier());
		for(String str : queryStrings){
			Set<Triple> tripleSet = null;
			if(queryMapSet.containsKey(str)){
				tripleSet = queryMapSet.get(str);

				Triple t = null;
				for (Iterator<Triple> it = tripleSet.iterator(); it.hasNext(); ) {
					t = it.next();
					if(t.getPredicate().getIdentifier().equalsIgnoreCase(PredicateNode.getIdentifier())){
						break;
					}
				}
				if(t!=null){
					tripleSet.remove(t);
				}
				tripleSet.add(newTripleObject);
			}else{
				tripleSet = new HashSet<Triple>();
				tripleSet.add(newTripleObject);
				queryMapSet.put(str, tripleSet);
			}
		}
	}
}
