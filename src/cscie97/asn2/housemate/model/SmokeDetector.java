package cscie97.asn2.housemate.model;

import java.util.HashMap;
import java.util.Map;

public class SmokeDetector extends Sensor {
	private static final String statusOK = "OK";
	private static final String statusAlarm = "Alarm";
	private String currentStatus;
	Map<String, String> statusPredicateMap =  new HashMap<String, String>();
	
	 /**
     * Default constructor
     * Uses package visibility to restrict creation of the SmokeDetector class.
     */
	public SmokeDetector() {
		currentStatus = "N/A";
		statusPredicateMap.put("statusSmokeDetector", "has_state");
	}
	
	/**
	 * @return boolean if smoke detector has "Ok" status 
	 */
	boolean isOK(){
		return this.getDeviceStatusValue("").equals(statusOK);
	}
	
	/**
	 * @return boolean if smoke detector has "Alarm" status 
	 */
	boolean isAlarm(){
		return this.getDeviceStatusValue("").equals(statusAlarm);
	}

	/**
	 * Set the Smoke Detector Status value
	 * 
	 * @param name Status name.
	 * @param value Status value.
	 */
	public void setDeviceStatusValue(String name, String value){
		if(name.equals("statusSmokeDetector")){
			setCurrentStatus(value);
		}
	}
	
	/**
	 * Get the Smoke Detector Status value
	 * 
	 * @param name Camera Status name.
	 * @return Camera status value string.
	 */
	public String getDeviceStatusValue(String name){
		if(name.equals("statusSmokeDetector")) return getCurrentStatus();
		else return "";
	}
	
	
	/**
	 * @return map of status name value pairs. 
	 */
	@Override
	Map<String, String> getAllStatus() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("statusSmokeDetector", getDeviceStatusValue("statusSmokeDetector"));
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
		
		if(getCurrentStatus().equalsIgnoreCase("N/A"))
			needUpdate = false;
		
		return needUpdate;
	}

	/**
	 * Get the current status
	 * 
	 * @return currentstatus.
	 */
	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
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
