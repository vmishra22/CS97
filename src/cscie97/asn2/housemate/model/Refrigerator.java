package cscie97.asn2.housemate.model;

import java.util.HashMap;
import java.util.Map;

public class Refrigerator extends Appliance{
	
	private int temperature;
	private int beerCount;
	private String cleanRefrigerator;
	
	Map<String, String> statusPredicateMap =  new HashMap<String, String>();
	
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the Refrigerator class.
     */
	public Refrigerator() {
		this.temperature = -1;
		this.beerCount = -1;
		this.cleanRefrigerator = "N/A";
		statusPredicateMap.put("temperature", "has_temperature");
		statusPredicateMap.put("beerCount", "has_beerCount");
		statusPredicateMap.put("clean", "has_clean");
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
	 * @return beer count 
	 */
	public int getBeerCount() {
		return beerCount;
	}
	/**
	 * 
	 * @param beerCount beer Count to set (greater than zero)
	 */
	public void setBeerCount(int beerCount) {
		this.beerCount = beerCount;
	}
	
	/**
	 * @return clean refrigerator status 
	 */
	public String isCleanRefrigerator() {
		return cleanRefrigerator;
	}
	/**
	 * 
	 * @param cleanRefrigerator clean refrigerator status to set (greater than zero)
	 */
	public void setCleanRefrigerator(String cleanRefrigerator) {
		this.cleanRefrigerator = cleanRefrigerator;
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
		else if(name.equals("beerCount")){ 
			setBeerCount(Integer.parseInt(value));
		}
		else if(name.equals("clean")){
			setCleanRefrigerator(value);
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
		else if(name.equals("beerCount")) return String.valueOf(getBeerCount());
		else if(name.equals("clean")) return isCleanRefrigerator();
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
		map.put("beerCount", getDeviceStatusValue("beerCount"));
		map.put("clean", getDeviceStatusValue("clean"));
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
		else if(name.equals("beerCount")){
			if(getBeerCount() < 0)
				needUpdate = false;
		}
		else if(name.equals("clean")){
			if(isCleanRefrigerator().equalsIgnoreCase("N/A"))
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
