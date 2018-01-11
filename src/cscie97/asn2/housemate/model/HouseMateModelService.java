package cscie97.asn2.housemate.model;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import cscie97.asn4.housemate.entitlement.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.InvalidAccessTokenException;
import cscie97.asn4.housemate.entitlement.ResourceAlreadyExistsException;
import cscie97.asn4.housemate.entitlement.ResourceRoleAlreadyExistsException;
import cscie97.asn4.housemate.entitlement.UserAlreadyExistsException;
import cscie97.asn4.housemate.entitlement.UserCredentialOptionNotExistsException;

public interface HouseMateModelService {
	/**Define a house in House Mate system
	 * @param auth_token Authentication token
	 * @param name House name
	 * @param address House address
	 * @throws HouseAlreadyExistsException house already exists eception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 * @throws AccessDeniedException Access denied exception
	 * @throws InvalidAccessTokenException Access token invalid exception
	 * @throws ResourceAlreadyExistsException Resource already exists exception
	 */
	public void createHouse(String auth_token, String name, String address) 
			throws HouseAlreadyExistsException, EmptyOrNUllAuthTokenException, 
			InvalidAccessTokenException, AccessDeniedException, ResourceAlreadyExistsException;
	
	/**Define a room in House Mate system
	 * @param auth_token Authentication token
	 * @param name House name
	 * @param floor floor number
	 * @param type room type
	 * @param houseName  House name
	 * @param windowsCount  windows Count
	 * @throws RoomAlreadyExistsException Room already exists exception
	 * @throws HouseNotExistsException House not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 * @throws AccessDeniedException Access denied exception
	 * @throws InvalidAccessTokenException Access token invalid exception
	 */
	public void createRoom(String auth_token, String name, int floor, String type, String houseName, int windowsCount) 
			throws RoomAlreadyExistsException, HouseNotExistsException, EmptyOrNUllAuthTokenException, 
			InvalidAccessTokenException, AccessDeniedException;
	
	/**Define an occupant in house mate system
	 * @param auth_token Authentication token
	 * @param name House name
	 * @param type Occupant type
	 * @return Occupant object
	 * @throws OccupantAlreadyExistsException Occupant already exists exception
	 * @throws EmptyOrNUllAuthTokenException  Auth token null or empty exception
	 * @throws AccessDeniedException Access denied exception
	 * @throws InvalidAccessTokenException Access token invalid exception
	 * @throws UserCredentialOptionNotExistsException User credential option invalid exception
	 * @throws UserAlreadyExistsException User already exists exception
	 */
	public Occupant createOccupant(String auth_token, String name, String type) 
			throws OccupantAlreadyExistsException, EmptyOrNUllAuthTokenException,
			InvalidAccessTokenException, AccessDeniedException, UserCredentialOptionNotExistsException, UserAlreadyExistsException;
	
	/**Define a sensor in house mate system
	 * @param auth_token Authentication token
	 * @param name House name
	 * @param type Sensor type
	 * @param room room location
	 * @throws RoomNotExistsException Room not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 * @throws AccessDeniedException Access denied exception
	 * @throws InvalidAccessTokenException Access token invalid exception
	 */
	public void createSensor(String auth_token, String name, String type, String room) 
			throws RoomNotExistsException, EmptyOrNUllAuthTokenException, InvalidAccessTokenException, AccessDeniedException;
	
	/**Define an appliance in house mate system
	 * @param auth_token Authentication token
	 * @param name House name
	 * @param type Appliance type
	 * @param room room location
	 * @throws RoomNotExistsException Room not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 * @throws AccessDeniedException Access denied exception
	 * @throws InvalidAccessTokenException Access token invalid exception
	 */
	public void createAppliance(String auth_token, String name, String type, String room)
			throws RoomNotExistsException, EmptyOrNUllAuthTokenException, InvalidAccessTokenException, AccessDeniedException;
	
	/**Get all occupants in a room 
	 * @param auth_token Authentication token
	 * @param roomName Room name
	 * @return occupant set 
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 */
	public Set<String> getOccupantsInRoom(String auth_token, String roomName) throws EmptyOrNUllAuthTokenException;
	
	/**Get all devices in a room for certain type
	 * @param auth_token Authentication token
	 * @param roomName Room name
	 * @param type Device type
	 * @return String set
	 * @throws HouseNotExistsException House not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 * @throws AccessDeniedException Access denied exception
	 * @throws InvalidAccessTokenException Access token invalid exception
	 */
	public Set<String> getAllDevicesOfRoomForType(String auth_token, String roomName, DeviceType type)
			throws HouseNotExistsException, EmptyOrNUllAuthTokenException, InvalidAccessTokenException, AccessDeniedException;
	
	/**Add an occupant to the house
	 * @param auth_token Authentication token
	 * @param name Occupant name
	 * @param houseName House name
	 * @throws HouseNotExistsException House not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 * @throws AccessDeniedException Access denied exception
	 * @throws InvalidAccessTokenException Access token invalid exception
	 * @throws ResourceRoleAlreadyExistsException Resource role already exists exception
	 */
	public void addOccupantToHouse(String auth_token, String name, String houseName) 
			throws HouseNotExistsException, EmptyOrNUllAuthTokenException, 
			InvalidAccessTokenException, AccessDeniedException, ResourceRoleAlreadyExistsException;
	
