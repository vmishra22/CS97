package cscie97.asn4.housemate.entitlement;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class HouseMateEntitlementServiceImpl implements HouseMateEntitlementService {
	private EntitlementDomainFactory domainElementsFactory = new HouseMateEntitlementDomainFactory();
	
	private Map<String, HouseMateEntitlementElements> entitlementMap = new HashMap<String, HouseMateEntitlementElements>();
	private Map<String, HouseMateEntitlementElements> userMap = new HashMap<String, HouseMateEntitlementElements>() ;
	private Map<String, HouseMateEntitlementElements> resourceMap = new HashMap<String, HouseMateEntitlementElements>() ;
	private Map<String, HouseMateEntitlementElements> resourceRoleMap = new HashMap<String, HouseMateEntitlementElements>() ;

	
	/** Check access to a resource with a permission. 
	 * @param auth_token Authentication token
	 * @param resource Resource entity
	 * @param permission permission to the resource
	 * @return boolean 
	 * @throws InvalidAccessTokenException
	 * @throws AccessDeniedException
	 */
	@Override
	public boolean checkAccess(String auth_token, String resource, String permission) 
			throws InvalidAccessTokenException, AccessDeniedException {
		HouseMateEntitlementElements resourceObject = resourceMap.get(resource);
		HouseMateEntitlementElements roleObject = entitlementMap.get(permission);
		if(isTokenValid(auth_token)){
			User userObject = getUserAssociatedWithToken(auth_token);
			//Check Access to resource
			String retVal = null;
			if(userObject.getUserAuthentication().isAdmin())
			{
				retVal = roleObject.acceptVisitor(new HouseMateEntitlementElementCheckAccessVisitor(userObject));
				if(retVal != null && retVal.equalsIgnoreCase("Authenticated")){
					return true;
				}
			}else{
				retVal = resourceObject.acceptVisitor(new HouseMateEntitlementElementCheckAccessVisitor(userObject));
				if(retVal != null && retVal.equalsIgnoreCase("Authenticated"))
				{
					//Check Access to Roles
					String retVal2 = roleObject.acceptVisitor(new HouseMateEntitlementElementCheckAccessVisitor(userObject));
					if(retVal2 != null && retVal2.equalsIgnoreCase("Authenticated")){
						return true;
					}
					//Check access in ResourceRoles
					String retVal3 = userObject.acceptVisitor(new HouseMateEntitlementElementCheckAccessVisitor(roleObject));
					if(retVal3 != null && retVal3.equalsIgnoreCase("Authenticated")){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	private User getUserAssociatedWithToken(String auth_token) throws AccessDeniedException{
		User userWithToken = null;
		for (Entry<String, HouseMateEntitlementElements> entry : userMap.entrySet()) {
			User userObject = (User) entry.getValue();
			AccessToken token = userObject.getAccessToken();
			if(token == null) continue;
			if(token.getGuid().equalsIgnoreCase(auth_token) &&
					token.getTokenState() == AccessTokenState.ACTIVE)
			{
					if(token.isWithinExpirationDuration())
						userWithToken = userObject;
					else
						token.setTokenState(AccessTokenState.EXPIRED);
					
					break;
			}
		}
		if(userWithToken == null){
			AccessDeniedException ad = new AccessDeniedException();
			ad.setDescription("Access is denied");
			ad.setCommandContext(auth_token);
			throw ad;
		}
		return userWithToken;
	}

	/** Create a permission.
	 * @param permission_id Permission id
	 * @param permission_name Permission name
	 * @param permission_description Permission description
	 * @throws PermissionAlreadyExistsException 
	 */
	@Override
	public void createPermission(String permission_id, String permission_name, String permission_description) throws PermissionAlreadyExistsException{
		if(!(entitlementMap.containsKey(permission_id))){
			HouseMateEntitlementElements permission = domainElementsFactory.createPermission(permission_id, permission_name, permission_description);
			entitlementMap.put(permission_id, permission);
		}else{
			PermissionAlreadyExistsException uc = new PermissionAlreadyExistsException();
			uc.setDescription("role already extsts");
			uc.setCommandContext(permission_id);
			throw uc;
		}
	}

	/**Create a role.
	 * @param role_id Role Id
	 * @param role_name Role name
	 * @param role_description Role description
	 * @throws RoleAlreadyExistsException 
	 */
	@Override
	public void createRole(String role_id, String role_name, String role_description) throws RoleAlreadyExistsException {
		if(!(entitlementMap.containsKey(role_id))){
			HouseMateEntitlementElements role = domainElementsFactory.createRole(role_id, role_name, role_description);
			entitlementMap.put(role_id, role);
		}else{
			RoleAlreadyExistsException uc = new RoleAlreadyExistsException();
			uc.setDescription("role already extsts");
			uc.setCommandContext(role_id);
			throw uc;
		}
	}

	/**Add an entitlement to a role.
	 * @param role_id Role ID
	 * @param entitlement_id Entitlement id
	 * @throws UnsupportedOperationException 
	 */
	@Override
	public void addEntitlementToRole(String role_id, String entitlement_id) throws UnsupportedOperationException {
		UserRole roleObject = (UserRole)entitlementMap.get(role_id);
		UserRole entitlementObject = (UserRole)entitlementMap.get(entitlement_id);
		roleObject.add(entitlementObject);
	}

	/** Create a user with given id and name.
	 * @param user_id User id
	 * @param user_name User name
	 * @throws UserAlreadyExistsException 
	 */
	@Override
	public void createUser(String user_id, String user_name) throws UserAlreadyExistsException {
		if(!(entitlementMap.containsKey(user_id))){
			HouseMateEntitlementElements user = domainElementsFactory.createUser(user_id, user_name);
			userMap.put(user_id, user);	
		}else{
			UserAlreadyExistsException uc = new UserAlreadyExistsException();
			uc.setDescription("user already extsts");
			uc.setCommandContext(user_id);
			throw uc;
		}
	}

	/** Add user credentials,with a username/password combination or voice signature. 
	 * @param user_id User id
	 * @param user_name User name
	 * @param voicePasswordOption option
	 * @param value authentication value
	 * @throws UserCredentialOptionNotExistsException
	 */
	@Override
	public void addUserCredential(String user_id, String user_name, String voicePasswordOption, String value) throws UserCredentialOptionNotExistsException {
		User userObject = (User)userMap.get(user_id);
		UserAuthentication auth_object = null;
		if(voicePasswordOption.equalsIgnoreCase("voice_print")){
			auth_object = new VoicePrint(value);
			auth_object.setType(AuthenticationType.VOICE_PRINT);
		}else if(voicePasswordOption.equalsIgnoreCase("password")){
			auth_object = new UserNamePassword(user_name, value);
			auth_object.setType(AuthenticationType.USER_PASSWORD);
		}
		if(auth_object == null){
			UserCredentialOptionNotExistsException uc = new UserCredentialOptionNotExistsException();
			uc.setDescription("credential option not extsts");
			uc.setCommandContext(voicePasswordOption);
			throw uc;
		}
		userObject.setUserAuthentication(auth_object);
	}

	/**Add a role to the user.
	 * @param user_id User id
	 * @param role Role name
	 */
	@Override
	public void addRoleToUser(String user_id, String role) {
		UserRole roleObject = (UserRole)entitlementMap.get(role);
		User userObject = (User)userMap.get(user_id);
		userObject.addUserRole(roleObject);
	}

	/**Create a resource with given name and description.
	 * @param resource_name Resource name
	 * @param resource_description Resource description
	 * @throws ResourceAlreadyExistsException 
	 */
	@Override
	public void createResource(String resource_name, String resource_description) throws ResourceAlreadyExistsException {
		if(!(entitlementMap.containsKey(resource_name))){
			HouseMateEntitlementElements resourceObject = domainElementsFactory.createResource(resource_name, resource_description);
			resourceMap.put(resource_name, resourceObject);
		}else{
			ResourceAlreadyExistsException uc = new ResourceAlreadyExistsException();
			uc.setDescription("user already extsts");
			uc.setCommandContext(resource_name);
			throw uc;
		}
	}
	
	/**Create a resource role
	 * @param resource_role_name Resource role name
	 * @param role Role
	 * @param resource Resource
	 * @throws ResourceRoleAlreadyExistsException 
	 */
	@Override
	public void createResourceRole(String resource_role_name, String role, String resource) throws ResourceRoleAlreadyExistsException {
		HouseMateEntitlementElements roleObject = entitlementMap.get(role);
		HouseMateEntitlementElements resourceObject = resourceMap.get(resource);
		if(!(entitlementMap.containsKey(resource_role_name))){
			HouseMateEntitlementElements resourceRole = domainElementsFactory.createResourceRole(resource_role_name, roleObject, resourceObject);
			resourceRoleMap.put(resource_role_name, resourceRole);
		}else{
			ResourceRoleAlreadyExistsException uc = new ResourceRoleAlreadyExistsException();
			uc.setDescription("user already extsts");
			uc.setCommandContext(resource_role_name);
			throw uc;
		}
	}

	/**Add a resource role to the user
	 * @param user_id User id
	 * @param resource_role Resource role
	 */
	@Override
	public void addResourceRoleToUser(String user_id, String resource_role) {
		User userObject = (User)userMap.get(user_id);
		ResourceRole roleObject = (ResourceRole)resourceRoleMap.get(resource_role);
		userObject.addResourceRole(roleObject);
	}

	private String getAuthenticationToken(User userObject){
		if(userObject.getAccessToken() == null){
			userObject.setAccessToken(new AccessToken(60));
			return userObject.getAccessToken().getGuid();
		}else{
			AccessToken token = userObject.getAccessToken();
			if(token.getTokenState() == AccessTokenState.ACTIVE 
					&& token.isWithinExpirationDuration() )
			{
				userObject.getAccessToken().setLastUsed(LocalDateTime.now());
				return userObject.getAccessToken().getGuid();
			}
			else{
				token.setTokenState(AccessTokenState.EXPIRED);
				userObject.setAccessToken(null);
				userObject.setAccessToken(new AccessToken(60));
				return userObject.getAccessToken().getGuid();
			}
		}
	}
	
	/** Authenticate a user name and password.
	 * @param user_name User name
	 * @param password Password
	 * @return Authentication token guid
	 * @throws UserNotAuthenticatedException
	 */
	@Override
	public String loginAuthentication(String user_name, String password) throws UserNotAuthenticatedException {
		boolean userAuthenticated = false;
		for (Entry<String, HouseMateEntitlementElements> entry : userMap.entrySet()) {
			User userObject = (User) entry.getValue();
			if(userObject.getUserAuthentication().isUserAuthenticated(user_name, password))
			{
				userAuthenticated = true;
				return getAuthenticationToken(userObject);
			}
		}
		if(!userAuthenticated){
			UserNotAuthenticatedException ua = new UserNotAuthenticatedException();
			ua.setDescription("User could not be authenticated");
			ua.setCommandContext(user_name);
			throw ua;
		}
		return null;
	}

	private User findMatchingUserWithVoicePrint(String voice_print){
		for (Entry<String, HouseMateEntitlementElements> entry : userMap.entrySet()) {
			User userObject = (User) entry.getValue();
			if(userObject.getUserAuthentication().isOccupantVoicePrintAuthenticated(voice_print))
			{
				return userObject;
			}
		}
		
		return null;
	}
	
	/** Authenticate the voice print signature
	 * @param voice_print Voice signature
	 * @return Authentication token guid
	 * @throws UserNotAuthenticatedException
	 */
	@Override
	public String voicePrintAuthentication(String voice_print) throws UserNotAuthenticatedException {
		
		boolean userAuthenticated = false;
		User userObject = findMatchingUserWithVoicePrint(voice_print);
		if(userObject != null){
			userAuthenticated = true;
			return getAuthenticationToken(userObject);
		}
		
		if(!userAuthenticated){
			UserNotAuthenticatedException ua = new UserNotAuthenticatedException();
			ua.setDescription("User's voice print could not be authenticated");
			ua.setCommandContext(voice_print);
			throw ua;
		}
		return null;
	}

	/**Logout for a given authentication token
	 * @param auth_token Authentication token
	 * @throws AccessTokenNotExistsException
	 */
	@Override
	public void logout(String auth_token) throws AccessTokenNotExistsException {
		boolean auth_token_found = false;
		for (Entry<String, HouseMateEntitlementElements> entry : userMap.entrySet()) {
			User userObject = (User) entry.getValue();
			AccessToken token = userObject.getAccessToken();
			if(token != null && token.getGuid().equalsIgnoreCase(auth_token)){
				auth_token_found = true;
				userObject.getAccessToken().setTokenState(AccessTokenState.EXPIRED);
				break;
			}
		}
		if(!auth_token_found){
			AccessTokenNotExistsException at = new AccessTokenNotExistsException();
			at.setDescription("Access token does not exist");
			at.setCommandContext(auth_token);
			throw at;
		}
	}

	/** Check if token is valid
	 * @param auth_token Authentication token
	 * @return true or false
	 * @throws InvalidAccessTokenException
	 */
	public boolean isTokenValid(String auth_token) throws InvalidAccessTokenException{
		boolean token_valid = true;
		for (Entry<String, HouseMateEntitlementElements> entry : userMap.entrySet()) {
			User userObject = (User) entry.getValue();
			AccessToken token = userObject.getAccessToken();
			if(token == null) continue;
			if(token.getGuid().equalsIgnoreCase(auth_token) &&
					token.getTokenState() == AccessTokenState.EXPIRED &&
					!(token.isWithinExpirationDuration()) ){
				token_valid = false;
				InvalidAccessTokenException ia = new InvalidAccessTokenException();
				ia.setDescription("Access token is invalid");
				ia.setCommandContext(auth_token);
				throw ia;
			}
		}
		
		return token_valid;
	}

	/** Print the entitlement inventory using a given admin authentication token.
	 * @param auth_token Authentication token
	 * @throws UserNotAuthenticatedException
	 * @throws AccessDeniedException
	 * @throws InvalidAccessTokenException
	 */
	@Override
	public void printInventory(String auth_token) 
			throws UserNotAuthenticatedException, AccessDeniedException, InvalidAccessTokenException {
		if(isTokenValid(auth_token)){
			User adminObject = getUserAssociatedWithToken(auth_token);
			if(adminObject.getUserAuthentication().isAdmin())
			{
				for (Entry<String, HouseMateEntitlementElements> entry : userMap.entrySet()) {
					User userObject = (User) entry.getValue();
					userObject.acceptVisitor(new HouseMateEntitlementElementPrintVisitor());
					System.out.println("");
				}
			}
		}
		
	}

}
