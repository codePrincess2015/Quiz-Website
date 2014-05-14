package quiz;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddServlet
 */
@WebServlet("/AddServlet")
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddServlet() {
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
		HttpSession session = request.getSession();
		Quiz quiz = (Quiz) session.getAttribute("quiz");
		if(	quiz == null){
			quiz = new Quiz();
			session.setAttribute("quiz",quiz);
		}
		QuizItem item = new QuizItem();
		String question = request.getParameter("question");
		String picture = request.getParameter("picture");
		String answers = request.getParameter("answers");
		String multipleChoice = request.getParameter("multipleChoice");
		String save = request.getParameter("save");
		if(save != null){
			quiz.removeQuizItem(Integer.parseInt(save));
		}
		if(question != null) {
			item.editQuestion(question);
		
		}
		else if(picture != null){
			item.editPicture(picture);	
		}
		if(multipleChoice != null){
			StringTokenizer tokenizer = new StringTokenizer(multipleChoice,",");
			while(tokenizer.hasMoreTokens()){
				String token = tokenizer.nextToken();
				item.addChoiceAnswer(token);
			}
		}
		if(answers != null){
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
		RequestDispatcher rd = request.getRequestDispatcher("CreateQuiz.jsp");
		rd.forward(request,response);
	}

}
