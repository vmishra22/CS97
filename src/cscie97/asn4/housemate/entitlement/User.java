package cscie97.asn4.housemate.entitlement;

import java.util.ArrayList;

/**
 * @author V1A
 *
 */
class User implements HouseMateEntitlementElements {

	private String id;
	private String name;
	private UserAuthentication userAuthentication;
	private AccessToken accessToken = null;
	private ArrayList<UserRole> rolesList = new ArrayList<UserRole>();
	private ArrayList<ResourceRole> resourceRolesList = new ArrayList<ResourceRole>();
	
	/**Constructor
	 * @param id
	 * @param name
	 */
	User(String id, String name){
		this.setId(id);
		this.setName(name);
	}
	
	/**accept visitor function for user object. 
	 * @param visitor
	 * @return string
	 */
	@Override
	public String  acceptVisitor(HouseMateEntitlementElementVisitor visitor) {
		return visitor.visit(this);
		
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
	 * @return authentication object
	 */
	public UserAuthentication getUserAuthentication() {
		return userAuthentication;
	}

	/**
	 * @param userAuthentication
	 */
	public void setUserAuthentication(UserAuthentication userAuthentication) {
		this.userAuthentication = userAuthentication;
	}

	/**
	 * @return list of roles
	 */
	public ArrayList<UserRole> getRolesList() {
		return rolesList;
	}

	/**
	 * @param rolesList
	 */
	public void setRolesList(ArrayList<UserRole> rolesList) {
		this.rolesList = rolesList;
	}

	/**
	 * @return resourceRoles list object
	 */
	public ArrayList<ResourceRole> getResourceRolesList() {
		return resourceRolesList;
	}

	/**
	 * @param resourceRolesList
	 */
	public void setResourceRolesList(ArrayList<ResourceRole> resourceRolesList) {
		this.resourceRolesList = resourceRolesList;
	}

	/**
	 * @param roleObject 
	 */
	public void addUserRole(UserRole roleObject) {
		rolesList.add(roleObject);
	}

	/**
	 * @param roleObject
	 */
	public void addResourceRole(ResourceRole roleObject) {
		resourceRolesList.add(roleObject);
	}

	/**
	 * @return accesstoken object
	 */
	public AccessToken getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken
	 */
	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

}
