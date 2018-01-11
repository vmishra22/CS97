package cscie97.asn4.housemate.entitlement;

import java.util.UUID;

/**
 * @author V1A
 *
 */
class UserNamePassword extends UserAuthentication {
	
	private String userName;
	private int passwordHash;
	
	/**Constructor
	 * @param name
	 * @param password
	 */
	UserNamePassword(String name, String password){
		this.setId(UUID.randomUUID().toString());
		this.userName = name;
		this.passwordHash = password.hashCode();
	}

	/**
	 * @return true if user is admin
	 */
	@Override
	boolean isAdmin() {
		return true;
	}

	/**
	 * @param userName
	 * @param password
	 * @return true if user admin is authenticated
	 */
	@Override
	boolean isUserAuthenticated(String userName, String password) {
		return (this.userName.equalsIgnoreCase(userName) && this.passwordHash == password.hashCode());
	}

	/**
	 * @param voicePrint
	 * @return true if user's voice print is authenticated.
	 */
	@Override
	boolean isOccupantVoicePrintAuthenticated(String voicePrint) {
		return false;
	}

	/**
	 * @return username string
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return password hash string
	 */
	public int getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash
	 */
	public void setPasswordHash(int passwordHash) {
		this.passwordHash = passwordHash;
	}

}
