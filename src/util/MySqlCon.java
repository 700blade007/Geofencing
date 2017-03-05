package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.App;
import bean.Geofence;
import bean.User;

public class MySqlCon {

	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/mama";
	private static final String username = "root";
	private static final String password = "qwerty";
	private static Connection con;
	private static PreparedStatement ps;
	private static ResultSet rs;

	static {
		try {
			Class.forName(driver);
			con=DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean userAuth(User user)
	{
		try {
			ps=con.prepareStatement("select uname from user where uname=? and pwd=?");
			ps.setString(1,user.getUname());
			ps.setString(2,user.getPwd());
			rs=ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int insertUser(User user) {
		try {
			ps=con.prepareStatement("insert into user values (?,?,?)");
			ps.setString(1,user.getUname());
			ps.setString(2,user.getPwd());
			ps.setString(3,user.getEmail());
			int i=ps.executeUpdate();
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static ArrayList<App> getUserApps(String uname) {
		try
		{
			ArrayList<App> appList = new ArrayList<App>();
			App app = null;
			ps=con.prepareStatement("Select * from app where uname = ?");
			ps.setString(1, uname);
			rs=ps.executeQuery();
			while(rs.next())
			{
				app = new App(rs.getString(1),rs.getString(2),rs.getString(3));
				appList.add(app);
			}
			return appList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int insertApp(App app)
	{
		try {
			ps=con.prepareStatement("insert into app values (?,?,?)");
			ps.setString(1,app.getUname());
			ps.setString(2,app.getAppKey());
			ps.setString(3,app.getAppName());
			int i=ps.executeUpdate();
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int insertGeofence(Geofence geofence)
	{
		try {
			ps=con.prepareStatement("insert into geofence (appkey,addr,lat,lng,rad,msg) values (?,?,?,?,?,?)");
			ps.setString(1,geofence.getAppKey());
			ps.setString(2,geofence.getAddr());
			ps.setString(3,geofence.getLat());
			ps.setString(4,geofence.getLng());
			ps.setString(5,geofence.getRad());
			ps.setString(6,geofence.getMsg());
			int i=ps.executeUpdate();
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static ArrayList<Geofence> getAllGeofences(String appKey)
	{
		try {
			ArrayList<Geofence> geoList = new ArrayList<Geofence>();
			Geofence geo = null;
			ps=con.prepareStatement("Select appkey,addr,lat,lng,rad,msg from geofence where appkey=?");
			ps.setString(1,appKey);
			rs=ps.executeQuery();
			while(rs.next())
			{
				geo = new Geofence(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
				geoList.add(geo);
			}
			return geoList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int deleteGeofence(String lat, String lng) {
		try {
			System.out.println(lat+":"+lng);
			ps=con.prepareStatement("delete from geofence where lat =? and lng =?");
			ps.setString(1,lat);
			ps.setString(2,lng);
			int i=ps.executeUpdate();
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void main(String args[])
	{
		try {
			ps=con.prepareStatement("select * from student");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int deleteApp(String appKey) {
		try {
			ps=con.prepareStatement("delete from geofence where appkey = ?");
			ps.setString(1,appKey);
			int i=ps.executeUpdate();
			ps=con.prepareStatement("delete from app where appkey = ?");
			ps.setString(1,appKey);
			i=ps.executeUpdate();
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	
}
