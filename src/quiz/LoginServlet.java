package quiz;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		AccountManager manager = (AccountManager) context.getAttribute("accountManager");
		String name = request.getParameter("username");
		String attempt = request.getParameter("password");
		
		RequestDispatcher rd = request.getRequestDispatcher("HomePage.jsp");
		if (manager.passwordMatches(name, attempt)) {
			HttpSession session = request.getSession();
			UserProfile profile;
			if (session.getAttribute("userProfile") == null) {
				profile = new UserProfile(name);
				UserMySQLAccess con = new UserMySQLAccess();
				if(con.getAdmin(name)){
					profile.setAdmin();
				}
				AchievementsList list = new AchievementsList(con,name);
				session.setAttribute("list",list);
				session.setAttribute("userProfile", profile);
			}
			profile = (UserProfile) session.getAttribute("userProfile");
			rd.forward(request, response);
		} else {
			rd = request.getRequestDispatcher("TryAgain.jsp");
			rd.forward(request, response);
		}
	}
	

}
