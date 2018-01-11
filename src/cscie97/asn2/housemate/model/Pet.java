package cscie97.asn2.housemate.model;

public class Pet extends Animal {

	/**
     * Default constructor
     * Uses package visibility to restrict creation of the Pet class.
     */
	Pet() {
		
	}
	/**
	 * @return "pet" type 
	 */
	public String getType() {
		return "pet";
	}
	
	@Override
	public String toString() {
		return "Pet [isPerson()=" + isPerson() + ", isKnown()=" + isKnown() + ", getName()=" + getName()
				+ ", getLocation()=" + getLocation() + ", getState()=" + getState() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
