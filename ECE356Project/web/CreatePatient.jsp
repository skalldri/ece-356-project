<%-- 
    Document   : CreatePatient
    Created on : 21-Nov-2014, 11:18:49 PM
    Author     : stuart
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Patient</title>
    </head>
    <body>
        <form method="post" action="CreatePatient">
             Name: <input type="text" name="name"> <br/>
             Health card: <input type="text" name="ohip"> <br/>
             Phone: <input type="text" name="phone"> <br/>
             SIN: <input type="text" name="sin"> <br/>
             Address: <input type="text" name="address"> <br/>
             Health State: <input type="text" name="health_state"> <br/>
             <input type="submit">
         </form>
    </body>
</html>
