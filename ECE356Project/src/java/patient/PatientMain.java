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
        
        String ohip = request.getParameter("ohip");
        
        Patient patient = retrievePatient(ohip);
        
        request.getSession().setAttribute("patient", patient);
        
        request.getRequestDispatcher("PatientMain.jsp").forward(request, response);
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PatientMain</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PatientMain at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    private Patient retrievePatient(String ohip)
    {
        Patient p = null;
        
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            
            
            String query = new StringBuilder().append("SELECT * FROM Patients WHERE health_card = '").
                    append(ohip).
                    append("' AND deleted_datetime IS NULL LIMIT 1").
                    toString();
            
            ResultSet result = stmt.executeQuery(query);
            
            
            con.close();
            
            if (!result.next())
                return p;
                
            
            p = new Patient(
                    ohip,
                    result.getString("name"), 
                    result.getString("address"), 
                    result.getString("phone_number"), 
                    result.getString("sin"), 
                    result.getString("default_doctor_username"), 
                    result.getString("patient_health"), 
                    result.getDate("create_datetime"), 
                    result.getString("deleted_datetime"), 
                    result.getString("comments"));
            
        }
        catch(Exception e) 
        {
            return p;
        }
        
        return p;
        
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