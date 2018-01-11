package cscie97.asn4.housemate.entitlement;

/**
 * @author V1A
 *
 */
interface HouseMateEntitlementElementVisitor {

	/**visit the role object
	 * @param role Role object
	 * @return string
	 */
	String visit(Role role);

	/**visit the permission object
	 * @param permission Permission object
	 * @return string
	 */
	String visit(Permission permission);

	/**visit the user object
	 * @param user
	 * @return string
	 */
	String visit(User user);

	/**visit the resource object
	 * @param resource
	 * @return string
	 */
	String visit(Resource resource);

	/**visit the resource role object
	 * @param resourceRole
	 * @return string
	 */
	String visit(ResourceRole resourceRole);
	
}
