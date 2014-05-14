<%@ page import="quiz.Quiz,quiz.QuizItem,quiz.Answer,java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>
<link href="bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
<h1>Quiztopia!</h1>
<nav>
<a href="HomePage.jsp">Home</a> |
<a href="QuizCatalog.jsp">All Quizzes</a> |
<a href="UserCatalog.jsp">Users</a> |
<a href="/">Messages</a> |
<a href="/">My Account</a>
</nav>
<%String type = request.getParameter("type");%>

<h1><%=type%></h1>
<ul>
<%
	
	ArrayList<String> results = (ArrayList<String>) request.getAttribute("results");
	for(int i = 0; i < results.size(); i++){
		if(type.equals("Quiz")){%>
		<li><a href=<%="QuizPage.jsp?id="+results.get(i)%>> <%=results.get(i)%></a>
	<%}
		else{%>
		<li><a href=<%="UserProfile.jsp?id="+results.get(i)%>><%=results.get(i) %></a>
	<%}
	}

%>
</ul>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</body>
</html>