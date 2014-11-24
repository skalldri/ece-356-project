<%-- 
    Document   : CreatePatient
    Created on : 21-Nov-2014, 11:18:49 PM
    Author     : stuart
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Patient</title>
    </head>
    <jsp:useBean id="userData" class="ece356.UserData" scope="session"/>  
    <body>
        
        <br/> <a href="<%= request.getParameter("go_back")%>?reload">BACK</a> <br/><br/>
        
        <form method="post" action="CreatePatient">
             Name: <input type="text" name="name" value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>"> <br/>
             Health card: <input type="text" name="ohip" value="<%= request.getParameter("ohip") != null ? request.getParameter("ohip") : "" %>"> <br/>
             Phone: <input type="text" name="phone" value="<%= request.getParameter("phone") != null ? request.getParameter("phone") : "" %>"> <br/>
             SIN: <input type="text" name="sin" value="<%= request.getParameter("sin") != null ? request.getParameter("sin") : "" %>"> <br/>
             Address: <input type="text" name="address" value="<%= request.getParameter("address") != null ? request.getParameter("address") : "" %>"> <br/>
             Health State: <input type="text" name="health_state" value="<%= request.getParameter("health_state") != null ? request.getParameter("health_state") : "" %>"> <br/>
             Comments: <input type="text" name="comments" value="<%= request.getParameter("comments") != null ? request.getParameter("comments") : "" %>"> <br/>
             Default doctor: <% if(!userData.getUserVariant().equals("STAFF")) { out.println(request.getParameter("default_doctor")); } %> <input <% if(!userData.getUserVariant().equals("STAFF")) { out.print(" hidden=\"true\" "); } %> type="text" name="default_doctor" value="<%=request.getParameter("default_doctor")%>"><br/>
             
             <input type="submit">
         </form>
    </body>
</html>
