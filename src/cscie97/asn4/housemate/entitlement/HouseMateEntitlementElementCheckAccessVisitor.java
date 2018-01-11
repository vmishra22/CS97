package cscie97.asn4.housemate.entitlement;

import java.util.ArrayList;

/**
 * @author V1A
 *
 */
public class HouseMateEntitlementElementCheckAccessVisitor implements HouseMateEntitlementElementVisitor {
	
	HouseMateEntitlementElements elementObject;

	HouseMateEntitlementElementCheckAccessVisitor(HouseMateEntitlementElements userObject){
		this.elementObject = userObject;
	}
	
	/**visit the role object
	 * @param role Role object
	 * @return string
	 */
	@Override
	public String visit(Role role) {
		String str = null;
		if(this.elementObject instanceof User){
			User userObject = (User) this.elementObject;
			ArrayList<UserRole> userRoleList = userObject.getRolesList();
			for(UserRole userRole:userRoleList){
				str = userRole.acceptVisitor(new HouseMateEntitlementElementCheckAccessVisitor(role));
				if(str != null)
					return str;
			}
		}else if(this.elementObject instanceof Role){
			Role roleObject = (Role) this.elementObject;
			ArrayList<UserRole> userRoleList1 = roleObject.getRolesList();
			for(UserRole userRole:userRoleList1){
				str = userRole.acceptVisitor(new HouseMateEntitlementElementCheckAccessVisitor(role));
				if(str != null)
					return str;
			}
		}else if(this.elementObject instanceof Permission){
			ArrayList<UserRole> roleList = role.getRolesList();
			for(UserRole userRole:roleList){
				str = userRole.acceptVisitor(new HouseMateEntitlementElementCheckAccessVisitor(this.elementObject));
				if(str != null)
					return str;
			}
		}
		return null;
	}

	/**visit the permission object
	 * @param permission Permisison object
	 * @return string
	 */
	@Override
	public String visit(Permission permission) {
		String str = null;
		if(this.elementObject instanceof User){
			User userObject = (User) this.elementObject;
			ArrayList<UserRole> userRoleList = userObject.getRolesList();
			for(UserRole userRole:userRoleList){
				str = userRole.acceptVisitor(new HouseMateEntitlementElementCheckAccessVisitor(permission));
				if(str != null)
					return str;
			}
		}else if(this.elementObject instanceof Role){
			Role roleObject = (Role) this.elementObject;
			ArrayList<UserRole> roleList = roleObject.getRolesList();
			for(UserRole userRole:roleList){
				if(userRole instanceof Permission){
					Permission permissionObject = (Permission) userRole;
					if(permissionObject.getId().equalsIgnoreCase(permission.getId()))
						return "Authenticated";
				}
			}
		}else if(this.elementObject instanceof Permission){
			Permission permissionObject = (Permission) this.elementObject;
			String ID1 = permissionObject.getId();
			String ID2 = permission.getId();
			if(ID1.equalsIgnoreCase(ID2))
				return "Authenticated";
		}
		return null;
	}

	/**visit the user object
	 * @param user User object
	 * @return string
	 */
	@Override
	public String visit(User user) {
		String str = null;
		if(this.elementObject instanceof Permission){
			Permission permissionObject = (Permission) this.elementObject;
			ArrayList<ResourceRole> resourceRoleList = user.getResourceRolesList();
			for(ResourceRole resRole:resourceRoleList){
				HouseMateEntitlementElements userRoleObject = resRole.getUserRole();
				str = userRoleObject.acceptVisitor(new HouseMateEntitlementElementCheckAccessVisitor(permissionObject));
				if(str != null)
					return str;
			}
		}
		return null;
		
	}

	/**visit the resource object
	 * @param resource
	 * @return string
	 */
	@Override
	public String visit(Resource resource) {
		if(this.elementObject instanceof User){
			User userObject = (User) this.elementObject;
			ArrayList<ResourceRole> resourceRoleList = userObject.getResourceRolesList();
			for(ResourceRole resRole:resourceRoleList){
				Resource res = (Resource)resRole.getResource();
				if(res != null && res.getId().equalsIgnoreCase(resource.getId())){
					return "Authenticated";
				}
			}
		}
		return null;
	}

	/**visit the resource Role object
	 * @param resourceRole
	 * @return string
	 */
	@Override
	public String visit(ResourceRole resourceRole) {
		
		return null;
	}

}
