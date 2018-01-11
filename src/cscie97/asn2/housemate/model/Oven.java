package cscie97.asn2.housemate.model;

import java.util.HashMap;
import java.util.Map;

public class Oven extends Appliance {

	private int temperature;
	private int timeToCook;
	private String power;
	
	Map<String, String> statusPredicateMap =  new HashMap<String, String>();
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the Oven class.
     */
	 Oven() {
		this.power = "N/A";
		this.temperature = -1;
		this.timeToCook = -1;
		statusPredicateMap.put("temperature", "has_temperature");
		statusPredicateMap.put("timeToCook", "has_timeToCook");
		statusPredicateMap.put("power", "has_power");
	}

	/**
	 * @return temperature 
	 */
	public int getTemperature() {
		return temperature;
	}
	
	/**
	 * 
	 * @param temperature to set (greater than zero)
	 */
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return timeToCook 
	 */
	public int getTimeToCook() {
		return timeToCook;
	}
	
	/**
	 * 
	 * @param timeToCook to set (greater than zero)
	 */
	public void setTimeToCook(int timeToCook) {
		this.timeToCook = timeToCook;
	}

	/**
	 * @return power 
	 */
	public String getPower() {
		return power;
	}

	/**
	 * 
	 * @param power set power value
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
		if(name.equals("temperature")){
			setTemperature(Integer.parseInt(value));
		}
		else if(name.equals("timeToCook")){
			setTimeToCook(Integer.parseInt(value));
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
		
		if(name.equals("temperature")) return String.valueOf(getTemperature());
		else if(name.equals("timeToCook")) return String.valueOf(getTimeToCook());
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
		map.put("temperature", getDeviceStatusValue("temperature"));
		map.put("timeToCook", getDeviceStatusValue("timeToCook"));
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
		if(name.equals("temperature")){
			if(getTemperature() < 0)
				needUpdate = false;
		}
		else if(name.equals("timeToCook")){
			if(getTimeToCook() < 0)
				needUpdate = false;
		}
		else if(name.equals("power")){
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
