package quiz;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
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
		String type = request.getParameter("type");
		UserMySQLAccess con = new UserMySQLAccess();
		ArrayList<String> results = new ArrayList<String>();
		if(type.equals("Quiz")){
			String quizName = request.getParameter("query");
			ResultSet set = con.searchForQuiz(quizName);
			try {
				while(set.next()){
					results.add(set.getString("quizName"));	
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else{
			String username = request.getParameter("query");
			ResultSet set = con.searchForUser(username);
			try {
				while(set.next()){
					results.add(set.getString("username"));	
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("type", type);
		request.setAttribute("results", results);
		RequestDispatcher rd = request.getRequestDispatcher("SearchResults.jsp");
		con.closeConnection();
		rd.forward(request,response);
	}
}
