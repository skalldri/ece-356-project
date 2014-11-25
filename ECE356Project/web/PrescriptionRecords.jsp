<%-- 
    Document   : PrescriptionRecords
    Created on : 24-Nov-2014, 2:30:43 AM
    Author     : Bo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Prescription"%>
<%@page import="java.text.DateFormat"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prescription Records</title>
        
        <script src="jquery-1.11.1.min.js"></script>
        <script> 
            function updateFilter()
            {
                var nowDate = new Date();
                var filter = $("select").val();
                
                $(".dataRows").each(function() {
                    //alert($("#start").text());
                    var dateString = $(this).find("#end").text() + " 23:59:59";
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
    <body>
        <h1>Prescription Records</h1>
    
        <br/> <a href="<%= request.getParameter("go_back")%>?reload">BACK</a> <br/><br/>
        
        Show records
        <select id="filter">
            <option value="future">Unfinished Prescriptions</option>
            <option value="past">Finished Prescriptions</option>
            <option value="all">All</option>
        </select>
        
        
        <table>
            <thead>
                <tr>
                    <th>Prescriber</th>
                    <th>Patient</th>
                    <th>Drug Name</th>
                    <th>Refills</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                </tr>
            </thead>
            <tbody>
                <% ArrayList<Prescription> prescriptions = (ArrayList<Prescription>) request.getSession().getAttribute("prescriptions"); %>
                <%  for (Prescription p : prescriptions) { %>
                    <tr class="dataRows">
                        <td><%= p.getDoctor_username() %></td>
                        <td><%= p.getHealth_card() %></td>
                        <td><%= p.getDrug_name() %></td>
                        <td><%= p.getRefills() %></td>
                        <td><%= DateFormat.getDateInstance().format(p.getStart_datetime()) %></td>
                        <td><%= DateFormat.getDateInstance().format(p.getEnd_datetime()) %></td>
                        <td id="end" style="display: none">
                            <%= (p.getEnd_datetime().getYear() + 1900) + "-" +
                                (p.getEnd_datetime().getMonth() + 1) + "-" +
                                p.getEnd_datetime().getDate()
                            %>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </body>
</html>
