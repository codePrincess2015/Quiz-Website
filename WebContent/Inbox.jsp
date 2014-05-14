<%@ page import="java.sql.*,quiz.UserMySQLAccess,quiz.UserProfile" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Messages</title>
</head>
 <link href="bootstrap.min.css" rel="stylesheet" media="screen">
<body>

<h1>Inbox</h1>
<nav>
<a href="HomePage.jsp">Home</a> |
<a href="CreateQuiz.jsp">Create a Quiz</a> |
<a href="QuizCatalog.jsp">All Quizzes</a> |
<a href="UserCatalog.jsp">Users</a> |
<a href="Inbox.jsp">Messages</a> |
<a href="MyAccount.jsp">My Account</a>
</nav>
<br><br>
<%
UserProfile profile = (UserProfile)session.getAttribute("userProfile");
String user = profile.getName();
UserMySQLAccess con = new UserMySQLAccess();
ResultSet rs = con.getMessageAttributes(user);
%>
<form action="ComposeMessage.jsp" method="post">
<input type="submit" value=Compose>
</form>
<b>Messages</b>
<form action="MessageServlet" method="post">
<%
	while(rs.next()){
		String messageType = rs.getString("messagetype");
		String sender = rs.getString("creator");
		String messageId = rs.getString("messageId");
		String messageText = rs.getString("messagetext");
		System.out.println(messageText);
		String quizName = rs.getString("quiz");
		if(sender.equals("null")) break;
		%>
		<br><br>
		
<br>
		<input type="submit" value=<%=sender%> name="action">  
		<input type="checkbox" name="checkboxes" value=<%=messageId%>>
		<input type="hidden" value=<%=messageId%> name="messageId">
		<input type="hidden" value=<%=sender%> name="sender">
		<input type="hidden" value=<%=messageType%> name="type">
		<input type="hidden" value="<%=messageText%>" name="text">
		<input type="hidden" value=<%=user%> name="user">
		<input type="hidden" value=<%=quizName%> name="quizName">
	<%}
	con.closeConnection();
%>
<br>
<input type="submit" value="delete" name="action">
</form>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</body>
</html>