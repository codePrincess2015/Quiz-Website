package quiz;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPException;

/**
 * Servlet implementation class QuestionServlet
 */
@WebServlet("/QuestionServlet")
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuestionServlet() {
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
		UserMySQLAccess con = new UserMySQLAccess();
		ResultSet attributes = con.returnQuizAttributes(request.getParameter("quizName"));
		ResultSet questions = con.returnQuestions(request.getParameter("quizName"));
		String practice = request.getParameter("practice");
		HttpSession session = request.getSession();
		session.setAttribute("practice",practice);
		session.setAttribute("startTime",""+System.currentTimeMillis());
		session.setAttribute("quizName",request.getParameter("quizName"));
		try {
			Quiz quiz = new Quiz();
			session.setAttribute("quiz",quiz);
			while(questions.next()){
				QuizItem item = new QuizItem();
				String question = questions.getString("question");
				String picture = questions.getString("picture");
				String answers = questions.getString("answers");
				String multipleChoice = questions.getString("multipleChoice");
				if(!question.equals("null")) {
					item.editQuestion(question);

				}
				else if(!picture.equals("null")){
					item.editPicture(picture);	
				}
				if(!multipleChoice.equals("null")){
					StringTokenizer tokenizer = new StringTokenizer(multipleChoice,",");
					while(tokenizer.hasMoreTokens()){
						String token = tokenizer.nextToken();
						item.addChoiceAnswer(token);
					}
				}
				if(!answers.equals("null")){
					StringTokenizer tokenizer = new StringTokenizer(answers,";");
					while(tokenizer.hasMoreTokens()){
						String token = tokenizer.nextToken();
						Answer answer = item.addAnswer();
						StringTokenizer t = new StringTokenizer(token,",");
						while(t.hasMoreTokens()){
							String x = t.nextToken();
							answer.addPossibleAnswer(x);
						}
					}
				}
				quiz.addQuizItem(item);
			}
			if(attributes.next()){
				String display = attributes.getString("display");
				session.setAttribute("display",display);
				String random = attributes.getString("random");
				String imCor = attributes.getString("correction");
				session.setAttribute("imCor",imCor);
				RequestDispatcher rd = null;
				if(random.equals("yes")){
					Collections.shuffle(quiz.getQuizItems());
				}
				if(display.equals("onePage")){
					rd = request.getRequestDispatcher("OnePageQuiz.jsp");
				}
				else{
					if(imCor.equals("no")){
						ArrayList<String> userAnswers = new ArrayList<String>();
						session.setAttribute("userAnswers",userAnswers);
					}
					session.setAttribute("quizItemNumber",""+0);
					session.setAttribute("score",""+0);
					rd = request.getRequestDispatcher("MultPageQuiz.jsp");
				}
				rd.forward(request, response);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
