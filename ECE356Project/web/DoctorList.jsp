<%-- 
    Document   : DoctorList
    Created on : 24-Nov-2014, 1:58:19 AM
    Author     : Tomasz
--%>


<%@page import="java.util.ArrayList"%>
<%@page import="models.Staff"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title> View Doctors </title>

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
    
    <jsp:useBean id="doctors" class="java.util.ArrayList" scope="session"/>
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/>
    
    <body>
        <h1>Doctor List</h1>
        Search for a doctor <input type="text" id="search" placeholder="Type to search"> <br><br>
        <table border="1">
            <thead>
                <tr>
                    <th>Doctor Name</th>
                    <th>View Financial Page</th>
                </tr>
            </thead>
            <tbody id="mainTable">
                <%
                ArrayList<Staff> list = doctors;
                for(int i = 0; i < list.size(); i++)
                {             
                   out.println("<tr><td>" + list.get(i).get_username() + "</td>");
                   out.println("<td><a href=\"FinanceLists?page=doc1&doctor=" + list.get(i).get_username() + "\">View</a></td>");
                   out.println("</tr>");
                }
                %>
            </tbody>
        </table>
    </body>
</html>
