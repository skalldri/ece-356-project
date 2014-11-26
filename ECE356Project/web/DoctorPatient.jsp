<%-- 
    Document   : DoctorPatient
    Created on : 25-Nov-2014, 4:45:08 AM
    Author     : Tomasz
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="models.Visit"%>
<%@page import="models.Prescription"%>
<%@page import="java.text.DateFormat"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title> Doctor record </title>
    </head>
    
    <jsp:useBean id="patientVisits" class="java.util.ArrayList" scope="session"/>  
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/>
    <jsp:useBean id="doctor" class="java.lang.String" scope="session"/>
    <jsp:useBean id="ohip" class="java.lang.String" scope="session"/> 
    <jsp:useBean id="numpVisits" type="Integer" scope="session"/>
    <jsp:useBean id="prescriptions" class="java.util.ArrayList" scope="session"/>    
    
    
    
    <body>
        <h1>Patient OHIP #: <%=ohip%></h1> <br>
             
        Times seen by doctor <%=doctor%>: <%out.println(numpVisits);%><br><br>
        Visitations and procedures: <br>
        <table border="1">
            <thead>
                <tr>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Diagnosis</th>
                    <th>Procedure Description</th>
                    <th>Procedure Cost</th>
                </tr>
            </thead>
            <tbody id ="visitTable">
                <%
                ArrayList<Visit> list = patientVisits;
                for(int i = 0; i < list.size(); i++)
                {             
                   out.println("<tr><td>" + list.get(i).getStart_datetime() + "</td>");
                   out.println("<td>" + list.get(i).getEnd_datetime() + "</td>");
                   out.println("<td>" + list.get(i).getDiagnosis() + "</td>");
                   out.println("<td>" + list.get(i).getProcedure_description() + "</td>");
                   out.println("<td>" + list.get(i).getProcedure_cost() + "</td>");
                   out.println("</tr>");
                }
                %>
            </tbody>
        </table> <br>
        
        Prescription's from <%=doctor%>:
        <table border="1">
            <thead>
                <tr>
                    <th>Drug Name</th>
                    <th>Refills</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                </tr>
            </thead>
            <tbody id ="prescriptionTable">
                <%
                ArrayList<Prescription> plist = prescriptions;
                for(int i = 0; i < plist.size(); i++)
                {    
                   out.println("<tr><td>" + plist.get(i).getDrug_name() + "</td>");
                   out.println("<td>" + plist.get(i).getRefills() + "</td>");
                   out.println("<td>" + plist.get(i).getStart_datetime() + "</td>");
                   out.println("<td>" + plist.get(i).getEnd_datetime() + "</td>");
                   out.println("</tr>");
                }
                %>
            </tbody>
        </table>
    </body>
</html>
