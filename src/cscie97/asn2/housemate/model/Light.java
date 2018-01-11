package cscie97.asn2.housemate.model;

import java.util.HashMap;
import java.util.Map;


public class Light extends Appliance {
	private int intensity;
	private String power;

	Map<String, String> statusPredicateMap =  new HashMap<String, String>();
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the Light class.
     */
	Light() {
		this.power = "N/A";
		this.intensity = -1;
		statusPredicateMap.put("intensity", "has_intensity");
		statusPredicateMap.put("power", "has_power");
	}

	/**
	 * @return intensity
	 */
	public int getIntensity() {
		return intensity;
	}

	/**
	 * @param intensity set intensity
	 */
	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

	/**
	 * @return power
	 */
	public String getPower() {
		return power;
	}

	/**
	 * @param power set power
	 */
	public void setPower(String power) {
		this.power = power;
	}
	
	/**
	 * Set the Device Status value
	 * 
	 * @param name Status name.
	 * @param value Status value.
	 */
	public void setDeviceStatusValue(String name, String value){
		if(name.equals("intensity")){
			setIntensity(Integer.parseInt(value));
		}
		else if(name.equals("power")){
			setPower(value);
		}
	}
	
	/**
	 * Get the Device Status value
	 * 
	 * @param name Device Status name.
	 * @return Device status value string.
	 */
	public String getDeviceStatusValue(String name){
		
		if(name.equals("intensity")) return String.valueOf(getIntensity());
		else if(name.equals("power")) return getPower();
		else return "";

	}

	/**
	 * Get the all status values of device
	 * 
	 * @return map of status name value pairs.
	 */
	@Override
	Map<String, String> getAllStatus() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("intensity", getDeviceStatusValue("intensity"));
		map.put("power", getDeviceStatusValue("power"));
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
		if(name.equals("intensity")){
			if(getIntensity() < 0)
				needUpdate = false;
		}
		if(name.equals("power")){
			if(getPower().equalsIgnoreCase("N/A"))
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
