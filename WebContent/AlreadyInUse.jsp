<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create account</title>
<link href="bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
<h1> The Name <%=request.getParameter("newUsername")%> is Already in Use. </h1>
<p> Please enter another name and password. </p>
<form action="LoginServlet" method="post">
<p>User Name: <input type="text" name="username" /></p>
<p>Password: <input type="password" name="password"/>
<input type="submit" value="Login"/></p>
</form> 
<script src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap.min.js"></script>
</body>
</html>