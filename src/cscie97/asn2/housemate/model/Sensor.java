package cscie97.asn2.housemate.model;

public abstract class Sensor extends Device {
	
	protected String status;

	 /**
     * Default constructor
     * Uses package visibility to restrict creation of the Sensor class.
     */
	Sensor() {
		status = "";
	}

	/**
	 * Set the Sensor Status value
	 * 
	 * @param name Status name.
	 * @param value Status value.
	 */
	public void setDeviceStatusValue(String name, String value){
		this.status = value;
	}
	/**
	 * Get the Sensor Status value
	 * 
	 * @param name Sensor Status name.
	 * @return Sensor status value string.
	 */
	public String getDeviceStatusValue(String name){
		return status;
	}
	
	/**
	 * Return if device is controllable
	 * 
	 * @return false for sensor.
	 */
	@Override
	boolean isControllable() {
		// TODO Auto-generated method stub
		return false;
	}

}
