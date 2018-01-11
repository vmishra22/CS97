package cscie97.asn2.housemate.model;

public class Animal extends Occupant {

	
	 /**
     * Default constructor
     * Uses package visibility to restrict creation of the Animal class.
     */
	Animal() {
		
	}

	/**
	 * @return false for Animal class.
	 */
	@Override
	boolean isPerson() {
		return false;
	}

}
