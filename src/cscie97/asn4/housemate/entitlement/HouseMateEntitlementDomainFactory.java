package cscie97.asn4.housemate.entitlement;

public class HouseMateEntitlementDomainFactory extends EntitlementDomainFactory {

	/**Create a user object. 
	 * @param id user id
	 * @param name user name
	 * @return user element
	 */
	@Override
	public HouseMateEntitlementElements createUser(String id, String name) {
		return new User(id, name);
	}

	/**Create a resource  object. 
	 * @param id resource id
	 * @param description resource decription
	 * @return resource element
	 */
	@Override
	public HouseMateEntitlementElements createResource(String id, String description) {
		return new Resource(id, description);
	}

	/**Create a role  object. 
	 * @param id role id
	 * @param name role name
	 * @param description role description
	 * @return role element
	 */
	@Override
	public HouseMateEntitlementElements createRole(String id, String name, String description) {
		return new Role(id, name, description);
	}

	/**Create a permission  object. 
	 * @param id permission id
	 * @param name permission name
	 * @param description permission description
	 * @return permission element
	 */
	@Override
	public HouseMateEntitlementElements createPermission(String id, String name, String description) {
		return new Permission(id, name, description);
	}

	/**Create a resource role  object. 
	 * @param id resource role id
	 * @param roleObject role object
	 * @param resourceObject resource object
	 * @return resource role element
	 */
	@Override
	public HouseMateEntitlementElements createResourceRole(String id, HouseMateEntitlementElements roleObject, HouseMateEntitlementElements resourceObject) {
		return new ResourceRole(id, roleObject, resourceObject);
	}

}
