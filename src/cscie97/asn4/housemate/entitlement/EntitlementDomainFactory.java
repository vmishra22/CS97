package cscie97.asn4.housemate.entitlement;

/**
 * @author V1A
 *
 */
public abstract class EntitlementDomainFactory {
	
	/**Create a user object. 
	 * @param id user id
	 * @param name user name
	 * @return user element
	 */
	public abstract HouseMateEntitlementElements createUser(String id, String name);
	/**Create a resource object
	 * @param id resource id
	 * @param description resource description
	 * @return resource element
	 */
	public abstract HouseMateEntitlementElements createResource(String id, String description);
	/**Create role object
	 * @param id role id 
	 * @param name role name
	 * @param description role description
	 * @return role element
	 */
	public abstract HouseMateEntitlementElements createRole(String id, String name, String description);
	/**Create a permission object
	 * @param id permission id
	 * @param name permission name
	 * @param description permission description
	 * @return permission object
	 */
	public abstract HouseMateEntitlementElements createPermission(String id, String name, String description);
	/**Create  resource role object
	 * @param id resource role id
	 * @param roleObject role object
	 * @param resourceObject resource object
	 * @return resource role element
	 */
	public abstract HouseMateEntitlementElements createResourceRole(String id, HouseMateEntitlementElements roleObject, HouseMateEntitlementElements resourceObject);
}
