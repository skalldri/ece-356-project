<%-- 
    Document   : doctor_home
    Created on : 18-Nov-2014, 2:56:02 PM
    Author     : stuart
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="models.Patient"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Homepage</title>
    </head>
    
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/>
    <jsp:useBean id="doctorData" class="models.DoctorData" scope="session"/>
    
    
    <body>
         <h1>Welcome <%= userData.getUsername() %></h1>
         
         <%
            ArrayList<Patient> list = doctorData.getPatients();
         %>
         
         My Patients:
         <form method="post" action="DoctorMain">
            Username: <input type="text" name="username"> <br/>
            Username: <input type="text" name="username"> <br/>
            <select name="patient">
            <% for(int i = 0; i < list.size(); i++)
               {             
                  out.println("<option value=\"" + list.get(i).getHealth_card() + "\">" + list.get(i).getName() + "</option>");
               } 
            %>
            </select>
            <br/>
            <input name="visits" value="Visitations" type="submit">
            <br/>
            <input name="record" value="General Record" type="submit">
         </form>            
    </body>
</html>
