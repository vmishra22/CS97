package cscie97.asn4.housemate.entitlement;

/**
 * @author V1A
 *
 */
abstract class UserRole implements HouseMateEntitlementElements {
	private String id;
	private String name;
	private String description;
	
	
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
	 * @return description string
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @param uRole
	 * @throws UnsupportedOperationException
	 */
	public void add(UserRole uRole) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param uRole
	 * @throws UnsupportedOperationException
	 */
	public void remove(UserRole uRole) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param i
	 * @return userrole object
	 * @throws UnsupportedOperationException
	 */
	public UserRole getChild(int i) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
}
