<%-- 
    Document   : DrugSearch
    Created on : 22-Nov-2014, 2:07:04 PM
    Author     : Bo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Drug Search</title>
        <script src="jquery-1.11.1.min.js"></script>
        <script>
            $(document).ready(function() {
                var param = window.location.search.replace("?", "");
                var ohip = param.substr(5); // chop off "ohip="
                $("#patient").val(ohip);
            });
        </script>
        
    </head>
    <body>
        <h1>Find a Drug</h1>
        <br/> <a href="<%= request.getParameter("go_back")%>?reload">BACK</a> <br/><br/>
        
        <a href="NewDrug.jsp?go_back=DrugSearch.jsp">Enter New Drug</a>
        
        <div id="searchArea">
            <h2>Search Parameters</h2>
            <form method="post" action="DrugSearch">
                <table>
                    <tr>
                        <th>
                            Field
                        </th>
                        <th>
                            Value
                        </th>
                    </tr>
                    
                    <tr>
                        <td>Name</td>
                        <td><input type="text" name="nameSearch" /></td>                    
                    <tr>
                        <td>Min Cost</td>
                        <td><input type="number" name="minCost" min="0" value="0"/></td>
                    </tr>
                    <tr>
                        <td>Max Cost</td>
                        <td><input type="number" name="maxCost" min="0" value="0"/></td>
                    </tr>
                </table>
                <input type="hidden" name="patient" id="patient" value="" />
                <input type="submit" value="Search" />
            </form>
        </div>
    </body>
</html>
