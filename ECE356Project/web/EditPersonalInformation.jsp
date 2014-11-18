<%-- 
    Document   : EditPersonalInformation
    Created on : 17-Nov-2014, 9:10:50 PM
    Author     : Bo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Information</title>
    </head>
    
    <jsp:useBean id="patient" class="models.Patient" scope="session"/>
    
    <body>
        <h1>Edit Information</h1>
        
        <form method="post" action="EditPatientInformation">
            Address: 
            <input type="text" name="address" class="fields" value="<%= patient.getAddress() %>"/>
            <br>
            
            Phone Number:
            <input type="text" name="phone" class="fields" value="<%= patient.getPhone_number() %>"/>
            <br>
            
            <input type="submit" id="submitButton" disabled="true" />
        </form>
    </body>

    <script type="text/javascript">
        $(function() {
            $(".fields").onchange(function() {
                document.getElementById("submitButton").disabled = false;
            });
        });
    </script>
</html>

