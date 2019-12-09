package publisher;

public class SensorDataSolution {
	
	String sensorname;
	String sensorvalue;
	String userid;
	String sensordate;

	
	public SensorDataSolution(String sensorname, String sensorvalue, String userid, String sensordate) {
		super();
		this.sensorname = sensorname;
		this.sensorvalue = sensorvalue;
		this.userid = userid;
		this.sensordate = sensordate;
	}

	// Constructors depending on which parameters are known
	public SensorDataSolution(String sensorname, String sensorvalue, String userid) {
		super();
		this.sensorname = sensorname;
		this.sensorvalue = sensorvalue;
		this.userid = userid;
		this.sensordate = "unknown";
	}
	public SensorDataSolution(String sensorname, String sensorvalue) {
		super();
		this.sensorname = sensorname;
		this.sensorvalue = sensorvalue;
		// Defaults for when no userid or location known
		this.userid = "unknown";
		this.sensordate = "unknown";
	}

	public String getSensorname() {
		return sensorname;
	}
	public void setSensorname(String sensorname) {
		this.sensorname = sensorname;
	}
	public String getSensorvalue() {
		return sensorvalue;
	}
	public void setSensorvalue(String sensorvalue) {
		this.sensorvalue = sensorvalue;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSensordate() {
		return sensordate;
	}
	public void setSensordate(String sensorvalue) {
		this.sensordate = sensorvalue;
	}

	@Override
	public String toString() {
		return "SensorData [sensorname=" + sensorname + ", sensorvalue=" + sensorvalue + ", userid=" + userid
				+ ", sensordate=" + sensordate + "]";
	}
	
	
	
}
