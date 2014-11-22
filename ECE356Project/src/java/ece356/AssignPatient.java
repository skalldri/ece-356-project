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
import java.sql.Statement;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Patient;
import patient.PatientMain;

/**
 *
 * @author Stuart Alldritt
 */
public class AssignPatient extends HttpServlet {

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
            Enumeration<String> test = request.getParameterNames();
            String ohip = request.getParameter("ohip");
            Patient oldPatient = PatientMain.retrievePatient(request.getParameter("ohip"));
            java.util.Date now = new java.util.Date();
            String formattedDate = PatientMain.formatSqlDate(now);
            
            Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
                stmt = con.createStatement();
                                
                String modifyQuery = new StringBuilder().
                        append("UPDATE Patient SET deleted_datetime='").
                        append(PatientMain.formatSqlDate(now)).
                        append("' WHERE health_card='").
                        append(oldPatient.getHealth_card()).
                        append("' AND deleted_datetime ='0000-00-00 00:00:00'").
                        toString();
                
                int result = stmt.executeUpdate(modifyQuery);
                
                // if result != 1 then something bad has gone wrong
          
                String insertQuery = new StringBuilder().
                        append("INSERT INTO Patient (health_card, name, address, phone_number, "
                        + "sin, default_doctor_username, patient_health, created_datetime, password) VALUES ('").
                        append(oldPatient.getHealth_card()).
                        append("', '").
                        append(oldPatient.getName()).
                        append("', '").
                        append(oldPatient.getAddress()).
                        append("', '").
                        append(oldPatient.getPhone_number()).
                        append("', '").
                        append(oldPatient.getSin()).
                        append("', '").
                        append(request.getParameter("username")).
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
        catch(Exception e) 
        {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                /* TODO output your page here. You may use following sample code. */
                out.println("<html>");
                out.println("<head>");
                out.println("<title>AssignPatient</title>");            
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
        
        request.getRequestDispatcher("doctor_home.jsp").forward(request, response);
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
