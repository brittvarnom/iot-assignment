package lock;

public class LockData {

	String roomid;
	String doorid;

	public LockData(String roomid, String doorid) {
		super();
		this.roomid = roomid;
		this.doorid = doorid;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getDoorid() {
		return doorid;
	}

	public void setDoorid(String doorid) {
		this.doorid = doorid;
	}

	@Override
	public String toString() {
		return "SensorData [roomid=" + roomid + ", doorid=" + doorid + "]";
	}

}
