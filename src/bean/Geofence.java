package bean;

public class Geofence {
	
	private String appKey;
	private String addr;
	private String lat;
	private String lng;
	private String rad;
	private String msg;
	public Geofence() {
		super();
	}
	public Geofence(String appKey, String addr, String lat, String lng, String rad, String msg) {
		super();
		this.appKey = appKey;
		this.addr = addr;
		this.lat = lat;
		this.lng = lng;
		this.rad = rad;
		this.msg = msg;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getRad() {
		return rad;
	}
	public void setRad(String rad) {
		this.rad = rad;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
