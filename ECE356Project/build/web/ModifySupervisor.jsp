<%-- 
    Document   : ModifySupervisor
    Created on : Nov 24, 2014, 9:57:19 PM
    Author     : Stuart Alldritt
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
    <jsp:useBean id="supervisees" class="java.util.ArrayList" scope="session"/>
    
    <body>
        <h1>Modify Supervised Employees</h1>
        
        <br/> <a href="doctor_home.jsp">BACK</a> <br/><br/>
        
        
        You are currently supervising:
        <table border="1">
            <tr>
                <th>Supervisee</th>
                <th>Delete</th>
            </tr>
            <%
                ArrayList<String> list = supervisees;

                for(int i = 0; i < list.size(); i++)
                {             
                   out.println("<tr><td>" + list.get(i) + "</td>");
                   out.println("<td><a href=\"ModifySupervisor?go_back=" + request.getParameter("go_back") + "&action=delete&staff=" + list.get(i) + "\">DELETE</a></td>");
                   out.println("</tr>");
                }
            %>
        </table>
        
        <br/><br/>
        
        Add supervised staff:
        <form method="post" action="ModifySupervisor">
             Staff Username: <input type="text" name="staff"> <br/>
             <input type="text" name="action" value="add" hidden="true">
             <input type="text" name="go_back" value="<%= request.getParameter("go_back")%>?reload" hidden="true">
             <input type="submit">
         </form>   
    </body>
</html>
