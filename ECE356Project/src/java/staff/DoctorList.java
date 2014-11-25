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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Staff;
import patient.PatientMain;

/**
 *
 * @author Tomasz
 */
public class DoctorList extends HttpServlet {

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
        ArrayList<Staff> list = fetchDoctors();
        session.setAttribute("doctors", list);    
        request.getRequestDispatcher("DoctorList.jsp").forward(request, response);
    }

    
    private ArrayList<Staff> fetchDoctors() {
        ArrayList<Staff> doctors = new ArrayList<Staff>();               
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
           // String query = new StringBuilder().
                 //   append("SELECT * FROM Staff WHERE type = 'DOCTOR' AND deleted_datetime = '0000-00-00 00:00:00'").
                    //toString();
            
            ResultSet result = stmt.executeQuery("SELECT * FROM Staff WHERE type = 'DOCTOR'");

            while (result.next())
            {             
                doctors.add(
                        new Staff(
                            result.getNString ("username"),
                            result.getNString ("type"),
                            result.getTimestamp ("created_datetime"),
                            result.getTimestamp ("deleted_datetime")
                        ));
            }     
            con.close();          
        }
        catch(Exception e) 
        {
            return doctors;
        }       
        return doctors;
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
