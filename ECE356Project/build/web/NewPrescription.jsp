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
                //var param = window.location.search.replace("?", "");
                
                //var drugStart = param.indexOf("drug=") + 5;
                //var drugEnd = param.indexOf("&", drugStart);
                
                // case of did not find & after drug, can go to end of string
                //if (drugEnd < drugStart) {
                //    $("#drug_name").val(param.substr(drugStart));
                //}
                //else {
                //    $("#drug_name").val(param.substr(drugStart, drugEnd - drugStart));
                //}
                
                //var ohipStart = param.indexOf("ohip=") + 5;
                //var ohipEnd = param.indexOf("&", ohipStart);
                
                // case of did not find & after drug, can go to end of string
                //if (ohipEnd < ohipStart) {
                //    $("#health_card").val(param.substr(ohipStart));
                //}
                //else {
                //    $("#health_card").val(param.substr(ohipStart, ohipEnd - ohipStart));
                //}
                
                
                $("#drugSelect").click(function(e) {
                    e.preventDefault();
                    window.location.href = "DrugSearch.jsp?ohip=" + $("#health_card").val(); 
                });
            });
        </script>
        
    </head>
    <body>
        <h1>New Prescription</h1>
        <jsp:useBean id="mostRecentPrescription" class="models.Prescription" scope="session" />
        
        <br/> <a href="<%= request.getParameter("go_back")%>?reload">BACK</a> <br/><br/>
        
        <form method="post" action="NewPrescription">
            <table>
                <tr>
                    <td>Patient Health Card</td>
                    
                    <td><input type="text" name="health_card" id="health_card" value="<%= request.getParameter("ohip") != null ? request.getParameter("ohip") : "" %>" /></td>
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
                    
            <input type="submit" value="Create Prescription" />
        </form>
    </body>
</html>
