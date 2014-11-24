<%-- 
    Document   : DrugSearchResults
    Created on : 22-Nov-2014, 2:59:09 PM
    Author     : Bo
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="models.Drug"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Results</title>
        
        <script src="jquery-1.11.1.min.js"></script>
        <script>
            $(document).ready(function() {
                $(".prescribe").click(function(){
                    var row = $(this).parent().parent();
                    
                    var drug_name = row.find("#name").text();
                    
                    window.location.href = "NewPrescription.jsp?drug=" + drug_name + 
                        "&ohip=" + $("#patient").val();
                });
                
                var $rows = $('table .dataRows');
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
    <body>
        <h1>Drug Search Results</h1>
        
        <input type="text" id="search" placeholder="Filter more">
        
        <input type="hidden" id="patient" value="<%=  request.getSession().getAttribute("prescriptionPatient") %>" />
        <table>
            <tr>
                <th>Name</th>
                <th>Cost</th>
                <th></th>
            </tr>
            <% ArrayList<Drug> drugs = (ArrayList<Drug>) request.getSession().getAttribute("drugSearchResults"); %>
            <% for (Drug d : drugs) { %>
            <tr class="dataRows">
                <td id="name"><%= d.getDrug_name() %></td>
                <td id="cost"><%= d.getCost() %></td>
                <td><button class="prescribe">Prescribe</button></td>
            </tr>
            
            <% } %>
        </table>
    </body>
</html>
