/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package staff;

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
import models.Visit;

/**
 *
 * @author Tomasz
 */
public class FinanceLists extends HttpServlet {

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
        if(request.getSession().getAttribute("userData") == null)
        {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        
        HttpSession session = request.getSession(); 
        String doctor = request.getParameter("doctor");
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String page = request.getParameter("page");
        String ohip = request.getParameter("patient");
        
        if(page.equals("procedure")){
            ArrayList<Visit> procedures = fetchVisits(page);
            session.setAttribute("procedures", procedures);
            double procRevenue = fetchRevenue(page);            
            session.setAttribute("procRevenue", procRevenue);     
            request.getRequestDispatcher("ProcedureRevenue.jsp").forward(request, response);
        }
        else if(page.equals("visit")){
                ArrayList<Visit> visitVisits = fetchVisits(page);
                session.setAttribute("visitVisits", visitVisits);
                double visitRevenue = fetchRevenue(page);
                session.setAttribute("visitRevenue", visitRevenue);
                request.getRequestDispatcher("VisitRevenue.jsp").forward(request, response);
        }
        else if (page.equals("insurance")){
                ArrayList<Visit> insuranceVisits = fetchVisits(page);
                session.setAttribute("insuranceVisits", insuranceVisits);
                double insuranceRevenue = fetchRevenue(page);
                session.setAttribute("insuranceRevenue", insuranceRevenue);
                request.getRequestDispatcher("InsuranceBilling.jsp").forward(request, response);
        }
        else if (page.equals("doc2")){
            session.setAttribute("doctor", doctor);
            session.setAttribute("ohip", ohip);
            ArrayList<Visit> docVisits = fetchDoctorVisits(doctor,start,end);
            session.setAttribute("docVisits", docVisits);
            double docRevenue = fetchDoctorRevenue(doctor, start, end);
            session.setAttribute("docRevenue", docRevenue);                 
            request.getRequestDispatcher("DoctorFinance.jsp").forward(request, response);
        }
        else if (page.equals("docfin")){
            session.setAttribute("doctor", doctor);
            session.setAttribute("ohip", ohip);
            ArrayList<Visit> patientVisits = fetchPatientVisits(ohip, doctor, start, end);
            session.setAttribute("patientVisits", patientVisits);
            int numpVisits = fetchNumPVisits(ohip, doctor, start, end);
            //check for procedures and visits?
            session.setAttribute("numpVisits", numpVisits);
            double docRevenue = fetchDoctorRevenue(doctor, start, end);
            session.setAttribute("docRevenue", docRevenue);                 
            request.getRequestDispatcher("DoctorPatient.jsp").forward(request, response);
        }
        else{
            //ArrayList<Visit> docVisits = fetchDoctorVisits(doctor,start,end);
            //session.setAttribute("docVisits", docVisits);
            session.setAttribute("docRevenue", 0.0);
            session.setAttribute("doctor", doctor);
            session.setAttribute("docVisits", new ArrayList<Visit>());
            request.getRequestDispatcher("DoctorFinance.jsp").forward(request, response);
        }
    }
    
