<%-- 
    Document   : AssignPatient
    Created on : Nov 22, 2014, 3:30:42 PM
    Author     : Stuart Alldritt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Assign Patient</title>
    </head>
    <body>
        <form method="post" action="AssignPatient">
             Patient: <%= request.getParameter("patient") %> <input type="text" name="ohip" value="<%= request.getParameter("patient") %>" hidden="true"> <br/>
             Doctor Username: <input type="text" name="username"> <br/>             
             <input type="submit" value="Assign Patient to Doctor">
         </form>
    </body>
</html>
