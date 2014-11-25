<%-- 
    Document   : doctor_home
    Created on : 18-Nov-2014, 2:56:02 PM
    Author     : stuart
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="models.Patient"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Homepage</title>
    </head>
    
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/>    
    
    <body>
         <h1>Welcome to Doctor Homepage, <%= userData.getUsername() %></h1>
         
         <a href="PrescriptionSearch.jsp?go_back=doctor_home.jsp">Prescription Search</a> <br/>
         <a href="PatientSearchStart.jsp?go_back=doctor_home.jsp">Patient Search</a> <br/>
    </body>
</html>
