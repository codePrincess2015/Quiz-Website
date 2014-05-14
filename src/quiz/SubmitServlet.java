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
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class SubmitServlet
 */
@WebServlet("/SubmitServlet")
public class SubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitServlet() {
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
		HttpSession session = request.getSession(true);
		UserProfile profile = (UserProfile) session.getAttribute("userProfile");
		Quiz quiz = (Quiz) session.getAttribute("quiz");
		String username = profile.getName();
		String quizName = request.getParameter("quizName");
		String display = request.getParameter("display");
		System.out.println(display);
		String random = request.getParameter("random");
		String practice = request.getParameter("practice");
		String correction = request.getParameter("correction");
		String description = request.getParameter("description");
		UserMySQLAccess con = new UserMySQLAccess();
		Statement stmt = (Statement) con.returnStatement();
		try {
			String update = "INSERT INTO quizzes VALUES(\""+username+"\""+",\""+quizName+"\",\""+ display+"\",\""+random+"\",\""+practice+"\",\""+correction+"\",\""+description+"\",0)";
			stmt.executeUpdate(update);
			ResultSet set = stmt.executeQuery("SELECT * FROM users WHERE username = \""+username+"\"");
			if(set.next()){
				int numQuizzes = set.getInt("numQuizzesTaken")+1;
				stmt.executeUpdate("UPDATE users SET numQuizzesCreated = " + numQuizzes + " WHERE username = \""+username+"\"");
				profile.setNumQuizzesCreated(numQuizzes);
			}
			AchievementsList list = (AchievementsList) session.getAttribute("list");
			list.updatePlayerAchievements(profile,con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<QuizItem> items = quiz.getQuizItems();
		for(int i = 0; i < items.size();i++){
			QuizItem item = items.get(i);
			String question = item.getQuestion();
			String answers = item.getAnswers();
			String picture = item.getPicture();
			String multipleChoice = item.getMultipleChoiceAnswers();
			try {
				String update = "INSERT INTO quizItems VALUES(\""+quizName+"\""+",\""+question+"\",\""+ answers+"\",\""+picture+"\",\""+multipleChoice+"\")";
				System.out.println(update);
				stmt.executeUpdate(update);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		con.updateNewsfeed(username,"creation",quizName,System.currentTimeMillis());
		con.closeConnection();
		session.setAttribute("quiz", null);
		RequestDispatcher rd = request.getRequestDispatcher("QuizSubmitSuccess.html");
		rd.forward(request, response);
	}

}
