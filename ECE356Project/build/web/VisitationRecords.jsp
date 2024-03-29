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
        <script src="jquery-1.11.1.min.js"></script>
        <script> 
            function updateFilter()
            {
                var nowDate = new Date();
                var filter = $("select").val();
                
                $(".dataRows").each(function() {
                    //alert($("#start").text());
                    var dateString = $(this).find("#start").text();
                    var date = new Date(dateString);
                    
                    if (date < nowDate) {
                        if (filter == "past" || filter == "all") {
                            $(this).show();
                        }
                        else {
                            $(this).hide();
                        }
                    }
                    else {
                        if (filter == "future" || filter == "all") {
                            $(this).show();
                        }
                        else {
                            $(this).hide();
                        }
                    }
                });
            }
            
            
            $(document).ready(function(){
                updateFilter();
                
                $("select").change(function() {
                    updateFilter();
                });
            });
            
            
        </script>
        
    </head>
    
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/>
    
    <body>
        <h1>Visitation Records</h1>
        
        <br/> <a href="<%= request.getParameter("go_back")%>?reload">BACK</a> <br/><br/>
        
        <% if (!userData.getUserType().equals("patient")) { %>
        <br/> <a href="EditAppointment.jsp?go_back=VisitationRecords&health_card=<%=request.getParameter("patient")%><%= userData.getUserVariant().equals("DOCTOR") ? ("&doctor=" + userData.getUsername()) : "" %>">Create New</a> <br/><br/>
        <% } %>
        
        <br>
        Show records
        <select id="filter">
            <option value="future">Upcoming Appointments</option>
            <option value="past">Past Visits</option>
            <option value="all">All</option>
        </select>
        
        
        <table border="1" id="mainTable">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Doctor</th>
                    <th>Diagnosis</th>
                    <th>Description</th>
                    <th>Cost</th>
                    <th>Scheduling of Treatment</th>
                    <th>Edit</th>
                    <th>Delete</th>
                    <% if (!userData.getUserType().equals("patient")) { %>
                    <th>Create Prescription</th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
                <% ArrayList<Visit> visits = (ArrayList<Visit>) request.getSession().getAttribute("visits"); %>
                <%  for (int i = 0; i < visits.size(); i++) { %>
                <%  Visit v = visits.get(i); %>
                <tr class="dataRows">
                    <td><%= DateFormat.getDateInstance().format(v.getStart_datetime()) %></td>
                    <td><%= DateFormat.getTimeInstance().format(v.getStart_datetime()) %></td>
                    <td><%= DateFormat.getTimeInstance().format(v.getEnd_datetime()) %></td>
                    <td><%= v.getDoctor_username() %></td>
                    <td><%= v.getDiagnosis() %></td>
                    <td><%= v.getProcedure_description() %></td>
                    <td><%= v.getProcedure_cost() %></td>
                    <td><%= v.getScheduling_of_treatment() %></td>
                    <td id="start" style="display: none">
                        <%= (v.getStart_datetime().getYear() + 1900) + "-" +
                            (v.getStart_datetime().getMonth() + 1) + "-" +
                            v.getStart_datetime().getDate() + " " +
                            v.getStart_datetime().getHours() + ":" +
                            v.getStart_datetime().getMinutes() + ":" +
                            v.getStart_datetime().getSeconds()
                        %>
                    </td>
                    <% 
                    if(!userData.getUserType().equals("patient")){
                        out.println("<td><a href=\"EditAppointment.jsp?go_back=VisitationRecords&health_card=" + request.getParameter("patient") + "&start=" + v.getStart_datetime() + "&end=" + v.getEnd_datetime() + "&doctor=" + v.getDoctor_username() + "&diagnosis=" + v.getDiagnosis() + "&description=" + v.getProcedure_description() + "&cost=" + v.getProcedure_cost() + "&scheduling_of_treatment=" + v.getScheduling_of_treatment() + "&create=" + v.getCreated_datetime() + "\">Edit</a></td>");
                        out.println("<td><a href=\"VisitationRecords?go_back=PatientSearch&action=delete&patient=" + v.getHealth_card() + "&start=" + v.getStart_datetime() + "&doctor=" + v.getDoctor_username() + "\">Delete</a></td>");
                        out.println("<td><a href=\"NewPrescription.jsp?go_back=VisitationRecords&ohip=" + request.getParameter("patient") + "\">Create</a></td>");
                    } else {
                        out.println("<td>Not Available</td>");
                        out.println("<td>Not Available</td>");                                                 
                    }
                    %>
                </tr>
                <% } %>
            
            </tbody>
        </table>
    </body>
    
    
</html>
