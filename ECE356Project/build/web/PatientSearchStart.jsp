<%-- 
    Document   : PatientSearchStart
    Created on : Nov 24, 2014, 6:21:13 PM
    Author     : Stuart Alldritt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Search</title>
    </head>
    
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/> 
    
    <body>
        
        <br/><a href="<%= request.getParameter("go_back")%>?reload">BACK</a> <br/><br/>
        
        Patient Search:
         <form method="post" action="PatientSearch">
             <%
             if(userData.getUserVariant().equals("DOCTOR"))
             {
                 out.println("Search all patients: <input name=\"all_patients\" type=\"checkbox\"> <br/>");
             }
             %>
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
