package cscie97.asn2.housemate.model;

import java.util.Map;

public abstract class Device {
	private String identifier;
	private Room location;
	private DeviceType type;
	
	 /**
     * Default constructor
     * Uses package visibility to restrict creation of the Device class.
     */
	Device() {
		
	}
	
	/**
	 * @return device identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier set device identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return device location, the room object
	 */
	public Room getLocation() {
		return location;
	}

	/**
	 * @param location set room location
	 */
	public void setLocation(Room location) {
		this.location = location;
	}

	/**
	 * @return device type
	 */
	public DeviceType getType() {
		return type;
	}

	/**
	 * @param type set device type
	 */
	public void setType(DeviceType type) {
		this.type = type;
	}

	/**Abstract method
	 * @return if sensor or appliance
	 */
	abstract boolean isControllable();
	
	/**Abstract method
	 * Set the Device Status value
	 * 
	 * @param name Status name.
	 * @param value Status value.
	 */
	abstract void setDeviceStatusValue(String name, String value);
	
	/**Abstract method
	 * Get the Device Status value
	 * 
	 * @param name Device Status name.
	 * @return Device status value string.
	 */
	abstract String getDeviceStatusValue(String name);
	
	/**Abstract method
	 * Get the all status values of device
	 * 
	 * @return map of status name value pairs.
	 */
	abstract Map<String, String> getAllStatus();
	
	/**Abstract method
	 * Check if device value needs to be updated in the graph.
	 * 
	 * @param name status name
	 * @return boolean value
	 */
	abstract boolean isValueNeedUpdate(String name);
	
	/**Abstract method
	 * Get the status predicate value map.
	 * 
	 * @return map of status name and corresponding predicate
	 */
	abstract Map<String, String> getStatusPredicateMap();
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Device))
			return false;
		Device other = (Device) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "Device [identifier=" + identifier + ", location=" + location + ", type=" + type + "]";
	}

	
	
}
