<%@ page import="java.sql.*,quiz.UserMySQLAccess,quiz.UserProfile" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Messages</title>
 <link href="bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>

<h1>Compose Message</h1>
<nav>
<a href="HomePage.jsp">Home</a> |
<a href="CreateQuiz.jsp">Create a Quiz</a> |
<a href="QuizCatalog.jsp">All Quizzes</a> |
<a href="UserCatalog.jsp">Users</a> |
<a href="Inbox.jsp">Messages</a> |
<a href="MyAccount.jsp">My Account</a>
</nav>
<br><br>
<% UserProfile profile = (UserProfile)session.getAttribute("userProfile");%>
<form action="ComposeMessageServlet" method="post">
	<textarea rows="4" cols="50" name="text"></textarea>
<input type="hidden" value=<%=profile.getName()%> name="user">
<br><br>
Enter Quiz Name(Challenge Only) <input type="text" name="quizName">
<br><br>
Recipient <input type="text" name="target">
<br><br>
<input type="radio" value="0" name="type"> Friend Request
<input type="radio" value="1" name="type"> Challenge
<input type="radio" value="2" name="type"> Note
<input type="submit" name="Send">
</form>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</body>
</html>