<%@ page import="quiz.UserMySQLAccess,quiz.UserProfile,java.util.ArrayList,java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>User Catalog</title>
<link href="bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
<nav>
<a href="HomePage.jsp">Home</a> |
<a href="CreateQuiz.jsp">Create a Quiz</a> |
<a href="QuizCatalog.jsp">All Quizzes</a> |
<a href="UserCatalog.jsp">Users</a> |
<a href="Inbox.jsp">Messages</a> |
<a href="MyAccount.jsp">My Account</a>
</nav>
<br><br>
	<h1>User Catalog</h1>
	<%
		String userName;
		UserMySQLAccess con = new UserMySQLAccess();
		ResultSet allUsers = con.getResultSet();
		String URL;
		%>
	<ul>
		<%
		try {
			while (allUsers.next()) {
				userName = allUsers.getString("userName");
				URL = UserProfile.getURL(userName);
				%><li><a href=<%=URL%>> <%=userName%></a></li><%
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		%>
	</ul>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</body>
</html>