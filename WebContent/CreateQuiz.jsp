<%@ page import="quiz.Quiz,quiz.QuizItem,quiz.Answer,java.util.ArrayList" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Your Own Quiz!</title>
<link href="bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
<nav>
<a href="HomePage.jsp">Home</a> |
<a href="CreateQuiz.jsp">Create a Quiz</a> |
<a href="QuizCatalog.jsp">All Quizzes</a> |
<a href="UserCatalog.jsp">Users</a> |
<a href="/">Messages</a> |
<a href="MyAccount.jsp">My Account</a>
</nav>
<br><br>
<%
	Quiz currentQuiz = (Quiz) session.getAttribute("quiz");
	int edit = -1;
	if(request.getParameter("edit") != null){
		edit = Integer.parseInt(request.getParameter("edit"));
	}
	if (currentQuiz != null) {
		ArrayList<QuizItem> quizItems = currentQuiz.getQuizItems();
		for (int i = 0; i < quizItems.size(); i++) {
			QuizItem item = quizItems.get(i);
%>
			
			<%
				if(edit == i){%>
				<form action="AddServlet" method="post">
				<input type="submit"  value="Save Item">	
				<input type="hidden"  name = "save" value=<%=i%>>
				<%
					if (item.getQuestion() != null) {%>
						Question: <input type="text" value =<%=item.getQuestion()%> name="question">					

					<%} 
					else if (item.getPicture() != null) {%>
						Picture URL: <input type="text" value =<%=item.getPicture()%> name="picture">				
					<%}
					if (item.getMultipleChoiceAnswers() != null) {%>
						Choices: <input type="text" value=<%=item.getMultipleChoiceAnswers()%> name="multipleChoice">	
					<%}
					if (item.getAnswers() != null) {%>
						Answers: <input type ="text" value=<%=item.getAnswers()%> name="answers">			
					<%}
				%>
				</form>
			<%
					
				}
				else{%>
				<form action="EditServlet" method="post">
				<%
					if (item.getQuestion() != null) {%>
						Question: <%=item.getQuestion()%>
						<br><br>					

					<%} 
					else if (item.getPicture() != null) {%>
						Picture URL: <%=item.getPicture()%>	
						<br><br>				
					<%}
					if (item.getMultipleChoiceAnswers() != null) {%>
						Choices: <%=item.getMultipleChoiceAnswers()%>
						<br><br>	
					<%}
					if (item.getAnswers() != null) {%>
						Answers: <%=item.getAnswers()%>
						<br><br>			
					<%}
				%>
				<input type="submit"  value="Edit Item">
				<input type="hidden" name="edit" value=<%=i%>>
				</form>
					
					
				<%}
			
			%>
			
			<form action="DeleteServlet" method="post">	
			<input type="submit" value="Delete Item">
			<input type="hidden" name="delete" value=<%=i%>>
			</form>
				
			
		<%}
	}
%>



<form id ="Create"action="CreateServlet" method= "post">
<select name="qType">
<option value="multipleChoice">Multiple Choice</option>
<option value="picture">Picture</option>
<option value="freeResponse">Free Response</option>
<option value="fillInTheBlank">Fill in the Blank</option>
</select>
<input type="submit" value="Create Item">
</form>
<% 
	if(request.getParameter("qType") != null) {
	String questionCat = request.getParameter("qType");
	if(questionCat.equals("multipleChoice")){%>
		<form action="AddServlet" method="post">
		<input type="submit" value="Add Item">
		Question: <input type="text" name="question">
		Choices (separate by commas): <input type="text" name="multipleChoice">
		Answers (separate by semi-colons): <input type="text" name="answers">
		</form>
	<%}
	else if(questionCat.equals("picture")){%>
	<form action="AddServlet" method="post">
	<input type="submit" value="Add Item">
	Picture URL: <input type="text" name="picture">
	Answers (separate by commas): <input type="text" name="answers">
	</form>
	<%}
	else if(questionCat.equals("freeResponse")){%>
	<form action="AddServlet" method="post">
	<input type="submit" value="Add Item">
	Question: <input type="text" name="question">
	Answers (separate by commas): <input type="text" name="answers">
	</form>
	<%}
	else if(questionCat.equals("fillInTheBlank")){%>
	<form action="AddServlet" method="post">
	<input type="submit" value="Add Item">
	Question: <input type="text" name="question">
	Answers (separate similar answers by commas and separate each blank's set of possible answers by a semi-colon): <input type="text" name="answers">
	</form>
	<%}
	
}
	
%>
<br><br>
<form action="SubmitServlet" method="post">
Quiz Name: <input type="text" name="quizName">
Question Display 
<select name="display">
<option value="multPage">One Question per Page</option>
<option value="onePage">All Questions on Page</option>
</select>
Randomize?
<select name="random">
<option value="yes">Yes</option>
<option value="no">No</option>
</select>
Practice Mode?
<select name="practice">
<option value="yes">Yes</option>
<option value="no">No</option>
</select>
Immediate Correction? (Only for Multiple Page Quizzes)
<select name="correction">
<option value="yes">Yes</option>
<option value="no">No</option>
</select>
<br><br>
Quiz Description
<br><br>
<textarea name="description" style="height: 150px">
</textarea>
<input type="submit" value="Save Quiz">
</form>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</body>
</html>