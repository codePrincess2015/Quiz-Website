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
 * Servlet implementation class ComposeMessageServlet
 */
@WebServlet("/ComposeMessageServlet")
public class ComposeMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComposeMessageServlet() {
        super();
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
		String text = request.getParameter("text");
		String target = request.getParameter("target");
		String name = request.getParameter("user");
		int type = Integer.parseInt(request.getParameter("type"));
		String quizName = request.getParameter("quizName");
		RequestDispatcher rd = request.getRequestDispatcher("Inbox.jsp");
		UserMySQLAccess con = new UserMySQLAccess();
		String id = con.getNewMessageId();
		Message message = new Message(name,target,text,type,quizName,id,true);
		con.addMessage(message);
		con.closeConnection();
		rd.forward(request, response);
	}

}
