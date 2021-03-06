<%@ page import="quiz.Quiz,quiz.QuizItem,java.util.ArrayList,quiz.Answer" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=session.getAttribute("quizName") %></title>
</head>
 <link href="bootstrap.min.css" rel="stylesheet" media="screen">
<body>
<h1>Quiztopia!</h1>
<nav>
<a href="HomePage.jsp">Home</a> |
<a href="QuizCatalog.jsp">All Quizzes</a> |
<a href="UserCatalog.jsp">Users</a> |
<a href="/">Messages</a> |
<a href="MyAccount.jsp">My Account</a>
</nav>
<%
	String imcor = (String) session.getAttribute("imCor");
	String nextPage = null;
	if(imcor.equals("yes")){
		nextPage = "MultPageServlet";
	}
	else{
		nextPage = "GradeServlet";
	}

%>
<form action = <%=nextPage %> method=post>
<%
	int quizItemNumber = Integer.parseInt((String)session.getAttribute("quizItemNumber"));
	Quiz quiz = (Quiz) session.getAttribute("quiz");
	ArrayList<QuizItem> items = quiz.getQuizItems();
	QuizItem item = items.get(quizItemNumber);
	String question = item.getQuestion();
	String picture = item.getPicture();
	ArrayList<String> multipleChoice = item.getMultipleChoiceArray();
	ArrayList<Answer> answers = item.getAnswersArray();
	if(question != null){%>
		<br><br>
		<strong><%=question%></strong>
		<br><br>
		<%
		if(multipleChoice != null){
			if(answers.size() == 1){
				for(int j = 0; j < multipleChoice.size(); j++){%>
					<input type ="radio" name=<%=quizItemNumber%> value=<%=multipleChoice.get(j) %>><%=multipleChoice.get(j)%>
					<br><br>
				<%}
				}
				else{
					for(int j = 0; j < multipleChoice.size(); j++){%>
					<input type ="checkBox" name=<%=quizItemNumber%> value=<%=multipleChoice.get(j) %>><%=multipleChoice.get(j)%>
					<br><br>
				<%}

				}
			}
		else {%>
			<input type="text" name=<%=quizItemNumber%>>
		<%}
	}		
	
	else{%>
		<img src=<%=picture%>> 
		<input type="text" name=<%=quizItemNumber%>>
	<%}
%>


<input type="submit" value="Submit">
</form>
</body>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</html>