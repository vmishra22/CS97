package cscie97.asn2.housemate.model;


public abstract class Appliance extends Device {

	 /**
     * Default constructor
     * Uses package visibility to restrict creation of the Appliance class.
     */
	Appliance() {
		
	}

	
	/**
	 * @return true for appliance class.
	 */
	@Override
	boolean isControllable() {
		
		return true;
	}
	
}
