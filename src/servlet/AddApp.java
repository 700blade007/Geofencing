package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.App;
import util.MySqlCon;
import util.RandomAppKey;

/**
 * Servlet implementation class AddApp
 */
@WebServlet("/AddApp")
public class AddApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddApp() {
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
		String uname = (String)session.getAttribute("uname");
		
		RandomAppKey keyGenerator = new RandomAppKey(20);
		String appKey = keyGenerator.nextString();
		
		String appName = request.getParameter("appName");
		
		App app = new App(uname, appKey, appName);
		int i=MySqlCon.insertApp(app);
		
		ArrayList<App> appList = MySqlCon.getUserApps(uname);
		request.setAttribute("appList",appList);
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}

}
