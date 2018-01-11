package cscie97.asn2.housemate.model;

import java.util.HashMap;
import java.util.Map;

public class Thermostat extends Appliance {

	private int temperature;
	Map<String, String> statusPredicateMap =  new HashMap<String, String>();
	 /**
     * Default constructor
     * Uses package visibility to restrict creation of the Thermostat class.
     */
	Thermostat() {
		this.temperature = -1;
		statusPredicateMap.put("temperature", "has_temperature");
	}
	
	/**
	 * @return temperature 
	 */
	public int getTemperature() {
		return temperature;
	}
	
	/**
	 * 
	 * @param temperature temperature to set (greater than zero)
	 */
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	
	/**
	 * Get the Device Status value
	 * 
	 * @param name Device Status name.
	 * @return Device status value string.
	 */
	public String getDeviceStatusValue(String name) {
		
		return String.valueOf(getTemperature());
	}

	/**
	 * Set the Device Status value
	 * 
	 * @param name Status name.
	 * @param value Status value.
	 */
	public void setDeviceStatusValue(String name, String value) {
		setTemperature(Integer.parseInt(value));
	}

	
	/**
	 * Get the all status values of device
	 * 
	 * @return map of status name value pairs.
	 */
	@Override
	Map<String, String> getAllStatus() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("temperature", getDeviceStatusValue("temperature"));
		return map;
	}

	/**
	 * Check if device value needs to be updated in the graph.
	 * 
	 * @param name status name
	 * @return boolean value
	 */
	@Override
	boolean isValueNeedUpdate(String name) {
		boolean needUpdate = true;
		if(name.equals("temperature")){
			if(getTemperature() < 0)
				needUpdate = false;
		}
		return needUpdate;
	}

	/**
	 * Get the status predicate value map.
	 * 
	 * @return map of status name and corresponding predicate
	 */
	@Override
	Map<String, String> getStatusPredicateMap() {
		return statusPredicateMap;
	}
}
