package cscie97.asn2.housemate.model;

public class Children extends Person {
	
	 /**
     * Default constructor
     * Uses package visibility to restrict creation of the Children class.
     */
	Children() {
		super();
	}

	/**
	 * @return "children" type
	 */
	@Override
	public String getType() {
		return "child";
	}
	
	@Override
	public String toString() {
		return "Children [isPerson()=" + isPerson() + ", toString()=" + super.toString() + ", isKnown()=" + isKnown()
				+ ", getName()=" + getName() + ", getLocation()=" + getLocation() + ", getState()=" + getState()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
}
