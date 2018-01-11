package cscie97.asn2.housemate.model;

/**
 * @author V1A
 *
 */
public class Adult extends Person {

	 /**
     * Default constructor
     * Uses package visibility to restrict creation of the Adult class.
     */
	Adult() {
		super();
	}

	/**
	 * @return "adult" for Adult class
	 */
	@Override
	public String getType() {
		return "adult";
	}

	@Override
	public String toString() {
		return "Adult [isPerson()=" + isPerson() + ", toString()=" + super.toString() + ", isKnown()=" + isKnown()
				+ ", getName()=" + getName() + ", getLocation()=" + getLocation() + ", getState()=" + getState()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
