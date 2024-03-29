/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package patient;

import databaseTools.Constants;
import ece356.AdaptableHttpRequest;
import ece356.UserData;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Patient;

/**
 *
 * @author Bo
 */
public class EditPatientInformation extends HttpServlet {

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
        
        String newAddress = request.getParameter("address");
        String newPhone = request.getParameter("phone");
        String ohip = ((UserData)request.getSession().getAttribute("userData")).getUsername();
        
        Patient oldPatient = PatientMain.retrievePatient(ohip);
        
        if (oldPatient.getAddress().equals(newAddress) &&
            oldPatient.getPhone_number().equals(newPhone))
        {
            // nothing changed
        }
        else
        {
            // make new patient and delete old one
            Patient newPatient = new Patient(
                    ohip, 
                    oldPatient.getName(), 
                    newAddress, 
                    newPhone, 
                    oldPatient.getSin(), 
                    oldPatient.getDefault_doctor_username(),
                    oldPatient.getPatient_health(), 
                    oldPatient.getCreate_datetime(), 
                    null, 
                    oldPatient.getComments(),
                    oldPatient.getPassword());
            
            java.util.Date now = new java.util.Date();
            String formattedDate = PatientMain.formatSqlDate(now);
            
            Statement stmt;
            Connection con;
            
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
                stmt = con.createStatement();
                                
                String modifyQuery = new StringBuilder().
                        append("UPDATE Patient SET deleted_datetime='").
                        append(PatientMain.formatSqlDate(now)).
                        append("' WHERE health_card='").
                        append(ohip).
                        append("' AND deleted_datetime ='0000-00-00 00:00:00'").
                        toString();
                
                int result = stmt.executeUpdate(modifyQuery);
                
                // if result != 1 then something bad has gone wrong
          
                String insertQuery = new StringBuilder().
                        append("INSERT INTO Patient (health_card, name, address, phone_number, "
                        + "sin, default_doctor_username, patient_health, created_datetime, password) VALUES ('").
                        append(ohip).
                        append("', '").
                        append(oldPatient.getName()).
                        append("', '").
                        append(newAddress).
                        append("', '").
                        append(newPhone).
                        append("', '").
                        append(oldPatient.getSin()).
                        append("', '").
                        append(oldPatient.getDefault_doctor_username()).
                        append("', '").
                        append(oldPatient.getPatient_health()).
                        append("', '").
                        append(PatientMain.formatSqlDate(oldPatient.getCreate_datetime())).
                        append("', '").
                        append(oldPatient.getPassword()).
                        append("')").
                        toString();
                
                stmt.executeUpdate(insertQuery);                
            }
            catch (Exception e)
            {
                PrintWriter out = response.getWriter();
                try {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>EditPatientInformation</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Exception occurred:" + e.toString() + "</h1>");
                    out.println("</body>");
                    out.println("</html>");
                } finally {            
                    out.close();
                }
                
                return;
            }
                
            if(request.getParameter("go_back") != null)
            {
                AdaptableHttpRequest addedRequest = new AdaptableHttpRequest(request);
                addedRequest.addParameter("reload", "true");

                request.getRequestDispatcher(request.getParameter("go_back")).forward(addedRequest, response);
                return;
            }
            
            request.getRequestDispatcher("PatientMain").forward(request, response);   
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
