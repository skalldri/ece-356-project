<%-- 
    Document   : index
    Created on : Nov 9, 2014, 11:18:12 PM
    Author     : Stuart Alldritt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LOGIN</title>
    </head>
    <body>
        <h1>Login</h1>
        
        <form method="post" action="UserLoginServlet">
            Username: <input type="text" name="username"> <br/>
            Password: <input type="password" name="password"> <br/>
            
            <select name="usertype">
                <option value="patient">Patient</option>
                <option value="staff">Staff</option>
            </select>
            <br/>
            <input type="submit">
        </form>
        
    </body>
</html>
