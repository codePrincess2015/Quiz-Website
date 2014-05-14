<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Results</title>
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
<br><br>
<h1>Quiz Results</h1>
<h2><%=session.getAttribute("quizName") %></h2>
<h3>Score: <%=session.getAttribute("score")%></h3>
<h3>Time Elapsed: <%=session.getAttribute("timeTaken")%></h3>
</body>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</html>