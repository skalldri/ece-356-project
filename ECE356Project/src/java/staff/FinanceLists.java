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
import models.Prescription;

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
            ArrayList<Visit> procedures = fetchVisits(page, start, end);
            session.setAttribute("procedures", procedures);
            double procRevenue = fetchRevenue(page, start, end);            
            session.setAttribute("procRevenue", procRevenue);     
            request.getRequestDispatcher("ProcedureRevenue.jsp").forward(request, response);
        }
        else if(page.equals("visit")){
                ArrayList<Visit> visitVisits = fetchVisits(page, start, end);
                session.setAttribute("visitVisits", visitVisits);
                double visitRevenue = fetchRevenue(page, start, end);
                session.setAttribute("visitRevenue", visitRevenue);
                request.getRequestDispatcher("VisitRevenue.jsp").forward(request, response);
        }
        else if (page.equals("insurance")){
                ArrayList<Visit> insuranceVisits = fetchVisits(page, start, end);
                session.setAttribute("insuranceVisits", insuranceVisits);
                double insuranceRevenue = fetchRevenue(page, start, end);
                session.setAttribute("insuranceRevenue", insuranceRevenue);
                double insurancePtRevenue = fetchPtInsuranceRevenue(start, end);
                session.setAttribute("insurancePtRevenue", insurancePtRevenue);                
                request.getRequestDispatcher("InsuranceBilling.jsp").forward(request, response);
        }
        else if (page.equals("doc2")){
            session.setAttribute("doctor", doctor);
            session.setAttribute("ohip", ohip);
            ArrayList<Visit> docVisits = fetchDoctorVisits(doctor, start, end);
            session.setAttribute("docVisits", docVisits);
            double docRevenue = fetchDoctorVPRevenue(doctor, start, end);
            session.setAttribute("docRevenue", docRevenue);
            double docPtRevenue = fetchPtDocRevenue(doctor, start, end);
            session.setAttribute("docPtRevenue", docPtRevenue);
            int patientsViewed = fetchPatientsViewed(doctor, start, end);
            session.setAttribute("patientsViewed", patientsViewed);
            request.getRequestDispatcher("DoctorFinance.jsp").forward(request, response);
        }
        else if (page.equals("docfin")){
            session.setAttribute("doctor", doctor);
            session.setAttribute("ohip", ohip);
            ArrayList<Visit> patientVisits = fetchPatientVisits(ohip, doctor);
            session.setAttribute("patientVisits", patientVisits);
            int numpVisits = fetchNumPVisits(ohip, doctor);
            session.setAttribute("numpVisits", numpVisits);
            ArrayList<Prescription> prescriptions = fetchPrescriptions(ohip, doctor);
            session.setAttribute("prescriptions", prescriptions);
            //double docRevenue = fetchDoctorVPRevenue(doctor, start, end);
            //session.setAttribute("docRevenue", docRevenue);
            //double docPtRevenue = fetchPtDocRevenue(doctor, start, end);
            //session.setAttribute("docPtRevenue", docPtRevenue);
            //int patientsViewed = fetchPatientsViewed(doctor, start, end);
           // session.setAttribute("patientsViewed", patientsViewed);
            request.getRequestDispatcher("DoctorPatient.jsp").forward(request, response);
        }
        else{
            //ArrayList<Visit> docVisits = fetchDoctorVisits(doctor,start,end);
            //session.setAttribute("docVisits", docVisits);
            session.setAttribute("docRevenue", 0.0);
            session.setAttribute("docPtRevenue", 0.0);
            session.setAttribute("patientsViewed", 0);
            session.setAttribute("doctor", doctor);
            session.setAttribute("docVisits", new ArrayList<Visit>());
            request.getRequestDispatcher("DoctorFinance.jsp").forward(request, response);
        }
    }
    
    private int fetchNumPVisits(String ohip, String doctor) {
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
    
    
    private ArrayList<Visit> fetchPatientVisits(String ohip, String doctor) {
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
                visits.add(
                        new Visit(
                            result.getNString("doctor_username"), 
                            result.getTimestamp("start_datetime"),
                            result.getTimestamp("end_datetime"), 
                            result.getNString("health_card"), 
                            result.getNString("diagnosis"), 
                            result.getNString("procedure_description"), 
                            result.getDouble("procedure_cost"),
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
    
    private ArrayList<Prescription> fetchPrescriptions(String ohip, String doctor) {
        ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();               
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
           String query = new StringBuilder().
                    append("SELECT * FROM Prescription WHERE doctor_username = '").
                    append(doctor).
                    append("' AND deleted_datetime = '0000-00-00 00:00:00' AND health_card = '").
                    append(ohip).
                    append("'").
                    toString();
            
            ResultSet result = stmt.executeQuery(query);

            while (result.next())
            {             
                prescriptions.add(
                        new Prescription(
                            result.getNString("doctor_username"),  
                            result.getNString("health_card"), 
                            result.getNString("drug_name"),
                            new Integer(result.getInt("refills")),
                            result.getDate("start_datetime"),
                            result.getDate("end_datetime")
                        ));
            }     
            con.close();          
        }
        catch(Exception e) 
        {
            return prescriptions;
        }       
        return prescriptions;
    }
    
    private double fetchRevenue(String page, String start, String end) {
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
                    append("SELECT SUM(procedure_cost) FROM Visit WHERE deleted_datetime = '0000-00-00 00:00:00' AND procedure_cost > '0.0'").
                    append("AND start_datetime >= '").
                    append(start).
                    append(" 00:00:00").
                    append("' AND start_datetime <= '").
                    append(end).
                    append(" 23:59:59' AND procedure_cost > '0.0'").
                    toString();
                ResultSet result = stmt.executeQuery(query);
                while (result.next())
                {             
                    cost += result.getDouble(1);
                } 
            }
            
            if (page.equals("visit")||page.equals("insurance")) {
                query = new StringBuilder().
                    append("SELECT COUNT(*) FROM Visit WHERE deleted_datetime = '0000-00-00 00:00:00'").
                    append("AND start_datetime >= '").
                    append(start).
                    append(" 00:00:00").
                    append("' AND start_datetime <= '").
                    append(end).
                    append(" 23:59:59'").
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
    
    private double fetchPtInsuranceRevenue(String start, String end) {
        Double cost = 0.0;              
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            String query = new StringBuilder().
                    append("SELECT SUM(cost) FROM Prescription a, Drug b WHERE a.drug_name = b.drug_name AND a.deleted_datetime = '0000-00-00 00:00:00'").
                    append("AND a.start_datetime >= '").
                    append(start).
                    append(" 00:00:00").
                    append("' AND a.start_datetime <= '").
                    append(end).
                    append(" 23:59:59'").
                    toString();
                ResultSet result = stmt.executeQuery(query);
                while (result.next())
                {             
                    cost += result.getDouble(1);
                } 
               
            con.close();          
        }
        catch(Exception e) 
        {
            return cost;
        }       
        return cost;
    }
    
    private double fetchPtDocRevenue(String doctor, String start, String end) {
        Double cost = 0.0;              
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            String query = new StringBuilder().
                    append("SELECT SUM(cost) FROM Prescription a, Drug b WHERE a.drug_name = b.drug_name AND a.deleted_datetime = '0000-00-00 00:00:00' AND a.doctor_username = '").
                    append(doctor).
                    append("' AND a.start_datetime >= '").
                    append(start).
                    append(" 00:00:00").
                    append("' AND a.start_datetime <= '").
                    append(end).
                    append(" 23:59:59'").
                    toString();
                ResultSet result = stmt.executeQuery(query);
                while (result.next())
                {             
                    cost += result.getDouble(1);
                } 
               
            con.close();          
        }
        catch(Exception e) 
        {
            return cost;
        }       
        return cost;
    }
    
    private int fetchPatientsViewed(String doctor, String start, String end) {
        int count = 0;              
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            String query = new StringBuilder().
                    append("SELECT COUNT(DISTINCT health_card) FROM Visit WHERE deleted_datetime = '0000-00-00 00:00:00' AND doctor_username = '").
                    append(doctor).
                    append("' AND start_datetime >= '").
                    append(start).
                    append(" 00:00:00' AND start_datetime <= '").
                    append(end).
                    append(" 23:59:59'").
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
    
    
    private ArrayList<Visit> fetchVisits(String page, String start, String end) {
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
                    append("SELECT * FROM Visit WHERE deleted_datetime = '0000-00-00 00:00:00'").
                    append("AND start_datetime >= '").
                    append(start).
                    append(" 00:00:00").
                    append("' AND start_datetime <= '").
                    append(end).
                    append(" 23:59:59' AND procedure_cost > '0.0'").
                    toString();
            }
            
            else  {
                query = new StringBuilder().
                    append("SELECT * FROM Visit WHERE deleted_datetime = '0000-00-00 00:00:00'").
                    append("AND start_datetime >= '").
                    append(start).
                    append(" 00:00:00").
                    append("' AND start_datetime <= '").
                    append(end).
                    append(" 23:59:59'").
                    toString();
            }
             
            ResultSet result = stmt.executeQuery(query);

            while (result.next())
            {
                visits.add(
                        new Visit(
                            result.getNString("doctor_username"), 
                            result.getTimestamp("start_datetime"),
                            result.getTimestamp("end_datetime"), 
                            result.getNString("health_card"), 
                            result.getNString("diagnosis"), 
                            result.getNString("procedure_description"), 
                            result.getDouble("procedure_cost"),
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
    
    private double fetchDoctorVPRevenue(String doctor, String start, String end) {
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
                    append(" 23:59:59' AND procedure_cost > '0.0'").
                    toString();
             
            ResultSet result = stmt.executeQuery(query);

            while (result.next())
            {             
                cost += result.getDouble(1);
            }            
            
            query = new StringBuilder().
                    append("SELECT COUNT(*) FROM Visit WHERE doctor_username = '").
                    append(doctor).
                    append("' AND deleted_datetime = '0000-00-00 00:00:00' AND start_datetime >= '").
                    append(start).
                    append(" 00:00:00").
                    append("' AND start_datetime <= '").
                    append(end).
                    append(" 23:59:59'").
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
                visits.add(
                        new Visit(
                            result.getNString("doctor_username"), 
                            result.getTimestamp("start_datetime"),
                            result.getTimestamp("end_datetime"), 
                            result.getNString("health_card"), 
                            result.getNString("diagnosis"), 
                            result.getNString("procedure_description"), 
                            result.getDouble("procedure_cost"),
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
