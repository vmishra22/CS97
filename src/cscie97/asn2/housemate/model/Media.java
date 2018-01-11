package cscie97.asn2.housemate.model;

import java.util.HashMap;
import java.util.Map;

public class Media extends Appliance {
	private int channel;
	private String power;
	private int volume;

	Map<String, String> statusPredicateMap =  new HashMap<String, String>();
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the Media class.
     */
	Media() {
		this.power = "N/A";
		this.volume = -1;
		this.channel = -1;
		statusPredicateMap.put("channel", "has_channel");
		statusPredicateMap.put("volume", "has_volume");
		statusPredicateMap.put("power", "has_power");
	}

	/**
	 * @return channel value
	 */
	public int getChannel() {
		return channel;
	}

	/**
	 * @param channel set channel value
	 */
	public void setChannel(int channel) {
		this.channel = channel;
	}

	/**
	 * @return power value
	 */
	public String getPower() {
		return power;
	}

	/**
	 * @param power set power value
	 */
	public void setPower(String power) {
		this.power = power;
	}

	/**
	 * @return volume value
	 */
	public int getVolume() {
		return volume;
	}

	/**
	 * @param volume set volume value
	 */
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	/**
	 * Set the Device Status value
	 * 
	 * @param name Status name.
	 * @param value Status value.
	 */
	public void setDeviceStatusValue(String name, String value){
		if(name.equals("channel")){
			setChannel(Integer.parseInt(value));
		}
		else if(name.equals("volume")){
			setVolume(Integer.parseInt(value));
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
		
		if(name.equals("channel")) return String.valueOf(getChannel());
		else if(name.equals("volume")) return String.valueOf(getVolume());
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
		map.put("channel", getDeviceStatusValue("channel"));
		map.put("volume", getDeviceStatusValue("channel"));
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
		if(name.equals("channel")){
			if(getChannel() < 0)
				needUpdate = false;
		}
		else if(name.equals("volume")){
			if(getVolume() < 0)
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
