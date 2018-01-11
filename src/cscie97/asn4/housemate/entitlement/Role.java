package cscie97.asn4.housemate.entitlement;

import java.util.ArrayList;

/**
 * @author V1A
 *
 */
class Role extends UserRole {

	ArrayList<UserRole> rolesList = new ArrayList<UserRole>();
	
	/**Constructor
	 * @param id
	 * @param name
	 * @param description
	 */
	Role(String id, String name, String description){
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
	}
	
	/**accept visitor function for role object. 
	 * @param visitor
	 * @return string
	 */
	@Override
	public String acceptVisitor(HouseMateEntitlementElementVisitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * @param uRole role object to add
	 */
	public void add(UserRole uRole){
		rolesList.add(uRole);
	}
	
	/**
	 * @param uRole role object to remove
	 */
	public void remove(UserRole uRole){
		rolesList.remove(uRole);
	}
	
	/**
	 * @return role objects list.
	 */
	public ArrayList<UserRole> getRolesList() {
		return rolesList;
	}
	
	/**
	 * @param rolesList role object list to set
	 */
	public void setRolesList(ArrayList<UserRole> rolesList) {
		this.rolesList = rolesList;
	}
	
	/**
	 * @param i index
	 * @return role object.
	 */
	public UserRole getChild(int i){
		return (UserRole)rolesList.get(i);
	}
}
