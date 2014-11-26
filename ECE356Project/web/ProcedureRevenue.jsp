<%-- 
    Document   : ProcedureRevenue
    Created on : 24-Nov-2014, 5:03:46 AM
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
    <title> Procedures </title>

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
    
    <jsp:useBean id="procedures" class="java.util.ArrayList" scope="session"/>
    <jsp:useBean id="procRevenue" type="Double" scope="session"/>
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/>   
    
    <body>
        <h1>Procedure Revenue</h1> <br>
        Select period:<br>
        <%out.println("<form method=\"post\" action=\"FinanceLists?page=procedure\">");%>
            From:
            <input type="date" name="start">
            To:
            <input type="date" name="end">
            <input type="submit" value="Update">
        </form> <br>
        Total revenue from procedures in this period: <%out.println(procRevenue);%> <br>
        Filter procedures: 
        <input type="text" id="search" placeholder="Enter filter"> <br>
        <table border="1">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Doctor</th>
                    <th>Patient Health Card</th>
                    <th>Procedure Description</th>
                    <th>Procedure Cost</th>
                </tr>
            </thead>
            <tbody id ="mainTable">
                <%
                ArrayList<Visit> list = procedures;
                for(int i = 0; i < list.size(); i++)
                {             
                   out.println("<tr><td>" + DateFormat.getDateInstance().format(list.get(i).getStart_datetime()) + "</td>");
                   out.println("<td>" + DateFormat.getTimeInstance().format(list.get(i).getStart_datetime()) + "</td>");
                   out.println("<td>" + DateFormat.getTimeInstance().format(list.get(i).getEnd_datetime()) + "</td>");
                   out.println("<td>" + list.get(i).getDoctor_username() + "</td>");
                   out.println("<td>" + list.get(i).getHealth_card() + "</td>");
                   out.println("<td>" + list.get(i).getProcedure_description() + "</td>");
                   out.println("<td>" + list.get(i).getProcedure_cost() + "</td>");
                   out.println("</tr>");
                }
                %>
            </tbody>
        </table>
    </body>
</html>
