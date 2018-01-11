package cscie97.asn2.housemate.model;

import java.util.HashSet;
import java.util.Set;

public class House {

	private final String houseIdentifier;
	private String houseAddress;
	private String houseName;
	

	private Set<Room> roomSet = new HashSet<Room>();
	private Set<Occupant> occupantSet = new HashSet<Occupant>();
	private Set<Device> deviceSet = new HashSet<Device>();
	
	/**
     * Default constructor
     * Uses package visibility to restrict creation of the House class.
     * @param name house name
     * @param address house address
     */
	House(String name, String address){
		houseName = name;
		houseAddress = address;
		houseIdentifier = name.toLowerCase() + address.toLowerCase();
	}
	
	/**
	 * @return Set of rooms for the house
	 */
	public Set<Room> getRoomSet() {
		return roomSet;
	}
	
	/**
	 * @param roomSet set the set of rooms for house
	 */
	public void setRoomSet(Set<Room> roomSet) {
		this.roomSet = roomSet;
	}
	
	/**
	 * @return house identifier
	 */
	public String getIdentifier(){
		return this.houseIdentifier;
	}
	
	/**
	 * @param address set house address
	 */
	public void setAddress(String address){
		this.houseAddress = address;
	}
	
	/**
	 * @return house address
	 */
	public String getAddress(){
		return this.houseAddress;
	}
	
	/**
	 * @param name set house name
	 */
	public void setName(String name){
		this.houseName = name;
	}
	
	/**
	 * @return house name
	 */
	public String getName(){
		return this.houseName;
	}

	/**
	 * @return set of occupants for the house
	 */
	public Set<Occupant> getOccupantSet() {
		return occupantSet;
	}

	/**
	 * @param occupantSet set the occupant set
	 */
	public void setOccupantSet(Set<Occupant> occupantSet) {
		this.occupantSet = occupantSet;
	}

	/**
	 * @return set of devices for the house
	 */
	public Set<Device> getDeviceSet() {
		return deviceSet;
	}

	/**
	 * @param deviceSet set the devices set
	 */
	public void setDeviceSet(Set<Device> deviceSet) {
		this.deviceSet = deviceSet;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((houseName == null) ? 0 : houseName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof House))
			return false;
		House other = (House) obj;
		if (houseIdentifier == null) {
			if (other.houseIdentifier != null)
				return false;
		} else if (!houseIdentifier.equals(other.houseIdentifier))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "House [houseIdentifier=" + houseIdentifier + ", houseAddress=" + houseAddress + ", houseName="
				+ houseName + ", roomSet=" + roomSet + ", occupantSet=" + occupantSet + "]";
	}
}
