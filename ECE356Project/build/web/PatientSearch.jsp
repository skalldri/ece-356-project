<%-- 
    Document   : PatientSearch
    Created on : Nov 21, 2014, 2:24:05 PM
    Author     : Stuart Alldritt
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="models.Patient"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Search Results</title>
    </head>
    
    <jsp:useBean id="resultingPatients" class="java.util.ArrayList" scope="session"/>
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/>
    
    <body>
        
        <table border="1">
            <tr>
                <th>Name</th>
                <th>Health Card</th>
                <th>Address</th>
                <th>Phone</th>
                <th>SIN</th>
                <th>Comments</th>
                <th>Health</th>
                <th>Invalid as of</th>
                <th>Visit Records</th>
                <th>Prescription Records</th>
                <th>Add Permissions</th>
                <th>Reassign Patient</th>
            </tr>
            <%
                ArrayList<Patient> list = resultingPatients;

                for(int i = 0; i < list.size(); i++)
                {             
                   out.println("<tr><td>" + list.get(i).getName() + "</td>");
                   out.println("<td>" + list.get(i).getHealth_card() + "</td>");
                   out.println("<td>" + list.get(i).getAddress() + "</td>");
                   out.println("<td>" + list.get(i).getPhone_number() + "</td>");
                   out.println("<td>" + list.get(i).getSin() + "</td>");
                   out.println("<td>" + list.get(i).getComments() + "</td>");
                   out.println("<td>" + list.get(i).getPatient_health() + "</td>");
                   out.println("<td>" + list.get(i).getDeleted_datetime() + "</td>");
                   if(list.get(i).editable)
                   {
                       out.println("<td><a href=\"VisitationRecords?patient=" + list.get(i).getHealth_card() + "\">View</a></td><td><a href=\"PatientMain\">View</a></td><td><a href=\"AddPermission.jsp?patient=" + list.get(i).getHealth_card() + "\">Add</a></td><td><a href=\"AssignPatient.jsp?patient=" + list.get(i).getHealth_card() + "\">Assign</a></td>");
                   }
                   out.println("</tr>");
                }
            %>
        </table>
    </body>
</html>
