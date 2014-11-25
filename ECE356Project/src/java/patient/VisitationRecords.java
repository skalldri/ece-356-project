/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package patient;

import databaseTools.Constants;
import ece356.UserData;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Patient;
import models.Visit;

/**
 *
 * @author Bo
 */
public class VisitationRecords extends HttpServlet {

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
        
        //If user is logged in
        if(request.getSession().getAttribute("userData") == null)
        {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        
        String ohip;
        if(((UserData)request.getSession().getAttribute("userData")).getUserType().equals("patient"))
        {
            ohip = ((UserData)request.getSession().getAttribute("userData")).getUsername();
        }
        else // We're a doctor looking for a patient's records
        {
            ohip = request.getParameter("patient");
        }
        
        List<Visit> visits = fetchVisits(ohip);
        
        request.getSession().setAttribute("visits", visits);
        
        request.getRequestDispatcher("VisitationRecords.jsp").forward(request, response);
    }

    public static Visit retrieveVisit(String doctor_username, Timestamp start_datetime, Timestamp end_datetime, String health_card, String diagnosis, String procedure_description, Double procedure_cost, String scheduling_of_treatment, Timestamp created_datetime) {
        Visit visit = null;
        
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            String query = new StringBuilder().
                    append("SELECT * FROM Visit WHERE doctor_username = '").
                    append(doctor_username).
                    append("' AND start_datetime = '").
                    append(start_datetime.toString()).
                    append("' AND end_datetime = '").
                    append(end_datetime.toString()).
                    append("' AND health_card = '").
                    append(health_card).
                    append("' AND diagnosis = '").
                    append(diagnosis).
                    append("' AND procedure_description = '").
                    append(procedure_description).
                    append("' AND procedure_cost = '").
                    append(procedure_cost).
                    append("' AND scheduling_of_treatment = '").
                    append(scheduling_of_treatment).
                    append("' AND created_datetime = '").
                    append(created_datetime.toString()).
                    append("' AND deleted_datetime = '0000-00-00 00:00:00' LIMIT 1").
                    toString();
            
            ResultSet result = stmt.executeQuery(query);

            if (!result.next())
                return visit;

            visit = new Visit(
                    result.getNString("doctor_username"),
                    result.getTimestamp("start_datetime"), 
                    result.getTimestamp("end_datetime"),
                    result.getNString("health_card"),
                    result.getNString("diagnosis"),
                    result.getNString("procedure_description"),
                    result.getDouble("procedure_cost"),
                    result.getNString("scheduling_of_treatment"),
                    result.getTimestamp("created_datetime"),
                    result.getTimestamp("deleted_datetime"));
            
            con.close();
            
        }
        catch(Exception e) 
        {
            String exception = e.toString();
            return new Visit(null, null, null, null, null, null, null, null, null, null);
        }
        
        return visit;
    }
    
    private List<Visit> fetchVisits(String ohip) {
        List<Visit> visits = new ArrayList<Visit>();
                 
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            String query = new StringBuilder().
                    append("SELECT * FROM Visit WHERE health_card = '").
                    append(ohip).
                    append("' AND deleted_datetime = '0000-00-00 00:00:00'").
                    toString();
            
            ResultSet result = stmt.executeQuery(query);

            while (result.next())
            {                
                visits.add(
                        new Visit(
                            result.getNString("doctor_username"), 
                            result.getTimestamp("start_datetime"),
                            result.getTimestamp("end_datetime"), 
                            ohip, 
                            result.getNString("diagnosis"), 
                            result.getNString("procedure_description"), 
                            result.getDouble("procedure_cost"),
                            result.getNString("scheduling_of_treatment"), 
                            result.getTimestamp("created_datetime"),
                            result.getTimestamp("created_datetime"))
                        );
            }
            
            con.close();
            
        }
        catch(Exception e) 
        {
            return visits;
        }
        
        return visits;
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
