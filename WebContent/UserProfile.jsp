<%@ page import="quiz.UserMySQLAccess,quiz.UserProfile,java.util.ArrayList,java.sql.*,java.util.Set"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<%
String id = request.getParameter("id");
UserProfile thisUser = new UserProfile(id);
UserProfile me = (UserProfile) session.getAttribute("userProfile");
int numQuizzesCreated = thisUser.getNumQuizzesCreated();
int numQuizzesTaken = thisUser.getNumQuizzesTaken();
%>
<title><%=id%></title>
<link href="bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
<h1><%=id%></h1>
<nav>
<a href="HomePage.jsp">Home</a> |
<a href="CreateQuiz.jsp">Create a Quiz</a> |
<a href="QuizCatalog.jsp">All Quizzes</a> |
<a href="UserCatalog.jsp">Users</a> |
<a href="Inbox.jsp">Messages</a> |
<a href="MyAccount.jsp">My Account</a>
</nav>
<% UserMySQLAccess con = new UserMySQLAccess();
boolean isFriend = con.areFriends(thisUser.getName(), me.getName());
if (isFriend) {
	%>
	<form action ="ComposeMessageServlet" method="post">
	<input name="target" type="hidden" value=<%=thisUser.getName()%>>
	<input name="sender" type="hidden" value=<%=me.getName()%>>
	<input name="type" type="hidden" value="1">
	<input type="submit" value="Send a message">
	</form>
	
	<form action = "RemoveFriendServlet" method = "post">
	<input name="toRemove" type="hidden" value=<%=thisUser.getName()%>>
	<input name="removing" type="hidden" value=<%=me.getName()%>>
	<input type="submit" value="Remove friend">
	</form>
	<%
} else {
	// Set the parameters for a friend request.
	// get text from box
	%>
	<form action ="ComposeMessageServlet" method="post">
	<input name="target" type="hidden" value=<%=thisUser.getName()%>>
	<input name="user" type="hidden" value=<%=me.getName()%>>
	<input name="type" type="hidden" value="1">
	<input type = "hidden" value="Be my friend!" name = "text">
	<input type = "hidden" value ="garbage" name="quizName">
	<input type="submit" value="Send a friend request">
	</form>
	<%
}
%>
<img border= "0" src=<%=thisUser.getProfilePic()%> width="100" height="100">
<ul>
<%
Set<String> friendsSet = thisUser.getFriends();

for(String friend : friendsSet) {
%>
<li><a href = <%="UserProfile.jsp?id=" + friend%>><%=friend %></a></li>
<%
}
%>

</ul>
<p> 
<%=numQuizzesCreated%> Quizzes Created: 
</p>
<ul>
<%
ArrayList<String> quizzesCreated = thisUser.getQuizzesCreated();
//Place links to quizzes next to names.
for (int i = 0; i < quizzesCreated.size(); i++) {
	%> 
	<li><a href=<%="QuizPage.jsp?id=" + quizzesCreated.get(i)%>><%=quizzesCreated.get(i)%></a></li>
	<%
}
%>
</ul>

<p>
<%=numQuizzesTaken%> Quizzes Taken:
</p>
<ul>
<%
ArrayList<String> quizzesTaken = thisUser.getQuizzesTaken();
//Place links to quizzes next to names.
for (int i = 0; i < quizzesTaken.size(); i++) {
	%>
	<li><a href=<%="QuizPage.jsp?id=" + quizzesTaken.get(i)%>><%=quizzesTaken.get(i)%></a></li>
	<%
}
%>
</ul>

<%
	con.closeConnection();

%>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</body>
</html>
