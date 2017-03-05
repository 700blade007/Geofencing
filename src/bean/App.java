package bean;

public class App {
	
	private String uname;
	private String appKey;
	private String appName;
	public App() {
		super();
	}
	public App(String uname, String appKey, String appName) {
		super();
		this.uname = uname;
		this.appKey = appKey;
		this.appName = appName;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
}
