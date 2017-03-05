package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Geofence;
import util.MySqlCon;

/**
 * Servlet implementation class DeleteGeofence
 */
@WebServlet("/DeleteGeofence")
public class DeleteGeofence extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteGeofence() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String appKey = (String)session.getAttribute("appKey");
		String lat = request.getParameter("lat");
		String lng = request.getParameter("lng");
		
		int i = MySqlCon.deleteGeofence(lat,lng);
		System.out.println(i);
		
		ArrayList<Geofence> geoList= MySqlCon.getAllGeofences(appKey);
		
		request.setAttribute("geoList",null);
		if(geoList.size()!=0)
		{
			request.setAttribute("geoList",geoList);
			request.setAttribute("currgeo",geoList.get(geoList.size()-1));
		}
		request.getRequestDispatcher("NewFile1.jsp").forward(request, response);
	}

}
