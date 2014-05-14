<%@ page import="quiz.UserMySQLAccess,quiz.UserProfile,java.util.ArrayList,java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Change Password</title>
 <link href="bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
<h1>Change Password</h1>
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
</body>
</html>