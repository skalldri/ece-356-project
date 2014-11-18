<%-- 
    Document   : doctor_home
    Created on : 18-Nov-2014, 2:56:02 PM
    Author     : stuart
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Homepage</title>
    </head>
    
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/>
    
    <body>
         <h1>Welcome <%= userData.getUsername() %></h1>
    </body>
</html>
