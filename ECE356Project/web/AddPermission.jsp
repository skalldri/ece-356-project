<%-- 
    Document   : AddPermission
    Created on : Nov 22, 2014, 2:16:15 PM
    Author     : Stuart Alldritt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Permission</title>
    </head>
    <body>
        
        <a href="<%= request.getParameter("go_back")%>">BACK</a> <br/>
        
        <form method="post" action="AddPermission">
             Patient: <%= request.getParameter("patient") %> <input type="text" name="ohip" value="<%= request.getParameter("patient") %>" hidden="true"> <br/>
             Doctor Username: <input type="text" name="username"> <br/>             
             <input type="submit" value="Grant Access Permission">
         </form>
    </body>
</html>
