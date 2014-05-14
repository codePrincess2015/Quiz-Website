<%@ page import="quiz.UserMySQLAccess,quiz.UserProfile,java.util.ArrayList,java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Try Again</title>
 <link href="bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
<h1>Try Again</h1>
<nav>
<a href="HomePage.jsp">Home</a> |
<a href="CreateQuiz.jsp">Create a Quiz</a> |
<a href="QuizCatalog.jsp">All Quizzes</a> |
<a href="UserCatalog.jsp">Users</a> |
<a href="ComposeMessage.jsp">Messages</a> |
<a href="MyAccount.jsp">My Account</a>
</nav>
<h3>The passwords you entered did not match. Please try again.</h3>
<form action="ChangePasswordServlet" method="post">
<p>Please enter your old and new passwords.</p>
<%
	UserProfile profile = (UserProfile) session.getAttribute("userProfile");
	String currentUser = profile.getName();
%>
<input name="name" type ="hidden" value =<%=currentUser%>>
<p>Old Password: <input type="password" name="oldPassword" ></p>
<p>New Password: <input type="password" name="newPassword"></p>
<p>Confirm New Password: <input type="password" name="confirmNewPassword">
<input type="submit" value="Change Password"></p>
</form> 
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</body>
</html>