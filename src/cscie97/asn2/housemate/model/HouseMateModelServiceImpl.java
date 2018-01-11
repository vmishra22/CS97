package cscie97.asn2.housemate.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import cscie97.asn1.knowledge.engine.KnowledgeGraph;
import cscie97.asn1.knowledge.engine.Triple;
import cscie97.asn4.housemate.entitlement.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.HouseMateEntitlementService;
import cscie97.asn4.housemate.entitlement.HouseMateEntitlementServiceFactory;
import cscie97.asn4.housemate.entitlement.InvalidAccessTokenException;
import cscie97.asn4.housemate.entitlement.ResourceAlreadyExistsException;
import cscie97.asn4.housemate.entitlement.ResourceRoleAlreadyExistsException;
import cscie97.asn4.housemate.entitlement.UserAlreadyExistsException;
import cscie97.asn4.housemate.entitlement.UserCredentialOptionNotExistsException;

class HouseMateModelServiceImpl extends Observable implements HouseMateModelService {

	private Set<House> houseSet = new HashSet<House>();
	private Set<Occupant> occupantSet = new HashSet<Occupant>();
	private HouseMateEntitlementService entitlementService = HouseMateEntitlementServiceFactory.getInstance();

	/**
     * Default constructor
     * Uses package visibility to restrict creation of the HouseMateModelServiceImpl class.
     */
	HouseMateModelServiceImpl(){
	}
	
	public void stateChanged(MessagePacket packet){
		setChanged();
		notifyObservers(packet);
	}
	
	/**
	 * @return return the house set managed by service.
	 */
	public Set<House> getHouseSet() {
		return houseSet;
	}

	/**
	 * @param houseSet set the house set managed by service.
	 */
	public void setHouseSet(Set<House> houseSet) {
		this.houseSet = houseSet;
	}

	/**
	 * @return return the occupant set managed by service.
	 */
	public Set<Occupant> getOccupantSet() {
		return occupantSet;
	}

	/**
	 * @param occupantSet set the occupant set managed by service.
	 */
	public void setOccupantSet(Set<Occupant> occupantSet) {
		this.occupantSet = occupantSet;
	}

	/**Define a house in House Mate system
	 * @param auth_token Authentication token
	 * @param name House name
	 * @param address House address
	 * @throws HouseAlreadyExistsException house already exists eception
	 * @throws EmptyOrNUllAuthTokenException 
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 * @throws ResourceAlreadyExistsException 
	 */
	@Override
	public void createHouse(String auth_token, String name, String address) 
			throws HouseAlreadyExistsException, EmptyOrNUllAuthTokenException, 
			InvalidAccessTokenException, AccessDeniedException, ResourceAlreadyExistsException {
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for create house");
			throw en;
		}
		boolean checkAccess = entitlementService.checkAccess(auth_token, "", "user_admin");
		if(!checkAccess){
			AccessDeniedException ad = new AccessDeniedException();
			ad.setDescription("Access is denied for create house");
			throw ad;
		}
		
