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
            Start: <input type="date" name="startdate" value="<%= request.getParameter("start") != null ? request.getParameter("start").substring(0, 10) : "" %>"> 
            <input type="time" name="starttime" value="<%= request.getParameter("start") != null ? request.getParameter("start").substring(11) : "" %>"><br/>
            End: <input type="date" name="enddate" value="<%= request.getParameter("end") != null ? request.getParameter("end").substring(0, 10) : "" %>"> 
            <input type="time" name="endtime" value="<%= request.getParameter("end") != null ? request.getParameter("end").substring(11) : "" %>"><br/>
            Doctor: <input type="text" name="doctor_username" value="<%= request.getParameter("doctor") != null ? request.getParameter("doctor") : "" %>"> <br/>
            Diagnosis: <input type="text" name="diagnosis" value="<%= request.getParameter("diagnosis") != null ? request.getParameter("diagnosis") : "" %>"> <br/>
            Description: <input type="text" name="procedure_description" value="<%= request.getParameter("description") != null ? request.getParameter("description") : "" %>"> <br/>
            Cost: <input type="text" name="procedure_cost" value="<%= request.getParameter("cost") != null ? request.getParameter("cost") : "" %>"> <br/>
            Scheduling of Treatment: <input type="text" name="scheduling_of_treatment" value="<%= request.getParameter("scheduling_of_treatment") != null ? request.getParameter("scheduling_of_treatment") : "" %>"> <br/>
            
            <input type="text" name="health_card" hidden="true" value="<%= request.getParameter("health_card") != null ? request.getParameter("health_card") : "" %>">
            <input type="date" name="createdate" hidden="true" value="<%= request.getParameter("create") != null ? request.getParameter("create").substring(0, 10) : "" %>">
            <input type="time" name="createtime" hidden="true" value="<%= request.getParameter("create") != null ? request.getParameter("create").substring(11) : "" %>">
            <input type="text" name="go_back" value="<%= request.getParameter("go_back")%>" hidden="true">
            <input type="submit">
         </form>
    </body>
</html>
