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

import quiz.AccountManager;

/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordServlet() {
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
		String oldPassword = request.getParameter("oldPassword");
		UserMySQLAccess con = new UserMySQLAccess();
		RequestDispatcher rd = request.getRequestDispatcher("PasswordChanged.jsp");
		if (manager.passwordMatches(request.getParameter("name"), oldPassword)) {
			if (request.getParameter("newPassword").equals(request.getParameter("confirmNewPassword"))) {
				con.setHashedPassword(AccountManager.computePasswordHash(request.getParameter("newPassword")),request.getParameter("name"));
			}
			else{
				rd = request.getRequestDispatcher("PasswordsDidNotMatch.jsp");
			}
		}
		con.closeConnection();
		rd.forward(request,response);
		
	}

}
