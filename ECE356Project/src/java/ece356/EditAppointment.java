/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

import databaseTools.Constants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Patient;
import models.Visit;
import patient.PatientMain;
import patient.VisitationRecords;

/**
 *
 * @author nause
 */
public class EditAppointment extends HttpServlet {

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
        
        String username = ((UserData)request.getSession().getAttribute("userData")).getUsername();         
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            if(request.getParameter("default_doctor") != null)
            {
                username = request.getParameter("default_doctor");
            }
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Timestamp start_datetime = new java.sql.Timestamp(dateFormat.parse(request.getParameter("startdate") + " " + request.getParameter("starttime")).getTime());
            Timestamp end_datetime = new java.sql.Timestamp(dateFormat.parse(request.getParameter("enddate") + " " + request.getParameter("endtime")).getTime());
            Timestamp created_datetime = new java.sql.Timestamp(dateFormat.parse(request.getParameter("createdate") + " " + request.getParameter("createtime")).getTime());
            
            String doctor_username = request.getParameter("doctor_username");
            String health_card = request.getParameter("health_card");
            String diagnosis = request.getParameter("diagnosis") != null ? request.getParameter("diagnosis") : "";
            String procedure_description = request.getParameter("procedure_description") != null ? request.getParameter("procedure_description") : "";
            Double procedure_cost = Double.valueOf(request.getParameter("procedure_cost"));
            String scheduling_of_treatment = request.getParameter("scheduling_of_treatment") != null ? request.getParameter("scheduling_of_treatment") : "";
            
            Visit visit = VisitationRecords.retrieveVisit(doctor_username, start_datetime);
                        
            String modifyQuery = new StringBuilder().
                        append("UPDATE Visit SET deleted_datetime='").
                        append(PatientMain.formatSqlDate(new java.util.Date())).
                        append("' WHERE doctor_username = '").
                        append(visit.getDoctor_username()).
                        append("' AND start_datetime = '").
                        append(visit.getStart_datetime().toString()).
                        append("' AND deleted_datetime ='0000-00-00 00:00:00'").
                        toString();
                
            stmt.executeUpdate(modifyQuery);
            
            String insertQuery = new StringBuilder().
                        append("INSERT INTO Visit (doctor_username, start_datetime, end_datetime, health_card, diagnosis, procedure_description, procedure_cost, scheduling_of_treatment, created_datetime, deleted_datetime) VALUES ('").
                        append(doctor_username).
                        append("', '").
                        append(start_datetime.toString().substring(0, 19)).
                        append("', '").
                        append(end_datetime.toString().substring(0, 19)).
                        append("', '").
                        append(health_card).
                        append("', '").
                        append(diagnosis).
                        append("', '").
                        append(procedure_description).
                        append("', '").
                        append(procedure_cost).
                        append("', '").
                        append(scheduling_of_treatment).
                        append("', '").
                        append(PatientMain.formatSqlDate(new java.util.Date())).
                        append("', '").    
                        append("0000-00-00 00:00:00").
                        append("')").
                        toString();
            
            stmt.executeUpdate(insertQuery);
            
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
                out.println("<title>Edit Appointment</title>");            
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
        
        if(request.getParameter("go_back") != null)
        {
            AdaptableHttpRequest addedRequest = new AdaptableHttpRequest(request);
            addedRequest.addParameter("reload", "true");
            
            request.getRequestDispatcher(request.getParameter("go_back")).forward(addedRequest, response);
            return;
        }
        
        if(((UserData)request.getSession().getAttribute("userData")).getUserVariant().equals("STAFF"))
        {
            request.getRequestDispatcher("StaffMain.jsp").forward(request, response);
        }
        else if (((UserData)request.getSession().getAttribute("userData")).getUserVariant().equals("DOCTOR"))
        {
            request.getRequestDispatcher("StaffMain.jsp").forward(request, response);
        } else
        {
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
