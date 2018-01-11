package cscie97.asn4.housemate.entitlement;

/**
 * @author V1A
 *
 */
abstract class UserAuthentication {
	private AuthenticationType type;
	private String id;
	
	/**
	 * @return true if user is admin
	 */
	abstract boolean isAdmin();
	/**
	 * @param userName
	 * @param password
	 * @return true if user admin is authenticated
	 */
	abstract boolean isUserAuthenticated(String userName, String password);
	/**
	 * @param voicePrint
	 * @return true if user's voice print is authenticated.
	 */
	abstract boolean isOccupantVoicePrintAuthenticated(String voicePrint);
	
	/**
	 * @return authentication type
	 */
	public AuthenticationType getType() {
		return type;
	}
	/**
	 * @param type
	 */
	public void setType(AuthenticationType type) {
		this.type = type;
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
	protected void setId(String id) {
		this.id = id;
	}
}
