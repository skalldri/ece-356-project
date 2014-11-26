<%-- 
    Document   : DoctorFinance
    Created on : 24-Nov-2014, 5:03:12 AM
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
    <title> Doctor record </title>

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
    
    <jsp:useBean id="docVisits" class="java.util.ArrayList" scope="session"/>  
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/>
    <jsp:useBean id="doctor" class="java.lang.String" scope="session"/> 
    <jsp:useBean id="docRevenue" type="Double" scope="session"/>
    <jsp:useBean id="docPtRevenue" type="Double" scope="session"/>
    <jsp:useBean id="patientsViewed" type="Integer" scope="session"/>
    
    
    <body>
        <h1>Doctor <%=doctor%>'s financial page</h1> <br>
        Select period:<br>
        <%out.println("<form method=\"post\" action=\"FinanceLists?page=doc2&doctor="+doctor+"\">");%>
            From:
            <input type="date" name="start">
            To:
            <input type="date" name="end">
            <input type="submit" value="Update">
        </form> <br>
             
        Revenue earned from procedures and visits: <%out.println(docRevenue);%><br>
        Revenue earned from prescriptions: <%out.println(docPtRevenue);%><br>
        Number of patients examined: <%out.println(patientsViewed);%><br>
        Filter patient visits and procedures: 
        <input type="text" id="search" placeholder="Enter filter"> <br>
        <table border="1">
            <thead>
                <tr>
                    <th>Start Datetime</th>
                    <th>End Datetime</th>
                    <th>Patient Health Card</th>
                    <th>Diagnosis</th>
                    <th>Procedure Description</th>
                    <th>Procedure Cost</th>
                    <th>View More Patient Information</th>
                </tr>
            </thead>
            <tbody id ="mainTable">
                <%
                ArrayList<Visit> list = docVisits;
                for(int i = 0; i < list.size(); i++)
                {             
                   out.println("<tr><td>" + list.get(i).getStart_datetime() + "</td>");
                   out.println("<td>" + list.get(i).getEnd_datetime() + "</td>");
                   out.println("<td>" + list.get(i).getHealth_card() + "</td>");
                   out.println("<td>" + list.get(i).getDiagnosis() + "</td>");
                   out.println("<td>" + list.get(i).getProcedure_description() + "</td>");
                   out.println("<td>" + list.get(i).getProcedure_cost() + "</td>");
                   out.println("<td><a href=\"FinanceLists?page=docfin&doctor=" + doctor + "&patient="+ list.get(i).getHealth_card() + "\">View</a></td>");
                   out.println("</tr>");
                }
                %>
            </tbody>
        </table>
    </body>
</html>
