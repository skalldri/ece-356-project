<%-- 
    Document   : NewPrescription
    Created on : 22-Nov-2014, 4:01:17 PM
    Author     : Bo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Prescription</title>
        
        <script src="jquery-1.11.1.min.js"></script>
        <script>
            $(document).ready(function() {           
                $("#drugSelect").click(function(e) {
                    e.preventDefault();
                    window.location.href = "DrugSearch.jsp?go_back=NewPrescription.jsp&ohip=" + $("#health_card").val(); 
                });
            });
        </script>
        
    </head>
    <body>
        <h1>New Prescription</h1>
        <jsp:useBean id="mostRecentPrescription" class="models.Prescription" scope="session" />
        
        <%
        if(request.getParameter("reload") == null)
        {
            request.getSession().setAttribute("new_prescription_go_back", request.getParameter("go_back"));
        }
        %>
        
        <br/> <a href="<%= request.getParameter("reload") != null ? (String)request.getSession().getAttribute("new_prescription_go_back") : request.getParameter("go_back")%>?reload">BACK</a> <br/><br/>
        
        <form method="post" action="NewPrescription">
            <table>
                <tr>
                    <td>Patient Health Card</td>
                    
                    <td><input type="text" name="health_card" id="health_card" value="<%= request.getParameter("ohip") != null && !request.getParameter("ohip").equals("null") ? request.getParameter("ohip") : "" %>" /></td>
                </tr>
                <tr>
                    <td>Drug Name</td>
                    <td><input type="text" name="drug_name" id="drug_name" value="<%= request.getParameter("drug") != null ? request.getParameter("drug") : "" %>" /></td>
                    <td><button id="drugSelect">Select Drug</button></td>
                </tr>
                <tr>
                    <td>Refills</td>
                    <td><input type="number" min="0" name="refills" value="0" /></td>
                </tr>
                <tr>
                    <td>Start Date</td>
                    <td><input type="date" name="start_datetime" /></td>
                </tr>
                <tr>
                    <td>End Date</td>
                    <td><input type="date" name="end_datetime" /></td>
                </tr>
            </table>
            <input type="text" name="go_back" value="<%= request.getParameter("reload") != null ? (String)request.getSession().getAttribute("new_prescription_go_back") : request.getParameter("go_back")%>" hidden="true">        
            <input type="submit" value="Create Prescription" />
        </form>
    </body>
</html>
