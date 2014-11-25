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
        
        <%
        if(userData.getUserVariant().equals("DOCTOR"))
        {
            out.println("<br/> <a href=\"PatientSearchStart.jsp?go_back=doctor_home.jsp\">BACK</a> <br/><br/>");
        } else if(userData.getUserVariant().equals("STAFF"))
        {
            out.println("<br/> <a href=\"PatientSearchStart.jsp?go_back=StaffMain.jsp\">BACK</a> <br/><br/>");
        }
        %>
        
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
                <th>Edit Patient Data</th>
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
                   out.println("<td>" + (list.get(i).getDeleted_datetime() != null ? list.get(i).getDeleted_datetime() : "STILL VALID") + "</td>");
                   if(list.get(i).editable)
                   {
                       out.println("<td><a href=\"VisitationRecords?go_back=PatientSearch&patient=" + list.get(i).getHealth_card() + "\">View</a></td>");
                       out.println("<td><a href=\"PrescriptionRecords?go_back=PatientSearch&ohip=" + list.get(i).getHealth_card() + "\">View</a></td>");
                       if(userData.getUserVariant().equals("DOCTOR"))
                       {
                           out.println("<td><a href=\"AddPermission?go_back=PatientSearch&ohip=" + list.get(i).getHealth_card() + "\">Add</a></td>");
                           out.println("<td><a href=\"AssignPatient.jsp?go_back=PatientSearch&patient=" + list.get(i).getHealth_card() + "\">Assign</a></td>");
                       } else {
                           out.println("<td>Not Available</td>");
                           out.println("<td>Not Available</td>");
                       }
                       
                       out.println("<td><a href=\"CreatePatient.jsp?go_back=PatientSearch&ohip=" + list.get(i).getHealth_card() + "&name=" + list.get(i).getName() + "&address=" + list.get(i).getAddress() + "&phone=" + list.get(i).getPhone_number() + "&health_state=" + list.get(i).getPatient_health() + "&sin=" + list.get(i).getSin() + "&default_doctor=" + list.get(i).getDefault_doctor_username() + "&comments=" + list.get(i).getComments() + "\">Edit</a></td>");
                   } else {
                       out.println("<td>Not Available</td>");
                       out.println("<td>Not Available</td>");
                       out.println("<td>Not Available</td>");
                       out.println("<td>Not Available</td>");
                       out.println("<td>Not Available</td>");
                   }
                   out.println("</tr>");
                }
            %>
        </table>
    </body>
</html>
