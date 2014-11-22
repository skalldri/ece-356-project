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
    </head>
    <body>
        <h1>Drug Search Results</h1>
        
        <table>
            <tr>
                <th>Name</th>
                <th>Cost</th>
            </tr>
            <% ArrayList<Drug> drugs = (ArrayList<Drug>) request.getSession().getAttribute("drugSearchResults"); %>
            <% for (Drug d : drugs) { %>
            <tr>
                <td><%= d.getDrug_name() %></td>
                <td><%= d.getCost() %></td>
            </tr>
            
            <% } %>
        </table>
    </body>
</html>
