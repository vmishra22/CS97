package cscie97.asn4.housemate.entitlement;

import java.util.UUID;

/**
 * @author V1A
 *
 */
class VoicePrint extends UserAuthentication {

	private String voiceSignature;
	
	/**Constructor
	 * @param voicePrint
	 */
	VoicePrint(String voicePrint){
		this.setId(UUID.randomUUID().toString());
		this.voiceSignature = voicePrint;
	}
	
	/**
	 * @return true if user is admin
	 */
	@Override
	boolean isAdmin() {
		
		return false;
	}

	/**
	 * @param userName
	 * @param password
	 * @return true if user admin is authenticated
	 */
	@Override
	boolean isUserAuthenticated(String userName, String password) {
		
		return false;
	}

	/**
	 * @param voicePrint
	 * @return true if user's voice print is authenticated.
	 */
	@Override
	boolean isOccupantVoicePrintAuthenticated(String voicePrint) {
		
		return this.voiceSignature.equals(voicePrint);
	}

	/**
	 * @return voice print string
	 */
	public String getVoicePrint() {
		return voiceSignature;
	}

	/**
	 * @param voicePrint
	 */
	public void setVoicePrint(String voicePrint) {
		this.voiceSignature = voicePrint;
	}
}