	public DeviceType getDeviceType(String type);
	/**Update occupant location in the house
	 * @param auth_token Authentication token
	 * @param name Occupant name
	 * @param roomName Room name
	 * @throws HouseNotExistsException House not exists exception
	 * @throws RoomNotExistsException Room not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 * @throws AccessDeniedException Access denied exception
	 * @throws InvalidAccessTokenException Access token invalid exception
	 */
	public void updateOccupantLocation(String auth_token, String name, String roomName) 
			throws HouseNotExistsException, RoomNotExistsException, EmptyOrNUllAuthTokenException,
			InvalidAccessTokenException, AccessDeniedException;
	
	/**
	 * @param auth_token Auth token
	 * @param name name
	 * @param roomName roomname
	 * @param state state
	 * @throws HouseNotExistsException house not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 * @throws InvalidAccessTokenException Access token invalid exception
	 * @throws AccessDeniedException Access denied exception
	 */
	void updateOccupantState(String auth_token, String name, String roomName,  String state)
			throws HouseNotExistsException, EmptyOrNUllAuthTokenException, InvalidAccessTokenException, AccessDeniedException;
	
	/**Set the sensor status with a value
	 * @param auth_token Authentication token
	 * @param name Sensor name
	 * @param statusName Status name
	 * @param value Status value
	 * @throws SensorNotExistsException Sensor not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 */
	public void setSensorStatus(String auth_token, String name, String statusName, String value) 
			throws SensorNotExistsException, EmptyOrNUllAuthTokenException;
	
	/**Get the sensor status value
	 * @param auth_token Authentication token
	 * @param name Sensor name
	 * @param statusName Status name
	 * @return Sensor status value
	 * @throws SensorNotExistsException Sensor not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 */
	public String getSensorStatus(String auth_token, String name, String statusName) 
			throws SensorNotExistsException, EmptyOrNUllAuthTokenException;
	
	/**Set the appliance status with a value
	 * @param auth_token Authentication token
	 * @param name Appliance name
	 * @param statusName Status name
	 * @param value Status value
	 * @param voice_print Voice print if provided by occupant
	 * @throws ApplianceNotExistsException Appliance not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 * @throws AccessDeniedException Access denied exception
	 * @throws InvalidAccessTokenException Access token invalid exception
	 */
	public void setApplianceStatus(String auth_token, String name, String statusName, String value, String voice_print) 
			throws ApplianceNotExistsException, EmptyOrNUllAuthTokenException, InvalidAccessTokenException, AccessDeniedException;
	
	/**Get the appliance status value
	 * @param auth_token Authentication token
	 * @param name Appliance name
	 * @param statusName Status name
	 * @return Appliance status value
	 * @throws ApplianceNotExistsException Appliance not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 * @throws AccessDeniedException Access denied exception
	 * @throws InvalidAccessTokenException Access token invalid exception
	 */
	public String getApplianceStatus(String auth_token, String name, String statusName) 
			throws ApplianceNotExistsException, EmptyOrNUllAuthTokenException, InvalidAccessTokenException, AccessDeniedException;
	
	/**Get all status values
	 * @param auth_token Authentication token
	 * @param name Sensor/Appliance name
	 * @return map of status name value pairs
	 * @throws ApplianceNotExistsException Appliance not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 */
	public Map<String, String> getAllStatusOfSensorOrAppliance(String auth_token, String name) 
			throws ApplianceNotExistsException, EmptyOrNUllAuthTokenException;
	
	/**Get the room configuration
	 * @param auth_token Authentication token
	 * @param name Room name
	 * @return set of strings containing room information e.g. occupants, sensors etc.
	 * @throws RoomNotExistsException Room not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 */
	public Set<String> getRoomConfiguration(String auth_token, String name) 
			throws RoomNotExistsException, EmptyOrNUllAuthTokenException;
	
	/**Get the house configuration
	 * @param auth_token Authentication token
	 * @param name House name
	 * @return set of strings containing House information e.g. rooms, occupants, sensors etc.
	 * @throws HouseNotExistsException House not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 */
	public Set<String> getHouseConfiguration(String auth_token, String name) 
			throws HouseNotExistsException, EmptyOrNUllAuthTokenException;
	
	/**Get the configuration of all the houses
	 * @param auth_token Authentication token
	 * @return set of sets containing House information e.g. rooms, occupants, sensors etc. for all the houses defined in the service.
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 */
	public Set<LinkedHashSet<String>> getAllHousesConfiguration(String auth_token) 
			throws EmptyOrNUllAuthTokenException;

	/**
	 * @param auth_token Auth token
	 * @param roomName room name
	 * @return map of floor and windows
	 * @throws HouseNotExistsException House not exists exception
	 * @throws RoomNotExistsException Room not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 */
	Map<String, Integer> getFloorAndWindowCountForRoom(String auth_token, String roomName)
			throws HouseNotExistsException, RoomNotExistsException, EmptyOrNUllAuthTokenException;

	/**
	 * @param auth_token Auth token
	 * @param OccupantName name
	 * @return location string
	 * @throws OccupantNotExistsException occupant not exists exception
	 * @throws EmptyOrNUllAuthTokenException Auth token null or empty exception
	 */
	String getOccupantLocation(String auth_token, String OccupantName) 
			throws OccupantNotExistsException, EmptyOrNUllAuthTokenException;

	Set<String> getOccupantsInHouse(String auth_token, String houseName) 
			throws EmptyOrNUllAuthTokenException;

	Set<String> getAllDevicesOfHouseForType(String auth_token, String houseName, DeviceType type)
			throws HouseNotExistsException, EmptyOrNUllAuthTokenException;

	
}
