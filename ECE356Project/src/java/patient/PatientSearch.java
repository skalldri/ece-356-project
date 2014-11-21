/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package patient;

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
import javax.servlet.http.HttpSession;
import models.Patient;

/**
 *
 * @author Stuart Alldritt
 */
public class PatientSearch extends HttpServlet {

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
        
        HttpSession session = request.getSession();
        
        ArrayList<Patient> resultingPatients = new ArrayList<Patient>();
        
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            String query = new StringBuilder().
                    append("SELECT *, name LIKE %").
                    append(request.getParameter("name")).
                    append(", address LIKE '%").
                    append(request.getParameter("address")).
                    append("%', phone_number LIKE ").
                    append(request.getParameter("phone")).
                    append("%', sin LIKE %").
                    append(request.getParameter("sin")).
                    append("%' FROM Patient WHERE deleted_datetime = '0000-00-00 00:00:00'").
                    toString();
            
            ResultSet result = stmt.executeQuery(query);

            while(result.next())
            {
                Patient p = new Patient(
                        result.getNString("health_card"),
                        result.getNString("name"), 
                        result.getNString("address"), 
                        result.getNString("phone_number"), 
                        result.getNString("sin"), 
                        result.getNString("default_doctor_username"), 
                        result.getNString("patient_health"), 
                        result.getTimestamp("created_datetime"), 
                        result.getNString("comments"),
                        result.getNString("password"));
            }
            
            session.setAttribute("resultingPatients", resultingPatients);
            
            con.close();
            
        }
        catch(Exception e) 
        {
        }
        
        request.getRequestDispatcher("PatientSearch.jsp").forward(request, response);
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
