package quiz;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GradeServlet
 */
@WebServlet("/GradeServlet")
public class GradeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GradeServlet() {
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
		String immediateCorrection = (String) session.getAttribute("imCor");
		Quiz quiz = (Quiz) session.getAttribute("quiz");
		ArrayList<QuizItem> quizItems = quiz.getQuizItems();
		ArrayList<String> userAnswerArray;
		if(immediateCorrection.equals("no")){
			int index = 0;
			int score = 0;
			if(session.getAttribute("display").equals("onePage")){
				Enumeration<String> userAnswers = request.getParameterNames();
				while(userAnswers.hasMoreElements()){
					ArrayList<Answer> answers = quizItems.get(index).getAnswersArray();
					userAnswerArray = new ArrayList<String>();
					String answer = "";
					if(answers.size() > 1){
						String [] array = request.getParameterValues(userAnswers.nextElement());
						for(int i = 0; i < array.length;i++){
							answer+=array[i];
							if(i< array.length-1){
								answer+=",";
							}
						}
					}
					else{
						answer = request.getParameter(userAnswers.nextElement());
					}
					StringTokenizer tokenizer = new StringTokenizer(answer,",");
					while(tokenizer.hasMoreTokens()){
						String token = tokenizer.nextToken();
						userAnswerArray.add(token);
					}
					score += quiz.grade(answers,userAnswerArray);
					index++;
				}
				String forPractice = (String) session.getAttribute("practice");
				UserMySQLAccess con = new UserMySQLAccess();
				UserProfile profile = (UserProfile) session.getAttribute("userProfile");
				String username = profile.getName();
				String quizName = (String) session.getAttribute("quizName");
				double startTime = Double.parseDouble((String) session.getAttribute("startTime"))/1000;
				double endTime = System.currentTimeMillis()/1000;
				double timeElapsed = endTime - startTime;
				if(forPractice == null){
					int num = con.updateQuizzesTaken(username,quizName,score,timeElapsed,System.currentTimeMillis());
					profile.setNumQuizzesTaken(num);
					AchievementsList list = (AchievementsList) session.getAttribute("list");
					list.updatePlayerAchievements(profile,con);
					con.updateNewsfeed(username,"taking",quizName,System.currentTimeMillis());
				}
				else{
					con.updateHasTakenPracticeQuiz(username);
					profile.setTookPracticeQuiz(true);
					AchievementsList list = (AchievementsList) session.getAttribute("list");
					list.updatePlayerAchievements(profile,con);
				}
				RequestDispatcher rd = request.getRequestDispatcher("scorePage.jsp");
				session.setAttribute("score",score);
				session.setAttribute("timeTaken", timeElapsed);
				rd.forward(request, response);

			}
			else{
				String a = (String) request.getParameter((String)session.getAttribute("quizItemNumber"));
				ArrayList<String>userAnswers = (ArrayList<String>) session.getAttribute("userAnswers");
				userAnswers.add(a);
				for(int i = 0; i < quizItems.size(); i++){
					String answer = userAnswers.get(i);
					ArrayList<Answer> answers = quizItems.get(index).getAnswersArray();
					userAnswerArray = new ArrayList<String>();
					StringTokenizer tokenizer = new StringTokenizer(answer,",");
					while(tokenizer.hasMoreTokens()){
						String token = tokenizer.nextToken();
						userAnswerArray.add(token);
					}
					score += quiz.grade(answers,userAnswerArray);
					index++;
				}
				String forPractice = (String) session.getAttribute("practice");;
				UserMySQLAccess con = new UserMySQLAccess();
				UserProfile profile = (UserProfile) session.getAttribute("userProfile");
				String username = profile.getName();
				String quizName = (String) session.getAttribute("quizName");
				double startTime = Double.parseDouble((String) session.getAttribute("startTime"))/1000;
				double endTime = System.currentTimeMillis()/1000;
				double timeElapsed = endTime - startTime;
				if(forPractice == null){
					int num = con.updateQuizzesTaken(username,quizName,score,timeElapsed,System.currentTimeMillis());
					profile.setNumQuizzesTaken(num);
					AchievementsList list = (AchievementsList) session.getAttribute("list");
					list.updatePlayerAchievements(profile,con);
					con.updateNewsfeed(username,"taking",quizName,System.currentTimeMillis());
				}
				else{
					con.updateHasTakenPracticeQuiz(username);
					profile.setTookPracticeQuiz(true);
					AchievementsList list = (AchievementsList) session.getAttribute("list");
					list.updatePlayerAchievements(profile,con);
				}
				RequestDispatcher rd = request.getRequestDispatcher("scorePage.jsp");
				session.setAttribute("score",score);
				session.setAttribute("timeTaken", timeElapsed);
				rd.forward(request, response);

			}
			


		}
		else{
			String forPractice = (String) session.getAttribute("practice");
			UserMySQLAccess con = new UserMySQLAccess();
			UserProfile profile = (UserProfile) session.getAttribute("userProfile");
			String username = profile.getName();
			String quizName = (String) session.getAttribute("quizName");
			double startTime = Double.parseDouble((String) session.getAttribute("startTime"))/1000;
			double endTime = System.currentTimeMillis()/1000;
			double timeElapsed = endTime - startTime;
			if(forPractice == null){
				String scoreString = (String) session.getAttribute("score");
				int score = Integer.parseInt(scoreString);
				int num = con.updateQuizzesTaken(username,quizName,score,timeElapsed,System.currentTimeMillis());
				profile.setNumQuizzesTaken(num);
				AchievementsList list = (AchievementsList) session.getAttribute("list");
				list.updatePlayerAchievements(profile,con);
				con.updateNewsfeed(username,"taking",quizName,System.currentTimeMillis());
			}
			else{
				con.updateHasTakenPracticeQuiz(username);
				profile.setTookPracticeQuiz(true);
				AchievementsList list = (AchievementsList) session.getAttribute("list");
				list.updatePlayerAchievements(profile,con);
			}
			session.setAttribute("quiz",null);
			session.setAttribute("timeTaken",timeElapsed);
			RequestDispatcher rd = request.getRequestDispatcher("scorePage.jsp");
			rd.forward(request, response);
		}


	}

}