		House newHouse = new House(name, address);
		if(!(houseSet.contains(newHouse))){
			houseSet.add(newHouse);
			//<house_name> has_address <house_address>
			KnowledgeGraph.getInstance().importTriple(newHouse.getName(), "has_address", newHouse.getAddress());
			
			entitlementService.createResource(name, "");
		}
		else{
			HouseAlreadyExistsException he = new HouseAlreadyExistsException();
			he.setDescription("House cannot be created, it already exists.");
			he.setHouseNameContext(name);
			throw he;
		}
	}

	/**Define a room in House Mate system
	 * @param auth_token Authentication token
	 * @param name House name
	 * @param floor floor number
	 * @param type room type
	 * @param houseName  House name
	 * @param windowsCount  windows Count
	 * @throws RoomAlreadyExistsException Room already exists exception
	 * @throws HouseNotExistsException House not exists exception
	 * @throws EmptyOrNUllAuthTokenException 
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 */
	@Override
	public void createRoom(String auth_token, String name, int floor, String type, String houseName, int windowsCount) 
			throws RoomAlreadyExistsException, HouseNotExistsException, EmptyOrNUllAuthTokenException,
			InvalidAccessTokenException, AccessDeniedException {
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for create room");
			throw en;
		}
		boolean checkAccess = entitlementService.checkAccess(auth_token, "", "user_admin");
		if(!checkAccess){
			AccessDeniedException ad = new AccessDeniedException();
			ad.setDescription("Access is denied for create house");
			throw ad;
		}
		
		boolean foundHouse = false;
		for(House h:houseSet){
			if(h.getName().equals(houseName)){
				foundHouse = true;
				Room newRoom = new Room(name, floor, type, houseName, windowsCount);
				if(!(h.getRoomSet().contains(newRoom))){
					h.getRoomSet().add(newRoom);
					
					/* <room_name> located_on_floor <floor>
					 * <room_name> has_type <roomType>
					 * <room_name> has_windows <windowsCount>
					 * */
					KnowledgeGraph.getInstance().importTriple(newRoom.getRoomName(), "belongs_to", houseName);
					KnowledgeGraph.getInstance().importTriple(newRoom.getRoomName(), "located_on_floor", String.valueOf(newRoom.getFloor()));
					KnowledgeGraph.getInstance().importTriple(newRoom.getRoomName(), "has_type", newRoom.getRoomType());
					KnowledgeGraph.getInstance().importTriple(newRoom.getRoomName(), "has_windows", String.valueOf(newRoom.getWindowsCount()));
				}
				else{
					RoomAlreadyExistsException re = new RoomAlreadyExistsException();
					re.setDescription("Room cannot be created, it already exists.");
					re.setRoomNameContext(name);
					throw re;
				}
			}
		}
		if(!foundHouse){
			HouseNotExistsException he = new HouseNotExistsException();
			he.setDescription("House does not exist.");
			he.setHouseNameContext(houseName);
			throw he;
		}
	}

	/**Define an occupant in house mate system
	 * @param auth_token Authentication token
	 * @param name House name
	 * @param type Occupant type
	 * @return Occupant object
	 * @throws OccupantAlreadyExistsException Occupant already exists exception
	 * @throws EmptyOrNUllAuthTokenException 
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 * @throws UserCredentialOptionNotExistsException 
	 * @throws UserAlreadyExistsException 
	 */
	@Override
	public Occupant createOccupant(String auth_token, String name, String type) 
			throws OccupantAlreadyExistsException, EmptyOrNUllAuthTokenException, 
			InvalidAccessTokenException, AccessDeniedException, UserCredentialOptionNotExistsException, UserAlreadyExistsException {
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for create occupant");
			throw en;
		}
		boolean checkAccess = entitlementService.checkAccess(auth_token, "", "user_admin");
		if(!checkAccess){
			AccessDeniedException ad = new AccessDeniedException();
			ad.setDescription("Access is denied for create house");
			throw ad;
		}
		Occupant newOccupant = null;
		if(type.equalsIgnoreCase("adult")){
			newOccupant = new Adult();
		}else if(type.equalsIgnoreCase("child")){
			newOccupant = new Children();
		}else if(type.equalsIgnoreCase("pet")){
			newOccupant = new Pet();
		}
		
		if(newOccupant != null){
			newOccupant.setName(name);
			newOccupant.setType(type);
		}
		
		if(!(occupantSet.contains(newOccupant))){
			occupantSet.add(newOccupant);
			
			/* <occupant_name> has_type <type>
			 * */
			KnowledgeGraph.getInstance().importTriple(newOccupant.getName(), "has_type", type);
			
			entitlementService.createUser(name, name);
			String voice_print_value = "--" + name + "--";
			entitlementService.addUserCredential(name, name, "voice_print", voice_print_value);
		}
		else{
			OccupantAlreadyExistsException oe = new OccupantAlreadyExistsException();
			oe.setDescription("Occupant already exists");
			oe.setOccupantNameContext(name);
			throw oe;
		}
		
		return newOccupant;
		
	}

	/**Define a sensor in house mate system
	 * @param auth_token Authentication token
	 * @param name House name
	 * @param type Sensor type
	 * @param room room location
	 * @throws RoomNotExistsException Room not exists exception
	 * @throws EmptyOrNUllAuthTokenException 
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 */
	@Override
	public void createSensor(String auth_token, String name, String type, String room) throws RoomNotExistsException, EmptyOrNUllAuthTokenException, InvalidAccessTokenException, AccessDeniedException {
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for create sensor");
			throw en;
		}
		boolean checkAccess = entitlementService.checkAccess(auth_token, "", "user_admin");
		if(!checkAccess){
			AccessDeniedException ad = new AccessDeniedException();
			ad.setDescription("Access is denied for create sensor");
			throw ad;
		}
		Device device = null;
		boolean foundRoom = false;
		for(House h:houseSet){
			Set<Room> roomSet = h.getRoomSet();
			for(Room r:roomSet){
				if((r.getRoomName()).equalsIgnoreCase(room)){
					if(type.equalsIgnoreCase("smoke_detector")){
						device = new SmokeDetector();
						device.setType(DeviceType.SMOKE_DETECTOR);
					}
					else if(type.equalsIgnoreCase("camera")){
						device = new Camera();
						device.setType(DeviceType.CAMERA);
					}
					
					if(device != null){
						device.setIdentifier(room + ":" + name);
						device.setLocation(r);
						h.getDeviceSet().add(device);
						
						/* <sensor_name> has_category <category>
						 * <sensor_name> has_type <type>
						 * <sensor_name> has_location <room_name>
						 * */
						KnowledgeGraph.getInstance().importTriple(device.getIdentifier(), "has_category", "sensor");
						KnowledgeGraph.getInstance().importTriple(device.getIdentifier(), "has_type", type);
						KnowledgeGraph.getInstance().importTriple(device.getIdentifier(), "has_location", room);
						KnowledgeGraph.getInstance().importTriple(room, "has_sensor", device.getIdentifier());
					}
					foundRoom = true;
					break;
				}
			}
			if(foundRoom) break;
		}
		
		if(!foundRoom){
			RoomNotExistsException re = new RoomNotExistsException();
			re.setDescription("Room does not exist to add sensor");
			re.setRoomNameContext(room);
			throw re;
		}
	}

	@Override
	public DeviceType getDeviceType(String type){
		DeviceType devicetype = DeviceType.DEFAULT;
		if(type.equalsIgnoreCase("refrigerator")){
			devicetype = DeviceType.REFRIGERATOR;
		}
		else if(type.equalsIgnoreCase("ava")){
			devicetype = DeviceType.AVA;
		}
		else if(type.equalsIgnoreCase("oven")){
			devicetype = DeviceType.OVEN;
		}
		else if(type.equalsIgnoreCase("pandora")){
			devicetype = DeviceType.PANDORA;
		}
		else if(type.equalsIgnoreCase("tv")){
			devicetype = DeviceType.TV;
		}
		else if(type.equalsIgnoreCase("light")){
			devicetype = DeviceType.LIGHT;
		}
		else if(type.equalsIgnoreCase("door")){
			devicetype = DeviceType.DOOR;
		}
		else if(type.equalsIgnoreCase("window")){
			devicetype = DeviceType.WINDOW;
		}
		else if(type.equalsIgnoreCase("thermostat")){
			devicetype = DeviceType.THERMOSTAT;
		}else if(type.equalsIgnoreCase("smoke_detector")){
			devicetype = DeviceType.SMOKE_DETECTOR;
		}
		else if(type.equalsIgnoreCase("camera")){
			devicetype = DeviceType.CAMERA;
		}
		
		return devicetype;
	}
	
	/**Define an appliance in house mate system
	 * @param auth_token Authentication token
	 * @param name House name
	 * @param type Appliance type
	 * @param room room location
	 * @throws RoomNotExistsException Room not exists exception
	 * @throws EmptyOrNUllAuthTokenException 
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 */
	@Override
	public void createAppliance(String auth_token, String name, String type, String room) throws RoomNotExistsException, EmptyOrNUllAuthTokenException, InvalidAccessTokenException, AccessDeniedException {
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for create appliance");
			throw en;
		}
		boolean checkAccess = entitlementService.checkAccess(auth_token, "", "user_admin");
		if(!checkAccess){
			AccessDeniedException ad = new AccessDeniedException();
			ad.setDescription("Access is denied for create appliance");
			throw ad;
		}
		Device device = null;
		boolean foundRoom = false;
		for(House h:houseSet){
			Set<Room> roomSet = h.getRoomSet();
			for(Room r:roomSet){
				if((r.getRoomName()).equalsIgnoreCase(room)){
					if(type.equalsIgnoreCase("refrigerator")){
						device = new Refrigerator();
						device.setType(DeviceType.REFRIGERATOR);
					}
					else if(type.equalsIgnoreCase("ava")){
						device = new Ava();
						device.setType(DeviceType.AVA);
					}
					else if(type.equalsIgnoreCase("oven")){
						device = new Oven();
						device.setType(DeviceType.OVEN);
					}
					else if(type.equalsIgnoreCase("pandora")){
						device = new Pandora();
						device.setType(DeviceType.PANDORA);
					}
					else if(type.equalsIgnoreCase("tv")){
						device = new TV();
						device.setType(DeviceType.TV);
					}
					else if(type.equalsIgnoreCase("light")){
						device = new Light();
						device.setType(DeviceType.LIGHT);
					}
					else if(type.equalsIgnoreCase("door")){
						device = new Door();
						device.setType(DeviceType.DOOR);
					}
					else if(type.equalsIgnoreCase("window")){
						device = new Window();
						device.setType(DeviceType.WINDOW);
					}
					else if(type.equalsIgnoreCase("thermostat")){
						device = new Thermostat();
						device.setType(DeviceType.THERMOSTAT);
					}
					if(device != null){
						device.setIdentifier(room + ":" + name);
						device.setLocation(r);
						h.getDeviceSet().add(device);
						/* <appliance_name> has_category <category>
						 * <appliance_name> has_type <type>
						 * <appliance_name> has_location <room_name>
						 * */
						KnowledgeGraph.getInstance().importTriple(device.getIdentifier(), "has_category", "appliance");
						KnowledgeGraph.getInstance().importTriple(device.getIdentifier(), "has_type", type);
						KnowledgeGraph.getInstance().importTriple(device.getIdentifier(), "has_location", room);
						KnowledgeGraph.getInstance().importTriple(room, "has_appliance", device.getIdentifier());
					}
					foundRoom = true;
					break;
				}
			}
			if(foundRoom) break;
		}
			
		
		if(!foundRoom){
			RoomNotExistsException re = new RoomNotExistsException();
			re.setDescription("Room does not exist to add appliance");
			re.setRoomNameContext(room);
			throw re;
		}
	}

	@Override
	public Set<String> getOccupantsInHouse(String auth_token, String houseName) throws EmptyOrNUllAuthTokenException{
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for getOccupantsInHouse");
			throw en;
		}
		Set<String> occupantFoundSet = new LinkedHashSet<String>();
		for(House h:houseSet){
			if(h.getName().equalsIgnoreCase(houseName)){
				for (Iterator<Occupant> it = occupantSet.iterator(); it.hasNext(); ) {
					Occupant occ = it.next();
					Set<Triple> tripleOccSetLocation = KnowledgeGraph.getInstance().executeQuery(occ.getName(), "has_location", "?");
					if(tripleOccSetLocation != null){
						occupantFoundSet.add(occ.getName());
					}
				}
			}
		}
		return occupantFoundSet;
	}
	
	@Override
	public Set<String> getOccupantsInRoom(String auth_token, String roomName) throws EmptyOrNUllAuthTokenException{
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for getOccupantsInRoom");
			throw en;
		}
		Set<String> occupantFoundSet = new LinkedHashSet<String>();
		for (Iterator<Occupant> it = occupantSet.iterator(); it.hasNext(); ) {
			Occupant occ = it.next();
			Set<Triple> tripleOccSetLocation = KnowledgeGraph.getInstance().executeQuery(occ.getName(), "has_location", "?");
			if(tripleOccSetLocation != null){
				for (Triple tripleOcc : tripleOccSetLocation) {
					String[] parts = (tripleOcc.getIdentifier()).split("\\s+");
					if(parts[2].equalsIgnoreCase(roomName))
						occupantFoundSet.add(occ.getName());
	            }
			}
		}
		return occupantFoundSet;
	}
	
	@Override
	public Set<String> getAllDevicesOfHouseForType(String auth_token, String houseName, DeviceType type)throws HouseNotExistsException, EmptyOrNUllAuthTokenException{
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for getAllDevicesOfHouseForType");
			throw en;
		}
		boolean foundHouse = false;
		Set<String> deviceSet = new LinkedHashSet<String>();
		for(House h:houseSet){
			if(h.getName().equalsIgnoreCase(houseName)){
				foundHouse = true;
				for (Iterator<Device> it = h.getDeviceSet().iterator(); it.hasNext(); ) {
					Device d = it.next();
					if(d.getType() == type){
						deviceSet.add(d.getIdentifier());
					}
				}
			}
		}
		
		if(!foundHouse){
			HouseNotExistsException he = new HouseNotExistsException();
			he.setDescription("House does not exist to get the devices of this room.");
			he.setHouseNameContext(houseName);
			throw he;
		}
		
		return deviceSet;
		
	}
	
	@Override
	public Set<String> getAllDevicesOfRoomForType(String auth_token, String roomName, DeviceType type)
			throws HouseNotExistsException, EmptyOrNUllAuthTokenException, InvalidAccessTokenException, AccessDeniedException{
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for getAllDevicesOfRoomForType");
			throw en;
		}
		
		boolean foundHouse = false;
		Set<String> deviceSet = new LinkedHashSet<String>();
		String[] partsString = roomName.split(":");
		assert(partsString.length != 2);
		
		for(House h:houseSet){
			if(h.getName().equalsIgnoreCase(partsString[0])){
				
				boolean checkAccess = entitlementService.checkAccess(auth_token, h.getName(), "user_admin");
				if(!checkAccess){
					AccessDeniedException ad = new AccessDeniedException();
					ad.setDescription("Access is denied for adding the occupant to the house");
					throw ad;
				}
				
				foundHouse = true;

				for (Iterator<Device> it = h.getDeviceSet().iterator(); it.hasNext(); ) {
					Device d = it.next();
					if(d.getType() == type && (d.getLocation()).getRoomName().equalsIgnoreCase(roomName)){
						deviceSet.add(d.getIdentifier());
					}
				}
				break;
			}
		}
		
		if(!foundHouse){
			HouseNotExistsException he = new HouseNotExistsException();
			he.setDescription("House does not exist to get the devices of this room.");
			he.setHouseNameContext(partsString[0]);
			throw he;
		}
		
		return deviceSet;
		
	}
	
	@Override
	public String getOccupantLocation(String auth_token, String occupantName) throws OccupantNotExistsException, EmptyOrNUllAuthTokenException{
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for getFloorAndWindowCountForRoom");
			throw en;
		}
		String location = null;
		Set<Triple> tripleOccSetLocation = KnowledgeGraph.getInstance().executeQuery(occupantName, "has_location", "?");
		if(tripleOccSetLocation != null){
			for (Triple tripleOcc : tripleOccSetLocation) {
				String[] parts = (tripleOcc.getIdentifier()).split("\\s+");
				location = parts[2];
            }
		}
		if(location == null){
			OccupantNotExistsException oe = new OccupantNotExistsException();
			oe.setDescription("Occupant not exists");
			oe.setOccupantNameContext(occupantName);
			throw oe;
		}
		
		return location;
	}
	
	@Override
	public Map<String, Integer> getFloorAndWindowCountForRoom(String auth_token, String roomName)throws HouseNotExistsException, RoomNotExistsException, EmptyOrNUllAuthTokenException{
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for getFloorAndWindowCountForRoom");
			throw en;
		}
		boolean foundHouse = false, foundRoom = false;
		String[] partsString = roomName.split(":");
		assert(partsString.length != 2);
		
		Map<String, Integer> floorWindowMap = new HashMap<String, Integer>();
		for(House h:houseSet){
			if(h.getName().equalsIgnoreCase(partsString[0])){
				foundHouse = true;
				Set<Room> roomSet = h.getRoomSet();
				for(Room r:roomSet){
					if((r.getRoomName()).equalsIgnoreCase(roomName)){
						foundRoom = true;
						floorWindowMap.put("floor", r.getFloor());
						floorWindowMap.put("window", r.getWindowsCount());
						break;
					}
					
					if(foundRoom) break;
				}
				if(!foundRoom){
					RoomNotExistsException re = new RoomNotExistsException();
					re.setDescription("Room does not exist to add sensor");
					re.setRoomNameContext(roomName);
					throw re;
				}
				
				if(foundHouse) break;
			}
		}
		
		if(!foundHouse){
			HouseNotExistsException he = new HouseNotExistsException();
			he.setDescription("House does not exist.");
			he.setHouseNameContext(partsString[0]);
			throw he;
		}
		return floorWindowMap;
	}
	/**Add an occupant to the house
	 * @param auth_token Authentication token
	 * @param name Occupant name
	 * @param houseName House name
	 * @throws HouseNotExistsException House not exists exception
	 * @throws EmptyOrNUllAuthTokenException 
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 * @throws ResourceRoleAlreadyExistsException 
	 */
	@Override
	public void addOccupantToHouse(String auth_token, String name, String houseName) 
			throws HouseNotExistsException, EmptyOrNUllAuthTokenException, 
			InvalidAccessTokenException, AccessDeniedException, ResourceRoleAlreadyExistsException {
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for addOccupantToHouse");
			throw en;
		}
		boolean checkAccess = entitlementService.checkAccess(auth_token, "", "user_admin");
		if(!checkAccess){
			AccessDeniedException ad = new AccessDeniedException();
			ad.setDescription("Access is denied for adding the occupant to the house");
			throw ad;
		}
		boolean foundHouse = false;
		for(House h:houseSet){
			if(h.getName().equalsIgnoreCase(houseName)){
				foundHouse = true;
				for (Iterator<Occupant> it = occupantSet.iterator(); it.hasNext(); ) {
					Occupant occupant = it.next();
			        if (occupant.getName().equalsIgnoreCase(name)){
			        	if(!(h.getOccupantSet().contains(occupant))){
			        		h.getOccupantSet().add(occupant);
			        		occupant.getHouseSet().add(h);
			        		
			        		String occupant_role = occupant.getType() + "_resident";
			        		String resource_role_name = houseName + "_" + occupant_role;
			        		entitlementService.createResourceRole(resource_role_name, occupant_role, houseName);
			        		entitlementService.addResourceRoleToUser(name, resource_role_name);
			        		
			        		/* <occupant_name> recognized_by <house_name>
			        		 * */
			        		KnowledgeGraph.getInstance().importTriple(occupant.getName(), "recognized_by", houseName);
			    		}
			        }
			    }
			}
		}
		if(!foundHouse){
			HouseNotExistsException he = new HouseNotExistsException();
			he.setDescription("House does not exist.");
			he.setHouseNameContext(houseName);
			throw he;
		}
	}

	
	/**Update occupant location in the house
	 * @param auth_token Authentication token
	 * @param name Occupant name
	 * @param roomName Room name
	 * @throws HouseNotExistsException House not exists exception
	 * @throws RoomNotExistsException Room not exists exception
	 * @throws EmptyOrNUllAuthTokenException 
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 */
	@Override
	public void updateOccupantLocation(String auth_token, String name, String roomName) 
			throws HouseNotExistsException, RoomNotExistsException, EmptyOrNUllAuthTokenException, InvalidAccessTokenException, AccessDeniedException{
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for updateOccupantLocation");
			throw en;
		}
		boolean checkAccess = entitlementService.checkAccess(auth_token, "", "user_admin");
		if(!checkAccess){
			AccessDeniedException ad = new AccessDeniedException();
			ad.setDescription("Access is denied for adding the occupant to the house");
			throw ad;
		}
		
		if(roomName.equalsIgnoreCase("N/A")){
			for(House h:houseSet){
				for (Iterator<Occupant> it = h.getOccupantSet().iterator(); it.hasNext(); ) {
					Occupant occupant = it.next();
			        if (occupant.getName().equalsIgnoreCase(name)){
			        	//occupant.setLocation(null);
			        	KnowledgeGraph.getInstance().updateTriple(occupant.getName(), "has_location", "null");
			        	System.out.println("Occupant: " + name + " location set to: null");
			        	return;
			        }
				}
			}
		}
		//Retrieve the house name from room name
		boolean foundHouse = false, foundRoom = false;
		String[] partsString = roomName.split(":");
		assert(partsString.length != 2);
		
		for(House h:houseSet){
			if(h.getName().equalsIgnoreCase(partsString[0])){
				foundHouse = true;
				Set<Room> roomSet = h.getRoomSet();
				for(Room r:roomSet){
					if((r.getRoomName()).equalsIgnoreCase(roomName)){
						foundRoom = true;
						for (Iterator<Occupant> it = h.getOccupantSet().iterator(); it.hasNext(); ) {
							Occupant occupant = it.next();
					        if (occupant.getName().equalsIgnoreCase(name)){
					        	boolean needUpdateTriple = false;
					        	if(occupant.getLocation()!= null) needUpdateTriple = true;
					        	occupant.setLocation(r);
					        	System.out.println("Occupant: " + name + " location set to: " + roomName);
					        	/* <occupant_name> has_loaction <room_name>
				        		 * */
					        	if(!needUpdateTriple)
					        		KnowledgeGraph.getInstance().importTriple(occupant.getName(), "has_location", roomName);
					        	else
					        		KnowledgeGraph.getInstance().updateTriple(occupant.getName(), "has_location", roomName);
					        	
					        	break;
					        }
						}
						if(foundRoom) break;
					}
					
				}
				if(!foundRoom){
					RoomNotExistsException re = new RoomNotExistsException();
					re.setDescription("Room does not exist to add sensor");
					re.setRoomNameContext(roomName);
					throw re;
				}
				
				if(foundHouse) break;
			} 
		}
		if(!foundHouse){
			HouseNotExistsException he = new HouseNotExistsException();
			he.setDescription("House does not exist.");
			he.setHouseNameContext(partsString[0]);
			throw he;
		}
	}
	
	@Override
	public void updateOccupantState(String auth_token, String name, String roomName, String state)
			throws HouseNotExistsException, EmptyOrNUllAuthTokenException, InvalidAccessTokenException, AccessDeniedException{
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for updateOccupantState");
			throw en;
		}
		
		boolean checkAccess = entitlementService.checkAccess(auth_token, "", "user_admin");
		if(!checkAccess){
			AccessDeniedException ad = new AccessDeniedException();
			ad.setDescription("Access is denied for adding the occupant to the house");
			throw ad;
		}
		//Retrieve the house name from room name
		boolean foundHouse = false;
		String[] partsString = roomName.split(":");
		assert(partsString.length != 2);
		
		for(House h:houseSet){
			if(h.getName().equalsIgnoreCase(partsString[0])){
				foundHouse = true;
				for (Iterator<Occupant> it = h.getOccupantSet().iterator(); it.hasNext(); ) {
					Occupant occupant = it.next();
			        if (occupant.getName().equalsIgnoreCase(name)){
			        	boolean needUpdateTriple = false;
			        	if(!(occupant.getState().equalsIgnoreCase("N/A"))) needUpdateTriple = true;
			        	
			        	System.out.println("Occupant: " + name + " state set to: " + state);
			        	/* <occupant_name> has_state <state>
		        		 * */
			        	occupant.setState(state);
			        	if(!needUpdateTriple)
			        		KnowledgeGraph.getInstance().importTriple(occupant.getName(), "has_state", state);
			        	else
			        		KnowledgeGraph.getInstance().updateTriple(occupant.getName(), "has_state", state);
			        	break;
			        }
				}
			}
		}
		
		if(!foundHouse){
			HouseNotExistsException he = new HouseNotExistsException();
			he.setDescription("House does not exist.");
			he.setHouseNameContext(partsString[0]);
			throw he;
		}
	}
	
	/**Set the sensor status with a value
	 * @param auth_token Authentication token
	 * @param name Sensor name
	 * @param statusName Status name
	 * @param value Status value
	 * @throws SensorNotExistsException Sensor not exists exception
	 * @throws EmptyOrNUllAuthTokenException 
	 */
	@Override
	public void setSensorStatus(String auth_token, String name, String statusName, String value) throws SensorNotExistsException, EmptyOrNUllAuthTokenException {
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for setSensorStatus");
			throw en;
		}
		boolean foundSensor = false;
		for(House h:houseSet){
			for (Iterator<Device> it = h.getDeviceSet().iterator(); it.hasNext(); ) {
				Device d = it.next();
				if(d.getIdentifier().equalsIgnoreCase(name)){
					foundSensor = true;
					Map<String,String> statusPredicateMap = d.getStatusPredicateMap();
					if(statusPredicateMap != null){
						String predicate = statusPredicateMap.get(statusName);
						if(d.isValueNeedUpdate(statusName)){
							KnowledgeGraph.getInstance().updateTriple(d.getIdentifier(), predicate, value);
						}else{
							KnowledgeGraph.getInstance().importTriple(d.getIdentifier(), predicate, value);
						}
						d.setDeviceStatusValue(statusName, value);
						MessagePacket packet = new MessagePacket();
						packet.setOriginatingDeviceType(d.getType());
						packet.setCommandIdentifier(statusName);
						packet.setTargetIdentifier(value);
						packet.setSubjectIdentifier(name);
						stateChanged(packet);
					}
				}
				if(foundSensor) break;
			}
		}
		if(!foundSensor){
			SensorNotExistsException se = new SensorNotExistsException();
			se.setDescription("Appliance not found");
			se.setSensorNameContext(name);
			throw se;
		}
	}
	
	/**Get the sensor status value
	 * @param auth_token Authentication token
	 * @param name Sensor name
	 * @param statusName Status name
	 * @return Sensor status value
	 * @throws SensorNotExistsException Sensor not exists exception
	 * @throws EmptyOrNUllAuthTokenException 
	 */
	@Override
	public String getSensorStatus(String auth_token, String name, String statusName) throws SensorNotExistsException, EmptyOrNUllAuthTokenException {
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for getSensorStatus");
			throw en;
		}
		boolean foundSensor = false;
		for(House h:houseSet){
			for (Iterator<Device> it = h.getDeviceSet().iterator(); it.hasNext(); ) {
				Device d = it.next();
				if(d.getIdentifier().equalsIgnoreCase(name)){
					foundSensor = true;
					Map<String,String> statusPredicateMap = d.getStatusPredicateMap();
					if(statusPredicateMap != null){
						String predicate = statusPredicateMap.get(statusName);
						Set<Triple> tripleSet = KnowledgeGraph.getInstance().executeQuery(d.getIdentifier(), predicate, "?");
						if(tripleSet!= null && tripleSet.size() == 1){
							String val = "";
							for (Triple triple : tripleSet) {
								val = triple.getObject().getIdentifier();
				            }
							return val;
						}
					}
				}
			}
		}
		if(!foundSensor){
			SensorNotExistsException se = new SensorNotExistsException();
			se.setDescription("Appliance not found");
			se.setSensorNameContext(name);
			throw se;
		}
		return null;
	}

	private String getAppliancePermissionId(DeviceType deviceType){
		String permissionId = null;
		switch(deviceType){
			case DOOR:
				permissionId = "control_door";
				break;
			case WINDOW:
				permissionId = "control_window";
				break;
			case OVEN:
				permissionId = "control_oven";
				break;
			case THERMOSTAT:
				permissionId = "control_thermostat";
				break;
			case LIGHT:
				permissionId = "control_light";
				break;
			default:
				break;
		}
		return permissionId;
	}

	/**Set the appliance status with a value
	 * @param auth_token Authentication token
	 * @param name Appliance name
	 * @param statusName Status name
	 * @param value Status value
	 * @throws ApplianceNotExistsException Appliance not exists exception
	 * @throws EmptyOrNUllAuthTokenException 
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 */
	@Override
	public void setApplianceStatus(String auth_token, String name, String statusName, String value, String voice_print) 
			throws ApplianceNotExistsException, EmptyOrNUllAuthTokenException, InvalidAccessTokenException, AccessDeniedException
	{
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for setApplianceStatus");
			throw en;
		}
		boolean foundAppliance = false;
		for(House h:houseSet){
			for (Iterator<Device> it = h.getDeviceSet().iterator(); it.hasNext(); ) {
				Device d = it.next();
				if(d.getIdentifier().equalsIgnoreCase(name)){
					foundAppliance = true;
					boolean accessGranted = false;
					String permissionId = getAppliancePermissionId(d.getType());
					if(permissionId == null){
						//Check if admin token is supplied to control appliance
						accessGranted = entitlementService.checkAccess(auth_token, h.getName(), "user_admin");
					}else{
						accessGranted = entitlementService.checkAccess(auth_token, h.getName(), permissionId);
					}
					if(!accessGranted){
						AccessDeniedException ad = new AccessDeniedException();
						ad.setDescription("Access is denied for setting the appliance: " + name + " status: " + statusName);
						throw ad;
					}
					Map<String,String> statusPredicateMap = d.getStatusPredicateMap();
					if(statusPredicateMap != null){
						String predicate = statusPredicateMap.get(statusName);
						if(d.isValueNeedUpdate(statusName)){
							KnowledgeGraph.getInstance().updateTriple(d.getIdentifier(), predicate, value);
						}else{
							KnowledgeGraph.getInstance().importTriple(d.getIdentifier(), predicate, value);
						}
						d.setDeviceStatusValue(statusName, value);
						System.out.println("Device " + name + " got the status: " + statusName + " with value: " + value);
						MessagePacket packet = new MessagePacket();
						packet.setOriginatingDeviceType(d.getType());
						packet.setApplianceStatusName(statusName);
						packet.setCommandIdentifier(value);
						packet.setSubjectIdentifier(name);
						packet.setGenericAvaCommand(statusName);
						packet.setAvaQuestion(statusName);
						packet.setVoicePrint(voice_print);
						stateChanged(packet);
					}
					break;
				}
			}
		}
		if(!foundAppliance){
			ApplianceNotExistsException ae = new ApplianceNotExistsException();
			ae.setDescription("Appliance not found");
			ae.setapplianceNameContext(name);
			throw ae;
		}
		
	}

	/**Get the appliance status value
	 * @param auth_token Authentication token
	 * @param name Appliance name
	 * @param statusName Status name
	 * @return Appliance status value
	 * @throws ApplianceNotExistsException Appliance not exists exception
	 * @throws EmptyOrNUllAuthTokenException 
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 */
	@Override
	public String getApplianceStatus(String auth_token, String name, String statusName) 
			throws ApplianceNotExistsException, EmptyOrNUllAuthTokenException, InvalidAccessTokenException, AccessDeniedException {
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for getApplianceStatus");
			throw en;
		}
		boolean checkAccess = entitlementService.checkAccess(auth_token, "", "user_admin");
		if(!checkAccess){
			AccessDeniedException ad = new AccessDeniedException();
			ad.setDescription("Access is denied for adding the occupant to the house");
			throw ad;
		}
		boolean foundAppliance = false;
		for(House h:houseSet){
			for (Iterator<Device> it = h.getDeviceSet().iterator(); it.hasNext(); ) {
				Device d = it.next();
				if(d.getIdentifier().equalsIgnoreCase(name)){
					foundAppliance = true;
					Map<String,String> statusPredicateMap = d.getStatusPredicateMap();
					if(statusPredicateMap != null){
						String predicate = statusPredicateMap.get(statusName);
						Set<Triple> tripleSet = KnowledgeGraph.getInstance().executeQuery(d.getIdentifier(), predicate, "?");
						if(tripleSet!= null && tripleSet.size() == 1){
							String val = "";
							for (Triple triple : tripleSet) {
								val = triple.getObject().getIdentifier();
				            }
							return val;
						}
					}
				}
			}
		}
		if(!foundAppliance){
			ApplianceNotExistsException ae = new ApplianceNotExistsException();
			ae.setDescription("Appliance not found");
			ae.setapplianceNameContext(name);
			throw ae;
		}
		return null;
	}

	/**Get all status values
	 * @param auth_token Authentication token
	 * @param name Sensor/Appliance name
	 * @return map of status name value pairs
	 * @throws ApplianceNotExistsException Appliance not exists exception
	 * @throws EmptyOrNUllAuthTokenException 
	 */
	@Override
	public Map<String, String> getAllStatusOfSensorOrAppliance(String auth_token, String name) throws ApplianceNotExistsException, EmptyOrNUllAuthTokenException {
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for getAllStatusOfSensorOrAppliance");
			throw en;
		}
		boolean foundAppliance = false;
		Map<String, String> allStatus = new HashMap<String, String>();
		for(House h:houseSet){
			for (Iterator<Device> it = h.getDeviceSet().iterator(); it.hasNext(); ) {
				Device d = it.next();
				if(d.getIdentifier().equalsIgnoreCase(name)){
					foundAppliance = true;
					Map<String,String> statusPredicateMap = d.getStatusPredicateMap();
					if(statusPredicateMap != null){
						for (Map.Entry<String, String> entry : statusPredicateMap.entrySet()) {
							Set<Triple> tripleSet = KnowledgeGraph.getInstance().executeQuery(d.getIdentifier(), entry.getValue(), "?");
							if(tripleSet!= null && tripleSet.size() == 1){
								String val = "";
								for (Triple triple : tripleSet) {
									val = triple.getObject().getIdentifier();
					            }
								allStatus.put(entry.getKey(), val);
							}
						}
					}
				}
			}
		}
		if(!foundAppliance){
			ApplianceNotExistsException ae = new ApplianceNotExistsException();
			ae.setDescription("Sensor/Appliance not found");
			ae.setapplianceNameContext(name);
			throw ae;
		}
		return allStatus;
	}

	/**Get the room configuration
	 * @param auth_token Authentication token
	 * @param name Room name
	 * @return set of strings containing room information e.g. occupants, sensors etc.
	 * @throws RoomNotExistsException Room not exists exception
	 * @throws EmptyOrNUllAuthTokenException 
	 */
	@Override
	public Set<String> getRoomConfiguration(String auth_token, String name) throws RoomNotExistsException, EmptyOrNUllAuthTokenException {
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for getRoomConfiguration");
			throw en;
		}
		boolean foundRoom = false;
		Set<String> roomConfigSet = new LinkedHashSet<String>();
		for(House h:houseSet){
			Set<Room> roomSet = h.getRoomSet();
			for(Room r:roomSet){
				if((r.getRoomName()).equalsIgnoreCase(name)){
					foundRoom = true;
					Set<Triple> tripleRoomSet = KnowledgeGraph.getInstance().executeQuery(name, "?", "?");
					for (Triple tripleRoom : tripleRoomSet) {
						roomConfigSet.add(tripleRoom.getIdentifier());
		            }
				}
			}
		}
		if(!foundRoom){
			RoomNotExistsException re = new RoomNotExistsException();
			re.setDescription("Room does not exist to get configuration");
			re.setRoomNameContext(name);
			throw re;
		}
		return roomConfigSet;
	}

	/**Get the house configuration
	 * @param auth_token Authentication token
	 * @param name House name
	 * @return set of strings containing House information e.g. rooms, occupants, sensors etc.
	 * @throws HouseNotExistsException House not exists exception
	 * @throws EmptyOrNUllAuthTokenException 
	 */
	@Override
	public Set<String> getHouseConfiguration(String auth_token, String name) throws HouseNotExistsException, EmptyOrNUllAuthTokenException {
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for getHouseConfiguration");
			throw en;
		}
		boolean foundHouse = false;
		Set<String> houseConfigSet = new LinkedHashSet<String>();
		for(House h:houseSet){
			if(h.getName().equalsIgnoreCase(name)){
				foundHouse = true;
				houseConfigSet.add("House: " + h.getName());
				Set<Room> roomSet = h.getRoomSet();
				String numRooms = "Rooms: " + roomSet.size();
				houseConfigSet.add(numRooms);
				int roomCounter = 1;
				for (Iterator<Room> it = roomSet.iterator(); it.hasNext(); ) {
					Room room = it.next();
					houseConfigSet.add("Room " + (roomCounter++));
					String roomInfo = "Room Name: " + room.getRoomName() + 
							              ", Type: " + room.getRoomType() + 
							              ", Floor: " + room.getFloor() +
							              ", Windows: " + room.getWindowsCount();
					houseConfigSet.add(roomInfo);
				}
				
				Set<Occupant> occupantSet = h.getOccupantSet();
				String numOccupants = "Occupants: " + occupantSet.size();
				houseConfigSet.add(numOccupants);
				int occCounter = 1;
				for (Iterator<Occupant> it = occupantSet.iterator(); it.hasNext(); ) {
					Occupant occ = it.next();
					houseConfigSet.add("Occupant " + (occCounter++));
					String occupantInfo = "Occupant Name: " + occ.getName() + 
							              ", State: " + occ.getState() + 
							              ", IsKnown: " + occ.isKnown();
					Set<Triple> tripleOccSet = KnowledgeGraph.getInstance().executeQuery(occ.getName(), "recognized_by", "?");
					occupantInfo += ", belongs to House: ";
					for (Triple tripleOcc : tripleOccSet) {
						String[] parts = (tripleOcc.getIdentifier()).split("\\s+");
						occupantInfo += parts[2] + " ";
		            }
					
					Set<Triple> tripleOccSetLocation = KnowledgeGraph.getInstance().executeQuery(occ.getName(), "has_location", "?");
					
					if(tripleOccSetLocation != null){
						occupantInfo += ", location: ";
						for (Triple tripleOcc : tripleOccSetLocation) {
							String[] parts = (tripleOcc.getIdentifier()).split("\\s+");
							occupantInfo += parts[2] + " ";
			            }
					}
						
					houseConfigSet.add(occupantInfo);
				}
				
				Set<Device> deviceSet = h.getDeviceSet();
				String numDevices = "Devices: " + deviceSet.size();
				houseConfigSet.add(numDevices);
				int deviceCounter = 1;
				for (Iterator<Device> it = deviceSet.iterator(); it.hasNext(); ) {
					Device device = it.next();
					houseConfigSet.add("Device " + (deviceCounter++));
					
					Set<Triple> tripleDeviceSet = KnowledgeGraph.getInstance().executeQuery(device.getIdentifier(), "?", "?");
					for (Triple tripleOcc : tripleDeviceSet) {
						houseConfigSet.add(tripleOcc.getIdentifier());
		            }
				}
			}
		}
		if(!foundHouse){
			HouseNotExistsException he = new HouseNotExistsException();
			he.setDescription("House does not exist.");
			he.setHouseNameContext(name);
			throw he;
		}
		return houseConfigSet;
	}

	/**Get the configuration of all the houses
	 * @param auth_token Authentication token
	 * @return set of sets containing House information e.g. rooms, occupants, sensors etc. for all the houses defined in the service.
	 * @throws EmptyOrNUllAuthTokenException 
	 */
	@Override
	public Set<LinkedHashSet<String>> getAllHousesConfiguration(String auth_token) throws EmptyOrNUllAuthTokenException {
		if(auth_token==null || auth_token.isEmpty())
		{
			EmptyOrNUllAuthTokenException en = new EmptyOrNUllAuthTokenException();
			en.setDescription("Auth Token is null for getAllHousesConfiguration");
			throw en;
		}
		
		Set<LinkedHashSet<String>> allHousesConfigSet = new LinkedHashSet< LinkedHashSet<String> >();
		for(House h:houseSet){
			try {
				Set<String> houseConfig = getHouseConfiguration(auth_token, h.getName());
				allHousesConfigSet.add((LinkedHashSet<String>) houseConfig);
			} catch (HouseNotExistsException he) {
				System.out.println(he);
			}
		}
		return allHousesConfigSet;
	}
	
}
