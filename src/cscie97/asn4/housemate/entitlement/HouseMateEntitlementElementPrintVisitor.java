package cscie97.asn4.housemate.entitlement;

import java.util.ArrayList;

/**
 * @author V1A
 *
 */
public class HouseMateEntitlementElementPrintVisitor implements HouseMateEntitlementElementVisitor {

	/**visit the role object
	 * @param role Role object
	 * @return string
	 */
	@Override
	public String visit(Role role) {
		System.out.println("User Role: " + role.getName() + " role Id: " + role.getId()+ " description: " + role.getDescription());
		
		ArrayList<UserRole> rolesList = role.getRolesList();
		for(UserRole userRole:rolesList){
			userRole.acceptVisitor(new HouseMateEntitlementElementPrintVisitor());
		}
		return null;
	}

	/**visit the permission object
	 * @param permission Permisison object
	 * @return string
	 */ 
	@Override
	public String visit(Permission permission) {
		System.out.println("User Permission: " + permission.getName() + " description: " + permission.getDescription());
		return null;
	}

	/**visit the user object
	 * @param user User object
	 * @return string
	 */
	@Override
	public String visit(User user) {
		System.out.println("User Name: " + user.getName() + " User Id: " + user.getId());
		ArrayList<ResourceRole> resourceRoleList = user.getResourceRolesList();
		for(ResourceRole resRole:resourceRoleList){
			resRole.acceptVisitor(new HouseMateEntitlementElementPrintVisitor());
		}
		
		ArrayList<UserRole> rolesList = user.getRolesList();
		for(UserRole role:rolesList){
			role.acceptVisitor(new HouseMateEntitlementElementPrintVisitor());
		}
		return null;
	}

	/**visit the resource object
	 * @param resource
	 * @return string
	 */
	@Override
	public String visit(Resource resource) {
		System.out.println("Resource: " + resource.getId());
		return null;
	}

	
	/**visit the resource Role object
	 * @param resourceRole
	 * @return string
	 */
	@Override
	public String visit(ResourceRole resourceRole) {
		System.out.println("Resource Role: " + resourceRole.getName());
		
		HouseMateEntitlementElements userRoleObject = resourceRole.getUserRole();
		userRoleObject.acceptVisitor(new HouseMateEntitlementElementPrintVisitor());
		
		HouseMateEntitlementElements userResource = resourceRole.getResource();
		userResource.acceptVisitor(new HouseMateEntitlementElementPrintVisitor());
		return null;
	}

}
