package quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MultPageServlet
 */
@WebServlet("/MultPageServlet")
public class MultPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MultPageServlet() {
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
		if(session.getAttribute("imCor").equals("yes")){
			String scoreString = (String) session.getAttribute("score");
			int oldScore = Integer.parseInt(scoreString);
			Quiz quiz = (Quiz) session.getAttribute("quiz");
			ArrayList<QuizItem> items = quiz.getQuizItems();
			int currentIndex = Integer.parseInt((String) session.getAttribute("quizItemNumber"));
			ArrayList<Answer> answers = items.get(currentIndex).getAnswersArray();
			ArrayList<String>userAnswerArray = new ArrayList<String>();
			String answer = "";
			if(answers.size() > 1){
				String [] array = request.getParameterValues((String)session.getAttribute("quizItemNumber"));
				for(int i = 0; i < array.length;i++){
					answer+=array[i];
					if(i< array.length-1){
						answer+=",";
					}
				}
			}
			else{
				answer = (String) request.getParameter((String)session.getAttribute("quizItemNumber"));
			}
			StringTokenizer tokenizer = new StringTokenizer(answer,",");
			while(tokenizer.hasMoreTokens()){
				String token = tokenizer.nextToken();
				userAnswerArray.add(token);
			}
			int newScore = oldScore + quiz.grade(answers,userAnswerArray);
			session.setAttribute("score", ""+newScore);
			System.out.println("score" + newScore);

			RequestDispatcher rd;
			if((newScore - oldScore) != userAnswerArray.size()){
				rd = request.getRequestDispatcher("Wrong.jsp");
			}
			else{
				rd = request.getRequestDispatcher("Right.jsp");
			}
			rd.forward(request, response);
		}
		else{
			session = request.getSession();
			String answer = (String) request.getParameter((String)session.getAttribute("quizItemNumber"));
			ArrayList<String> userAnswers = (ArrayList<String>) session.getAttribute("userAnswers");
			userAnswers.add(answer);
			int newIndex = Integer.parseInt((String) session.getAttribute("quizItemNumber")) + 1;
			session.setAttribute("quizItemNumber",""+newIndex);
			Quiz quiz = (Quiz) session.getAttribute("quiz");
			ArrayList<QuizItem> items = quiz.getQuizItems();
			if(newIndex == items.size()-1){
				RequestDispatcher rd = request.getRequestDispatcher("MultEnd.jsp");
				rd.forward(request, response);
			}
			else {
				RequestDispatcher rd = request.getRequestDispatcher("MultPageQuiz.jsp");
				rd.forward(request, response);
			}
		}
	}

}
