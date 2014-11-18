<%-- 
    Document   : VisitationRecords
    Created on : 17-Nov-2014, 9:11:01 PM
    Author     : Bo
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="models.Visit"%>
<%@page import="java.text.DateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Visitation Records</title>
    </head>
    
    
    
    
    <body>
        <h1>Visitation Records</h1>
        Show records
        <select name="filter">
            <option value="future">Upcoming Appointments</option>
            <option value="past">Past Visits</option>
            <option value="all">All</option>
        </select>
        
        <table>
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Doctor</th>
                    <th>Diagnosis</th>
                    <th>Description</th>
                    <th>Cost</th>
                    <th>Created Time</th>
                </tr>
            </thead>
            <tbody>
                <% ArrayList<Visit> visits = (ArrayList<Visit>) request.getSession().getAttribute("visits"); %>
                <%  for (int i = 0; i < visits.size(); i++) { %>
                <%  Visit v = visits.get(i); %>
                <tr>
                    
                    <td><%= DateFormat.getDateInstance().format(v.getStart_datetime()) %></td>
                    <td><%= DateFormat.getTimeInstance().format(v.getStart_datetime()) %></td>
                    <td><%= DateFormat.getTimeInstance().format(v.getEnd_datetime()) %></td>
                    <td><%= v.getDoctor_username() %></td>
                    <td><%= v.getDiagnosis() %></td>
                    <td><%= v.getProcedure_description() %></td>
                    <td><%= v.getProcedure_cost() %></td>
                    <td><%= DateFormat.getDateTimeInstance().format(v.getCreated_datetime()) %></td>
                </tr>
                <% } %>
            
            </tbody>
        </table>
    </body>
</html>
