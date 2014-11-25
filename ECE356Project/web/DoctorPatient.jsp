<%-- 
    Document   : DoctorPatient
    Created on : 25-Nov-2014, 4:45:08 AM
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
    
    <jsp:useBean id="patientVisits" class="java.util.ArrayList" scope="session"/>  
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/>
    <jsp:useBean id="doctor" class="java.lang.String" scope="session"/>
    <jsp:useBean id="ohip" class="java.lang.String" scope="session"/> 
    <jsp:useBean id="numpVisits" type="Integer" scope="session"/>
    
    
    <body>
        <h1>Patient OHIP #: <%=ohip%></h1> <br>
             
        Times seen by doctor <%=doctor%>: <%out.println(numpVisits);%><br><br>
        Filter visits and procedures: 
        <input type="text" id="search" placeholder="Enter filter"><br>
        <table border="1">
            <thead>
                <tr>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Diagnosis</th>
                    <th>Procedure Description</th>
                    <th>Procedure Cost</th>
                    <th>PRESCRIPTIONNNN</th>
                </tr>
            </thead>
            <tbody id ="mainTable">
                <%
                ArrayList<Visit> list = patientVisits;
                for(int i = 0; i < list.size(); i++)
                {             
                   out.println("<td>" + list.get(i).getStart_datetime() + "</td>");
                   out.println("<td>" + list.get(i).getEnd_datetime() + "</td>");
                   out.println("<td>" + list.get(i).getDiagnosis() + "</td>");
                   out.println("<td>" + list.get(i).getProcedure_description() + "</td>");
                   out.println("<td>" + list.get(i).getProcedure_cost() + "</td>");
                   out.println("<td> lots of drugs huehue </td>");
                   out.println("</tr>");
                }
                %>
            </tbody>
        </table>
    </body>
</html>
