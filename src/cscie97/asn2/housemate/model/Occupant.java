package cscie97.asn2.housemate.model;

import java.util.HashSet;
import java.util.Set;

public abstract class Occupant {
	private String name;
	private Room location;
	private String state;
	private String isKnown;
	private String type;
	private Set<House> houseSet = new HashSet<House>();
	
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the Occupant class.
     */
	Occupant(){
		this.state = "N/A";
		this.location = null;
		this.isKnown = "KNOWN";
	}
	
	/**
	 * @return known status to the house. 
	 */
	public String isKnown() {
		return isKnown;
	}
	
	/**
	 * 
	 * @param isKnown set known status
	 */
	public void setKnown(String isKnown) {
		this.isKnown = isKnown;
	}
	
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name set name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return location
	 */
	public Room getLocation() {
		return location;
	}
	
	/**
	 * 
	 * @param location set location
	 */
	public void setLocation(Room location) {
		this.location = location;
	}
	
	/**
	 * @return state
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * 
	 * @param state set state
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * @return type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * 
	 * @param type set type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return get set of houses where occupant is recognized
	 */
	public Set<House> getHouseSet() {
		return houseSet;
	}
	/**
	 * @param houseSet set of houses where occupant is recognized
	 */
	public void setHouseSet(Set<House> houseSet) {
		this.houseSet = houseSet;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Occupant))
			return false;
		Occupant other = (Occupant) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	abstract boolean isPerson();
	
	
}
