<%@ page import="java.sql.*,quiz.UserMySQLAccess,quiz.UserProfile,quiz.UserMySQLAccess,java.util.ArrayList,quiz.Newsfeed,quiz.AchievementsList" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Quiztopia!</title>
 <link href="bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
<%
	UserProfile currentProfile = (UserProfile)session.getAttribute("userProfile");	
	String currentUser = currentProfile.getName();
	boolean isAdmin = currentProfile.getAdmin();
	

%>
<h1>Welcome to Quiztopia,<%=" "+ currentUser %>!</h1>
<nav>
<a href="HomePage.jsp">Home</a> |
<a href="CreateQuiz.jsp">Create a Quiz</a> |
<a href="QuizCatalog.jsp">All Quizzes</a> |
<a href="UserCatalog.jsp">Users</a> |
<a href="Inbox.jsp">Messages</a> |
<a href="MyAccount.jsp">My Account</a>
</nav>
<br><br>
<form action="SearchServlet" method="post">
	<input type="text" name="query" value="Search for quizzes or users!">
	<input type="radio" name="type" value="Quiz"> Quiz
	<input type="radio" name="type" value="User"> User
	<input type ="submit" value = "Search">

</form>

<%
	session.setAttribute("quiz",null);
	session.setAttribute("quizName",null);
	UserMySQLAccess con = new UserMySQLAccess();
	AchievementsList list = (AchievementsList) session.getAttribute("list");
	boolean newMessage = con.newMessage(currentUser);
	ResultSet feed = con.getNewsfeed();
	ResultSet popQuizzes = con.returnQuizOrder();
	ResultSet announce = con.getAnnouncements();
	System.out.println("HELLO");
	ResultSet friendsAchievements = con.returnAchievements();
	ArrayList<Newsfeed> myCreatingActivity = new ArrayList<Newsfeed>();
	ArrayList<Newsfeed> myTakingActivity = new ArrayList<Newsfeed>();
	ArrayList<String> myAchievements = list.getPlayerAchievements();
	ArrayList<Newsfeed> myFriendsActivity = new ArrayList<Newsfeed>();
	ArrayList<Newsfeed> recentlyCreatedQuizzes = new ArrayList<Newsfeed>();
	ArrayList<Newsfeed> mostPopQuizzes = new ArrayList<Newsfeed>();
	ArrayList<String> announcements = new ArrayList<String>();
	while (feed.next()) {
		String type = feed.getString("type");
		String username = feed.getString("username");
		String details = feed.getString("details");
		Newsfeed news = new Newsfeed(username, type, details);
		;
		if (type.equals("creation")) {
			if (username.equals(currentUser)) {
				myCreatingActivity.add(news);

			} 
			else if (con.areFriends(currentUser, username)) {
				myFriendsActivity.add(news);
			}
			recentlyCreatedQuizzes.add(news);
		}
		else if (type.equals("taking")) {
			if (username.equals(currentUser)) {
				myTakingActivity.add(news);

			} 
			else if (con.areFriends(currentUser, username)) {
				myFriendsActivity.add(news);
			}
		} 

	}
	if (popQuizzes != null) {
		for (int i = 0; i < 20; i++) {
			if (popQuizzes.next()) {
				String quizName = popQuizzes.getString("quizName");
				Newsfeed quiz = new Newsfeed(null, null, quizName);
				mostPopQuizzes.add(quiz);
			}
		}
	}
	while(friendsAchievements.next()){
		String username = friendsAchievements.getString("user");
		String achievement = friendsAchievements.getString("achievement");
		if(!username.equals(currentUser) && con.areFriends(currentUser, username) ){
			Newsfeed news = new Newsfeed(username,"achievement",achievement);
			myFriendsActivity.add(news);
		}
	}
	while(announce.next()){
		String string = announce.getString("text");
		announcements.add(string);
	}
	
	con.closeConnection();
	
	if(newMessage){%>
		<h3>You have new message(s)</h3>
	<%}
%>
<h3>Announcements</h3>
<%
	if(isAdmin){%>
		<a href="CreateAnnouncement.jsp"> Create An Announcement</a>
	<%}
	

%>
<ul>
<%
	for(int i = 0; i < announcements.size(); i++){
		%>
			<li><%=announcements.get(i)%></li>
		<%
	}

%>
</ul>

<h3>My Quiz Creation Activity</h3>
<ul>
<%
	for(int i = 0; i < myCreatingActivity.size(); i++){
		Newsfeed news = myCreatingActivity.get(i);
		String quizName = news.getDetails();
		%>
			<li> I created <a href=<%="QuizPage.jsp?id=" +quizName %>> <%=quizName%> </a></li>
		<%
	}

%>
</ul>
<h3>My Quiz Taking Activity</h3>
<ul>
<%
	for(int i = 0; i < myTakingActivity.size(); i++){
		Newsfeed news = myTakingActivity.get(i);
		String quizName = news.getDetails();
		%>
			<li> I took <a href=<%="QuizPage.jsp?id=" +quizName %>> <%=quizName%> </a></li>
		<%
	}

%>
</ul>
<h3>My Achievements</h3>
<ul>
<%
	for(int i = 0; i < myAchievements.size(); i++){
		%>
			<li> I achieved  <%=myAchievements.get(i)%> </li>
		<%
	}

%>
</ul>

<h3>My Friends' Activity</h3>
<ul>
<%
	for(int i = 0; i < myFriendsActivity.size(); i++){
		Newsfeed news = myFriendsActivity.get(i);
		String details = news.getDetails();
		String type = news.getType();
		String username = news.getUser();
		if(type.equals("taking")){
		%>
			<li> <a href=<%=UserProfile.getURL(username)%>> </a> <%=username%> took <% %> <a href=<%="QuizPage.jsp?id=" +details %>> <%=details%> </a></li>
		<%
		}
		else if(type.equals("creating")){
			%>
				<li> <a href=<%=UserProfile.getURL(username)%>></a> <%=username%> created <% %><a href=<%="QuizPage.jsp?id=" +details %>> <%=details%> </a></li>
			<%
		}
		else if(type.equals("achievement")){
			%>
				<li> <a href=<%=UserProfile.getURL(username)%>> </a> <%=username%> achieved <% %><%=details%> </li>
			<%
		}
	}

%>
</ul>

<h3>Most Popular Quizzes</h3>
<ol>
<%
	for(int i = 0; i <mostPopQuizzes.size();i++){
		Newsfeed news = mostPopQuizzes.get(i);
		String quizName = news.getDetails();
		%>
			<li> <a href=<%="QuizPage.jsp?id=" +quizName %>> <%=quizName%> </a></li>
		<%
	}

%>
</ol>

<h3>Recently Created Quizzes</h3>
<ul>
<%
	for(int i = 0; i < recentlyCreatedQuizzes.size(); i++){
		Newsfeed news = recentlyCreatedQuizzes.get(i);
		String quizName = news.getDetails();
		String username = news.getUser();
		%>
			<li> <a href=<%=UserProfile.getURL(username)%>> <%=username%></a> created <% %>  <a href=<%="QuizPage.jsp?id=" +quizName %>> <%=quizName%> </a></li>
		<%
	}

%>
</ul>


<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</body>
</html>

