/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package staff;

import databaseTools.Constants;
import ece356.UserData;
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
import javax.servlet.http.HttpSession;
import models.DoctorData;
import models.Patient;
import models.Visit;
import patient.PatientMain;

/**
 *
 * @author stuart
 */
public class DoctorMain extends HttpServlet {

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
        
        //If user is logged in
        if(request.getSession().getAttribute("userData") == null || !((UserData)request.getSession().getAttribute("userData")).getUserType().equals("staff"))
        {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        
        if(request.getParameter("visits") != null) // Forward to the visitation page
        {
            request.getRequestDispatcher("VisitationRecords").forward(request, response);
            return;
        }
               
        String username = ((UserData)request.getSession().getAttribute("userData")).getUsername();
        DoctorData doctorData = new DoctorData();
        
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            String query = new StringBuilder().
                    append("SELECT health_card FROM Patient WHERE default_doctor_username = '").
                    append(username).
                    append("' AND deleted_datetime = '0000-00-00 00:00:00'").
                    toString();
            
            ResultSet result = stmt.executeQuery(query);
            
            while (result.next())
            {
                doctorData.addPatient(PatientMain.retrievePatient(result.getNString("health_card")));
            }
            
            session.setAttribute("doctorData", doctorData);
            
            con.close();
            
        }
        catch(Exception e) 
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
