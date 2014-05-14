package quiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		AccountManager manager = (AccountManager) context.getAttribute("accountManager");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("newUsername"); // "new" to distinguish from username and password attributes for login
		String password = request.getParameter("newPassword");
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("AlreadyInUse.jsp");
		if (manager.accountExists(username)) {
			System.out.println("hi");
			rd.forward(request, response);
		} else {
			manager.createAccount(firstName, lastName, username, password);
			UserProfile profile;
			if (session.getAttribute("userProfile") == null) {
				profile = new UserProfile(firstName);
				UserMySQLAccess con = new UserMySQLAccess();
				if(con.getAdmin(firstName)){
					profile.setAdmin();
				}
				AchievementsList list = new AchievementsList(con,firstName);
				session.setAttribute("list",list);
				session.setAttribute("userProfile", profile);
			}
			profile = (UserProfile) session.getAttribute("userProfile");
			rd = request.getRequestDispatcher("HomePage.jsp");
			rd.forward(request, response);
		}
	}

}
