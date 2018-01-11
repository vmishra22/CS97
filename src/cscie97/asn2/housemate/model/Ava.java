package cscie97.asn2.housemate.model;

import java.util.HashMap;
import java.util.Map;


/**
 * @author V1A
 *
 */
public class Ava extends Appliance {

	private String statusCommand;
	private String statusGenericCommand;
	private String statusQuestion;
	private String statusResponse;
	
	Map<String, String> statusPredicateMap =  new HashMap<String, String>();
	 /**
     * Default constructor
     * Uses package visibility to restrict creation of the Ava class.
     */
	Ava() {
		statusQuestion = "N/A";
		statusCommand = "N/A";
		statusGenericCommand = "N/A";
		statusResponse = "N/A";
		statusPredicateMap.put("statusQuestion", "has_statusQuestion");
		statusPredicateMap.put("statusCommand", "has_statusCommand");
		statusPredicateMap.put("statusResponse", "has_statusResponse");
		statusPredicateMap.put("statusGenericCommand", "has_statusGenericCommand");
	}
	
	public String getStatusResponse() {
		return statusResponse;
	}

	public void setStatusResponse(String statusResponse) {
		this.statusResponse = statusResponse;
	}
	
	/**
	 * @return status command value
	 */
	public String getStatusCommand() {
		return statusCommand;
	}

	/**
	 * @param statusCommand set status command value
	 */
	public void setStatusCommand(String statusCommand) {
		this.statusCommand = statusCommand;
	}

	/**
	 * @return status question value
	 */
	public String getStatusQuestion() {
		return statusQuestion;
	}

	/**
	 * @param statusQuestion set status question value
	 */
	public void setStatusQuestion(String statusQuestion) {
		this.statusQuestion = statusQuestion;
	}
	
	public String getStatusGenericCommand() {
		return statusGenericCommand;
	}

	public void setStatusGenericCommand(String statusGenericCommand) {
		this.statusGenericCommand = statusGenericCommand;
	}
	
	/**
	 * Set the Ava Status value
	 * 
	 * @param name Status name.
	 * @param value Status value.
	 */
	public void setDeviceStatusValue(String name, String value){
		if(name.equals("statusQuestion")){
			setStatusQuestion(value);
		}
		else if(name.equals("statusCommand")){
			setStatusCommand(value);
		}
		else if(name.equals("statusResponse")){
			setStatusResponse(value);
		}
		else if(name.equals("statusGenericCommand")){
			setStatusGenericCommand(value);
		}
	}
	
	/**
	 * Get the Ava Status value
	 * 
	 * @param name Camera Status name.
	 * @return Camera status value string.
	 */
	public String getDeviceStatusValue(String name){
		
		if(name.equals("statusQuestion")) return getStatusQuestion();
		else if(name.equals("statusCommand")) return getStatusCommand();
		else if(name.equals("statusResponse")) return getStatusResponse();
		else if(name.equals("statusGenericCommand")) return getStatusGenericCommand();
		else return "";
		
	}
	
	/**
	 * Get the all status values of Ava
	 * 
	 * @return map of status name value pairs.
	 */
	@Override
	Map<String, String> getAllStatus() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("statusQuestion", getDeviceStatusValue("statusQuestion"));
		map.put("statusCommand", getDeviceStatusValue("statusCommand"));
		map.put("statusGenericCommand", getDeviceStatusValue("statusGenericCommand"));
		map.put("statusResponse", getDeviceStatusValue("statusResponse"));
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
		if(name.equals("statusQuestion")){
			if(getStatusQuestion().equalsIgnoreCase("N/A"))
				needUpdate = false;
		}
		if(name.equals("statusCommand")){
			if(getStatusCommand().equalsIgnoreCase("N/A"))
				needUpdate = false;
		}
		if(name.equals("statusResponse")){
			if(getStatusResponse().equalsIgnoreCase("N/A"))
				needUpdate = false;
		}
		if(name.equals("statusGenericCommand")){
			if(getStatusGenericCommand().equalsIgnoreCase("N/A"))
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
