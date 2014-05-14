<%@ page import="java.sql.*,quiz.UserMySQLAccess,quiz.UserProfile" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Announcements</title>
</head>
<body>

<h1>Create Announcement</h1>
<nav>
<a href="HomePage.jsp">Home</a> |
<a href="CreateQuiz.jsp">Create a Quiz</a> |
<a href="QuizCatalog.jsp">All Quizzes</a> |
<a href="UserCatalog.jsp">Users</a> |
<a href="/jquery/">Rankings</a> |
<a href="/">Messages</a> |
<a href="/">My Account</a>
</nav>
<br><br>

<form action="CreateAnnouncementServlet" method="post">
	<textarea rows="4" cols="50" name="text"></textarea>
  	<input type="submit" value="Post">
</form>
<br>
