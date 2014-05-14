<%@ page import="quiz.UserMySQLAccess,quiz.UserProfile,java.util.ArrayList,java.sql.*,java.util.Set"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>My Account</title>
 <link href="bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>

<%
UserProfile me = (UserProfile) session.getAttribute("userProfile");
int numQuizzesCreated = me.getNumQuizzesCreated();
int numQuizzesTaken = me.getNumQuizzesTaken();
UserMySQLAccess con = new UserMySQLAccess();
%>
<h1><%=me.getName()%></h1>
<nav>
<a href="HomePage.jsp">Home</a> |
<a href="CreateQuiz.jsp">Create a Quiz</a> |
<a href="QuizCatalog.jsp">All Quizzes</a> |
<a href="UserCatalog.jsp">Users</a> |
<a href="Inbox.jsp">Messages</a> |
<a href="MyAccount.jsp">My Account</a>
</nav>

<img border= "0" src=<%=me.getProfilePic()%> width="150" height="150">
<br><br>
<a href="ChangePassword.jsp">Change Password</a>
<h1>My Friends</h1>
<ul>
<%
Set<String> friendsSet = me.getFriends();

for(String friend : friendsSet) {
%>
<li><a href = <%="UserProfile.jsp?id=" + friend%>><%=friend %></a></li>
<%
}
%>
</ul>
<h1><%=numQuizzesCreated%> Quizzes Created:</h1>
<ul>
<%
ArrayList<String> quizzesCreated = me.getQuizzesCreated();
//Place links to quizzes next to names.
for (int i = 0; i < quizzesCreated.size(); i++) {
	%> 
	<li><a href=<%="QuizPage.jsp?id=" + quizzesCreated.get(i)%>><%=quizzesCreated.get(i)%></a></li>
	<%
}
%>
</ul>

<h1><%=numQuizzesTaken%> Quizzes Taken:</h1>

<ul>
<%
ArrayList<String> quizzesTaken = me.getQuizzesTaken();
//Place links to quizzes next to names.
for (int i = 0; i < quizzesTaken.size(); i++) {
	%>
	<li><a href=<%="QuizPage.jsp?id=" + quizzesTaken.get(i)%>><%=quizzesTaken.get(i)%></a></li>
	<%
}
%>
</ul>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</body>
</html>