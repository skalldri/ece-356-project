<%-- 
    Document   : EditAppointment
    Created on : 25-Nov-2014, 12:41:53 AM
    Author     : nause
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Appointment</title>
    </head>
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/>  
    <body>
        
        <br/> <a href="<%= request.getParameter("go_back")%>?reload">BACK</a> <br/><br/>

        <form method="post" action="EditAppointment">
             Start: <input type="text" name="start" value="<%= request.getParameter("start") != null ? request.getParameter("start") : "" %>"> <br/>
             End: <input type="text" name="end" value="<%= request.getParameter("end") != null ? request.getParameter("end") : "" %>"> <br/>
             Doctor: <input type="text" name="doctor" value="<%= request.getParameter("doctor") != null ? request.getParameter("doctor") : "" %>"> <br/>
             Diagnosis: <input type="text" name="diagnosis" value="<%= request.getParameter("diagnosis") != null ? request.getParameter("diagnosis") : "" %>"> <br/>
             Description: <input type="text" name="description" value="<%= request.getParameter("description") != null ? request.getParameter("description") : "" %>"> <br/>
             Cost: <input type="text" name="cost" value="<%= request.getParameter("cost") != null ? request.getParameter("cost") : "" %>"> <br/>
             <input type="text" name="go_back" value="<%= request.getParameter("go_back")%>" hidden="true">
             <input type="submit">
         </form>
    </body>
</html>
