<%@ page import="quiz.Quiz,quiz.QuizItem,java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Right!</title>
<link href="bootstrap.min.css" rel="stylesheet" media="screen">
</head>

<body>
<h1>Right! :) </h1>
<%
	int currentIndex = Integer.parseInt((String) session.getAttribute("quizItemNumber"));
	System.out.println(currentIndex);
	String nextPage = null;
	Quiz quiz = (Quiz) session.getAttribute("quiz");
	ArrayList<QuizItem> items = quiz.getQuizItems();
	if (currentIndex == items.size()-1) {
		nextPage = "GradeServlet";
	} else {
		nextPage = "NextQuestion";
	}
%>
<form action = <%=nextPage%> method="post">
	<input type="submit" value="Next">
</form>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</body>
</html>