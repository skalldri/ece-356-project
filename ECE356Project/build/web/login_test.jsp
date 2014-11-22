<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
<title>Form 1</title>
</head>
<body>
<h2>Thank you for entering your user data.</h2>

<jsp:useBean id="userData" class="ece356.UserData" scope="session"/>

Name: <%= userData.getUsername() %><br/>
Password: <%= userData.getPassword() %><br/>
User Type: <%= userData.getUserType() %><br/>

<p>
<a href="index.jsp">Again</a>

</body>
</html>