    private int fetchNumPVisits(String ohip, String doctor, String start, String end) {
        int count = 0;              
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            String query = new StringBuilder().
                append("SELECT COUNT(*) FROM Visit WHERE deleted_datetime = '0000-00-00 00:00:00' AND health_card = '").
                append(ohip).
                append("' AND doctor_username = '").
                append(doctor).
                append("'").
                toString();
            ResultSet result = stmt.executeQuery(query);
            while (result.next())
            {             
                count += result.getInt(1);
            }
               
            con.close();          
        }
        catch(Exception e) 
        {
            return count;
        }       
        return count;
    }
    
    
    private ArrayList<Visit> fetchPatientVisits(String ohip, String doctor, String start, String end) {
        ArrayList<Visit> visits = new ArrayList<Visit>();               
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
           String query = new StringBuilder().
                    append("SELECT * FROM Visit WHERE doctor_username = '").
                    append(doctor).
                    append("' AND deleted_datetime = '0000-00-00 00:00:00' AND health_card = '").
                    append(ohip).
                    append("'").
                    toString();
            
            ResultSet result = stmt.executeQuery(query);

            while (result.next())
            {             
                Double cost = result.getDouble("procedure_cost");
                visits.add(
                        new Visit(
                            result.getNString("doctor_username"), 
                            result.getTimestamp("start_datetime"),
                            result.getTimestamp("end_datetime"), 
                            result.getNString("health_card"), 
                            result.getNString("diagnosis"), 
                            result.getNString("procedure_description"), 
                            cost,
                            result.getNString("scheduling_of_treatment"), 
                            result.getTimestamp("created_datetime"),
                            result.getTimestamp("created_datetime")
                        ));
            }     
            con.close();          
        }
        catch(Exception e) 
        {
            return visits;
        }       
        return visits;
    }
    
    private double fetchRevenue(String page) {
        Double cost = 0.0;              
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            String query = new StringBuilder().toString();
            
            if(page.equals("procedure")||page.equals("insurance")){
                query = new StringBuilder().
                    append("SELECT SUM(procedure_cost) FROM Visit WHERE deleted_datetime = '0000-00-00 00:00:00' AND procedure_description IS NOT NULL").
                    toString();
                ResultSet result = stmt.executeQuery(query);
                while (result.next())
                {             
                    cost += result.getDouble(1);
                } 
            }
            
            if (page.equals("visit")||page.equals("insurance")) {
                query = new StringBuilder().
                    append("SELECT COUNT(*) FROM Visit WHERE deleted_datetime = '0000-00-00 00:00:00' AND procedure_description IS NULL").
                    toString();
                ResultSet result = stmt.executeQuery(query);
                while (result.next())
                {             
                    cost += 100*result.getDouble(1);
                } 
            }             
               
            con.close();          
        }
        catch(Exception e) 
        {
            return cost;
        }       
        return cost;
    }
    
    
    private ArrayList<Visit> fetchVisits(String page) {
        ArrayList<Visit> visits = new ArrayList<Visit>();               
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            String query = new StringBuilder().toString();
                    
            if(page.equals("procedure")){
                query = new StringBuilder().
                    append("SELECT * FROM Visit WHERE deleted_datetime = '0000-00-00 00:00:00' AND procedure_description IS NOT NULL").
                    toString();
            }
            
            else if (page.equals("visit")) {
                query = new StringBuilder().
                    append("SELECT * FROM Visit WHERE deleted_datetime = '0000-00-00 00:00:00' AND procedure_description IS NULL").
                    toString();
            }
            
            else if (page.equals("insurance")) {
                query = new StringBuilder().
                    append("SELECT * FROM Visit WHERE deleted_datetime = '0000-00-00 00:00:00'").
                    toString();
            }
             
            ResultSet result = stmt.executeQuery(query);

            while (result.next())
            {             
                Double cost = result.getDouble("procedure_cost");
                visits.add(
                        new Visit(
                            result.getNString("doctor_username"), 
                            result.getTimestamp("start_datetime"),
                            result.getTimestamp("end_datetime"), 
                            result.getNString("health_card"), 
                            result.getNString("diagnosis"), 
                            result.getNString("procedure_description"), 
                            cost,
                            result.getNString("scheduling_of_treatment"), 
                            result.getTimestamp("created_datetime"),
                            result.getTimestamp("created_datetime")
                        ));
            }     
            con.close();          
        }
        catch(Exception e) 
        {
            return visits;
        }       
        return visits;
    }
    
    private double fetchDoctorRevenue(String doctor, String start, String end) {
        Double cost = 0.0;              
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();

            String query = new StringBuilder().
                    append("SELECT SUM(procedure_cost) FROM Visit WHERE doctor_username = '").
                    append(doctor).
                    append("' AND deleted_datetime = '0000-00-00 00:00:00' AND start_datetime >= '").
                    append(start).
                    append(" 00:00:00").
                    append("' AND start_datetime <= '").
                    append(end).
                    append(" 23:59:59' AND procedure_description IS NOT NULL").
                    toString();
             
            ResultSet result = stmt.executeQuery(query);

            while (result.next())
            {             
                cost = result.getDouble(1);
            }
            
            query = new StringBuilder().
                    append("SELECT COUNT(*) FROM Visit WHERE doctor_username = '").
                    append(doctor).
                    append("' AND deleted_datetime = '0000-00-00 00:00:00' AND start_datetime >= '").
                    append(start).
                    append(" 00:00:00").
                    append("' AND start_datetime <= '").
                    append(end).
                    append(" 23:59:59' AND procedure_description IS NULL").
                    toString();
            
            result = stmt.executeQuery(query);

            while (result.next())
            {             
                cost += 100*result.getDouble(1);
            }    
            con.close();          
        }
        catch(Exception e) 
        {
            return cost;
        }       
        return cost;
    }

    
    private ArrayList<Visit> fetchDoctorVisits(String doctor, String start, String end) {
        ArrayList<Visit> visits = new ArrayList<Visit>();               
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
           String query = new StringBuilder().
                    append("SELECT * FROM Visit WHERE doctor_username = '").
                    append(doctor).
                    append("' AND deleted_datetime = '0000-00-00 00:00:00' AND start_datetime >= '").
                    append(start).
                    append(" 00:00:00").
                    append("' AND start_datetime <= '").
                    append(end).
                    append(" 23:59:59").
                    append("'").
                    toString();
            
            ResultSet result = stmt.executeQuery(query);

            while (result.next())
            {             
                Double cost = result.getDouble("procedure_cost");
                visits.add(
                        new Visit(
                            result.getNString("doctor_username"), 
                            result.getTimestamp("start_datetime"),
                            result.getTimestamp("end_datetime"), 
                            result.getNString("health_card"), 
                            result.getNString("diagnosis"), 
                            result.getNString("procedure_description"), 
                            cost,
                            result.getNString("scheduling_of_treatment"), 
                            result.getTimestamp("created_datetime"),
                            result.getTimestamp("created_datetime")
                        ));
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
