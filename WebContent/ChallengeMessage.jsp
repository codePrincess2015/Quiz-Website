 <%@ page import="java.sql.*,quiz.UserMySQLAccess,quiz.UserProfile" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <link href="bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>

<h1>Challenge</h1>
<nav>
<a href="HomePage.jsp">Home</a> |
<a href="CreateQuiz.jsp">Create a Quiz</a> |
<a href="QuizCatalog.jsp">All Quizzes</a> |
<a href="UserCatalog.jsp">Users</a> |
<a href="Inbox.jsp">Messages</a> |
<a href="MyAccount.jsp">My Account</a>
</nav>
<br><br>
<b>SENDER <% session.getAttribute("creator"); %></b>
<br><br>
<%	UserProfile profile = (UserProfile)session.getAttribute("userProfile"); 
	UserMySQLAccess con = new UserMySQLAccess();
	System.out.print((String)session.getAttribute("creator"));
	String score=con.getQuizScore((String)session.getAttribute("creator"),(String)session.getAttribute("quizName"));
	String quizName = (String)session.getAttribute("quizName");
	con.closeConnection();
%>
<textarea readonly><%=(String)session.getAttribute("text")%></textarea>
<br><br>
<b>My Performance: <%=score %></b>
<br>
<b>Think you can do better?</b>
<br><br>
<form action=<%="QuizPage.jsp?id="+quizName %> method="post">
<input type="submit" value=<%=quizName%> name="quizName">
</form>

<form action="ComposeMessage.jsp" method="post">
<input type="submit" value=Reply>
<input type="hidden" value=<%session.getAttribute("creator"); %>>
</form>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</body>
</html>