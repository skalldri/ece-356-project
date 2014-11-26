<%-- 
    Document   : finance_home
    Created on : 22-Nov-2014, 6:35:25 PM
    Author     : Tomasz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Finance Homepage</title>
    </head>
    
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/>
    
    <body>
        <h1>Financial Inquiries</h1>
        Welcome <%= userData.getUsername() %> <br><br>
        
        <a href="DoctorList">View doctor records</a> <br>
        <a href="FinanceLists?page=procedure&start=0&end=0">View procedure revenues</a> <br>
        <a href="FinanceLists?page=visit&start=0&end=0">View visitation revenues</a> <br>  
        <a href="FinanceLists?page=insurance&start=0&end=0">Review provincial health insurance billing</a> <br>
    </body>
</html>
