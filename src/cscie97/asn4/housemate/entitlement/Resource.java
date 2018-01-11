package cscie97.asn4.housemate.entitlement;

/**
 * @author V1A
 *
 */
class Resource implements HouseMateEntitlementElements {
	private String id;
	private String description;
	
	/**Constructor
	 * @param id
	 * @param description
	 */
	Resource(String id, String description){
		this.setId(id);
		this.setDescription(description);
	}
	
	/**accept visitor function for resource object. 
	 * @param visitor
	 * @return string
	 */
	@Override
	public String acceptVisitor(HouseMateEntitlementElementVisitor visitor) {
		return visitor.visit(this);
		
	}
	/**
	 * @return id String
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return description string
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
