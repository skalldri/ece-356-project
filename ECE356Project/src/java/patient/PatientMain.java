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
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Patient;

/**
 *
 * @author Bo
 */
public class PatientMain extends HttpServlet {

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
        
        if(request.getSession().getAttribute("userData") == null || !((UserData)request.getSession().getAttribute("userData")).getUserType().equals("patient"))
        {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        
        //String ohip = request.getParameter("ohip"); //Should also be able to retreive ((UserData)request.getSession().getAttribute("userData")).getUsername()
        String ohip = ((UserData)request.getSession().getAttribute("userData")).getUsername();
        
        Patient patient = retrievePatient(ohip);
        
        request.getSession().setAttribute("patient", patient);
        
        request.getRequestDispatcher("PatientMain.jsp").forward(request, response);
    }

    public static Patient retrievePatient(String ohip)
    {
        Patient p = null;
        
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            String query = new StringBuilder().
                    append("SELECT * FROM Patient WHERE health_card = '").
                    append(ohip).
                    append("' AND deleted_datetime = '0000-00-00 00:00:00' LIMIT 1").
                    toString();
            
            ResultSet result = stmt.executeQuery(query);

            if (!result.next())
                return p;

            p = new Patient(
                    ohip,
                    result.getNString("name"), 
                    result.getNString("address"), 
                    result.getNString("phone_number"), 
                    result.getNString("sin"), 
                    result.getNString("default_doctor_username"), 
                    result.getNString("patient_health"), 
                    result.getTimestamp("created_datetime"), 
                    result.getTimestamp("deleted_datetime"), 
                    result.getNString("comments"),
                    result.getNString("password"));
            
            con.close();
            
        }
        catch(Exception e) 
        {
            String exception = e.toString();
            return new Patient();
        }
        
        return p;
        
    }
    
    public static String formatSqlDate(java.util.Date toFormat)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(toFormat);
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
