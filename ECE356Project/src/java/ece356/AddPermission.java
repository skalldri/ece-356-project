/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

import databaseTools.Constants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import patient.PatientMain;

/**
 *
 * @author Stuart Alldritt
 */
public class AddPermission extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //If user is logged in
        if(request.getSession().getAttribute("userData") == null || !((UserData)request.getSession().getAttribute("userData")).getUserType().equals("staff"))
        {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
    
        Statement stmt;
        Connection con;
        try
        {
            
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            if(request.getParameter("action") == null) //First page load
            {
                // Do nothing
            } 
            else if(request.getParameter("action").equals("delete"))
            {
                java.util.Date now = new java.util.Date();
                String formattedDate = PatientMain.formatSqlDate(now);

                String modifyQuery = new StringBuilder().
                            append("UPDATE Staff_Permissions SET deleted_datetime = '").
                            append(PatientMain.formatSqlDate(now)).
                            append("' WHERE username = '").
                            append(request.getParameter("staff")).
                            append("' AND health_card = '").
                            append(request.getParameter("ohip")).
                            append("' AND deleted_datetime ='0000-00-00 00:00:00'").
                            toString();

                int result = stmt.executeUpdate(modifyQuery);
            } 
            else if(request.getParameter("action").equals("add"))
            {
                String insertQuery = new StringBuilder().
                        append("INSERT INTO Staff_Permissions (username, health_card, permission_level) VALUES ('").
                        append(request.getParameter("staff")).
                        append("', '").
                        append(request.getParameter("ohip")).
                        append("', '").
                        append("0").
                        append("')").
                        toString();
            
                stmt.executeUpdate(insertQuery);
            }
            
            String query = new StringBuilder().
                    append("SELECT * FROM Staff_Permissions WHERE health_card = '").
                    append(request.getParameter("ohip")).
                    append("' AND deleted_datetime = '0000-00-00 00:00:00'").
                    toString();
            
            ResultSet result = stmt.executeQuery(query);
            ArrayList<String> permissions = new ArrayList<String>();
            
            while(result.next())
            {
                permissions.add(result.getNString("username"));
            }
            
            request.getSession().setAttribute("permissions", permissions);
            
            con.close();    
        }
        catch(Exception e) 
        {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                /* TODO output your page here. You may use following sample code. */
                out.println("<html>");
                out.println("<head>");
                out.println("<title>AddPermission</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Exception occurred: " + e.toString() + "</h1>");
                out.println("</body>");
                out.println("</html>");
            } finally {            
                out.close();
            }
            return;
        }
        
        AdaptableHttpRequest r = new AdaptableHttpRequest(request);
        
        request.getRequestDispatcher("AddPermission.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
