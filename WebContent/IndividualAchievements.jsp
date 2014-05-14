<%@ page import="java.sql.*,quiz.UserMySQLAccess,quiz.UserProfile" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Achievements</title>
</head>
<body>

<h1>Quiztopia!</h1>
<nav>
<a href="HomePage.jsp">Home</a> |
<a href="CreateQuiz.jsp">Create a Quiz</a> |
<a href="QuizCatalog.jsp">All Quizzes</a> |
<a href="UserCatalog.jsp">Users</a> |
<a href="/">Messages</a> |
<a href="/">My Account</a>
</nav>
<br><br>

<%
	UserProfile profile = (UserProfile)session.getAttribute("userProfile");
	String user = profile.getName();
	UserMySQLAccess con = new UserMySQLAccess();
	ResultSet rs = con.getIndividualAchievements(user);
	while(rs.next()){
		String achievementName = rs.getString("achievement");
		String description = con.getAchievementDescription(achievementName);
		if(achievementName.equals("null")) break;
		%>
		<br><br>
		<p>
			<b>achievementName  </b><textarea readonly>description</textarea>
		</p>
	<%}
	con.closeConnection();
%>