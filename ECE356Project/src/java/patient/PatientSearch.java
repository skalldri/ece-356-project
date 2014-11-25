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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Patient;
import models.PatientSearchData;

/**
 *
 * @author stuart
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
        
        //If user is logged in
        if(request.getSession().getAttribute("userData") == null || !((UserData)request.getSession().getAttribute("userData")).getUserType().equals("staff"))
        {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        
        ArrayList<Patient> resultingPatients = new ArrayList<Patient>();
        
        String username = ((UserData)request.getSession().getAttribute("userData")).getUsername();
        String userVariant = ((UserData)request.getSession().getAttribute("userData")).getUserVariant();
        
        
        PatientSearchData myData;
        
        if(request.getParameter("reload") != null)
        {
            myData = (PatientSearchData)request.getSession().getAttribute("patientSearchData");
            if(myData == null)
            {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                try {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Error</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Cannot reload search results: no previous results found</h1>");
                    out.println("</body>");
                    out.println("</html>");
                } finally {            
                    out.close();
                }
                return;
            }
        }
        else
        {
            myData = new PatientSearchData();
            myData.all_patients = (request.getParameter("all_patients") != null);
            myData.deleted_records = (request.getParameter("deleted_records") != null);        
            myData.name = request.getParameter("name");
            myData.phone = request.getParameter("phone");
            myData.comments = request.getParameter("comments");
            myData.diagnosis = request.getParameter("diagnosis");
            myData.sin = request.getParameter("sin");
            myData.lastVisitStart = request.getParameter("start_datetime");
            myData.lastVisitEnd = request.getParameter("end_datetime");
            
            request.getSession().setAttribute("patientSearchData", myData);
        }
        
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            String default_doctor = "";
            String deleted_records = "";
            String start_date = "";
            String end_date = "";
            String date = "";
            boolean editable = false;
            
            if(!myData.all_patients)
            {
                editable = true;
                default_doctor = " AND (default_doctor_username = '" + username + "' OR health_card in (SELECT health_card from Staff_Permissions WHERE username = '" + username + "' AND deleted_datetime = '0000-00-00 00:00:00'))";
            }
            
            if(!myData.deleted_records)
            {
                deleted_records = " AND deleted_datetime = '0000-00-00 00:00:00'";
            }
            
            if(!myData.lastVisitEnd.isEmpty())
            {
                start_date = " AND start_datetime <= '" + myData.lastVisitEnd + " 23:59:59" + "'";
            }
            
            if(!myData.lastVisitStart.isEmpty())
            {
                end_date = " AND start_datetime >= '" + myData.lastVisitStart + " 00:00:00" + "'";
            }
            
            if(!myData.lastVisitEnd.isEmpty() || !myData.lastVisitStart.isEmpty())
            {
                //TODO: Fix query to use specific query for Visit table in default_doctor's place
                date = " AND EXISTS (SELECT start_datetime FROM Visit WHERE deleted_datetime = '0000-00-00 00:00:00'" + default_doctor + start_date + end_date + ")";
            }
            
            String query = new StringBuilder().
                    append("SELECT * FROM Patient WHERE name LIKE '%").
                    append(myData.name).
                    append("%' AND phone_number LIKE '%").
                    append(myData.phone).
                    append("%' AND comments LIKE '%").
                    append(myData.comments).
                    append("%' AND patient_health LIKE '%").
                    append(myData.diagnosis).
                    append("%' AND sin LIKE '%").
                    append(myData.sin).
                    append("%'").
                    append(deleted_records).
                    append(default_doctor).
                    toString();
            
            if(userVariant.equals("STAFF"))
            {               
                query = new StringBuilder().
                    append("SELECT * FROM Patient WHERE (default_doctor_username in (SELECT supervisor_username from Supervisor WHERE staff_username = '").
                    append(username).    
                    append("' AND deleted_datetime = '0000-00-00 00:00:00') OR health_card in (SELECT health_card from Staff_Permissions WHERE username = '").
                    append(username).
                    append("' AND deleted_datetime = '0000-00-00 00:00:00')) AND name LIKE '%").
                    append(request.getParameter("name")).
                    append("%' AND phone_number LIKE '%").
                    append(request.getParameter("phone")).
                    append("%' AND comments LIKE '%").
                    append(request.getParameter("comments")).
                    append("%' AND patient_health LIKE '%").
                    append(request.getParameter("diagnosis")).
                    append("%' AND sin LIKE '%").
                    append(request.getParameter("sin")).
                    append("%'").
                    append(deleted_records).
                    toString();
            }
            
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
                        result.getTimestamp("deleted_datetime"),
                        result.getNString("comments"),
                        result.getNString("password"));
                
                p.editable = editable;
                
                resultingPatients.add(p);
            }
            
            session.setAttribute("resultingPatients", resultingPatients);
            
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
                out.println("<title>PatientSearch</title>");            
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
