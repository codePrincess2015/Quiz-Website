package quiz;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

/**
 * Servlet implementation class TakeServlet
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
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
		String action = request.getParameter("action");
		String id = request.getParameter("messageId");
		String creator = request.getParameter("sender");
		String text = request.getParameter("text");
		String name = request.getParameter("user");
		int type = Integer.parseInt(request.getParameter("type"));
		String quizName = request.getParameter("quizName");
		RequestDispatcher rd;
		UserMySQLAccess con = new UserMySQLAccess();
		if(type == Message.FRIEND_REQUEST){
			rd = request.getRequestDispatcher("FriendRequestMessage.jsp");
		}else if(type == Message.CHALLENGE){
			rd = request.getRequestDispatcher("ChallengeMessage.jsp");
		}else{
			rd = request.getRequestDispatcher("IndividualMessage.jsp");
		}
		if(action.equals("delete")){
			rd = request.getRequestDispatcher("Inbox.jsp");
			String[] allMessageIds = request.getParameterValues("checkboxes");
			for(int i=0;i<allMessageIds.length;i++){
				con.removeMessage(allMessageIds[i]);
			}
		} else{
			HttpSession session = request.getSession();
			session.setAttribute("id",id);
			session.setAttribute("creator",creator);
			session.setAttribute("text",text);
			session.setAttribute("name",name);
			session.setAttribute("type",type);
			session.setAttribute("quizName",quizName);
		}
		try {
			Statement stmt = con.con.createStatement();
			stmt.executeUpdate("UPDATE mailbox SET seen = 1 WHERE messageId = "+id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con.closeConnection();
		rd.forward(request, response);
	}

}
