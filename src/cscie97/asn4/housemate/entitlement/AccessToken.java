package cscie97.asn4.housemate.entitlement;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author V1A
 *
 */
class AccessToken {
	private String guid;
	private LocalDateTime lastUsed;
	private AccessTokenState tokenState;
	private long expirationDuration; //In seconds
	
	
	/**Constructor
	 * @param expiration Expriation duration for token
	 */
	AccessToken(long expiration){
		this.guid = UUID.randomUUID().toString();
		this.lastUsed = LocalDateTime.now();
		this.expirationDuration = expiration;
		this.tokenState = AccessTokenState.ACTIVE;
	}
	
	/**
	 * @return guid string
	 */
	public String getGuid() {
		return guid;
	}
	/**
	 * @param guid
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}
	/**
	 * @return lastused token date and time
	 */
	public LocalDateTime getLastUsed() {
		return lastUsed;
	}
	/**
	 * @param lastUsed
	 */
	public void setLastUsed(LocalDateTime lastUsed) {
		this.lastUsed = lastUsed;
	}
	/**
	 * @return token state enum
	 */
	public AccessTokenState getTokenState() {
		return tokenState;
	}
	/**
	 * @param tokenState
	 */
	public void setTokenState(AccessTokenState tokenState) {
		this.tokenState = tokenState;
	}
	/**
	 * @return expiration duration in seconds
	 */
	public long getExpirationDuration() {
		return expirationDuration;
	}
	/**
	 * @param expirationDuration
	 */
	public void setExpirationDuration(long expirationDuration) {
		this.expirationDuration = expirationDuration;
	}
	
	/**
	 * @return true if token is last used within expiration duration
	 */
	public boolean isWithinExpirationDuration(){
		boolean withinDuration = false;
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime lastUsedTime = this.getLastUsed();
		long diffInSeconds = java.time.Duration.between(lastUsedTime, currentTime).getSeconds();
		if(diffInSeconds < this.getExpirationDuration()){
			withinDuration = true;
		}
		return withinDuration;
	}
}
