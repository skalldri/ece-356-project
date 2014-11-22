<%-- 
    Document   : NewDrug
    Created on : 22-Nov-2014, 1:47:49 PM
    Author     : Bo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Drug</title>
        <script src="jquery-1.11.1.min.js"></script>
        <script>
            $(document).ready(function() {
                $("#submitButton").click(function(e){
                    var name = $("#drug_name").val().trim();
                    var cost = $("#cost").val();
                    
                $("#drug_name").val(name);
                    
                if (name == "") {
                    e.preventDefault();
                    $("#message").text("Drug name can't be empty");
                }
                else if (isNaN(cost) || cost < 0) {
                    e.preventDefault();
                    $("#message").text("cost must be a positive number");
                }
                else {
                    $("message").text("");
                }
                    
                });
            });
        </script>
    </head>
    <body>
        <h1>Enter New Drug Info</h1>
        <div id="message">
            
        </div>
        <form method="post" action="NewDrug">
            Drug Name:
            <input type="text" name="drug_name" id="drug_name" />
            <br>
            
            Cost
            <input type="number" name="cost" id="cost" value="0" min="0"/>
            
            <br>
            <input type="submit" id="submitButton" />
            
        </form>
        
    </body>
</html>
