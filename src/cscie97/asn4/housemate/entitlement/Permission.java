package cscie97.asn4.housemate.entitlement;

/**
 * @author V1A
 *
 */
public class Permission extends UserRole {

	/**Constructor
	 * @param id
	 * @param name
	 * @param description
	 */
	Permission(String id, String name, String description){
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
	}
	
	/**accept visitor function for permission object. 
	 * @param visitor
	 * @return string
	 */
	@Override
	public String acceptVisitor(HouseMateEntitlementElementVisitor visitor) {
		return visitor.visit(this);
	}

}
