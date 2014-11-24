<%-- 
    Document   : PrescriptionSearch
    Created on : 24-Nov-2014, 2:26:37 PM
    Author     : stuart
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prescription Search</title>
    </head>
    <body>
        <h1>Prescription Search</h1>
         <form method="post" action="PrescriptionSearch">
             Health card: <input type="text" name="ohip"> <br/>
             Drug: <input type="text" name="drug"> <br/>
             <input type="submit">
         </form> 
    </body>
</html>
