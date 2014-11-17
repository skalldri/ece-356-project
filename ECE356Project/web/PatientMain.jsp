<%-- 
    Document   : PatientMain.jsp
    Created on : 13-Nov-2014, 10:16:52 PM
    Author     : Bo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Landing Page</title>
    </head>
    
    <jsp:useBean id="patient" class="models.Patient" scope="session"/>
    
    <body>
        <h1>Welcome <%= patient.getName() %></h1>
        
        <h2>Patient Info</h2>
        <table>
            <tr>
                <td>Health Card</td>
                <td><%= patient.getHealth_card() %></td>
            </tr>
            <tr>
                <td>Address</td>
                <td><%= patient.getAddress() %></td>
            </tr>
            <tr>
                <td>Phone Number</td>
                <td><%= patient.getPhone_number() %></td>
            </tr>
            <tr>
                <td>SIN</td>
                <td><%= patient.getSin() %></td>
            </tr>
            <tr>
                <td>Default Doctor</td>
                <td><%= patient.getDefault_doctor_username() %></td>
            </tr>
            <tr>
                <td>Indication of Current Health</td>
                <td><%= patient.getPatient_health() %></td>
            </tr>
            <tr>
                <td>Patient Since</td>
                <td><%= patient.getCreate_datetime() %></td>
            </tr>
        </table>
        
        
    </body>
</html>
