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
         <br/><br/><br/>
         <a href="CreatePatient.jsp">Create New Patient</a>
         <br/><br/><br/>
         Patient Search:
         <form method="post" action="PatientSearch">
             Search all patients: <input name="all_patients" type="checkbox"> <br/>
             Show deleted records: <input name="deleted_records" type="checkbox"> <br/>
             Name: <input type="text" name="name"> <br/>
             Health card: <input type="text" name="ohip"> <br/>
             Phone: <input type="text" name="phone"> <br/>
             SIN: <input type="text" name="sin"> <br/>
             Last Visit date: <input type="text" name="date"> <br/>
             <input type="submit">
         </form>   
    </body>
</html>
