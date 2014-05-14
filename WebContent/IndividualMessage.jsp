<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Message</title>
<link href="bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>

<h1>Note</h1>
<nav>
<a href="HomePage.jsp">Home</a> |
<a href="CreateQuiz.jsp">Create a Quiz</a> |
<a href="QuizCatalog.jsp">All Quizzes</a> |
<a href="UserCatalog.jsp">Users</a> |
<a href="Inbox.jsp">Messages</a> |
<a href="MyAccount.jsp">My Account</a>
</nav>
<br><br>
<b>From <%=( String)session.getAttribute("creator")%></b>
<br><br>
<textarea rows="4" cols="50"><%=(String) session.getAttribute("text") %></textarea>
<br><br>
<form action="ComposeMessage.jsp" method="post">
<input type="submit" value=Reply>
</form>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</body>
</html>