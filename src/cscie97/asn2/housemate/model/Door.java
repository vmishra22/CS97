package cscie97.asn2.housemate.model;

import java.util.HashMap;
import java.util.Map;


public class Door extends Appliance {
	
	private static final String open = "Open";
	private static final String closed = "Closed";
	private static final String locked = "Locked";
	private String currentStatus;
	Map<String, String> statusPredicateMap =  new HashMap<String, String>();

	 /**
     * Default constructor
     * Uses package visibility to restrict creation of the Door class.
     */
	Door() {
		this.currentStatus = "N/A";
		statusPredicateMap.put("doorStatus", "has_state");
	}

	/**
	 * Get the Door Status value
	 * 
	 * @param name Door Status name.
	 * @return Door status value string.
	 */
	public String getDeviceStatusValue(String name) {
			return this.currentStatus;
	}

	/**
	 * Set the Door Status value
	 * 
	 * @param name Status name.
	 * @param value Status value.
	 */
	public void setDeviceStatusValue(String name, String value) {
		this.currentStatus = value;
	}

	/**
	 * Get the all status values of Door
	 * 
	 * @return map of status name value pairs.
	 */
	@Override
	Map<String, String> getAllStatus() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("doorStatus", getDeviceStatusValue(""));
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
		if(this.currentStatus.equalsIgnoreCase("N/A"))
			needUpdate = false;
		return needUpdate;
	}

	/**
	 * Get the status predicate value map.
	 * 
	 * @return map of status name and corresponding predicate
	 */
	@Override
	Map<String, String> getStatusPredicateMap() {
		// TODO Auto-generated method stub
		return statusPredicateMap;
	}

}
