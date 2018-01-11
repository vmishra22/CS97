package cscie97.asn4.housemate.entitlement;

public interface HouseMateEntitlementService {
	/** Check access to a resource with a permission. 
	 * @param auth_token Authentication token
	 * @param resource Resource entity
	 * @param permission permission to the resource
	 * @return boolean 
	 * @throws InvalidAccessTokenException
	 * @throws AccessDeniedException
	 */
	public boolean checkAccess(String auth_token, String resource, String permission) throws InvalidAccessTokenException, AccessDeniedException;
	
	/** Create a permission.
	 * @param permission_id Permission id
	 * @param permission_name Permission name
	 * @param permission_description Permission description
	 * @throws PermissionAlreadyExistsException 
	 */
	public void createPermission(String permission_id, String permission_name, String permission_description) throws PermissionAlreadyExistsException;
	
	/**Create a role.
	 * @param role_id Role Id
	 * @param role_name Role name
	 * @param role_description Role description
	 * @throws RoleAlreadyExistsException 
	 */
	public void createRole(String role_id, String role_name, String role_description) throws RoleAlreadyExistsException;
	
	/**Add an entitlement to a role.
	 * @param role_id Role ID
	 * @param entitlement_id Entitlement id
	 * @throws UnsupportedOperationException 
	 */
	public void addEntitlementToRole(String role_id, String entitlement_id) throws UnsupportedOperationException;
	
	/** Create a user with given id and name.
	 * @param user_id User id
	 * @param user_name User name
	 * @throws UserAlreadyExistsException 
	 */
	public void createUser(String user_id, String user_name) throws UserAlreadyExistsException;
	
	/** Add user credentials,with a username/password combination or voice signature. 
	 * @param user_id User id
	 * @param user_name User name
	 * @param voicePassword option
	 * @param value authentication value
	 * @throws UserCredentialOptionNotExistsException
	 */
	public void addUserCredential(String user_id, String user_name, String voicePassword, String value) throws UserCredentialOptionNotExistsException;
	
	/**Add a role to the user.
	 * @param user_id User id
	 * @param role Role name
	 */
	public void addRoleToUser(String user_id, String role);
	
	/**Create a resource with given name and description.
	 * @param resource_name Resource name
	 * @param resource_description Resource description
	 * @throws ResourceAlreadyExistsException 
	 */
	public void createResource(String resource_name, String resource_description) throws ResourceAlreadyExistsException;
	
	/**Create a resource role
	 * @param resource_role_name Resource role name
	 * @param role Role
	 * @param resource Resource
	 * @throws ResourceRoleAlreadyExistsException 
	 */
	public void createResourceRole(String resource_role_name, String role, String resource) throws ResourceRoleAlreadyExistsException;
	
	/**Add a resource role to the user
	 * @param user_id User id
	 * @param resource_role Resource role
	 */
	public void addResourceRoleToUser(String user_id, String resource_role);
	
	/** Authenticate a user name and password.
	 * @param user_name User name
	 * @param password Password
	 * @return Authentication token guid
	 * @throws UserNotAuthenticatedException
	 */
	public String loginAuthentication(String user_name, String password) throws UserNotAuthenticatedException;
	
	/** Authenticate the voice print signature
	 * @param voice_print Voice signature
	 * @return Authentication token guid
	 * @throws UserNotAuthenticatedException
	 */
	public String voicePrintAuthentication(String voice_print) throws UserNotAuthenticatedException;
	
	/**Logout for a given authentication token
	 * @param auth_token Authentication token
	 * @throws AccessTokenNotExistsException
	 */
	public void logout(String auth_token) throws AccessTokenNotExistsException;
	
	/** Check if token is valid
	 * @param auth_token Authentication token
	 * @return true or false
	 * @throws InvalidAccessTokenException
	 */
	public boolean isTokenValid(String auth_token) throws InvalidAccessTokenException;
	
	/** Print the entitlement inventory using a given admin authentication token.
	 * @param auth_token Authentication token
	 * @throws UserNotAuthenticatedException
	 * @throws AccessDeniedException
	 * @throws InvalidAccessTokenException
	 */
	public void printInventory(String auth_token) throws UserNotAuthenticatedException, AccessDeniedException, InvalidAccessTokenException;
}
