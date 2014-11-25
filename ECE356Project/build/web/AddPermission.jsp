<%-- 
    Document   : AddPermission
    Created on : Nov 22, 2014, 2:16:15 PM
    Author     : Stuart Alldritt
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Permission</title>
    </head>
    
    <jsp:useBean id="permissions" class="java.util.ArrayList" scope="session"/>
    
    <body>
        
        <br/> <a href="<%= request.getParameter("go_back")%>?reload">BACK</a> <br/><br/>
        
        
        These users have permission to view this patient:
        <table border="1">
            <tr>
                <th>Permission</th>
                <th>Delete</th>
            </tr>
            <%
                ArrayList<String> list = permissions;

                for(int i = 0; i < list.size(); i++)
                {             
                   out.println("<tr><td>" + list.get(i) + "</td>");
                   out.println("<td><a href=\"AddPermission?go_back=" + request.getParameter("go_back") + "&action=delete&staff=" + list.get(i) + "&ohip=" + request.getParameter("ohip") +"\">DELETE</a></td>");
                   out.println("</tr>");
                }
            %>
        </table>
        
        <br/><br/>
        
        <form method="post" action="AddPermission">
             Patient: <%= request.getParameter("ohip") %> <input type="text" name="ohip" value="<%= request.getParameter("ohip") %>" hidden="true"> <br/>
             Doctor Username: <input type="text" name="staff"> <br/>
             <input type="text" name="action" value="add" hidden="true">
             <input type="text" name="go_back" value="<%= request.getParameter("go_back")%>" hidden="true">
             <input type="submit" value="Grant Access Permission">
         </form>
    </body>
</html>
