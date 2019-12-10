package reader;

public class RFIDdata {

	String roomid;
	String tagid;
	String readerid;
	String valid;

	public RFIDdata(String roomid, String tagid, String readerid, String valid) {
		super();
		this.roomid = roomid;
		this.tagid = tagid;
		this.readerid = readerid;
		this.valid = valid;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getTagid() {
		return tagid;
	}

	public void setTagid(String tagid) {
		this.tagid = tagid;
	}

	public String getReaderid() {
		return readerid;
	}

	public void setReaderid(String readerid) {
		this.readerid = readerid;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "SensorData [roomid=" + roomid + ", tagid=" + tagid + ", readerid=" + readerid + ", valid=" + valid
				+ "]";
	}

}
