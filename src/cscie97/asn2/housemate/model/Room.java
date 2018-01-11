package cscie97.asn2.housemate.model;

public class Room {
	private String roomName;
	private String roomType;
	private int floor;
	private int windowsCount;

	 /**
    * overloaded constructor
    * Uses package visibility to restrict creation of the Room class.
    */
	Room(String name, int floor, String type, String house, int windows){
		this.roomName = house + ":" + name;
		this.roomType = type;
		this.floor = floor;
		this.windowsCount= windows;
	}
	
	/**
	 * @return room name 
	 */
	public String getRoomName() {
		return roomName;
	}
	/**
	 * 
	 * @param roomName name to set.
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	/**
	 * @return room type 
	 */
	public String getRoomType() {
		return roomType;
	}
	/**
	 * 
	 * @param roomType type to set.
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	/**
	 * @return room floor 
	 */
	public int getFloor() {
		return floor;
	}
	/**
	 * 
	 * @param floor floor to set.
	 */
	public void setFloor(int floor) {
		this.floor = floor;
	}
	/**
	 * @return windows count
	 */
	public int getWindowsCount() {
		return windowsCount;
	}
	/**
	 * 
	 * @param windowsCount windows to set.
	 */
	public void setWindowsCount(int windowsCount) {
		this.windowsCount = windowsCount;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roomName == null) ? 0 : roomName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Room))
			return false;
		Room other = (Room) obj;
		if (roomName == null) {
			if (other.roomName != null)
				return false;
		} else if (!roomName.equals(other.roomName))
			return false;
		return true;
	}
}
