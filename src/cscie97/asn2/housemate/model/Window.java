package cscie97.asn2.housemate.model;

import java.util.HashMap;
import java.util.Map;


public class Window extends Appliance {
	
	private static final String open = "Open";
	private static final String closed = "Closed";
	private static final String privacy = "Privacy";
	private String currentStatus;
	Map<String, String> statusPredicateMap =  new HashMap<String, String>();
	 /**
     * Default constructor
     * Uses package visibility to restrict creation of the Window class.
     */
	Window() {
		this.currentStatus = "N/A";
		statusPredicateMap.put("windowStatus", "has_state");
	}
	
	/**
	 * Get the Device Status value
	 * 
	 * @param name Device Status name.
	 * @return Device status value string.
	 */
	public String getDeviceStatusValue(String name) {
		return this.currentStatus;
	}

	/**
	 * Set the Device Status value
	 * 
	 * @param name Status name.
	 * @param value Status value.
	 */
	public void setDeviceStatusValue(String name, String value) {
		this.currentStatus = value;
	}

	@Override
	/**
	 * Get the all status values of device
	 * 
	 * @return map of status name value pairs.
	 */
	Map<String, String> getAllStatus() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("windowStatus", getDeviceStatusValue(""));
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
		if(this.currentStatus.equalsIgnoreCase("N/A"))
			return false;
		else 
			return true;
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
