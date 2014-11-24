<%-- 
    Document   : StaffMain
    Created on : Nov 22, 2014, 12:34:25 PM
    Author     : Stuart Alldritt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff Main</title>
    </head>
    <body>
        <h1>Staff Main Page</h1>
        
        <a href="CreatePatient.jsp">Create New Patient</a> <br/>
        <a href="PrescriptionSearch.jsp">Prescription Search</a> <br/>
        
        <br/><br/><br/>
        Patient Search:
         <form method="post" action="PatientSearch">
             Show deleted records: <input name="deleted_records" type="checkbox"> <br/>
             Name: <input type="text" name="name"> <br/>
             Health card: <input type="text" name="ohip"> <br/>
             Phone: <input type="text" name="phone"> <br/>
             SIN: <input type="text" name="sin"> <br/>
             Comments: <input type="text" name="comments"> <br/>
             Diagnosis: <input type="text" name="diagnosis"> <br/>
             Last Visit date (not working yet): <input type="text" name="date"> <br/>
             <input type="submit">
         </form>
    </body>
</html>
