package cscie97.asn4.housemate.entitlement;

/**
 * @author V1A
 *
 */
class ResourceRole implements HouseMateEntitlementElements {
	private String id;
	private String name;
	private String description;
	private HouseMateEntitlementElements resource;
	private HouseMateEntitlementElements userRole;
	
	/**Constructor
	 * @param id
	 * @param roleObject
	 * @param resourceObject
	 */
	ResourceRole(String id, HouseMateEntitlementElements roleObject, HouseMateEntitlementElements resourceObject){
		this.setId(id);
		this.setName(id);
		this.setUserRole(roleObject);
		this.setResource(resourceObject);
	}
	
	/**accept visitor function for resource role object. 
	 * @param visitor
	 * @return string
	 */
	@Override
	public String acceptVisitor(HouseMateEntitlementElementVisitor visitor) {
		return visitor.visit(this);
	}
	
	/**
	 * @return resource object
	 */
	public HouseMateEntitlementElements getResource() {
		return resource;
	}
	/**
	 * @param resourceObject
	 */
	public void setResource(HouseMateEntitlementElements resourceObject) {
		this.resource = resourceObject;
	}
	/**
	 * @return role object
	 */
	public HouseMateEntitlementElements getUserRole() {
		return userRole;
	}
	/**
	 * @param roleObject
	 */
	public void setUserRole(HouseMateEntitlementElements roleObject) {
		this.userRole = roleObject;
	}
	/**
	 * @return id string
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
	 * @return name string
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
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
