<%-- 
    Document   : InsuranceBilling
    Created on : 24-Nov-2014, 5:04:16 AM
    Author     : Tomasz
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="models.Visit"%>
<%@page import="java.text.DateFormat"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title> Insurance Billing </title>

    <script type='text/javascript' src='http://code.jquery.com/jquery-1.7.1.js'></script>
    <script type='text/javascript'>
    $(window).load(function(){
    var $rows = $('#mainTable tr');
    $('#search').keyup(function() {
      var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();

      $rows.show().filter(function() {
          var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
          return !~text.indexOf(val);
      }).hide();
    });
    }); 

    </script>
    </head>
    
    <jsp:useBean id="insuranceVisits" class="java.util.ArrayList" scope="session"/>  
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/>
    <jsp:useBean id="insuranceRevenue" type="Double" scope="session"/>
    
    <body>
        <h1>Monthly visitation and procedure revenue</h1> <br>
        Total revenue from visits and procedures for billing: $<%out.println(insuranceRevenue);%> <br>
        Filter visit and procedure list: 
        <input type="text" id="search" placeholder="Enter filter"> <br>
        <table border="1">
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
            <tbody id ="mainTable">   
                <% ArrayList<Visit> list = insuranceVisits; %>
                <%  for (int i = 0; i < list.size(); i++) { %>
                <%  Visit v = list.get(i); %>
                <tr class="dataRows">
                    <td><%= DateFormat.getDateInstance().format(v.getStart_datetime()) %></td>
                    <td><%= DateFormat.getTimeInstance().format(v.getStart_datetime()) %></td>
                    <td><%= DateFormat.getTimeInstance().format(v.getEnd_datetime()) %></td>
                    <td><%= v.getDoctor_username() %></td>
                    <td><%= v.getDiagnosis() %></td>
                    <td><%= v.getProcedure_description() %></td>
                    <td><%= v.getProcedure_cost() %></td>
                    <td><%= DateFormat.getDateTimeInstance().format(v.getCreated_datetime()) %></td>
                    <td id="start" style="display: none">
                        <%= (v.getStart_datetime().getYear() + 1900) + "-" +
                            (v.getStart_datetime().getMonth() + 1) + "-" +
                            v.getStart_datetime().getDate() + " " +
                            v.getStart_datetime().getHours() + ":" +
                            v.getStart_datetime().getMinutes() + ":" +
                            v.getStart_datetime().getSeconds()
                        %>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </body>
</html>
