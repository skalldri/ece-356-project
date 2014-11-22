<%-- 
    Document   : index
    Created on : Nov 9, 2014, 11:18:12 PM
    Author     : Stuart Alldritt
--%>

<%@page import="ece356.UserData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ECE 356 Hospital Database</title>
    </head>
    <body>
        <a href="login.jsp">Login</a> </br>
        <a href="DatabaseTest">Test Database Connection</a> </br>
        <a href="login_test.jsp">Print Session Data</a> </br>
        
        <% UserData ud = new UserData();
           ud.setUserType("patient");
           ud.setUsername("1234567890AB"); %>
        <% request.getSession().setAttribute("userData", ud); %>
        <a href="PatientMain">Patient Test</a>

    </body>
</html>
