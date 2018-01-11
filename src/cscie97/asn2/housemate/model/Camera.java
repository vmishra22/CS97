package cscie97.asn2.housemate.model;

import java.util.HashMap;
import java.util.Map;

public class Camera extends Sensor{
	private String statusDetected;
	private String statusLeaving;
	private String statusActive;
	private String statusInactive;
	
	Map<String, String> statusPredicateMap =  new HashMap<String, String>();
	 /**
     * Default constructor
     * Uses package visibility to restrict creation of the Camera class.
     */
	Camera() {
		statusDetected = "N/A";
		statusLeaving = "N/A";
		statusActive = "N/A";
		statusInactive = "N/A";
		statusPredicateMap.put("statusDetected", "has_detected_in_room");
		statusPredicateMap.put("statusLeaving", "has_detected_leaving_room");
		statusPredicateMap.put("statusActive", "is_active");
		statusPredicateMap.put("statusInactive", "is_inactive");
	}
	
	public String getStatusActive() {
		return statusActive;
	}

	public void setStatusActive(String statusActive) {
		this.statusActive = statusActive;
	}

	public String getStatusInactive() {
		return statusInactive;
	}

	public void setStatusInactive(String statusInactive) {
		this.statusInactive = statusInactive;
	}

	/**
	 * @return detected status value
	 */
	public String getStatusDetected() {
		return statusDetected;
	}

	/**
	 * @param statusDetected set detected status value
	 */
	public void setStatusDetected(String statusDetected) {
		this.statusDetected = statusDetected;
	}

	/**
	 * @return leaving status value
	 */
	public String getStatusLeaving() {
		return statusLeaving;
	}

	/**
	 * @param  statusLeaving set leaving status value
	 */
	public void setStatusLeaving(String statusLeaving) {
		this.statusLeaving = statusLeaving;
	}

	/**
	 * Set the Camera Status value
	 * 
	 * @param name Status name.
	 * @param value Status value.
	 */
	public void setDeviceStatusValue(String name, String value){
		if(name.equals("statusDetected")){
			setStatusDetected(value);
		}
		else if(name.equals("statusLeaving")){
			setStatusLeaving(value);
		}
		else if(name.equals("statusActive")){
			setStatusActive(value);
		}
		else if(name.equals("statusInactive")){
			setStatusInactive(value);
		}
	}
	
	/**
	 * Get the Camera Status value
	 * 
	 * @param name Camera Status name.
	 * @return Camera status value string.
	 */
	public String getDeviceStatusValue(String name){
		
		if(name.equals("statusDetected")) return getStatusDetected();
		else if(name.equals("statusLeaving")) return getStatusLeaving();
		else if(name.equals("statusActive")) return getStatusActive();
		else if(name.equals("statusInactive")) return getStatusInactive();
		else return "";
		
	}
	
	/**
	 * Get the all status values of Camera
	 * 
	 * @return map of status name value pairs.
	 */
	@Override
	Map<String, String> getAllStatus() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("statusDetected", getDeviceStatusValue("statusDetected"));
		map.put("statusLeaving", getDeviceStatusValue("statusLeaving"));
		map.put("statusActive", getDeviceStatusValue("statusActive"));
		map.put("statusInactive", getDeviceStatusValue("statusInactive"));
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
		if(name.equals("statusDetected")){
			if(getStatusDetected().equalsIgnoreCase("N/A"))
				needUpdate = false;
		}
		if(name.equals("statusLeaving")){
			if(getStatusLeaving().equalsIgnoreCase("N/A"))
				needUpdate = false;
		}
		if(name.equals("statusActive")){
			if(getStatusActive().equalsIgnoreCase("N/A"))
				needUpdate = false;
		}
		if(name.equals("statusInactive")){
			if(getStatusInactive().equalsIgnoreCase("N/A"))
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
