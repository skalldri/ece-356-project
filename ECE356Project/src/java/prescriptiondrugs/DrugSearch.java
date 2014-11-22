/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prescriptiondrugs;

import databaseTools.Constants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Drug;

/**
 *
 * @author Bo
 */
public class DrugSearch extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        String name = request.getParameter("nameSearch");
        String minCost = request.getParameter("minCost");
        String maxCost = request.getParameter("maxCost");
        
        boolean searchForName = !name.equals("");
        boolean searchForMin = !minCost.equals("0");
        boolean searchForMax = !maxCost.equals("0");
        
        StringBuilder sb = new StringBuilder();
        
        sb = sb.append("SELECT * FROM Drug ");
        
        if (searchForName || searchForMin || searchForMax)
        {
            sb = sb.append("WHERE ");
            boolean firstParam = true;
            
            if (searchForName)
            {
                firstParam = false;
                sb = sb.append("drug_name='").
                        append(name.toUpperCase()).
                        append("' ");
            }
            
            if (searchForMin)
            {
                sb = sb.append(firstParam ? "" : "AND ").
                        append("cost >= ").
                        append(minCost).
                        append(" ");
                
                firstParam = false;
            }
            
            if (searchForMax)
            {
                sb = sb.append(firstParam ? "" : "AND ").
                        append("cost <= ").
                        append(maxCost).
                        append(" ");
            }
        }
        
        String query = sb.toString();
        List<Drug> drugs = new ArrayList<Drug>();
        
        Statement stmt;
        Connection con;
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) 
            {
                Drug drug = new Drug(
                        rs.getNString("drug_name"), 
                        rs.getDouble("cost"),
                        rs.getDate("created_datetime"));
                
                drugs.add(drug);
            }
        }
        
        catch(Exception e)
        {
            
        }
        
        request.getSession().setAttribute("drugSearchResults", drugs);
        request.getRequestDispatcher("DrugSearchResults.jsp").forward(request, response);
        
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DrugSearch</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DrugSearch at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
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
