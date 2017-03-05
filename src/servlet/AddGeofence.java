package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Geofence;
import util.MySqlCon;

@WebServlet("/AddGeofence")
public class AddGeofence extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddGeofence() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String appKey = request.getParameter("appKey");

		ArrayList<Geofence> geoList= MySqlCon.getAllGeofences(appKey);
		HttpSession session = request.getSession();
		session.setAttribute("appKey", appKey);
		request.setAttribute("geoList",null);
		if(geoList.size()!=0)
		{
			request.setAttribute("geoList",geoList);
			request.setAttribute("currgeo",geoList.get(geoList.size()-1));
		}
		request.getRequestDispatcher("NewFile1.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String appKey = (String)session.getAttribute("appKey");
		String addr = request.getParameter("addr");
		String lat = request.getParameter("lat");
		String lng = request.getParameter("lng");
		String rad= request.getParameter("rad");
		String msg= request.getParameter("msg");

		Geofence obj = new Geofence(appKey, addr, lat, lng, rad , msg);
		int i = MySqlCon.insertGeofence(obj);

		ArrayList<Geofence> geoList= MySqlCon.getAllGeofences(appKey);

		request.setAttribute("geoList",geoList);
		request.setAttribute("currgeo",geoList.get(geoList.size()-1));
		request.getRequestDispatcher("NewFile1.jsp").forward(request, response);

		/*response.getWriter().println(addr);
		response.getWriter().println(rad);
		response.getWriter().println(lat);
		response.getWriter().println(lng);
		ResultSet rs = MySqlCon.searchStudent(Integer.parseInt(roll));
		try {
			while(rs.next())
			{
				response.getWriter().println((rs.getInt(1)+":"+rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}

}
