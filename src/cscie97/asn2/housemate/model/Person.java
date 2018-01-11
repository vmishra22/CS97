package cscie97.asn2.housemate.model;

public class Person extends Occupant {
	
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the Pet class.
     */
	Person() {
		super();
		
	}
	
	/**
	 * @return true 
	 */
	@Override
	boolean isPerson() {
		
		return true;
	}

	@Override
	public String toString() {
		return "Person [isPerson()=" + isPerson() + ", isKnown()=" + isKnown() + ", getName()=" + getName()
				+ ", getLocation()=" + getLocation() + ", getState()=" + getState() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
