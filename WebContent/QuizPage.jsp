<%@ page import="java.sql.*,quiz.UserMySQLAccess,java.util.ArrayList,quiz.Newsfeed,quiz.UserProfile" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String quizName = request.getParameter("id");
	UserProfile profile = (UserProfile)session.getAttribute("userProfile");
	String username = profile.getName();
	UserMySQLAccess con = new UserMySQLAccess();
	ResultSet rs = con.returnQuizAttributes(quizName);
	String description = null;
	String practice = null;
	String creator = null;
	ArrayList<Newsfeed> rank = new ArrayList<Newsfeed>();
	ResultSet topUsers = con.returnUserRanks(quizName);
	ResultSet myPerformance = con.returnMyQuizHistory(username,quizName);
	ArrayList<String> myPerf = new ArrayList<String>();
	/* ResultSet lastDay = con.returnLastDayHistory(quizName); */
	while (rs.next()) {
		description = rs.getString("description");
		practice = rs.getString("practice");
		creator = rs.getString("username");
	}
	for(int i = 0; i < 20; i++){
		if(topUsers.next()){
			Newsfeed news = new Newsfeed(topUsers.getString("username"),""+topUsers.getInt("time"),null);
			rank.add(news);
		}
	}
	while(myPerformance.next()){
		int score = myPerformance.getInt("score");
		double timeElapsed = myPerformance.getDouble("time");
		String perf = score + " "+timeElapsed+"sec";
		myPerf.add(perf);
	}
	con.closeConnection();
%>
<title><%=quizName%></title>
<link href="bootstrap.min.css" rel="stylesheet" media="screen">
</head>

<body>

<h1>Quiztopia!</h1>
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
<h1><%=quizName%> created by <%=creator%></h1>
<p><%=description%></p>
<h2>User Rankings for This Quiz!</h2>
<ol>
<%
for(int i = 0; i <rank.size();i++){
	Newsfeed news = rank.get(i);
	String user= news.getUser();
	String score = news.getType();
	%>
		<li> <a href=<%="UserProfile.jsp?id=" +user %>> <%=user%> <%=score%>  </a></li>
	<%
}
%>

</ol>
<h2>My Past Performances</h2>
<ol>
<%
for(int i = 0; i < myPerf.size();i++){
	String perf = myPerf.get(i);
	%>
		<li> <%=myPerf.get(i) %> </li>
	<%
}
%>
</ol>
<form action="QuestionServlet" method="post">
	<input type="submit" value="Take this Quiz!" >
	<input type="hidden" value=<%=quizName%> name="quizName">
	<%
		if(practice.equals("yes")){%>
			<br><br>
			Take For Practice
			<input type="checkBox" name="practice">
			
		<%}
	%>
</form>
<%
	if(profile.getAdmin()){%>
		<form action = "DeleteQuizServlet" method = post>
				<input type="submit" value="Remove Quiz">
				<input type="hidden" name="quizName" value=<%=quizName%>>
		</form>
	<%}

%>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</body>
</html>