package quiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FriendRequestReplyServlet
 */
@WebServlet("/FriendRequestReplyServlet")
public class FriendRequestReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FriendRequestReplyServlet() {
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
		HttpSession session = request.getSession();
		String requester = request.getParameter("requester");
		String receiver = request.getParameter("receiver");
		String reply = request.getParameter("reply");
		UserMySQLAccess con = new UserMySQLAccess();
		UserProfile profile = (UserProfile) session.getAttribute("userProfile");
		if(reply.equals("Yes")){
			con.addFriends(requester,receiver);
			profile.addFriend(requester);
		}
		String id = request.getParameter("id");
		con.removeMessage(id);
		con.closeConnection();
		RequestDispatcher rd = request.getRequestDispatcher("Inbox.jsp");
		rd.forward(request, response);
	}

}
